package edu.usna.mobileos.playsound2

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize MediaPlayer
        initializePlayer()

        //start playing
        mediaPlayer?.start()

    }

    private fun initializePlayer() {
        if (mediaPlayer == null) {

            //playing a sound clip from raw
            mediaPlayer = MediaPlayer.create(baseContext, R.raw.sound_file_1)
        }
    }

    private fun destroyPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
            }
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    fun stopPlayer(view: View) {
        destroyPlayer()
        finish()
    }
}