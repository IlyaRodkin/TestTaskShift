package by.rodkin.testtaskshift.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.rodkin.testtaskshift.data.model.BinInfo
import by.rodkin.testtaskshift.domain.BinRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val repo: BinRepo
) : ViewModel() {

    init {
        getHistoryFlow()
    }

    private val _bikInfo = MutableStateFlow<BinInfo?>(null)
    val bikInfo: StateFlow<BinInfo?> = _bikInfo

    private val _bikInfoHist = MutableStateFlow<List<BinInfo>?>(null)
    val bikInfoHist: StateFlow<List<BinInfo>?> = _bikInfoHist

    private val _exception = MutableStateFlow<String?>(null)
    val exception: StateFlow<String?> = _exception

    fun getInfoFromBinNetwork(bin: String) = viewModelScope.launch {
        try {
            repo.getInfoFromBinNetwork(bin)
        } catch (e: Exception) {
            _exception.tryEmit(e.stackTrace.toString())
        }
    }

    private fun getHistoryFlow() = viewModelScope.launch {
        repo.getHistory().collect {
            _bikInfoHist.tryEmit(it)
        }
    }

    fun cleanException() {
        _exception.tryEmit(null)
    }
}