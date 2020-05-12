package com.example.chess_time

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_item.view.*
import java.util.*

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

    // Member variable for forwarding Timer objects in implementation
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        val currentTimer: Timer = getItem(position)

        holder.textViewMatchTitle.text = currentTimer.title
        holder.textViewWhiteDuration.text = pretty_print(currentTimer.durationWhite)
        holder.textViewBlackDuration.text = pretty_print(currentTimer.durationBlack)
        holder.textViewWhiteIncrement.text = pretty_print(currentTimer.incrementWhite)
        holder.textViewBlackIncrement.text = pretty_print(currentTimer.incrementBlack)
        holder.textViewWhiteDelay.text = pretty_print(currentTimer.delayWhite)
        holder.textViewBlackDelay.text = pretty_print(currentTimer.delayBlack)

        var duration = currentTimer.durationWhite

        if (300000 <= duration && duration <= 600000) holder.matchTypeIcon.setImageResource(R.drawable.blitz_24dp)
        if (300000 > duration) holder.matchTypeIcon.setImageResource(R.drawable.bullet_24dp)
        if (duration > 600000 && duration < 3600000) holder.matchTypeIcon.setImageResource(R.drawable.rapid_24dp)
        if (duration >= 3600000) holder.matchTypeIcon.setImageResource(R.drawable.classical_24dp)
    }

    // Returns Timer object at the specified position
    fun getTimerAt(position: Int): Timer {
        return getItem(position)
    }


    // Holds a reference to each item in a recycler view
    inner class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Constructor assigns view listener we set up below
        // Listener has access to interface which forwards a timer object
        // We use adapter position to distinguish which card view was clicked
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
        var textViewWhiteDuration: TextView = itemView.white_duration_text_view
        var textViewBlackDuration: TextView = itemView.black_duration_text_view
        var textViewWhiteIncrement: TextView = itemView.white_increment_text_view
        var textViewBlackIncrement: TextView = itemView.black_increment_text_view
        var textViewWhiteDelay: TextView = itemView.white_delay_text_view
        var textViewBlackDelay: TextView = itemView.black_delay_text_view
        var matchTypeIcon: ImageView = itemView.match_type_image_view
    }

    // Allows us to provide a timer object and forward data to the implementation in main
    interface OnItemClickListener {
        fun onItemClick(timer: Timer)
    }

    // References interface, giving listener access to OnItemClick, therefore access in implementation
    // We 'catch' the click here
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun pretty_print (time: Int): String {
        val hours = time / 3600000
        var minutes = time / 60000
        val seconds = (time/1000) % 60
        val formatted_time: String

        if (hours >= 1) {
            if (minutes >= 60) minutes = minutes % 60
            formatted_time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else if (minutes > 0){
            formatted_time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            formatted_time = String.format(Locale.getDefault(), "%02d"+"s", seconds)
        }

        return formatted_time
    }
}