package ru.dragor.mytelegram.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dragor.mytelegram.R
import ru.dragor.mytelegram.databinding.ActivityRegisterBinding
import ru.dragor.mytelegram.ui.fragments.EnterPhoneNumberFragment
import ru.dragor.mytelegram.utilits.initFirebase
import ru.dragor.mytelegram.utilits.replaceActivity
import ru.dragor.mytelegram.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment(), true)
    }
}