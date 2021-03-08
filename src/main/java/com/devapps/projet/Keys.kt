package com.devapps.projet

object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}