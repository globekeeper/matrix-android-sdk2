package org.matrix.android.sdk.internal.session.user

enum class UserStatus(val value: Int) {
    PENDING(0),
    ACCEPTED(1),
    DELETED(2),
    DEACTIVATED(3),
    INQUEUE(4),
    EXPIRED(5),
    FAILED(6),
    VALIDATED(7),
    REMOVED(8);

    companion object {
        fun isNotDeactivatedOrRemoved(status: Int): Boolean {
            return status == DEACTIVATED.value || status == REMOVED.value
        }
    }
}
