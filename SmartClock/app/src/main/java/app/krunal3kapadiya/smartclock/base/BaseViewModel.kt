package app.krunal3kapadiya.smartclock.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val mCompositeDisposable: CompositeDisposable? = null

    override fun onCleared() {
        mCompositeDisposable!!.dispose()
        super.onCleared()
    }
}