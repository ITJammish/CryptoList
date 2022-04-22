package com.itj.cryptoviewer.cryptolist

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.itj.cryptoviewer.CryptoViewerApplication
import com.itj.cryptoviewer.R

/**
 * https://developer.android.com/jetpack/guide
 */
class CryptoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CryptoViewerApplication).appComponent
            .cryptoListComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model: CryptoListViewModel by viewModels()
        model.message.observe(this) { message ->
            // update UI
            findViewById<TextView>(R.id.message_view).text = message
        }

        findViewById<Button>(R.id.message_button).setOnClickListener {
            model.updateMessage()
        }
    }
}
