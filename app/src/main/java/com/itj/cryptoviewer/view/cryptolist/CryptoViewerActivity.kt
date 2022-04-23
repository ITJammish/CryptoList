package com.itj.cryptoviewer.view.cryptolist

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class CryptoViewerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this, viewModelFactory)[CryptoListViewModel::class.java]
        viewModel.message.observe(this) { message ->
            // update UI
            findViewById<TextView>(R.id.message_view).text = message
        }

        findViewById<Button>(R.id.message_button).setOnClickListener {
            viewModel.updateMessage()
        }
    }
}
