package com.example.chess_time

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_item.view.*

class TimerAdapter : ListAdapter<Timer, TimerAdapter.TimerHolder> (DIFF_CALLBACK){

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Timer>() {
            override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        val currentTimer: Timer = getItem(position)

        holder.textViewMatchTitle.text = currentTimer.title
        holder.textViewMatchType.text = currentTimer.Type
        holder.textViewWhiteDuration.text = currentTimer.durationWhite.toString()
        holder.textViewBlackDuration.text = currentTimer.durationBlack.toString()
        holder.textViewWhiteIncrement.text = currentTimer.incrementWhite.toString()
        holder.textViewBlackIncrement.text = currentTimer.incrementBlack.toString()
        holder.textViewWhiteDelay.text = currentTimer.delayWhite.toString()
        holder.textViewBlackDelay.text = currentTimer.delayBlack.toString()
    }

    fun getTimerAt(position: Int): Timer {
        return getItem(position)
    }


    // Holds a reference to each item in a recycler view
    inner class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        // Grab the attributes from our timer_item.xml file
        var textViewMatchTitle: TextView = itemView.match_title_text_view
        var textViewMatchType: TextView = itemView.match_type_text_view
        var textViewWhiteDuration: TextView = itemView.white_duration_text_view
        var textViewBlackDuration: TextView = itemView.black_duration_text_view
        var textViewWhiteIncrement: TextView = itemView.white_increment_text_view
        var textViewBlackIncrement: TextView = itemView.black_increment_text_view
        var textViewWhiteDelay: TextView = itemView.white_delay_text_view
        var textViewBlackDelay: TextView = itemView.black_delay_text_view

    }

    interface OnItemClickListener {
        fun onItemClick(timer: Timer)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}