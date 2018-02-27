package com.lsl.wordhelper

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import com.lsl.wh.R
import com.lsl.wordhelper.data.Repository
import com.lsl.wordhelper.job.ScheduleManager
import com.lsl.wordhelper.util.replaceFragmentInActivity
import com.lsl.wordhelper.util.setupActionBar
import com.lsl.wordhelper.wordundo.WordUndoFragment
import com.lsl.wordhelper.wordundo.WordPresenter


class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var wordPresenter: WordPresenter
    private lateinit var navigationView: NavigationView


    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startClipboardJob()
        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }

        navigationView = findViewById<NavigationView>(R.id.nav_view).apply {
            itemTextColor = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
            itemIconTintList = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
        }
        setupDrawerContent(navigationView)

        val wordFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as WordUndoFragment? ?: WordUndoFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        wordPresenter = WordPresenter(
                Repository.providerWordRepository(this@MainActivity),
                wordFragment)

    }

    /**
     * @param navigationView
     * Todo
     * 侧边栏监听
     */
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            resetAllMenuItemsTextColor(navigationView)
            if (menuItem.itemId == R.id.statistics_navigation_menu_item) {
//                跳转 Activity
            }

//            当侧边栏选中的时候将其关闭
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }


    private fun setTextColorForMenuItem(menuItem: MenuItem, @ColorRes color: Int) {
        val spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, color)),
                0,
                spanString.length,
                0)
        menuItem.title = spanString
    }

    private fun resetAllMenuItemsTextColor(navigationView: NavigationView) {
        for (i in 0..navigationView.menu.size()) {
            setTextColorForMenuItem(navigationView.menu.getItem(i), R.color.colorPrimary)
        }

    }

    private fun startClipboardJob() {
        // 启动监听粘贴板的 JobService
        ScheduleManager.scheduleClipboardJob(applicationContext)
        if (Build.VERSION.SDK_INT >= 23) { // 6.0以上
            goSetting()
        } else {// 6.0以下

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun goSetting() {
        if (!Settings.canDrawOverlays(applicationContext)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }
}
