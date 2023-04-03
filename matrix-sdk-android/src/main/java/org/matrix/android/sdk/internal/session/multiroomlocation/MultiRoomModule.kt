package org.matrix.android.sdk.internal.session.multiroomlocation

import dagger.Binds
import dagger.Module

@Module
internal abstract class MultiRoomModule {

    @Module
    companion object {
        // Do providers here if necessary
    }

    @Binds
    abstract fun bindMultiRoomService(service: DefaultMultiRoomService): MultiRoomService
}
