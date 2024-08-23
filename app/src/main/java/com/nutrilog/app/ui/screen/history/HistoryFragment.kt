package com.nutrilog.app.ui.screen.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.material.datepicker.MaterialDatePicker
import com.nutrilog.app.R
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionOption
import com.nutrilog.app.databinding.FragmentHistoryBinding
import com.nutrilog.app.core.ui.DatesAdapter
import com.nutrilog.app.core.ui.HistoryAdapter
import com.nutrilog.app.ui.base.BaseFragment
import com.nutrilog.app.ui.component.CenterSmoothScroller
import com.nutrilog.app.ui.component.bottomSheet.nutrition.NutritionBottomSheet
import com.nutrilog.app.utils.helpers.DayInfo
import com.nutrilog.app.utils.helpers.capitalizeWords
import com.nutrilog.app.utils.helpers.convertListToNutritionLevel
import com.nutrilog.app.utils.helpers.getCalender
import com.nutrilog.app.utils.helpers.getDaysInMonth
import com.nutrilog.app.utils.helpers.getTimeInMillis
import com.nutrilog.app.utils.helpers.getTimeToDate
import com.nutrilog.app.utils.helpers.gone
import com.nutrilog.app.utils.helpers.observe
import com.nutrilog.app.utils.helpers.roundToDecimalPlaces
import com.nutrilog.app.utils.helpers.setMonth
import com.nutrilog.app.utils.helpers.show
import com.nutrilog.app.utils.helpers.showSnackBar
import com.nutrilog.app.utils.helpers.sortNutritionByCreatedAtDescending
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    private lateinit var dates: List<DayInfo>
    private var dateMilliseconds: Long = MaterialDatePicker.todayInUtcMilliseconds()

    private val historyViewModel: HistoryViewModel by viewModel()

    private val listDatesAdapter by lazy {
        DatesAdapter { date -> updateActiveDate(date.dayOfMonth) }
    }

    private val listHistoryAdapter by lazy { HistoryAdapter { nutrition -> showDetail(nutrition) } }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding =
        FragmentHistoryBinding::inflate

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        initChart()
        initData()
        initObserve()
        initActions()
    }

    private fun initRecyclerView() {
        binding.apply {
            rvDates.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = listDatesAdapter
                isNestedScrollingEnabled = false
            }

            rvHistory.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = listHistoryAdapter
                isNestedScrollingEnabled = false
            }
        }
    }

    private fun initActions() {
        binding.apply {
            btnCalender.setOnClickListener {
                showDialogCalender()
            }

            selectMonth.setOnSpinnerItemSelectedListener<String> { oldIndex, _, newIndex, _ ->
                if (oldIndex != newIndex) {
                    historyViewModel.setMonth(newIndex)
                }
            }
        }
    }

    private fun initData() {
        setDate()
    }

    private fun initObserve() {
        observe(historyViewModel.currentFullDate) {
            val (date, month, year) = it

            getDays(month, year)
            binding.selectMonth.setMonth(month)

            val dateNow =
                if (date > dates.size) {
                    updateActiveDate(1)
                    1
                } else {
                    date
                }
            listDatesAdapter.updateActiveDay(dateNow)
            dateMilliseconds = getTimeInMillis(dateNow + 1, month, year)
            smoothScrollToActiveItem(dateNow - 1)

            val selectedDate = getTimeToDate(dateNow, month, year)
            observe(historyViewModel.fetchNutrients(selectedDate), ::onListHistoryResult)
        }
    }

    private fun onListHistoryResult(result: ResultState<List<Nutrition>>) {
        when (result) {
            is ResultState.Loading -> showLoading(true)
            is ResultState.Success -> {
                showLoading(false)
                if (result.data.isEmpty()) {
                    showEmpty(true)
                } else {
                    showEmpty(false)
                    val data = sortNutritionByCreatedAtDescending(result.data)
                    listHistoryAdapter.setHistoryData(data)
                    setChart(data)
                }
            }
            is ResultState.Error -> {
                showLoading(false)
                showEmpty(true)
                binding.root.showSnackBar(result.message)
            }
        }
    }

    private fun initChart() {
        val initSeries =
            NutritionOption.entries.map { option ->
                AASeriesElement()
                    .name(option.label.capitalizeWords())
                    .data(arrayOf(0.0))
            }

        val chartModel =
            AAChartModel()
                .chartType(AAChartType.Column)
                .title(getString(R.string.chart_title))
                .subtitle(getString(R.string.chart_subtitle))
                .dataLabelsEnabled(false)
                .borderRadius(8f)
                .markerRadius(4f)
                .categories(
                    arrayOf(
                        getString(
                            R.string.category_chart_total,
                        ),
                    ),
                )
                .animationType(AAChartAnimationType.EaseInCirc)
                .series(initSeries.toTypedArray())

        binding.chartHistory.aa_drawChartWithChartModel(chartModel)
    }

    private fun setChart(data: List<Nutrition>) {
        val nutritionLevels = convertListToNutritionLevel(data)
        val listSeriesElement =
            nutritionLevels.map { (option, value) ->
                val label =
                    when (option.label) {
                        "Calories" -> getString(R.string.label_nutrition_calories)
                        "Protein" -> getString(R.string.label_nutrition_protein)
                        "Fat" -> getString(R.string.label_nutrition_fat)
                        else -> getString(R.string.label_nutrition_carbs)
                    }
                val valueFormat = value.roundToDecimalPlaces(1)
                AASeriesElement()
                    .name(label)
                    .step(true)
                    .data(arrayOf(valueFormat))
            }.toTypedArray()

        binding.chartHistory.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
            listSeriesElement,
            true,
        )
    }

    private fun smoothScrollToActiveItem(position: Int) {
        binding.rvDates.post {
            val layoutManager = binding.rvDates.layoutManager as? LinearLayoutManager
            layoutManager?.let {
                val smoothScroller =
                    CenterSmoothScroller(requireContext()).apply {
                        targetPosition = position
                    }
                it.startSmoothScroll(smoothScroller)
            }
        }
    }

    private fun showDialogCalender() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(dateMilliseconds)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            setDate(it)
        }

        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun updateActiveDate(newDate: Int) {
        historyViewModel.setDate(newDate)
    }

    private fun setDate(date: Long? = null) {
        val dateObject = date?.let { Date(it) } ?: Date()
        val (day, month, year) = dateObject.getCalender(locale)
        historyViewModel.setFullDate(day, month, year)
    }

    private fun getDays(
        month: Int,
        year: Int,
    ) {
        dates = getDaysInMonth(month, year)
        listDatesAdapter.listDates = dates
    }

    private fun showDetail(nutrition: Nutrition) {
        val bottomSheet = NutritionBottomSheet.newInstance(nutrition, false)
        bottomSheet.show(childFragmentManager, NutritionBottomSheet.TAG)
    }

    private fun showEmpty(isEmpty: Boolean) {
        binding.apply {
            emptyLayout.root.apply {
                if (isEmpty) show() else gone()
            }
            rvHistory.apply {
                if (isEmpty) gone() else show()
            }
            layoutChartHistory.apply {
                if (isEmpty) gone() else show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLayout.root.apply {
            if (isLoading) show() else gone()
        }
    }
}
