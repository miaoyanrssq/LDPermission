package cn.zgy.permission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *
 * @Description:    请求入口
 * @Author:         zhengy
 * @CreateDate:     2020/5/14 上午11:03
 * @Version:        1.0
 */

class LDPermission {

    companion object {
        const val TAG = "permissions"
    }

    @Volatile
    private var ldFragment: LDFragment? = null

    constructor(activity: AppCompatActivity) {
        ldFragment = getInstance(activity.supportFragmentManager)
    }

    constructor(fragment: Fragment) {
        ldFragment = getInstance(fragment.childFragmentManager)
    }

    private fun getInstance(manager: FragmentManager) = ldFragment ?: synchronized(this) {
        ldFragment ?: (if (null == manager.findFragmentByTag(TAG))
            LDFragment().apply {
                manager.beginTransaction().add(this, TAG).commitNow()
            } else {
            manager.findFragmentByTag(TAG) as LDFragment
        })
    }


    fun request(vararg permissions: String) = requestArray(permissions)

    fun requestArray(permissions: Array<out String>) = ldFragment!!.permissionResult.apply {
        ldFragment!!.requestPermission(permissions)
    }
}