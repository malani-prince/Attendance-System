package com.example.attendancesystem

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancesystem.Constants.ExtraKey.IMAGE_CAPTURE_INTENT
import com.example.attendancesystem.databinding.FragmentAttendanceBinding
import java.io.File

class AttendanceFragment : Fragment() {
    
    private lateinit var binding: FragmentAttendanceBinding
    
    
    private lateinit var upLoadListAdapter: UpLoadListAdapter
    
    
    private var arrayUploadFiles: ArrayList<Attachments> = arrayListOf()
    
    private var photoFile: File? = null
    private var isClick: Boolean = false
    private val launcherMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uriList ->
        requireActivity().setAttachment(uriList, arrayUploadFiles, upLoadListAdapter)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAttendanceBinding.inflate(inflater)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onClicks()
        setAdapter()
    }
    
    private fun init() {
    }
    
    private fun onClicks() {
        binding.apply {
            llUploadPhotoOrVideo.setOnClickListener {
                if (!isClick) {
                    isClick = true
                    setDialog()
                }
            }
        }
    }
    
    private fun setDialog() {
        val alertDialog = AlertDialog.Builder(requireActivity())
        
        alertDialog.setOnDismissListener {
            isClick = false
        }
        val item = mutableListOf(getString(R.string.camera), getString(R.string.gallery))
        
        alertDialog.setItems(item.toTypedArray()) { dialog, index ->
            when (item[index]) {
                getString(R.string.gallery) -> {
                    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                        addFlags(takeFlags)
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        type = getString(R.string.image_type)
                        type = getString(R.string.video_type)
                    }
                    
                    launcherMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                    dialog.dismiss()
                }
                
                getString(R.string.camera) -> {
                    val cameraIntent = Intent(IMAGE_CAPTURE_INTENT)
                    cameraIntent.resolveActivity(requireActivity().packageManager)?.let {
                        photoFile = requireActivity().createImageFile()
                        val photoURI: Uri? = photoFile?.let { photoFile ->
                            FileProvider.getUriForFile(requireActivity(), "${requireActivity().packageName}.provider", photoFile)
                        }
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        captureImageRequestContract.launch(cameraIntent)
                    }
                    dialog.dismiss()
                }
            }
        }
        val dialog = alertDialog.create()
        dialog.show()
    }
    
    private fun setAdapter() {
        binding.apply {
            upLoadListAdapter = UpLoadListAdapter(requireActivity(), arrayUploadFiles, object : OnItemClickListener {
                override fun onItemClick(value: Any?) {
                    super.onItemClick(value)
                    value as Attachments
                    
                    if (value.awsImageUrl?.isNotEmpty() == true) {
                        ShowAttachmentDialogFragment.newInstance(value.awsImageUrl, value.type, value.key).apply {
                            show(requireActivity().supportFragmentManager, ShowAttachmentDialogFragment.TAG)
                        }
                        
                    } else if (value.path?.isNotEmpty() == true) {
                        ShowAttachmentDialogFragment.newInstance(value.path, value.type, value.key).apply {
                            show(requireActivity().supportFragmentManager, ShowAttachmentDialogFragment.TAG)
                        }
                        
                    }
                }
                
                override fun onItemClick(key: Any?, value: Any?) {
                    super.onItemClick(key, value)
                    key as Int
                    value as Attachments
                    
                    arrayUploadFiles.removeAt(key)
                    upLoadListAdapter.notifyItemRemoved(key)
                }
            })
            
            rvAttachments.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvAttachments.adapter = upLoadListAdapter
        }
    }
    
    private fun filterItems(selectedFilter: String) {
        Log.d("Selected Filter", selectedFilter)
    }
    
    @SuppressLint("NotifyDataSetChanged")
    private val captureImageRequestContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val photoSize = photoFile?.let { getImageSize(it) }
            
            photoSize?.let { size ->
                val filePath = photoFile?.path
                val imageBitMap = BitmapFactory.decodeFile(filePath)
                val resizedBitMap = Bitmap.createScaledBitmap(imageBitMap, imageBitMap.width / 3, imageBitMap.height / 3, true)
                val resizedFile = requireActivity().bitmapToFile(resizedBitMap)
                val resizeImagePath = Uri.fromFile(resizedFile).toString()
                val upLoadImageOrVideo = Attachments().apply {
                    this.path = Uri.parse(resizeImagePath).toString()
                    type = getString(R.string.image)
                }
                arrayUploadFiles.add(upLoadImageOrVideo)
                upLoadListAdapter.notifyDataSetChanged()
            }
            
        } else {
            photoFile = null
        }
    }
    
}