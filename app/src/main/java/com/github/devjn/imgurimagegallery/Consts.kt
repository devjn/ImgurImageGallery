package com.github.devjn.imgurimagegallery

import androidx.annotation.ArrayRes


/**
 * Created by @author Jahongir on 25-Sep-18
 * devjn@jn-arts.com
 * Consts
 */
object Consts {


}

object Section {
    const val HOT = "hot";
    const val TOP = "top";
    const val USER = "user";
}

class SubSection(val menu: Int, @ArrayRes val arrayResId: Int, val subAction: SubAction) {

    interface SubAction {
        fun apply(data: String)
    }

}