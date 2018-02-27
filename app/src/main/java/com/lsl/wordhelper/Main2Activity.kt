package com.lsl.wordhelper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lsl.wh.R
import com.lsl.wordhelper.job.ScheduleManager
import com.lsl.wordhelper.util.*
import com.lsl.wordhelper.wordundo.WordDoneFragment
import com.lsl.wordhelper.wordundo.WordUndoFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val tag = "Main2Activity"
    private var wordUndoFragment: WordUndoFragment? = null
    private var wordDoneFragment: WordDoneFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        startClipboardJob()
        toolbar.title = "待完成"
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        drawer_layout.setStatusBarBackground(R.color.colorPrimary)

        nav_view.setNavigationItemSelectedListener(this)

        setPage(0)
    }

    private fun setPage(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hiddenFragment(transaction)
        when (index) {
            0 -> {
                if (wordUndoFragment == null) {
                    wordUndoFragment = WordUndoFragment.newInstance()
                    transaction.add(R.id.contentFrame, wordUndoFragment)
                } else {
                    transaction.show(wordUndoFragment)
                }
            }
            1 -> {
                if (wordDoneFragment == null) {
                    wordDoneFragment = WordDoneFragment.newInstane()
                    transaction.add(R.id.contentFrame, wordDoneFragment)
                } else {
                    transaction.show(wordDoneFragment)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun hiddenFragment(transaction: FragmentTransaction) {
//        wordUndoFragment?.let {
//            hideFragmentInActivity(it)
//        }
        if (wordUndoFragment != null) {
            transaction.hide(wordUndoFragment)
        }
        if (wordDoneFragment != null) {
            transaction.hide(wordDoneFragment)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_no_finish -> {
                toolbar.title = "待完成"
                setPage(0)
            }
            R.id.nav_finished -> {
                toolbar.title = "已完成"
                setPage(1)
            }
//            R.id.nav_slideshow -> {
//                snackbar(fab, "nav_slideshow")
//                toolbar.title = "slideshow "
//            }
//            R.id.nav_manage -> {
//                snackbar(fab, "nav_manage")
//                toolbar.title = "nav_manage"
//            }
//            R.id.nav_share -> {
//                snackbar(fab, "nav_share")
//            }
//            R.id.nav_send -> {
//                snackbar(fab, "nav_send")
//            }
        }


        return true
    }

    override fun onResume() {
        super.onResume()

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
