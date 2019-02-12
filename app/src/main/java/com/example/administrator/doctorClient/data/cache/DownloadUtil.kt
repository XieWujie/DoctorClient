package com.example.administrator.dpreversation.data.cache

import android.os.AsyncTask
import android.text.TextUtils

import okhttp3.OkHttpClient
import okhttp3.Request

import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.HashSet

/**
 * Created by wli on 15/9/29.
 * 用于下载文件，会主动合并重复的下载
 */
object DownloadUtil {

    /**
     * 用于记录 DownLoadCallback，如果对于同一个 url 有多个请求，则下载完后应该执行所有回调
     * 此变量就是用于记录这些请求
     */
    private var downloadCallBackMap: MutableMap<String, ArrayList<DownLoadCallback>>? = null

    /**
     * 判断当前 url 是否正在下载，如果已经在下载，则没有必要再去做请求
     */
    private var isDownloadingFile: MutableSet<String>? = null

    /**
     * OkHttpClient 的 实例，官方不建议创建多个，所以这里搞了一个 static 实例
     */
    private var okHttpClient: OkHttpClient? = null

    init {
        downloadCallBackMap = HashMap()
        isDownloadingFile = HashSet()
        okHttpClient = OkHttpClient()
    }

    @Synchronized
    private fun addDownloadCallback(path: String, callback: DownLoadCallback?) {
        if (null != callback) {
            if (downloadCallBackMap!!.containsKey(path)) {
                downloadCallBackMap!![path]!!.add(callback)
            } else {
                downloadCallBackMap!![path] = ArrayList(Arrays.asList(callback))
            }
        }
    }

    @Synchronized
    private fun executeDownloadCallBack(path: String, e: Exception) {
        if (downloadCallBackMap!!.containsKey(path)) {
            val callbacks = downloadCallBackMap!![path]
            downloadCallBackMap!!.remove(path)
            for (callback in callbacks!!) {
                callback.done(e)
            }
        }
    }

    /**
     * 异步下载文件到指定位置
     *
     * @param url       需要下载远程地址
     * @param localPath 下载到本地的文件存放的位置
     * @param overlay   是否覆盖原文件
     */
    @JvmOverloads
    fun downloadFileAsync(url: String, localPath: String, overlay: Boolean = false) {
        downloadFile(url, localPath, overlay, null)
    }

    /**
     * 异步下载文件到指定位置
     *
     * @param url       需要下载远程地址
     * @param localPath 下载到本地的文件存放的位置
     * @param overlay   是否覆盖原文件
     * @param callback  下载完后的回调
     */
    fun downloadFile(
        url: String, localPath: String,
        overlay: Boolean, callback: DownLoadCallback?
    ) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(localPath)) {
            throw IllegalArgumentException("url or localPath can not be null")
        } else if (!overlay && isFileExist(localPath)) {
            callback?.done(null)
        } else {
            addDownloadCallback(url, callback)
            if (!isDownloadingFile!!.contains(url)) {
                object : AsyncTask<Void, Void, Exception>() {
                    override fun doInBackground(vararg params: Void): Exception? {
                        return downloadWithOKHttp(url, localPath)
                    }

                    override fun onPostExecute(e: Exception) {
                        executeDownloadCallBack(url, e)
                        isDownloadingFile!!.remove(url)
                    }
                }.execute()
            }
        }
    }

    private fun downloadWithOKHttp(url: String, localPath: String): Exception? {
        val file = File(localPath)
        var result: Exception? = null
        val call = okHttpClient!!.newCall(Request.Builder().url(url).get().build())
        var outputStream: FileOutputStream? = null
        var inputStream: InputStream? = null
        try {
            val response = call.execute()
            if (response.code() == 200) {
                outputStream = FileOutputStream(file)
                inputStream = response.body()!!.byteStream()
                val buffer = ByteArray(4096)
                var len: Int
                do {
                    len = inputStream!!.read(buffer)
                    outputStream!!.write(buffer,0,len)
                }while (len!=-1)
            } else {
                result = Exception("response code is " + response.code())
            }
        } catch (e: IOException) {
            result = e
            if (file.exists()) {
                file.delete()
            }
        } finally {
            closeQuietly(inputStream)
            closeQuietly(outputStream)
        }
        return result
    }

    private fun closeQuietly(closeable: Closeable?) {
        try {
            closeable!!.close()
        } catch (e: Exception) {
        }

    }

    private fun isFileExist(localPath: String): Boolean {
        val file = File(localPath)
        return file.exists()
    }
    class DownLoadCallback {
        fun done(e: Exception?) {}
    }
}


/**
 * 异步下载文件到指定位置
 *
 * @param url       需要下载远程地址
 * @param localPath 下载到本地的文件存放的位置
 */