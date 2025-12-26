package com.coleblvck.methodcall.methodType.definitions.startRecordingAudio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File

// Argument: listOf(Context, String filename)
val startRecordingAudio: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is String) {
        val context = arg[0] as Context
        val filename = arg[1] as String

        try {
            val outputFile = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "$filename.mp3")
            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFile.absolutePath)
                prepare()
                start()
            }
            // Note: You'll need to store the recorder instance to stop it later
        } catch (e: Exception) {
            Log.e("RECORD_AUDIO_ERROR", e.message ?: "Could not start recording")
        }
    }
}