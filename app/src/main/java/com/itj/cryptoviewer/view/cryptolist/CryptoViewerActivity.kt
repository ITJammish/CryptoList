package com.itj.cryptoviewer.view.cryptolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.GenericError
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.NetworkError
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryRecyclerViewAdapter
import dagger.android.AndroidInjection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        with(viewModel) {
            cryptoListData.safeCollect { cryptoListAdapter.setData(it) }

            error.safeCollect {
                when (it) {
                    GenericError,
                    NetworkError ->
                        Toast.makeText(this@CryptoViewerActivity, getString(it.resId), Toast.LENGTH_LONG).show()

                    else -> Unit
                }
            }
        }
    }

    private fun <T> Flow<T>.safeCollect(block: (T) -> Unit) {
        flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { block(it) }
            .launchIn(lifecycleScope)
    }
}
