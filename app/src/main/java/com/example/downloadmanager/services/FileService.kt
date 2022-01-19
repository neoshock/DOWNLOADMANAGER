package com.example.downloadmanager.services

import com.example.downloadmanager.models.Files
import retrofit2.Call
import retrofit2.http.GET

interface FileService {
     @GET("files")
     fun getFiles(): Call<List<Files>>
}