package com.example.attendancesystem

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.attendancesystem.Constants.ExtraKey.ATTACHMENT_KEY
import com.example.attendancesystem.Constants.ExtraKey.ATTACHMENT_PATH
import com.example.attendancesystem.Constants.ExtraKey.ATTACHMENT_TYPE
import com.example.attendancesystem.databinding.FragmentShowAttachmentDialogBinding

class ShowAttachmentDialogFragment : AppCompatDialogFragment() {
    
    private lateinit var binding: FragmentShowAttachmentDialogBinding
    
    //VARIABLE LIST
    private lateinit var strAttachmentPath: String
    private lateinit var strAttachmentType: String
    private lateinit var strAttachmentKey: String
    private var intCurrentPosition: Long? = 0L
    private var isPlay: Boolean? = false
    
    companion object {
        
        val TAG: String? = FragmentShowAttachmentDialogBinding::class.java.simpleName
        fun newInstance(path: String?, type: String?, key: String? = null): AppCompatDialogFragment {
            val dialog = ShowAttachmentDialogFragment()
            val bundle = Bundle().apply {
                putString(ATTACHMENT_PATH, path)
                putString(ATTACHMENT_TYPE, type)
                putString(ATTACHMENT_KEY, key)
            }
            dialog.arguments = bundle
            return dialog
        }
        
    }
    
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentShowAttachmentDialogBinding.inflate(inflater, container, false)
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.setCancelable(false)
        
        init()
        onClicks()
        setObserver()
        return binding.root
    }
    
    private fun init() {
        binding.apply {
            arguments?.let { argument ->
                strAttachmentPath = argument.getString(ATTACHMENT_PATH).toString()
                strAttachmentType = argument.getString(ATTACHMENT_TYPE).toString()
                strAttachmentKey = argument.getString(ATTACHMENT_KEY, "").toString()
            }
            
            
            binding.apply {
                when (strAttachmentType) {
                    getString(R.string.image) -> {
                        viewVisible(ivImage)
                        setImage(ivImage, strAttachmentPath)
                    }
                    
                    else -> {
                        try {
                            viewVisible(ivImage)
                            setImage(ivImage, BitmapFactory.decodeFile(strAttachmentPath))
                            
                        } catch (e: Exception) {
                            e.message
                        }
                    }
                }
            }
        }
    }
    
    private fun setObserver() {
    
    }
    
    private fun setAttachment(awsImageUrl: String) {
        binding.apply {
            when (strAttachmentType) {
                getString(R.string.image) -> {
                    viewVisible(ivImage)
                    viewVisible(progressBar)
                    setImage(ivImage, strAttachmentPath)
                }
                
                else -> {
                    try {
                        viewVisible(ivImage)
                        setImage(ivImage, BitmapFactory.decodeFile(awsImageUrl))
                        
                    } catch (e: Exception) {
                        e.message
                    }
                }
            }
        }
    }
    
    private fun onClicks() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
    
}