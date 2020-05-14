package cn.tmy.ldperminsson

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import cn.zgy.permission.LDPermission
import cn.zgy.permission.PermissionResult

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requstPermission()
    }

    private fun requstPermission() {

        LDPermission(this).request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).observe(this, Observer {
            when(it){
                is PermissionResult.Grant -> {  //权限允许
                    Toast.makeText(this, "Grant", Toast.LENGTH_SHORT).show()
                }
                is PermissionResult.Rationale -> {  //权限拒绝
                    it.permissions.forEach {s->
                        println("Rationale:${s}")
                    }
                    Toast.makeText(this, "Rationale", Toast.LENGTH_SHORT)
                        .show()
                }
                is PermissionResult.Deny -> {   //权限拒绝，且勾选了不再询问
                    it.permissions.forEach {s->
                        println("deny:${s}")
                    }
                    Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}
