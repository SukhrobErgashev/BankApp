package dev.sukhrob.bankapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.bankapp.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BankApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}