package com.example.attendancesystem

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.graphics.*
import android.net.Uri
import android.os.*
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.*
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.example.attendancesystem.Constants.DateFormat.YYYYMMDD_HHMMSS
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun Context?.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//FOR SHARED PREFERENCES
fun Context?.prefManager(): Preference {
    return Preference(this)
}

//LOGOUT FROM APP
fun Activity.logout() {
    prefManager().removePreferenceFile()/*
        finishAffinityAndNavigateTo(LoginActivity::class.java)
    */
    finishAffinity()
}

fun AppCompatActivity?.replaceFragment(fragment: Fragment?, frameId: Int?, bundle: Bundle?, isAddToBackStack: Boolean = true) {
    val backStateName = fragment?.let { fragment1 -> fragment1::class.java.name }
    val manager: FragmentManager? = this?.supportFragmentManager
    val ft: FragmentTransaction? = manager?.beginTransaction()
    frameId?.let { frame ->
        fragment?.let { fragment1 -> fragment1::class.java }?.let { fragmentClass -> ft?.replace(frame, fragmentClass, bundle, backStateName) }
    }
    ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (isAddToBackStack) {
        ft?.addToBackStack(backStateName)
    }
    ft?.commit()
}

//ADD FRAGMENT
fun AppCompatActivity?.addFragment(fragment: Fragment, frameId: Int?, bundle: Bundle?) {
    this?.supportFragmentManager?.inTransaction {
        frameId?.let { frameId ->
            add(frameId, fragment::class.java, bundle, fragment::class.java.simpleName)
        }
        addToBackStack(fragment::class.java.simpleName)
    }
}

fun setImage(imageView: ImageView?, uri: Uri?) {
    imageView?.let { view ->
        Glide.with(view.context).load(uri).centerCrop().into(view)
    }
}

fun setImage(imageView: ImageView?, bitmap: Bitmap) {
    imageView?.let { view ->
        Glide.with(view.context).load(bitmap).centerCrop().into(view)
    }
}
fun setImage(imageView: ImageView?, awsImageUrl: String?) {
    imageView?.let { view ->
        Glide.with(view.context).load(awsImageUrl).centerCrop().into(view)
    }
}

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

//START ACTIVITY
fun <T> Activity.navigateTo(mClass: Class<T>, bundle: (Bundle.() -> Unit) = {}) {
    val intent = Intent(this, mClass)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
}

//START ACTIVITY
fun <T> Activity.finishAndNavigateTo(mClass: Class<T>, bundle: (Bundle.() -> Unit) = {}) {
    val intent = Intent(this, mClass)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
    finish()
}

//MANGE BACK PRESS MAINTAIN
fun AppCompatActivity.addOnBackPressedDispatcher(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed.invoke()
        }
    })
}

//REMOVE ALL ACTIVITY
fun <T> Activity.finishAffinityAndNavigateTo(mClass: Class<T>, bundle: (Bundle.() -> Unit) = {}) {
    val intent = Intent(this, mClass)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
    finishAffinity()
}

//TO MAKE VIEW VISIBILITY GONE
fun viewGone(view: View?) {
    view?.visibility = View.GONE
}

//TO MAKE VIEW VISIBILITY VISIBLE
fun viewVisible(view: View?) {
    view?.visibility = View.VISIBLE
}

//IS EMPTY OR NULL
fun isEmpty(editText: EditText): Boolean {
    return getText(editText).trim().isEmpty()
}

//IS EMPTY OR NULL
fun isEmpty(textView: AppCompatTextView): Boolean {
    return getText(textView).trim().isEmpty()
}

//SET TEXT
fun setText(textView: TextView, string: String?) {
    textView.text = string ?: ""
}

//SET TEXT
fun setText(editText: EditText, string: String?) {
    editText.setText(string ?: "")
}

//SET TEXT
fun setText(editText: AppCompatEditText, string: String?) {
    editText.setText(string ?: "")
}

//SET TEXT
fun setText(textView: AppCompatTextView, string: String?) {
    textView.text = string ?: ""
}

