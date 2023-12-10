package com.yachae.yachaesori.util

// 예외 처리를 위한 Result 클래스 정의
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}