package com.tonghannteng.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform