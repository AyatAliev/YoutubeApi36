package com.example.youtubeapi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtubeapi.BuildConfig.API_KEY
import com.example.youtubeapi.`object`.Constant
import com.example.youtubeapi.base.BaseViewModel
import com.example.youtubeapi.model.Playlist
import com.example.youtubeapi.remote.ApiService
import com.example.youtubeapi.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun playlists(): LiveData<Playlist> {
        return getPlaylists()
    }

    private fun getPlaylists(): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()

        apiService.getPlaylists(Constant.part, Constant.channelId, API_KEY, Constant.maxResults)
            .enqueue(object : Callback<Playlist> {
                override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                    // 200 - 299
                }

                override fun onFailure(call: Call<Playlist>, t: Throwable) {
                    // 404 - not found, 401 - токен истек 400-499
                    print(t.stackTrace)
                }
            })

        return data
    }
}