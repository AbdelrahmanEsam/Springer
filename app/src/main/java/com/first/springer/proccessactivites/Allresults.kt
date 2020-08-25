package com.first.springer.proccessactivites
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.first.springer.Adapter
import com.first.springer.ItemDecoration
import com.first.springer.ModelResults
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.common.MyApplication
import com.first.springer.room.DatabaseViewModel
import java.io.File
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Allresults : Basefragment() {
    private lateinit  var recyclerView: RecyclerView
   private lateinit  var ViewModel: DatabaseViewModel
   private lateinit var recycleradapter: Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_allresults, container, false)
        ViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
        (activity as Fragmentholder).binding.toolbarTitle.text =
            "All Results"
        if (!MyApplication.swipetoast) {
            MyApplication.swipetoast = true
            Longtoast("Swipe to delete,Swipe the address to delete all.")
        }
        recyclerView = view.findViewById(R.id.recycler)
        recycler()
        ViewModel.getall()!!.observe(viewLifecycleOwner, Observer { modelResults: List<ModelResults?>? ->
                if ((activity as Fragmentholder).viewModel.notify.value == "no") {
                    (activity as Fragmentholder).viewModel.notify.setValue("yes")
                    recycleradapter.setdata(modelResults as MutableList<ModelResults>?)
                }
            })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return false }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val resultAt = recycleradapter.getResultAt(viewHolder.adapterPosition)
                var first: Int
                if (viewHolder.adapterPosition != 0) {
                    val file = File(resultAt!!.image)
                    file.delete()
                    ViewModel.delete(resultAt)
                    val prefs = activity!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                    first = prefs.getInt("first", 0)
                    val editor = prefs.edit()
                    editor.putInt("first", --first)
                    editor.apply()
                    recycleradapter.remove(viewHolder.adapterPosition)
                } else {
                    (activity as Fragmentholder).viewModel.notify.value =
                        "no"
                    ViewModel.deleteall()
                    val prefs = activity!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("first", 0)
                    editor.apply()
                    ViewModel.getall()!!.observe(activity!!,
                        Observer { modelResults: List<ModelResults?>? ->
                            for (i in modelResults!!.indices) {
                                val file = File(modelResults[i]!!.image)
                                file.delete()
                            }
                        })
                    recycleradapter.notifyDataSetChanged()
                }
            }
        }).attachToRecyclerView(recyclerView)
        return view
    }

    override fun onDestroy() {
        (activity as Fragmentholder).viewModel.notify.value = "no"
        super.onDestroy()
    }

    private fun recycler() {
        recycleradapter = Adapter(context)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recycleradapter
        val itemDecorator: RecyclerView.ItemDecoration = ItemDecoration(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }
}