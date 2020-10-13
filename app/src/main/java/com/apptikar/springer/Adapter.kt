package com.apptikar.springer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.apptikar.springer.proccessactivites.FragmentHolder
import java.util.*

class Adapter(var activity: FragmentActivity?) : RecyclerView.Adapter<ViewHolder>() {

    var data: MutableList<ModelResults>? = ArrayList()
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.resultsrecycler
        } else {
            R.layout.recyclerdesign
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = if (viewType == R.layout.resultsrecycler) {
            LayoutInflater.from(parent.context).inflate(R.layout.resultsrecycler, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerdesign, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.resultsrecycler -> holder.number!!.text = data!!.size.toString()
            R.layout.recyclerdesign -> {
                val modelResults = data!![position - 1]
                holder.moldOrDieMame!!.text = modelResults.moldorDie + " Name : " + modelResults.moldorDieName
                holder.compressionpercentresult!!.text = modelResults.percentprogressfinal!!.toInt().toString() + " %"
                holder.comperssiondestenceresult!!.text = modelResults.compressionDistancefinal.toString() + " mm"
                holder.maxdistenceresult!!.text = modelResults.maxprogressfinal.toString() + " mm"
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
                    (activity as FragmentHolder).viewModel.modelResults.value = data!![position - 1]
                    (activity as FragmentHolder).nav.navigate(R.id.action_allresults_to_resultshow)
                }
            }
        }
    }

    fun remove(position: Int) {
        data!!.removeAt(position - 1)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data!!.size + 1)
        notifyItemChanged(0)
    }

    fun adapterSetData(data: MutableList<ModelResults>?) {
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

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var number: TextView? = itemView.findViewById(R.id.recyclernumber)
    var comperssiondestenceresult: TextView? = itemView.findViewById(R.id.comprissiondistenceallresultsresult)
    var compressionpercentresult: TextView? = itemView.findViewById(R.id.comprissionpercentallresultsresult)
    var maxdistenceresult: TextView? = itemView.findViewById(R.id.maxcomprissiondistenceallresultsresult)
    var date: TextView? = itemView.findViewById(R.id.date)
    var moldOrDieMame: TextView? = itemView.findViewById(R.id.moldordienameallresults)
    var color: ImageView? = itemView.findViewById(R.id.colorimage)
    var bind: ConstraintLayout? = itemView.findViewById(R.id.holder)

}