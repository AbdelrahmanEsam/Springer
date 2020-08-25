package com.first.springer.proccessactivites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.databinding.FragmentResultshowBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Resultshow : Basefragment() {
    private var progress = 0
    private var percentprogress = 0
    private var maxprogress = 0
    private var handler: Handler? = null
   lateinit var binding: FragmentResultshowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_resultshow, container, false
        )
        val view = binding.root
        binding.viewModel = (activity as Fragmentholder).viewModel
        (activity as Fragmentholder).viewModel.notify.value = "no"
        val result = (Objects.requireNonNull(activity) as Fragmentholder).modelResults
        (activity as Fragmentholder).viewModel.resultshowCompressionDistancefinal.value = (result!!.compressionDistancefinal.toString()  + " Cm")
        (activity as Fragmentholder).viewModel.resultshowpercentprogressfinal.value = (result.percentprogressfinal.toString() + " %")
        (activity as Fragmentholder).viewModel.resultshowmaxprogressfinal.value = (result.maxprogressfinal.toString()  + " Cm")
        binding.comdesresultoneresult.text = result!!.compressionDistancefinal.toString() + " mm"
        binding.maxcomdesresultoneresult.text = result.maxprogressfinal.toString() + " mm"
        binding.compercentresultoneresult.text = result.percentprogressfinal.toString() + "%"
        binding.name.text = result.moldorDie + "name :" + result.moldorDieName
        binding.codeoneresult.text = "Code :" + result.moldorDieCode
        glide(result.image,binding.Moldimageoneresult)
        binding.timeoneresult.text = result.history
        binding.shareoneresult.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me share this result with you \n\n"
                shareMessage = """${shareMessage}CompressionDistance : ${result.compressionDistancefinal} mmpercentprogress :${result.percentprogressfinal} %maxprogress :${result.maxprogressfinal} mmSpringer Application"""
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
            }
        }
        Thread {
            if (result.compressionDistancefinal < 100) {
                var i = 0
                while (i < result.compressionDistancefinal) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        progress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else if (result.compressionDistancefinal >= 100 && result.compressionDistancefinal < 1000) {
                var i = 0
                while (i < result.compressionDistancefinal / 10) {
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
                while (i < result.compressionDistancefinal / 100) {
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
            while (i < result.percentprogressfinal) {
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
            if (result.maxprogressfinal < 100) {
                var i = 0
                while (i < result.maxprogressfinal) {
                    try {
                        Thread.sleep(50)
                        handler!!.sendEmptyMessage(0)
                        maxprogress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    i++
                }
            } else if (result.maxprogressfinal >= 100 && result.maxprogressfinal < 1000) {
                var i = 0
                while (i < result.maxprogressfinal / 10) {
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
                while (i < result.maxprogressfinal / 100) {
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
                if (progress <= result.compressionDistancefinal) {
                    if (result.compressionDistancefinal < 100) {
                        binding.progressresultshow.setDonut_progress(progress.toString())
                        binding.progressresultshow.text = "$progress mm"
                    } else if (result.compressionDistancefinal >= 100 && result.compressionDistancefinal < 1000) {
                        binding.progressresultshow.setDonut_progress(progress.toString())
                        binding.progressresultshow.text = "$progress cm"
                    } else {
                        binding.progressresultshow.text = "$progress M"
                    }
                }
                if (percentprogress <= result.percentprogressfinal) {
                    if (percentprogress <= 100) {
                        binding.percentprogressresultshow.setDonut_progress(percentprogress.toString())
                    }
                    binding.percentprogressresultshow.text = "$percentprogress %"
                }
                if (maxprogress <= result.maxprogressfinal) {
                    binding.maxprogressresultshow.setDonut_progress(maxprogress.toString())
                    if (result.maxprogressfinal < 100) {
                        binding.maxprogressresultshow.text = maxprogress.toString() + "mm"
                    } else if (result.maxprogressfinal >= 100 && result.maxprogressfinal < 1000) {
                        binding.maxprogressresultshow.text = maxprogress.toString() + "cm"
                    } else {
                        binding.maxprogressresultshow.text = maxprogress.toString() + "M"
                    }
                }
            }
        }
        return view
    }
}