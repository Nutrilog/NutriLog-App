package com.nutrilog.app.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.nutrilog.app.R
import com.nutrilog.app.ml.FoodClassifierModel
import com.nutrilog.app.utils.ImageProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.model.Model
import timber.log.Timber
import kotlin.coroutines.coroutineContext

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?,
) {
    private var listLabel: List<String>? = null
    private var model: FoodClassifierModel? = null

    init {
        initLabel()
    }

    private suspend fun setupModelIfNeeded() {
        if (model == null) {
            try {
                val compatList = CompatibilityList()
                val options =
                    if (compatList.isDelegateSupportedOnThisDevice) {
                        Model.Options.Builder().setDevice(Model.Device.GPU).build()
                    } else {
                        Model.Options.Builder().setNumThreads(4).build()
                    }

                model = FoodClassifierModel.newInstance(context, options)
            } catch (e: Exception) {
                withContext(coroutineContext) {
                    classifierListener?.onError(context.getString(R.string.message_error_analyze))
                }
                Timber.tag(TAG).e(e.message.toString())
            }
        }
    }

    private fun initLabel() {
        val fileLabel = "labels.txt"
        val inputString = context.assets.open(fileLabel).bufferedReader().use { it.readText() }
        listLabel = inputString.split("\n")
    }

    suspend fun classifyStaticImage(imageUri: Uri) {
        withContext(Dispatchers.Default) {
            setupModelIfNeeded()

            val imageProcessor = ImageProcessor()

            val contentResolver = context.contentResolver
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
                val tensorImage = imageProcessor.preprocess(bitmap)
                val outputs = model?.process(tensorImage)
                val outputBuffer = outputs?.outputFeature0AsTensorBuffer
                val valueMax = outputBuffer?.let { getMax(it.floatArray) }

                val label = listLabel?.get(valueMax ?: 0)

                classifierListener?.onResults(
                    ClassifierResult(
                        label ?: "Unknown",
                        valueMax ?: -1,
                    ),
                )
            }
        }
    }

    fun closeModel() {
        model?.close()
    }

    private fun getMax(arr: FloatArray): Int {
        var max = arr[0]
        var maxIndex = 0
        for (i in arr.indices) {
            if (arr[i] > max) {
                max = arr[i]
                maxIndex = i
            }
        }

        return maxIndex
    }

    interface ClassifierListener {
        fun onError(error: String)

        suspend fun onResults(results: ClassifierResult?)
    }

    data class ClassifierResult(
        val label: String,
        val index: Int,
    )

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
