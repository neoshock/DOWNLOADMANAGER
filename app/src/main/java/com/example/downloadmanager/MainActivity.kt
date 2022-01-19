package com.example.downloadmanager

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadmanager.adapters.FileAdapter
import com.example.downloadmanager.models.Files
import com.example.downloadmanager.services.FileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadDataFromUrl()
    }

    private fun loadDataFromUrl(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/neoshock/fakeonlinerestserver/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val fileService = retrofit.create(FileService::class.java)
        val call: Call<List<Files>> = fileService.getFiles()

        call.enqueue(object :Callback<List<Files>>{
            override fun onFailure(call: Call<List<Files>>, t: Throwable) {
                println("###Error###" + t.message)
            }
            override fun onResponse(call: Call<List<Files>>, response: Response<List<Files>>) {
                var fileList: List<Files> = response.body()!!
                addDataToList(fileList)
            }
        })
    }

    private  fun donwloadFile(files: Files){
        val request = DownloadManager.Request(Uri.parse(files.link))
            .setTitle("File-" + files.filename )
            .setDescription(files.description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    private fun addDataToList(files: List<Files>){
        val recycle: RecyclerView = findViewById(R.id.fileListToDownload)
        val layout = LinearLayoutManager(this)
        val adapter = FileAdapter(files,R.layout.list_files, this)

        adapter.setOnItemClickListener(object: FileAdapter.OnItemClickListener{
            override fun onItemClick(files: Files, position: Int) {
                Toast.makeText(this@MainActivity, "Descargando Archivo", Toast.LENGTH_SHORT).show()
                donwloadFile(files)
            }
        })

        recycle.layoutManager = layout
        recycle.adapter = adapter
    }
}