package com.keelim.free.ui.inject

import android.content.Intent
import android.content.Intent.ACTION_PROCESS_TEXT
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.webkit.URLUtil
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keelim.data.model.UrlState
import com.keelim.free.R
import com.keelim.free.databinding.ActivityInjectBinding
import com.keelim.free.ui.auth.AuthActivity
import com.keelim.free.ui.custome.Titanic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class InjectActivity : AppCompatActivity() {
    private val binding: ActivityInjectBinding by lazy {
        ActivityInjectBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: InjectViewModel by viewModels()

    private val token by lazy {
        val pref = getSharedPreferences("token", MODE_PRIVATE)
        pref.getString("token", "")
    }
    val titanic by lazy { Titanic() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenCheck()
        setContentView(binding.root)
        initViews()
        intentControl()
        observeState()
    }

    private fun tokenCheck() {
        token ?: run {
            showToast("로그인을 위한 서비스 입니다.")
            finishAffinity()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun initViews() = with(binding) {
        delegate.isHandleNativeActionModesEnabled = false
        injectField.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (injectField.text!!.isEmpty()) {
                        injectFieldLayout.error = "정보를 입력해주세요!"
                    } else {
                        injectFieldLayout.error = null
                        var url = URLUtil.guessUrl(injectField.text.toString())
                        if (Patterns.WEB_URL.matcher(url).matches()) {
//                            viewModel.submitUrl(token!!, url)
                        } else {
                            injectFieldLayout.error = "올바른 URL을 입력해주세요"
                        }
                    }
                }
            }
        }
        injectField.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (injectField.text!!.isEmpty()) {
                    injectFieldLayout.error = "정보를 입력해주세요!"
                } else {
                    injectFieldLayout.error = null
                    var url = URLUtil.guessUrl(injectField.text.toString())
                    if (Patterns.WEB_URL.matcher(url).matches()) {
//                        viewModel.submitUrl(token!!, url)
                    } else {
                        injectFieldLayout.error = "올바른 URL을 입력해주세요"
                    }
                }
            }
            false
        }
    }

    private fun intentControl() {
        when (intent?.action) {
            ACTION_SEND -> {
                if (intent.type?.startsWith("text/") == true) {
                    handleText(intent)
                }
            }
            ACTION_PROCESS_TEXT -> {
                if (intent.type?.startsWith("text/") == true) {
                    handleProcessText(intent)
                }
            }
            else -> Unit
        }
    }

    private fun handleText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            binding.injectField.setText(it)
        }
    }

    private fun handleProcessText(intent: Intent) {
        intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.let {
            binding.injectField.setText(it)
        }
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when(it){
                    is UrlState.Error -> Unit
                    is UrlState.Loading -> Unit
                    is UrlState.Success1 -> {
                        val result = it.data.map { folder->
                            folder.folder_name
                        }
                        val adapter = ArrayAdapter(this@InjectActivity, R.layout.list_item, result)
                        binding.autoFolders.setAdapter(adapter)
                    }
                    is UrlState.Success2 -> Unit
                    is UrlState.UnInitialized -> Unit
                }
            }
        }
    }
}