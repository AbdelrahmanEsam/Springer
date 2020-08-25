package com.first.springer.proccessactivites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.first.springer.ModelResults
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.common.MyApplication
import com.first.springer.databinding.FragmentResultsBinding
import com.first.springer.room.DatabaseViewModel
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Results : Basefragment() {
    private lateinit var binding: FragmentResultsBinding
    private lateinit var ViewModel: DatabaseViewModel
    private var handler: Handler? = null
    private var progress = 0
    private var percentprogress = 0
    private var maxprogress = 0
    private var viewprogress = 0.0
    private var viewpercentprogress = 0.0
    private var viewmaxprogress = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        ViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
        val view = binding.root
        binding.lifecycleOwner = this
        init()
        if ((activity as Fragmentholder).viewModel.CompressionDistancefinal.value!!.toDouble() >= (activity as Fragmentholder).viewModel.maxprogressfinal.value!!.toDouble()
        ) {
            Longtoasterror("The spring will be break down, we recommend decrease compression distance.")
        }
        binding.cancel.setOnClickListener {
            activity!!.viewModelStore.clear()
            (context as Fragmentholder).supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            (context as Fragmentholder).supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Mold()).addToBackStack(null).commit()
        }
        if ((activity as Fragmentholder).viewModel.ResultUri.value != null) {
            binding.Moldimageoneresult.setImageURI(Uri.parse((activity as Fragmentholder).viewModel.ResultUri.value))
        }
        binding.save.setOnClickListener { rx() }
        Thread {
            if (viewprogress <= 100) {
                var i = 0
                while (i < viewprogress) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        progress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else if (viewprogress > 100 && viewprogress < 1000) {
                var i = 0
                while (i < viewprogress / 10) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        progress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else {
                var i = 0
                while (i < viewprogress / 100) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        progress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            }
        }.start()
        Thread {
            var i = 0
            while (i < viewpercentprogress) {
                try {
                    Thread.sleep(50)
                    handler!!.sendEmptyMessage(0)
                    percentprogress++
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                i++
            }
        }.start()
        Thread {
            if (viewmaxprogress < 100) {
                var i = 0
                while (i < viewmaxprogress) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        maxprogress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else if (viewmaxprogress >= 100 && viewmaxprogress < 1000) {
                var i = 0
                while (i < viewmaxprogress / 10) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        maxprogress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else {
                var i = 0
                while (i < viewmaxprogress / 1000) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        maxprogress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            }
        }.start()
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(inputMessage: Message) {
                if (progress <= viewprogress) {
                    binding.progress.setDonut_progress(progress.toString())
                    if (viewprogress < 100) {
                        binding.progress.text = progress.toString() + "mm"
                    } else if (viewprogress >= 100 && viewprogress < 1000) {
                        binding.progress.text = progress.toString() + "cm"
                    } else {
                        binding.progress.text = progress.toString() + "M"
                    }
                }
                if (percentprogress <= viewpercentprogress) {
                    if (percentprogress <= 100) {
                        binding.percentprogress.setDonut_progress(percentprogress.toString())
                    }
                    binding.percentprogress.text = "$percentprogress%"
                }
                if (maxprogress <= viewmaxprogress) {
                    binding.maxprogress.setDonut_progress(maxprogress.toString())
                    if (viewmaxprogress < 100) {
                        binding.maxprogress.text = maxprogress.toString() + "mm"
                    } else if (viewmaxprogress >= 100 && viewmaxprogress < 1000) {
                        binding.maxprogress.text = maxprogress.toString() + "cm"
                    } else {
                        binding.maxprogress.text = maxprogress.toString() + "M"
                    }
                }
            }
        }
        return view
    }

    private fun save(bitmapImage: Bitmap) {
        val images = File(context!!.getExternalFilesDir(null)!!.absolutePath + "newprivate")
        if (!images.exists()) {
            images.mkdir()
        }
        val file = File(images, "." + System.currentTimeMillis() + ".jpg")
        try {
            val out = FileOutputStream(file, false)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        toDb(file.absoluteFile.toString())
    }

    private fun init() {
        viewpercentprogress = (activity as Fragmentholder).viewModel.percentprogressfinal.value!!.toDouble()
        viewmaxprogress = (activity as Fragmentholder).viewModel.maxprogressfinal.value!!.toDouble()
        viewprogress = (activity as Fragmentholder).viewModel.CompressionDistancefinal.value!!.toDouble()
        binding.maxcomdesresultoneresult.text = (activity as Fragmentholder).viewModel.maxprogressfinal.value.toString() + " mm"
        binding.compercentresultoneresult.text = (activity as Fragmentholder).viewModel.percentprogressfinal.value!!.toDouble().toString() + " %"
        binding.comdesresultoneresult.text = (activity as Fragmentholder).viewModel.CompressionDistancefinal.value.toString() + " mm"
        binding.codeoneresult.text = "Code :" + (activity as Fragmentholder).viewModel.MoldOrDieCode.value
        binding.name.text = (activity as Fragmentholder).viewModel.MoldOrDie.value.toString() + " Model : " + (activity as Fragmentholder).viewModel.MoldOrDieName.value
    }

    private fun sharedprefernces(): Int {
        val sharedPreferences = context!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("first", 0)
    }

    private fun rx() {
        if (MyApplication.available) {
            var first = sharedprefernces()
            if (first <= 50) {
                ++first
                val prefs = context!!.getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("first", first)
                editor.apply()
                Observable.create { emitter: ObservableEmitter<Any?>? ->
                    val bitmap = (binding.Moldimageoneresult.drawable as BitmapDrawable).bitmap
                    save(bitmap)
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
                Longtoastsuccess("Saved")
            } else {
                Longtoasterror("you have more than 50 results please delete one")
            }
        } else {
            Longtoasterror("No internet connection")
        }
    }

    private fun toDb(path: String?) {
        if (path != null) {
            val results = Resultsforfire()
            results.percentprogressfinal = (activity as Fragmentholder).viewModel.percentprogressfinal.value!!.toDouble()
            results.maxprogressfinal = (activity as Fragmentholder).viewModel.maxprogressfinal.value!!.toDouble()
            results.compressionDistancefinal = (activity as Fragmentholder).viewModel.CompressionDistancefinal.value!!.toDouble()
            toFirebase(results)
            val result = ModelResults()
            result.image = path
            val dateString = DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().time)
            result.history = dateString
            result.color = (activity as Fragmentholder).viewModel.Color.value
            result.moldorDieCode = (activity as Fragmentholder).viewModel.MoldOrDieCode.value!!.toInt()
            result.uri = (activity as Fragmentholder).viewModel.ResultUri.value
            result.moldorDie = (activity as Fragmentholder).viewModel.MoldOrDie.value
            result.moldorDieName = (activity as Fragmentholder).viewModel.MoldOrDieName.value
            result.percentprogressfinal = (activity as Fragmentholder).viewModel.percentprogressfinal.value!!.toDouble()
            result.maxprogressfinal = (activity as Fragmentholder).viewModel.maxprogressfinal.value!!.toDouble()
            result.compressionDistancefinal = (activity as Fragmentholder).viewModel.CompressionDistancefinal.value!!.toDouble()
            ViewModel.insert(result)
        }
    }

    private fun toFirebase(results: Resultsforfire) {
        FirebaseDatabase.getInstance().getReference("results")
            .child(mAuth!!.currentUser!!.uid + System.currentTimeMillis()).setValue(results)
    }


}