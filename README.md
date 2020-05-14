# LDPermission
基于livedata的权限请求

## 使用
基于livedata，并且为Androidx版本

```
implementation 'cn.zgy.permission:LDPermission:0.0.1'
```


```
 LDPermission(this).request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).observe(this, Observer {
            when(it){
                is PermissionResult.Grant -> {  //权限允许
                }
                is PermissionResult.Rationale -> {  //权限拒绝
                    it.permissions.forEach {s->
                        println("Rationale:${s}")
                    }
                }
                is PermissionResult.Deny -> {   //权限拒绝，且勾选了不再询问
                    it.permissions.forEach {s->
                        println("deny:${s}")
                    }
                }
            }
        })

    }
```
