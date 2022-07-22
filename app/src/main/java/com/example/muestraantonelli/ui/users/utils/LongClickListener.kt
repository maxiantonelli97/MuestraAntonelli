package com.example.muestraantonelli.ui.users.utils

import android.view.View
import com.example.muestraantonelli.entity.UsersModel

interface LongClickListener {

    fun longClick(v : View, index : Int, user : UsersModel)
}