package com.example.oso.reproductorfs

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_lista_canciones.*

class ListaCancionesActivity : AppCompatActivity() {

    lateinit var listaCanciones:ArrayList<Cancion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_canciones)

        mostrarCanciones()
    }

    private fun mostrarCanciones() {
        listaCanciones = ArrayList()
        leerMusica()
        val adapter:MyAdapter = MyAdapter(this,R.layout.item_lista_cancion,listaCanciones)
        listViewCanciones.adapter = adapter

        listViewCanciones.setOnItemClickListener { parent, view, position, id ->
            val cancion = listaCanciones[position]
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("Cancion",cancion.titulo)
            intent.putExtra("Artista",cancion.artista)
            intent.putExtra("Ruta",cancion.ruta)
            intent.putExtra("Imagen",cancion.imagen)
            startActivity(intent)
        }

    }

    private fun guardarImagenes(albumId:String) : String?{
        var path:String? = null
        val cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
                MediaStore.Audio.Albums._ID + "=?",
                arrayOf(albumId),
                null)

        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))

        }
        return path
    }

    private fun leerMusica() {
        val contenedor:ContentResolver = this.contentResolver
        val uriDatos = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursorCancion: Cursor? = contenedor.query(uriDatos,null,null,null,null)

        if (cursorCancion != null && cursorCancion.moveToFirst()){
            val tituloCancion = cursorCancion.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistaCancion = cursorCancion.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val localizacionCancion = cursorCancion.getColumnIndex(MediaStore.Audio.Media.DATA)
            val idAlbum = cursorCancion.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

            do {
                val titulo = cursorCancion.getString(tituloCancion)
                val artista = cursorCancion.getString(artistaCancion)
                val localizacion = cursorCancion.getString(localizacionCancion)
                val id = cursorCancion.getString(idAlbum)
                val img = guardarImagenes(id)
                listaCanciones.add(Cancion(titulo,artista,localizacion,img))
            }while (cursorCancion.moveToNext())
        }
    }
}
