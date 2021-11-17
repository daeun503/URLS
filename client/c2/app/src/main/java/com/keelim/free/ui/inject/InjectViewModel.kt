package com.keelim.free.ui.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.UrlState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class InjectViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<UrlState> = MutableStateFlow(UrlState.UnInitialized)
    val state: StateFlow<UrlState> get() = _state

    init{
        init()
    }

    fun init() = viewModelScope.launch {
        _state.emit(UrlState.Loading)
        kotlin.runCatching {
            urlUseCase.myFolder()
        }.onSuccess {
            _state.emit(UrlState.Success1(it))
        }.onFailure {
            _state.emit(UrlState.Error("에러를 표시해줍니다."))
        }
    }

    fun submitUrl(url:String, memo:String, folderId:String, tags:List<String>) = viewModelScope.launch {
        _state.emit(UrlState.Loading)
        runCatching {
            // 새로운 URL 을 폴더에 생성
        }.onSuccess {
            // 그 url 에 메모 추가하기
        }.onFailure {
            
        }
    }
}