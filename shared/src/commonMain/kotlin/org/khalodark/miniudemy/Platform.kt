package org.khalodark.miniudemy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform