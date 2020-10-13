package com.apptikar.springer.proccessactivites
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apptikar.springer.Adapter
import com.apptikar.springer.ItemDecoration
import com.apptikar.springer.ModelResults
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.MyApplication
import java.io.File

/**
 * A simple [Fragment] subclass.
 */


class Allresults : BaseFragment() {
    private lateinit  var recyclerView: RecyclerView
   private lateinit var recycleradapter: Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_allresults, container, false)
        (activity as FragmentHolder).binding.toolbarTitle.text =  "All Results"
        if (!MyApplication.swipeToast) {
            MyApplication.swipeToast = true
            longToast("Swipe to delete,Swipe the address to delete all.")
        }
        recyclerView = view.findViewById(R.id.recycler)
        recycler()
        viewModel.getAll()!!.observe(viewLifecycleOwner, { modelResults: List<ModelResults?>? ->
                if ((activity as FragmentHolder).viewModel.notify.value == "no") {
                    (activity as FragmentHolder).viewModel.notify.value = "yes"
                    recycleradapter.adapterSetData(modelResults as MutableList<ModelResults>?)
                }
            })
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val resultAt = recycleradapter.getResultAt(viewHolder.adapterPosition)
                var first: Int
                if (viewHolder.adapterPosition != 0) {
                    val file = File(resultAt!!.image)
                    file.delete()
                    viewModel.delete(resultAt)
                    val prefs = activity!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                    first = prefs.getInt("first", 0)
                    val editor = prefs.edit()
                    editor.putInt("first", --first)
                    editor.apply()
                    recycleradapter.remove(viewHolder.adapterPosition)
                } else {
                    (activity as FragmentHolder).viewModel.notify.value = "no"
                    viewModel.deleteAll()
                    val prefs = activity!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("first", 0)
                    editor.apply()
                    viewModel.getAll()!!.observe(activity!!,
                        { modelResults: List<ModelResults?>? ->
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



    override fun onPause() {
        (activity as FragmentHolder).viewModel.notify.value = "no"
        super.onPause()
    }
    private fun recycler() {
        recycleradapter = Adapter(activity)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recycleradapter
        val itemDecorator: RecyclerView.ItemDecoration = ItemDecoration(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }
}