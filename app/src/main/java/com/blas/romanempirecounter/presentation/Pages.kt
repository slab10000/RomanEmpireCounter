package com.blas.romanempirecounter.presentation

sealed class Pages {
    data object Counter: Pages()
    data object History: Pages()
}