package cn.zgy.permission
/**
  *
  * @Description:     权限请求结果
  * @Author:         zhengy
  * @CreateDate:     2020/5/14 上午10:35
  * @Version:        1.0
 */

sealed class PermissionResult {
    /**
     * 全部通过
     */
    object Grant : PermissionResult()

    /**
     * 拒绝（包括部分拒绝）
     */
    class Rationale(val permissions: Array<String>) : PermissionResult()
    /**
     * 拒绝并勾选不再询问(全部勾选不再询问)
     */
    class Deny(val permissions: Array<String>) : PermissionResult()
}