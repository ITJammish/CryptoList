package com.itj.cryptoviewer.view.cryptolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryRecyclerViewAdapter
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crypto_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_refresh -> viewModel.fetchData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindViews() {
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
