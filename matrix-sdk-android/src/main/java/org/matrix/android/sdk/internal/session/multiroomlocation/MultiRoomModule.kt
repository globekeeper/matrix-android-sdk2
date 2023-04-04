package org.matrix.android.sdk.internal.session.multiroomlocation

import dagger.Binds
import dagger.Module

@Module
internal abstract class MultiRoomModule {

    @Binds
    abstract fun bindMultiRoomService(service: DefaultMultiRoomService): MultiRoomService
}