//GET TEXT
fun getText(textView: AppCompatTextView): String {
    return textView.text.toString()
}

//GET TEXT
fun getText(editText: EditText): String {
    return editText.text.toString().trim()
}

internal fun getFilePathFromUri(context: Context, uri: Uri, uniqueName: Boolean): String? = if (uri.path?.contains("file://") == true) {
    uri.path
    
} else {
    getFileFromContentUri(context, uri, uniqueName).path
}

fun getFileFromContentUri(context: Context, contentUri: Uri, uniqueName: Boolean): File {
    val fileExtension = getFileExtension(context, contentUri) ?: ""
    val timeStamp = SimpleDateFormat(YYYYMMDD_HHMMSS, Locale.getDefault()).format(Date())
    val fileName = ("temp_" + if (uniqueName) (timeStamp + "_" + ((Math.random() * 9000) + 1000).toInt()) else "") + ".$fileExtension"
    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()
    var oStream: FileOutputStream? = null
    var inputStream: InputStream? = null
    
    try {
        oStream = FileOutputStream(tempFile)
        inputStream = context.contentResolver.openInputStream(contentUri)
        inputStream?.let { copy(inputStream, oStream) }
        oStream.flush()
        
    } catch (e: Exception) {
        e.message
        
    } finally {
        inputStream?.close()
        oStream?.close()
    }
    
    return tempFile
}

fun copy(source: InputStream, target: OutputStream) {
    val bytes = ByteArray(8192)
    var length: Int
    
    while (source.read(bytes).also { buffer ->
            length = buffer
        } > 0) {
        target.write(bytes, 0, length)
    }
}

fun getFileExtension(context: Context, uri: Uri): String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
    MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
    
} else {
    uri.path?.let { path ->
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(path)).toString())
    }
}

@SuppressLint("NotifyDataSetChanged")
fun Activity.setAttachment(uriList: List<Uri>?, arrayUploadFiles: ArrayList<Attachments>, upLoadListAdapter: UpLoadListAdapter) {
    try {
        uriList?.let { list ->
            list.forEach { imgUri ->
                imgUri.let { uri ->
                    val path = getFilePathFromUri(this, uri, true)
                    if (path.toString().contains(getString(R.string.jpg)) || path.toString().contains(getString(R.string.jpeg)) || path.toString().contains(getString(R.string.png)) || path.toString().contains(getString(R.string.heic)) || path.toString().contains(getString(R.string.gif))) {
                        val photoFile = File(path.toString())
                        val filePath = photoFile.path
                        val imageBitMap = BitmapFactory.decodeFile(filePath)
                        val resizedFile = bitmapToFile(imageBitMap)
                        val resizeImagePath = Uri.fromFile(resizedFile).toString()
                        val upLoadImageOrVideo = Attachments().apply {
                            this.path = Uri.parse(resizeImagePath).toString()
                            type = getString(R.string.jpg)
                            isImageLoaded = true
                        }
                        arrayUploadFiles.add(upLoadImageOrVideo)
                        upLoadListAdapter.notifyDataSetChanged()
                        
                    } else {
                        toast(getString(R.string.error_invalid_photo_video_type))
                    }
                }
            }
        }
        
    } catch (e: Exception) {
        e.message
    }
}

fun getImageSize(file: File): Long {
    return file.length().div(1024).div(1000)
}

//CREATE IMAGE FILE
@SuppressLint("SimpleDateFormat")
fun Activity.createImageFile(): File? {
    val timeStamp: String = SimpleDateFormat(YYYYMMDD_HHMMSS).format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir)
}

@SuppressLint("SimpleDateFormat")
fun Context.bitmapToFile(bitmap: Bitmap): File {
    val filesDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val timeStamp: String = SimpleDateFormat(YYYYMMDD_HHMMSS).format(Date())
    val imageFile = File.createTempFile("IMG_${timeStamp}_", getString(R.string.jpg), filesDir)
    val os: OutputStream
    
    try {
        os = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os)
        os.flush()
        os.close()
        
    } catch (e: Exception) {
        e.message
    }
    return imageFile
}
