//package com.example.xplorer.viewModels
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.xplorer.api.Notifier
//import com.example.xplorer.api.ToastNotifier
//import com.example.xplorer.api.world_bank.WorldBankData
//import com.example.xplorer.api.world_bank.WorldBankServiceImpl
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//class WBDataStorage (
//    @Inject private val api : WorldBankServiceImpl,
//    private val notifier : Notifier = ToastNotifier(),
//) : ViewModel() {
//
//    private val _countryList = MutableStateFlow<List<WorldBankData>> (emptyList())
//    val countryList : StateFlow<List<WorldBankData>> = _countryList
//
//    private val _isLoading = MutableStateFlow(true)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    private var i = 2
//
//    fun fetchCountryInfo (context : Context) {
//        val actualContext = context.applicationContext
//
//        viewModelScope.launch {
//            api.getTourismMostVisitedCountries(
//                notifier = notifier,
//                context = actualContext,
//                onSuccess = { data ->
//                    _countryList.value = data.sortedByDescending { it.value }
//                },
//                onFail = {
//                    notifier.notify("Something went wrong fetching info in World Bank!", context)
//                },
//                loadingFinished = {
//                    _isLoading.value = false
//                },
//                page = i++
//            )
//        }
//    }
//}