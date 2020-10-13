package com.apptikar.springer.proccessactivites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apptikar.springer.ModelResults
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.databinding.FragmentResultshowBinding
import kotlinx.coroutines.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class ResultShow : BaseFragment() {
    private var progress = 0
    private var percentProgress = 0
    private var maxProgress = 0
    private var handler: Handler? = null
   lateinit var binding: FragmentResultshowBinding
    lateinit var result:ModelResults
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_resultshow, container, false
        )
        val view = binding.root
        binding.viewModel = (activity as FragmentHolder).viewModel

        (activity as FragmentHolder).viewModel.notify.value = "no"
       result = (activity as FragmentHolder).viewModel.modelResults.value!!
        (activity as FragmentHolder).binding.toolbarTitle.text = result.moldorDieName
        (activity as FragmentHolder).viewModel.resultShowCompressionDistanceFinal.value = (result.compressionDistancefinal.toString()  + " mm")
        (activity as FragmentHolder).viewModel.resultShowPercentProgressFinal.value = (result.percentprogressfinal.toString() + " %")
        (activity as FragmentHolder).viewModel.resultShowMaxProgressFinal.value = (result.maxprogressfinal.toString()  + " mm")
        binding.comdesresultoneresult.text = result.compressionDistancefinal.toString() + " mm"
        binding.maxcomdesresultoneresult.text = result.maxprogressfinal.toString() + " mm"
        binding.compercentresultoneresult.text = result.percentprogressfinal.toString() + "%"
        binding.name.text = result.moldorDie + " name : " + result.moldorDieName
        binding.codeoneresult.text = "Code : " + result.moldorDieCode
        glide(result.image,binding.Moldimageoneresult)
        binding.timeoneresult.text = result.history
        binding.shareoneresult.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me share this result with you \n\n"
                shareMessage =
                    """${shareMessage}CompressionDistance : ${result.compressionDistancefinal} mmpercentprogress :${result.percentprogressfinal} %maxprogress :${result.maxprogressfinal} mmSpringer Application"""
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
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
        if (result.compressionDistancefinal!! < 100) {
            GlobalScope.launch {
                repeat(result.compressionDistancefinal!!.roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }

            }


        }else if (result.compressionDistancefinal!! >= 100 && result.compressionDistancefinal!! < 1000) {
            GlobalScope.launch {
                repeat((result.compressionDistancefinal!! / 10).roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }

            }
        }else
        {
            GlobalScope.launch {
                repeat((result.compressionDistancefinal!! / 100).roundToInt())
                {
                    progress++
                    delay(50)
                    updateProgressUI()
                }


            }
        }
    }


 private fun updateProgressUI() {
        if (progress <= result.compressionDistancefinal!!) {
            if (result.compressionDistancefinal!! < 100) {
                binding.progressresultshow.setDonut_progress(progress.toString())
                binding.progressresultshow.text = "$progress mm"
            } else if (result.compressionDistancefinal!! >= 100 && result.compressionDistancefinal!! < 1000) {
                binding.progressresultshow.setDonut_progress(progress.toString())
                binding.progressresultshow.text = "$progress cm"
            } else {
                binding.progressresultshow.text = "$progress M"
            }
        }
    }

 private fun percentProgress()
    {
        GlobalScope.launch {
            repeat(result.percentprogressfinal!!.roundToInt())
            {
                percentProgress++
                delay(50)
                updatePercentProgressUI()
            }




        }
    }

 private fun updatePercentProgressUI() {
        if (percentProgress <= result.percentprogressfinal!!) {
            if (percentProgress <= 100) {
                binding.percentprogressresultshow.setDonut_progress(percentProgress.toString())
            }
            binding.percentprogressresultshow.text = "$percentProgress %"
        }
    }

 private fun maxProgress()
    {
        if (result.maxprogressfinal!! < 100) {
            GlobalScope.launch {
                repeat(result.maxprogressfinal!!.roundToInt())
                {
                    maxProgress++
                    delay(50)
                    updateMaxProgressUI()
                }

            }
        } else if (result.maxprogressfinal!! >= 100 && result.maxprogressfinal!! < 1000) {
            GlobalScope.launch {
                repeat((result.maxprogressfinal!! / 10).roundToInt())
                {
                    maxProgress++
                    delay(50)
                    updateMaxProgressUI()
                }

            }
        } else {
            GlobalScope.launch {
                repeat((result.maxprogressfinal!! / 1000).roundToInt())
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
        if (maxProgress <= result.maxprogressfinal!!) {
            binding.maxprogressresultshow.setDonut_progress(maxProgress.toString())
            if (result.maxprogressfinal!! < 100) {
                binding.maxprogressresultshow.text = "$maxProgress  mm"
            } else if (result.maxprogressfinal!! >= 100 && result.maxprogressfinal!! < 1000) {
                binding.maxprogressresultshow.text = "$maxProgress cm"
            } else {
                binding.maxprogressresultshow.text = "$maxProgress M"
            }
        }
    }
}