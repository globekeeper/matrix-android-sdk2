package org.matrix.android.sdk.internal.session.livekitcall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.matrix.android.sdk.api.session.livekitcall.MxLivekitCall
import org.matrix.android.sdk.internal.session.SessionScope
import javax.inject.Inject

@SessionScope
internal class ActiveLivekitCallHandler @Inject constructor() {

    private val activeCallListLiveData: MutableLiveData<MutableList<MxLivekitCall>> by lazy {
        MutableLiveData<MutableList<MxLivekitCall>>(mutableListOf())
    }

    fun addCall(call: MxLivekitCall) {
        activeCallListLiveData.postValue(activeCallListLiveData.value?.apply { add(call) })
    }

    fun removeCall(callId: String) {
        activeCallListLiveData.postValue(activeCallListLiveData.value?.apply { removeAll { it.callId == callId } })
    }

    fun getCallWithId(callId: String): MxLivekitCall? {
        return activeCallListLiveData.value?.find { it.callId == callId }
    }

    fun getActiveCallsLiveData(): LiveData<MutableList<MxLivekitCall>> = activeCallListLiveData
}
