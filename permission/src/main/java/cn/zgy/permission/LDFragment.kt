package cn.zgy.permission

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

/**
  *
  * @Description:     权限请求页面
  * @Author:         zhengy
  * @CreateDate:     2020/5/14 上午10:37
  * @Version:        1.0
 */

internal class LDFragment : Fragment(){
    var permissionResult: MutableLiveData<PermissionResult> = MutableLiveData()
    private val REQUEST_CODE = 0x1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermission(permissions: Array<out String>){
        val temp = ArrayList<String>()
        //遍历剔除已经同意的权限，只请求未同意过的权限
        permissions.forEach {
            if(activity?.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED){
                temp.add(it)
            }
        }
        if(temp.isEmpty()){
            permissionResult.value = PermissionResult.Grant
        }else{
            requestPermissions(temp.toTypedArray(), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE){
            val deny = ArrayList<String>()
            val rationale = ArrayList<String>()
            for((index, value) in grantResults.withIndex()){
                if(value == PackageManager.PERMISSION_DENIED){
                    //不勾选不再询问选项
                    if(shouldShowRequestPermissionRationale(permissions[index])){
                        rationale.add(permissions[index])
                    }else{
                        deny.add(permissions[index])
                    }
                }
            }
            if(deny.isEmpty() && rationale.isEmpty()){
                permissionResult.value = PermissionResult.Grant
            }else{
                if(rationale.isNotEmpty()){
                    permissionResult.value = PermissionResult.Rationale(rationale.toTypedArray())
                } else if(deny.isNotEmpty()){
                    permissionResult.value = PermissionResult.Deny(deny.toTypedArray())
                }
            }
        }

    }

}