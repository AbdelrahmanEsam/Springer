package com.first.springer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.first.springer.proccessactivites.Fragmentholder
import com.first.springer.proccessactivites.Resultshow
import java.util.*

class Adapter(var context: Context?) : RecyclerView.Adapter<Viewholder>() {
    var data: MutableList<ModelResults>? = ArrayList()
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.resultsrecycler
        } else {
            R.layout.recyclerdesign
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view: View = if (viewType == R.layout.resultsrecycler) {
            LayoutInflater.from(parent.context).inflate(R.layout.resultsrecycler, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerdesign, parent, false)
        }
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        when (holder.itemViewType) {
            R.layout.resultsrecycler -> holder.number!!.text = data!!.size.toString()
            R.layout.recyclerdesign -> {
                val modelResults = data!![position - 1]
                holder.Moldordiename!!.text = modelResults.moldorDie + " Name : " + modelResults.moldorDieName
                holder.compressionpercentresult!!.text = modelResults.getPercentprogressfinal().toInt().toString() + "%"
                holder.comperssiondestenceresult!!.text = modelResults.getCompressionDistancefinal().toString() + "cm"
                holder.maxdistenceresult!!.text = modelResults.getMaxprogressfinal().toString() + " cm"
                holder.date!!.text = modelResults.history
                when (modelResults.color) {
                    "orange" -> holder.color!!.setImageResource(R.drawable.orangering)
                    "red" -> holder.color!!.setImageResource(R.drawable.redring)
                    "blue" -> holder.color!!.setImageResource(R.drawable.bluering)
                    "yellow" -> holder.color!!.setImageResource(R.drawable.yellowring)
                    "green" -> holder.color!!.setImageResource(R.drawable.greenring)
                    "gray" -> holder.color!!.setImageResource(R.drawable.gray)
                    "white" -> holder.color!!.setImageResource(R.drawable.whitering)
                    "lightgreen" -> holder.color!!.setImageResource(R.drawable.lightgreenring)
                }
                holder.itemView.setOnClickListener {
                    (context as Fragmentholder).modelResults = data!![position - 1]
                    (context as Fragmentholder).supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Resultshow()).addToBackStack(null).commit()
                }
            }
        }
    }

    fun remove(postion: Int) {
        data!!.removeAt(postion - 1)
        notifyItemRemoved(postion)
        notifyItemRangeChanged(postion, data!!.size + 1)
        notifyItemChanged(0)
    }

    fun setdata(data: MutableList<ModelResults>?) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getResultAt(position: Int): ModelResults? {
        return if (position >= 1) {
            data!![position - 1]
        } else {
         null
        }
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.size + 1 else 1
    }
}

class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var number: TextView? = itemView.findViewById(R.id.recyclernumber)
    var comperssiondestenceresult: TextView? = itemView.findViewById(R.id.comprissiondistenceallresultsresult)
    var compressionpercentresult: TextView? = itemView.findViewById(R.id.comprissionpercentallresultsresult)
    var maxdistenceresult: TextView? = itemView.findViewById(R.id.maxcomprissiondistenceallresultsresult)
    var date: TextView? = itemView.findViewById(R.id.date)
    var Moldordiename: TextView? = itemView.findViewById(R.id.moldordienameallresults)
    var color: ImageView? = itemView.findViewById(R.id.colorimage)
    var bind: ConstraintLayout? = itemView.findViewById(R.id.holder)

}