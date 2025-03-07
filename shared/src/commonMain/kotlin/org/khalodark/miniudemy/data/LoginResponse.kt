package org.khalodark.miniudemy.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String, val userId: String)
