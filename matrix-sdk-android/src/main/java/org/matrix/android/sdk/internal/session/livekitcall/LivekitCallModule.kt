package org.matrix.android.sdk.internal.session.livekitcall

import dagger.Binds
import dagger.Module
import org.matrix.android.sdk.api.session.livekitcall.LivekitCallSignalingService

@Module
internal abstract class LivekitCallModule {

    @Binds
    abstract fun bindLivekitCallSignalingService(service: DefaultLivekitCallSignalingService): LivekitCallSignalingService
}
