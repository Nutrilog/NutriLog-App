package com.nutrilog.app.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.databinding.HistoryCardBinding
import com.nutrilog.app.utils.helpers.capitalizeWords
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(
    private val onItemClickCallback: (Nutrition) -> Unit,
) : RecyclerView.Adapter<HistoryAdapter.HistoryDataViewHolder>() {
    private var historyDataList: List<Nutrition> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryDataViewHolder {
        val binding =
            HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryDataViewHolder(binding)
    }

    override fun getItemCount(): Int = historyDataList.size

    override fun onBindViewHolder(
        holder: HistoryDataViewHolder,
        position: Int,
    ) {
        val data = historyDataList[position]
        holder.bind(data)
    }

    fun setHistoryData(data: List<Nutrition>) {
        val newList = data.toList()
        val diffCallback = HistoryDiffCallback(historyDataList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        historyDataList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryDataViewHolder(
        val binding: HistoryCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nutrition: Nutrition) {
            with(binding) {
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                hourTV.text = dateFormat.format(nutrition.createdAt)

                foodNameTV.text = nutrition.foodName.capitalizeWords()

                root.setOnClickListener {
                    onItemClickCallback(nutrition)
                }
            }
        }
    }

    private class HistoryDiffCallback(
        private val oldList: List<Nutrition>,
        private val newList: List<Nutrition>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean {
            return oldList == newList
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }
}
