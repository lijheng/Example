package com.summer.example.protobuf


object ExampleLib {

    fun getPolicy(userInfo: Example.UserInfo) : Example.Policy {
        val userInfoData = userInfo.toByteArray()
        val policyData = Lib.getPolicy(userInfoData, userInfoData.size)
        return Example.Policy.parseFrom(policyData)
    }

    object Lib {

        init {
            System.loadLibrary("example_protobuf")
        }

        external fun getPolicy(userInfo: ByteArray, length: Int): ByteArray

    }
}