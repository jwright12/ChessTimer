package com.example.chess_time

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_item.view.*

class TimerAdapter : ListAdapter<Timer, TimerAdapter.TimerHolder> (){

    companion object {

    }

    private var listener: AdapterView.OnItemClickListener? = null

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        val currentTimer: Timer = getItem(position)

        holder.textViewMatchTitle.text = currentTimer.

    }


    fun getTimerAt(position: Int) : Timer {
        return getItem(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerHolder(itemView)
    }


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