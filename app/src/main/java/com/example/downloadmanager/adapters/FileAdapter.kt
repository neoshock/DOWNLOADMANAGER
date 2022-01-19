package com.example.downloadmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadmanager.R
import com.example.downloadmanager.models.Files

class FileAdapter(val file: List<Files>, val layout: Int, val contex: Context):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private lateinit var myListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FileViewHolder(layoutInflater.inflate(R.layout.list_files,parent,false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(file.get(position), myListener, contex)
    }

    override fun getItemCount(): Int {
        return  file.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myListener = listener
    }

    class FileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fileName: TextView = itemView.findViewById(R.id.ficheroNombre)
        val fileDescription: TextView = itemView.findViewById(R.id.ficheroDescripcion)
        val fileDate: TextView = itemView.findViewById(R.id.ficheroFecha)

        fun bind(file: Files, listener: OnItemClickListener, contex: Context) {
            fileName.setText(file.filename)
            fileDescription.setText(file.description)
            fileDate.setText(file.date)

            itemView.setOnClickListener {
                listener.onItemClick(file.link, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: String, position: Int)
    }
}