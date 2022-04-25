package com.itj.cryptoviewer.view.cryptolist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class CryptoViewerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var cryptoListAdapter: CryptoSummaryRecyclerViewAdapter

    private val viewModel: CryptoListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CryptoListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        bindData()
    }

    private fun bindViews() {
        findViewById<Button>(R.id.message_button).setOnClickListener {
            viewModel.fetchData()
        }
        findViewById<RecyclerView>(R.id.crypto_summary_recycler_view).also {
            it.layoutManager = layoutManager
            it.adapter = cryptoListAdapter
        }
    }

    private fun bindData() {
        viewModel.testData.observe(this) {
            cryptoListAdapter.setData(it)
        }
    }
}
