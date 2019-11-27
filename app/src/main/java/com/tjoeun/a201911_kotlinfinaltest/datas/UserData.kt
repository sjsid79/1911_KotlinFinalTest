package com.tjoeun.a201911_kotlinfinaltest.datas

import org.json.JSONObject
import java.io.Serializable

class UserData : Serializable {
    var loginId = ""
    var name = ""
    var phone = ""

    var category = Category()


    companion object {
        fun getUserDataFromJsonObject(json:JSONObject) : UserData {
            val user = UserData()
            user.loginId = json.getString("login_id")
            user.name = json.getString("name")
            user.phone = json.getString("phone")

            return user
        }
    }

}