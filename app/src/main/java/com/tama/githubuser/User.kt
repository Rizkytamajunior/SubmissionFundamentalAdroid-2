package com.tama.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val login: String,
    val id : Int,
    val avatar_url : String
) : Parcelable