package com.apptikar.springer
import android.animation.ObjectAnimator
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Helpadapter : RecyclerView.Adapter<HelpViewHolder>() {
    var booleans: MutableList<Boolean> = ArrayList()
    var data: List<String> = ArrayList()
    var data2: List<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helpadapter, parent, false)
        return HelpViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        val text = data[position]
        val text2 = data2[position]
        val word: Spannable = SpannableString(text)
        word.setSpan(ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.firsttext.text = word
        val wordTwo: Spannable = SpannableString(text2)
        wordTwo.setSpan(ForegroundColorSpan(Color.rgb(0, 145, 124)), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.firsttext.append(wordTwo)
        holder.down.setOnClickListener { v: View? ->
            if (!booleans[position]) {
                val animation = ObjectAnimator.ofInt(holder.firsttext, "maxLines", holder.firsttext.lineCount)
                animation.setDuration(50).start()
                holder.firsttext.setPadding(0, 3, 0, 0)
                booleans[position] = true
                val imageViewObjectAnimator = ObjectAnimator.ofFloat(holder.down, "rotation", 0f, 180f)
                imageViewObjectAnimator.duration = 200
                imageViewObjectAnimator.start()
            } else {
                val animation = ObjectAnimator.ofInt(holder.firsttext, "maxLines", 1)
                animation.setDuration(50).start()
                holder.firsttext.setPadding(0, 0, 0, 0)
                val imageViewObjectAnimator = ObjectAnimator.ofFloat(holder.down, "rotation", 0f, 360f)
                imageViewObjectAnimator.duration = 200
                imageViewObjectAnimator.start()
                booleans[position] = false
            }
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    fun setdata(data: List<String>, data2: List<String>) {
        this.data = data
        this.data2 = data2
        for (i in data.indices) {
            booleans.add(false)
        }
        notifyDataSetChanged()
    }
}
 class HelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var firsttext: TextView = itemView.findViewById(R.id.firsttext)
    var down: ImageView = itemView.findViewById(R.id.down)

}