package org.matrix.android.sdk.internal.session.multiroomlocation

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.matrix.android.sdk.internal.session.SessionScope
import retrofit2.Retrofit

@Module
internal abstract class MultiRoomModule {

    @Binds
    abstract fun bindMultiRoomService(service: DefaultMultiRoomService): MultiRoomService

    @Binds
    abstract fun bindGKSyncLocationTask(task: DefaultGKSyncLocationTask): GKSyncLocationTask

    @Module
    companion object {
        @Provides
        @JvmStatic
        @SessionScope
        fun providesMultiRoomAPI(retrofit: Retrofit): MultiRoomAPI {
            return retrofit.create(MultiRoomAPI::class.java)
        }
    }
}
