package com.example.oso.reproductorfs

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var sw = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ImageViewPortada.setImageResource(R.drawable.move_like_jagger_maroonfive)
//        mp = MediaPlayer.create(applicationContext, R.raw.maroonfivelikejagger)
//        mp.setAudioStreamType(AudioManager.STREAM_MUSIC)
        //Dando funcionalidad al seekBar
        /*handler= Handler()
        mp.setOnPreparedListener(  {
            seekBarMusic.max=mp.duration
            playCicle()
        })*/

        seekBarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mp.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        //reproducir canciones
        buttonPlaypause.setOnClickListener {

            Play()

        }
        buttonNext.setOnClickListener{
            mp.pause()
            sw=true
//            ImageViewPortada.setImageResource(R.drawable.melendi_un_alumno_mas)
//            mp=MediaPlayer.create(this,R.raw.melendiposdata)
            seekBarMusic.max=mp.duration
            Play()
        }
        buttonPrevious.setOnClickListener{
            mp.pause()
            sw=true
//            ImageViewPortada.setImageResource(R.drawable.fall_out_boy_dance)
//            mp=MediaPlayer.create(this,R.raw.falloutboydance)
            seekBarMusic.max=mp.duration
            Play()
        }
        imageButtonshare.setOnClickListener {
            val enviarWhats:Intent=Intent()
            enviarWhats.setAction(Intent.ACTION_GET_CONTENT)
            enviarWhats.setType("audio/mpeg")
            startActivity(enviarWhats)

        }
        imageButtonstar.setOnClickListener {
            Toast.makeText(this,"Añadido a favoritos", Toast.LENGTH_LONG).show()
//            imageButtonstar.setBackgroundResource(R.drawable.ic_star_black_24dp)
        }

        //Recoger datos

        val nomCancion:String = intent.getStringExtra("Cancion")
        val nomArtista:String = intent.getStringExtra("Artista")
        val rutaCancion:String = intent.getStringExtra("Ruta")
        val imgCancion:String? = intent.getStringExtra("Imagen")

        textViewCancion.text=nomCancion
        textViewArtista.text=nomArtista
        val uri = Uri.parse(rutaCancion)
        mp = MediaPlayer.create(this,uri)
        if (imgCancion != null){
            val bm:Bitmap = BitmapFactory.decodeFile(imgCancion)
            ImageViewPortada.setImageBitmap(bm)
        }


    }
    fun Play()
    {
        if (sw) {
            mp.start()
//            buttonPlaypause.setBackgroundResource(R.drawable.pausa)

            sw = false
        }
        else {
            mp.pause()
//            buttonPlaypause.setBackgroundResource(R.drawable.botonplay)
            sw = true
        }
    }

    private fun playCicle(){
        seekBarMusic.setProgress(mp.currentPosition)
        if(mp.isPlaying){
            runnable = object : Runnable {
                override fun run() {
                    playCicle()
                }
            }
            handler.postDelayed(runnable,1000)
        }
    }

    override fun onPause() {
        super.onPause()
        mp.stop()
        mp.release()
    }
}

