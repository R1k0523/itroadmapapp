package ru.boringowl.itroadmap.ui.features.profileedit.state

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class ProfileEditState(
    val user: UserInfo? = null,
    val success: Boolean = false,
    val passwordForm: PasswordForm = PasswordForm(),
    val verificationForm: VerificationForm = VerificationForm(),
    override val loading: Boolean = false,
    override val error: UIError? = null,
) : UiState


data class PasswordForm(
    val currentPassword: String = "",
    val newPassword: String = "",
    val matchingPassword: String = "",
    val currentPasswordVisible: Boolean = false,
    val newPasswordVisible: Boolean = false,
    val matchingPasswordVisible: Boolean = false,
)

data class VerificationForm(
    val codeRequested: Long = 0L,
    val code: String = ""
) {
    fun canBeRequested() = System.currentTimeMillis().minus(codeRequested) > 1000 * 60
}
@Serializable
data class UserInfo(
    val username: String,
    val email: String,
    val enabled: Boolean,
    val description: String,
    val fullName: String,
    val rating: Long,
)

fun User.toInfo() = UserInfo(
    username = username,
    email = email,
    enabled = enabled,
    description = description,
    fullName = fullName,
    rating = rating,
)
