package com.example.downloadmanager.models

import java.io.Serializable

class Files(val id: Int, val filename: String, val description: String, val date: String, val link: String): Serializable {

}