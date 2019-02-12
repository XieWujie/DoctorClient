package com.example.administrator.doctorClient.custom

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.utilities.PathUtil
import com.example.administrator.doctorClient.utilities.VOICE_MESSAGE
import java.io.File

class RecordText:TextView{

    private var id:String? = null
    private var name:String? =null
    private var mMediaRecorder:MediaRecorder? = null
    private var mAudioFile: File? = null
    private var startTime = 0L
    private var endTime = 0L
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    constructor(context: Context):super(context)

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)


    fun setConversation(id:String,name:String){
        post {
            this.name = name
            this.id = id
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null){
            return false
        }
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                startRecord()
            }

            MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP->{
                stopRecord()
            }
        }
        return true
    }

    /**
     * @description 开始进行录音
     * @author ldm
     * @time 2017/2/9 9:18
     */
    private fun startRecord() {
        if (isHavePermission()){
            vibrator.vibrate(50)
            //播放前释放资源
            releaseRecorder()
            //执行录音操作
            recordOperation()
        }else{
            if (context is Activity){
                ActivityCompat.requestPermissions(context as Activity, arrayOf( Manifest.permission.RECORD_AUDIO),1)
            }
        }
    }

    private fun isHavePermission() =
        (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)


    /**
     * @description 录音失败处理
     * @author ldm
     * @time 2017/2/9 9:35
     */
    private fun recordFail() {
        mAudioFile = null
    }

    /**
     * @description 录音操作
     * @author ldm
     * @time 2017/2/9 9:34
     */
    private fun recordOperation() {
        //创建MediaRecorder对象
        mMediaRecorder = MediaRecorder()
        //创建录音文件,.m4a为MPEG-4音频标准的文件的扩展名
        mAudioFile = File(PathUtil.getRecordPathByCurrentTime(context))
        mAudioFile!!.getParentFile().mkdirs()
        try {
            //创建文件
            mAudioFile!!.createNewFile()
            //配置mMediaRecorder相应参数
            //从麦克风采集声音数据
            mMediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                //设置保存文件格式为MP4
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
                setAudioSamplingRate(44100)
                //设置声音数据编码格式,音频通用格式是AAC
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                //设置编码频率
                setAudioEncodingBitRate(96000)
                //设置录音保存的文件
                setOutputFile(mAudioFile!!.getAbsolutePath())
                //开始录音
                prepare()
                start()
                //记录开始录音时间
                startTime = System.currentTimeMillis()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            recordFail()
        }

    }


    /**
     * @description 结束录音操作
     * @author ldm
     * @time 2017/2/9 9:18
     */
    private fun stopRecord() {
        endTime = System.currentTimeMillis()
        val time = (endTime - startTime)/1000.0
        if (time >= 0.2) {
            if (mAudioFile!=null){
                mMediaRecorder?.stop()
                vibrator.vibrate(50)
                sendVoiceMessage(mAudioFile!!.absolutePath,time)
            }
        } else {
            mAudioFile = null
        }
        //录音完成释放资源
        releaseRecorder()
    }

    /**
     * @description 翻放录音相关资源
     * @author ldm
     * @time 2017/2/9 9:33
     */
    private fun releaseRecorder() {
        mMediaRecorder?.release()
    }

    private fun sendVoiceMessage(path:String?,time:Double){
        if (id!=null && name!=null&&path!=null){
            MessageManage.sendMessage(name!!,id!!, VOICE_MESSAGE,path,time.toDouble()){

            }
        }
    }
}