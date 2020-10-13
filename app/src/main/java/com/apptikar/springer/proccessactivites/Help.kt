package com.apptikar.springer.proccessactivites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apptikar.springer.Helpadapter
import com.apptikar.springer.R
import java.util.*

class Help : Fragment() {
    private lateinit var adapter: Helpadapter
    private lateinit var helprecycler: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        (activity as FragmentHolder).binding.toolbarTitle.text = "Help"
        helprecycler = view.findViewById(R.id.recycler)
        recycler()
        return view
    }

    private fun recycler() {
        val data: MutableList<String> = ArrayList()
        val data2: MutableList<String> = ArrayList()
        data.add("What is Springer ?")
        data.add("What Is Spring Spaces ?")
        data.add("What is spring Housing ?")
        data.add("What is Ejection Stroke ?")
        data.add("What is Mold Code ?")
        data2.add("\n\nSpringer is app that help you to choose proper spring you need for Molds, Dies ....etc\nSpring Measure help you to choose proper spring you need, create data base and share results with others.\n")
        data2.add(" \n\nSpring Spaces is gaps between spring coils as shown in photo attached.\n")
        data2.add("\n\nSpring Housing is the place you will assemble the spring in it as shown in photo attached.\n")
        data2.add("\n\nEjection Stroke is distance that moved by ejection pins to eject product outside mold , Die ... etc.\n")
        data2.add("\n\nMold Code is unique code using in data base for every mold , die ...etc to make it easy for search.\n")
        data2.add("\n\nIn order to reset account password you need to enter your email which have you registered with confirm it with code you received in your email account\n")
        adapter = Helpadapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        helprecycler.layoutManager = linearLayoutManager
        adapter.setdata(data, data2)
        helprecycler.adapter = adapter
    }
}