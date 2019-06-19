package com.example.app

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.wangfeixixi.base.fram.BaseActivity
import com.wangfeixixi.logx.LogAndroid
import com.wangfeixixi.logx.LogXConfig
import com.wangfeixixi.logx.ui.XixiFileUtils
import java.io.File

class TestActivity : BaseActivity<FileDelegate>() {

    // 要申请的权限
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private var dialog: AlertDialog? = null

    private var files: List<File>? = null

    override fun getDelegateClass(): Class<FileDelegate> {
        return FileDelegate::class.java;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取读写权限
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            val i = ContextCompat.checkSelfPermission(this, permissions[0])
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission()
            } else {
                refreshData()
            }
        }


        viewDelegate.setOnClickListener(View.OnClickListener {
            if (it.id == R.id.btn_true) {
                IconUtils.enable(true, applicationContext)
            } else if (it.id == R.id.btn_false) {
                IconUtils.enable(false, applicationContext)
            }
        }, R.id.btn_true, R.id.btn_false)
    }


    private fun refreshData() {
        LogAndroid.d("来一次")
        files = XixiFileUtils.getFiles(LogXConfig.getDirpath())
        val sb = StringBuffer()
        for (file in files!!)
            sb.append("\n" + file.name + " | " + file.length() + "b")

        viewDelegate.setLog(sb.toString())
    }


    // 提示用户该请求权限的弹出框
    private fun showDialogTipUserRequestPermission() {

        AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("log需要存储本地，无存储权限，log将无法存储本地")
                .setPositiveButton("立即开启") { dialog, which -> startRequestPermission() }
                .setNegativeButton("取消") { dialog, which -> finish() }.setCancelable(false).show()
    }

    // 开始提交请求权限
    private fun startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321)
    }

    // 用户权限 申请 的回调方法
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    val b = shouldShowRequestPermissionRationale(permissions[0])
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting()
                    } else
                        finish()
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show()
                    refreshData()
                }
            }
        }
    }
    // 提示用户去应用设置界面手动开启权限

    private fun showDialogTipUserGoToAppSettting() {
        dialog = AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启") { dialog, which ->
                    // 跳转到应用设置界面
                    goToAppSetting()
                }
                .setNegativeButton("取消") { dialog, which -> finish() }.setCancelable(false).show()
    }

    // 跳转到当前应用的设置界面
    private fun goToAppSetting() {
        val intent = Intent()

        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri

        startActivityForResult(intent, 123)
    }

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                val i = ContextCompat.checkSelfPermission(this, permissions[0])
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting()
                } else {
                    if (dialog != null && dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show()
                    refreshData()
                }
            }
        }
    }
}
