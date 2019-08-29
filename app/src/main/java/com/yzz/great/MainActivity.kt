package com.yzz.great

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yzz.great.util.Constant
import com.yzz.great.view.MainTabItemView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MainTabItemView.OnTabItemStateChangeListener {

    private val tabs = ArrayList<MainTabItemView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        handleNewIntent(intent)
        initListener()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNewIntent(intent!!)
    }

    private fun initView() {
        homeTabItemView.fragmentClass = HomeFragment::class.java
        home1TabItemView.fragmentClass = Home1Fragment::class.java
        home2TabItemView.fragmentClass = Home2Fragment::class.java
        mineTabItemView.fragmentClass = MineFragment::class.java

        tabs.add(homeTabItemView)
        tabs.add(home1TabItemView)
        tabs.add(home2TabItemView)
        tabs.add(mineTabItemView)
    }

    private fun initListener() {
        homeTabItemView.setOnTabItemStateChangeListener(this)
        home1TabItemView.setOnTabItemStateChangeListener(this)
        home2TabItemView.setOnTabItemStateChangeListener(this)
        mineTabItemView.setOnTabItemStateChangeListener(this)
    }

    override fun shouldChangeTabItemState(tabItemView: MainTabItemView?): Boolean {
        return !tabItemView!!.isItemStateSelected
    }

    override fun onTabItemStateChanged(tabItemView: MainTabItemView?) {
        var index = 0
        for (i in tabs.indices) {
            if (tabs[i] === tabItemView) {
                index = i
                break
            }
        }
        setIndexWithoutException(index)
    }

    private fun setIndexWithoutException(index: Int) {
        if (BuildConfig.DEBUG) {
            if (index >= tabs.size || index < 0) {
                throw RuntimeException("index >= tabs.size() || index < 0, current index = $index")
            }
        }
        try {
            val tab = tabs[index]
            tab.isItemStateSelected = !tab.isItemStateSelected
            setIndex(index)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    private fun setIndex(index: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        for (i in tabs.indices) {
            val tab = tabs[i]
            val fragmentClass = tab.fragmentClass
            val tag = fragmentClass.name
            val fragmentByTag = fragmentManager.findFragmentByTag(tag)

            if (i == index) {
                tab.isItemStateSelected = true
                // 添加tab
                if (fragmentByTag == null) {
                    fragmentTransaction.add(R.id.container, fragmentClass.newInstance(), tag)
                } else {
                    if (fragmentByTag.isDetached) {
                        fragmentTransaction.attach(fragmentByTag)
                    }
                }
            } else if (tab.isItemStateSelected) {
                tab.isItemStateSelected = false
                // 移除tab
                if (fragmentByTag != null) {
                    fragmentTransaction.detach(fragmentByTag)
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun handleNewIntent(intent: Intent) {
        val tabId = intent.getIntExtra(Constant.KEY_INDEX, Constant.INDEX_HOME)
        changeTab(tabId)
    }

    private fun changeTab(index: Int) {
        if (isFinishing) {
            return
        }
        if (index == Constant.INDEX_NONE) {
            throw RuntimeException("sorry,not entry app")
        } else {
            setIndexWithoutException(index)
        }
    }
}
