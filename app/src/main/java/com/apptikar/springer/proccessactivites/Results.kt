package com.apptikar.springer.proccessactivites
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.apptikar.springer.ModelResults
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.MyApplication
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.FragmentResultsBinding
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.util.*
import kotlin.math.roundToInt



class Results : BaseFragment() {
    private lateinit var binding: FragmentResultsBinding
    private var progress = 0
    private var percentProgress = 0
    private var maxProgress = 0
    private var viewProgress = 0.0
    private var viewPercentProgress = 0.0
    private var viewMaxProgress = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        init()
        if ((activity as FragmentHolder).viewModel.compressionDistanceFinal.value!!.toDouble() >= (activity as FragmentHolder).viewModel.maxProgressFinal.value!!.toDouble()
        ) {
            longToastError("The spring will be break down, we recommend decrease compression distance.")
        }
        binding.cancel.setOnClickListener {
            (activity as FragmentHolder).viewModel.notify.value = "no"
            (activity as FragmentHolder).viewModel.init()
            (activity as FragmentHolder).nav.navigate(R.id.action_results_to_mold)
        }
        if ((activity as FragmentHolder).viewModel.resultUri.value != null) {
            binding.Moldimageoneresult.setImageURI(Uri.parse((activity as FragmentHolder).viewModel.resultUri.value))
        }
        binding.save.setOnClickListener {
            CoroutineScope(Main).launch {
                rx()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress()
        percentProgress()
        maxProgress()
    }

    private  fun progress()
    {



        if (viewProgress < 100) {
            GlobalScope.launch {
                repeat(viewProgress.roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }

            }


        }else if (viewProgress >= 100 && viewProgress < 1000) {
            GlobalScope.launch {
                repeat((viewProgress / 10).roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }

            }
        }else
        {
            GlobalScope.launch {
                repeat((viewProgress / 100).roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }


            }
        }
    }


    private fun updateProgressUI() {
        if (progress <= viewProgress) {
            binding.progress.setDonut_progress(progress.toString())
            if (viewProgress < 100) {
                binding.progress.text = "$progress mm"
            } else if (viewProgress >= 100 && viewProgress < 1000) {
                binding.progress.text = "$progress cm"
            } else {
                binding.progress.text = "$progress M"
            }
        }


    }

    private fun percentProgress()
    {
        GlobalScope.launch {
            repeat(viewPercentProgress.roundToInt())
            {
                percentProgress++
                delay(50)
                updatePercentProgressUI()
            }




        }
    }

    private fun updatePercentProgressUI() {
        if (percentProgress <= viewPercentProgress) {
            if (percentProgress <= 100) {
                binding.percentprogress.setDonut_progress(percentProgress.toString())
            }
            binding.percentprogress.text = "$percentProgress %"
        }
    }

    private fun maxProgress()
    {
        if ( viewMaxProgress < 100) {
            GlobalScope.launch {
                repeat(viewMaxProgress.roundToInt())
                {
                    maxProgress++
                    delay(50)
                    updateMaxProgressUI()
                }

            }
        } else if ( viewMaxProgress >= 100 &&  viewMaxProgress < 1000) {
            GlobalScope.launch {
                repeat(( viewMaxProgress / 10).roundToInt())
                {
                    maxProgress++
                    delay(50)
                    updateMaxProgressUI()
                }

            }
        } else {
            GlobalScope.launch {
                repeat(( viewMaxProgress / 1000).roundToInt())
                {
                    maxProgress++
                    delay(50)
                    updateMaxProgressUI()
                }

            }
        }
    }

    private fun updateMaxProgressUI()
    {
        if (maxProgress <= viewMaxProgress) {
            binding.maxprogress.setDonut_progress(maxProgress.toString())
            if (viewMaxProgress < 100) {
                binding.maxprogress.text = "$maxProgress mm"
            } else if (viewMaxProgress >= 100 && viewMaxProgress < 1000) {
                binding.maxprogress.text = "$maxProgress cm"
            } else {
                binding.maxprogress.text = "$maxProgress M"
            }
        }
    }

    private fun save(bitmapImage: Bitmap) {
        val images = File(requireContext().getExternalFilesDir(null)!!.absolutePath + "newprivate")
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
        progress = 0
        percentProgress = 0
        maxProgress = 0
        viewProgress = 0.0
        viewPercentProgress = 0.0
        viewMaxProgress = 0.0
        viewPercentProgress =(activity as FragmentHolder).viewModel.percentProgressFinal.value!!.toDouble()
        viewMaxProgress = (activity as FragmentHolder).viewModel.maxProgressFinal.value!!.toDouble()
        viewProgress = (activity as FragmentHolder).viewModel.compressionDistanceFinal.value!!.toDouble()
        binding.maxcomdesresultoneresult.text = (activity as FragmentHolder).viewModel.maxProgressFinal.value.toString() + " mm"
        binding.compercentresultoneresult.text = (activity as FragmentHolder).viewModel.percentProgressFinal.value!!.toDouble().toString() + " %"
        binding.comdesresultoneresult.text = (activity as FragmentHolder).viewModel.compressionDistanceFinal.value.toString() + " mm"
        binding.codeoneresult.text = "Code : " + (activity as FragmentHolder).viewModel.moldOrDieCode.value
        binding.name.text = (activity as FragmentHolder).viewModel.moldOrDie.value.toString() + " Model : " + (activity as FragmentHolder).viewModel.moldOrDieName.value
        (activity as FragmentHolder).binding.toolbarTitle.text = "Result"
    }

    private fun sharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("numofimages", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("first", 0)
    }

    private fun rx() {
        if (MyApplication.available) {
            var first = sharedPreferences()
            if (first <= 50) {
                ++first
                val prefs = requireContext().getSharedPreferences("numofimages", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("first", first)
                editor.apply()
                val bitmap = (binding.Moldimageoneresult.drawable as BitmapDrawable).bitmap
                CoroutineScope(IO).launch {
                    save(bitmap)
                }

                longToastSuccess("Saved")
            } else {
                longToastError("you have more than 50 results please delete one")
            }
        } else {
            longToastError("No internet connection")
        }
    }

    private fun toDb(path: String?) {
            val results = Resultsforfire()
            results.percentprogressfinal = (activity as FragmentHolder).viewModel.percentProgressFinal.value!!.toDouble()
            results.maxprogressfinal = (activity as FragmentHolder).viewModel.maxProgressFinal.value!!.toDouble()
            results.compressionDistancefinal = (activity as FragmentHolder).viewModel.compressionDistanceFinal.value!!.toDouble()

            val result = ModelResults()
            result.image = path
            val dateString = DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().time)
            result.history = dateString
            result.color = (activity as FragmentHolder).viewModel.color.value
            result.moldorDieCode = (activity as FragmentHolder).viewModel.moldOrDieCode.value!!.toInt()
            result.uri = (activity as FragmentHolder).viewModel.resultUri.value
            result.moldorDie = (activity as FragmentHolder).viewModel.moldOrDie.value
            result.moldorDieName = (activity as FragmentHolder).viewModel.moldOrDieName.value
            result.percentprogressfinal = (activity as FragmentHolder).viewModel.percentProgressFinal.value!!.toDouble()
            result.maxprogressfinal = (activity as FragmentHolder).viewModel.maxProgressFinal.value!!.toDouble()
            result.compressionDistancefinal = (activity as FragmentHolder).viewModel.compressionDistanceFinal.value!!.toDouble()
            viewModel.insert(result)
            toFirebase(results)

    }

    private fun toFirebase(results: Resultsforfire) {
        FirebaseDatabase.getInstance().getReference("results")
            .child(mAuth!!.currentUser!!.uid + System.currentTimeMillis()).setValue(results)
    }


}