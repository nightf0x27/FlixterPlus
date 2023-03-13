package com.example.flixterplus

import com.google.gson.annotations.SerializedName

class NowPlayingMovie {

    @JvmField
    @SerializedName("title")
    var title: String? = null

    //TODO movieImageUrl

    @SerializedName("poster_path")
    var movieImageUrl: String? = null

    //TODO description

    @SerializedName("overview")
    var description: String? = null


}