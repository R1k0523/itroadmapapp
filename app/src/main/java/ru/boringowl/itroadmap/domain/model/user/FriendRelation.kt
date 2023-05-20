package ru.boringowl.itroadmap.domain.model.user

import java.util.UUID

data class FriendRelation(
    var userId: UUID,
    var friendId: UUID,
)
