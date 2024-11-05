package com.summer.example.protobuf

import org.junit.Assert.*

import org.junit.Test
import java.util.UUID

class ExampleLibTest {

    @Test
    fun getPolicy() {
        @Test
        fun getPolicy() {
            val info = Example.UserInfo.newBuilder()
                .setUserid("haha")
                .setDeviceId(UUID.randomUUID().toString())
                .build()
            val policy = ExampleLib.getPolicy(info)
            println("policy=== enable: ${policy.enable}, background: ${policy.background}")
        }
    }
}