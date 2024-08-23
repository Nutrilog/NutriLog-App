package com.nutrilog.app.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nutrilog.app.R
import com.nutrilog.app.databinding.DayCardBinding
import com.nutrilog.app.utils.helpers.DayInfo
import kotlin.properties.Delegates

class DatesAdapter(
    private val onItemClickCallback: (DayInfo) -> Unit,
) : RecyclerView.Adapter<DatesAdapter.ListViewHolder>() {
    private var activeDay: Int? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val binding = DayCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
    ) {
        holder.bind(listDates[position])
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as? Bundle
            payload?.let {
                if (it.containsKey("KEY_ACTIVE_DAY")) {
                    holder.bind(listDates[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = listDates.size

    inner class ListViewHolder(private val binding: DayCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: DayInfo) {
            with(binding) {
                tvDay.text = date.dayOfWeek.substring(0, 3)
                tvDate.text = date.dayOfMonth.toString()
                updateBackground(activeDay == date.dayOfMonth)

                root.apply {
                    setOnClickListener {
                        onItemClickCallback.invoke(date)
                    }
                }
            }
        }

        fun updateBackground(isActive: Boolean) {
            with(binding) {
                if (isActive) {
                    setDayActive()
                } else {
                    setDayInactive()
                }
            }
        }
    }

    private fun DayCardBinding.setDayActive() {
        root.setBackgroundResource(R.drawable.bg_day_card_active)
        tvDay.setTextColor(root.context.getColor(R.color.md_theme_onPrimary))
        tvDate.setTextColor(root.context.getColor(R.color.md_theme_onPrimary))
    }

    private fun DayCardBinding.setDayInactive() {
        root.setBackgroundResource(R.drawable.bg_day_card)
        tvDay.setTextColor(root.context.getColor(R.color.md_theme_onBackground))
        tvDate.setTextColor(root.context.getColor(R.color.md_theme_onBackground))
    }

    fun updateActiveDay(newActiveDay: Int) {
        val oldActiveDay = activeDay
        activeDay = newActiveDay
        notifyActiveDayChanged(oldActiveDay, newActiveDay)
    }

    private fun notifyActiveDayChanged(
        oldActiveDay: Int?,
        newActiveDay: Int,
    ) {
        listDates.forEachIndexed { index, dayInfo ->
            if (dayInfo.dayOfMonth == oldActiveDay || dayInfo.dayOfMonth == newActiveDay) {
                notifyItemChanged(
                    index,
                    Bundle().apply {
                        putBoolean("KEY_ACTIVE_DAY", dayInfo.dayOfMonth == newActiveDay)
                    },
                )
            }
        }
    }

    private fun notifyChanges(
        oldList: List<DayInfo>,
        newList: List<DayInfo>,
    ) {
        val diff =
            DiffUtil.calculateDiff(
                object : DiffUtil.Callback() {
                    override fun areItemsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int,
                    ): Boolean {
                        return oldList[oldItemPosition].dayOfMonth == newList[newItemPosition].dayOfMonth
                    }

                    override fun areContentsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int,
                    ): Boolean {
                        return oldList[oldItemPosition] == newList[newItemPosition]
                    }

                    override fun getChangePayload(
                        oldItemPosition: Int,
                        newItemPosition: Int,
                    ): Any? {
                        val diffBundle = Bundle()
                        if (oldList[oldItemPosition].dayOfMonth == activeDay) {
                            diffBundle.putBoolean(
                                "KEY_ACTIVE_DAY",
                                oldList[oldItemPosition].dayOfMonth == activeDay,
                            )
                        }
                        return if (diffBundle.size() == 0) null else diffBundle
                    }

                    override fun getOldListSize() = oldList.size

                    override fun getNewListSize() = newList.size
                },
            )

        diff.dispatchUpdatesTo(this)
    }

    var listDates: List<DayInfo> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList)
    }
}
