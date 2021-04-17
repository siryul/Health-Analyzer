package com.example.healthanalyzers.entity

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val username: String, var nickname: String, var sex: Boolean, var age: Int) {

}