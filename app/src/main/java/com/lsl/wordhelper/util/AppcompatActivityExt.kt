package com.lsl.wordhelper.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.addFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        add(frameId, fragment)
    }
}

fun AppCompatActivity.hideFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        hide(fragment)
    }
}

fun AppCompatActivity.showFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        show(fragment)
    }
}


//调用某对象的 apply 函数，在函数块内可以通过 this 指代该对象。返回值为该对象自己。
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}