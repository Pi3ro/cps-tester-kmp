package me.pi3ro.cps

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform