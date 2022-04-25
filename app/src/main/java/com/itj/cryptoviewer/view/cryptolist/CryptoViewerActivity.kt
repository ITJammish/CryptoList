package com.itj.cryptoviewer.view.cryptolist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        findViewById<Button>(R.id.message_button).setOnClickListener {
            viewModel.fetchData()
        }
        val rvAdapter = CryptoSummaryRecyclerViewAdapter()
        findViewById<RecyclerView>(R.id.crypto_summary_recycler_view).also {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // can inject this
            it.adapter = rvAdapter
        }
        viewModel.testData.observe(this) {
            rvAdapter.setData(it)
        }
    }
}
