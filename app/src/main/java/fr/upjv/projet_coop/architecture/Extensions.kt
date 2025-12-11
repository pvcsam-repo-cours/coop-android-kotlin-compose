package fr.upjv.projet_coop.architecture

import android.content.Context

fun Context.getCustomApplication(): CustomApplication {
    return (this.applicationContext as? CustomApplication)
        ?: throw IllegalStateException("Application must be an instance of CustomApplication")
}

