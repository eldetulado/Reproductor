package com.example.oso.reproductorfs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(context:Context, val layout:Int, val lista:ArrayList<Cancion>):
        ArrayAdapter<Cancion>(context,layout,lista){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val holder:Holder
        if (convertView == null){
            val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout,parent, false)

            holder = Holder()
            holder.nombreCancion = view.findViewById(R.id.textViewNombreCancion)
            holder.artistaCancion = view.findViewById(R.id.textViewNombreArtista)
            view.tag = holder
        }else{
            holder = convertView.tag as Holder
            view = convertView
        }

        val cancion = lista[position]
        holder.nombreCancion?.text = cancion.titulo
        holder.artistaCancion?.text = cancion.artista

        return view
    }

    private class Holder{
        var nombreCancion: TextView? = null
        var artistaCancion: TextView? = null
    }
}