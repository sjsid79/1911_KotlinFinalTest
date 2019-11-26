package com.tjoeun.a201911_kotlinfinaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tjoeun.a201911_kotlinfinaltest.utils.ContextUtil
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val userId = userIdEdt.text.toString()
            val userPw = userPwEdt.text.toString()

            ServerUtil.postRequestLogin(mContext, userId, userPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    Log.d("서버응답", json.toString())
                }

            })

        }

        saveIdCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->


            ContextUtil.setSaveIdChecked(mContext, isChecked)
        }

    }

    override fun setValues() {

        saveIdCheckBox.isChecked = ContextUtil.getSaveIdChecked(mContext)
    }

}
