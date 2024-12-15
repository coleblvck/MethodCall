package com.coleblvck.methodcall

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Activity.ROLE_SERVICE
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.coleblvck.methodcall.services.ScreeningService

class ActivityTool(private val activity: ComponentActivity) {
    private var generalLauncher: ActivityResultLauncher<Intent> =
        activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent: Intent? = result.data
                //TODO? Maybe.
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun commenceBusiness() {
        checkAndRequestPermissions()
        //startScreeningService()
    }

    private fun startScreeningService() {
        val screeningServiceIntent = Intent(activity, ScreeningService::class.java)
        activity.startService(screeningServiceIntent)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkAndRequestPermissions() {
        requestCallScreeningRole()
        requestReadContactsPermission()
        //Runtime.getRuntime().exec("su")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestCallScreeningRole() {
        val roleManager = activity.getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        generalLauncher.launch(intent)
    }

    private fun overlayPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity.applicationContext,
            ACTION_MANAGE_OVERLAY_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestOverlayPermission() {
        val intent = Intent(ACTION_MANAGE_OVERLAY_PERMISSION)
        generalLauncher.launch(intent)
    }

    private fun requestReadContactsPermission() {
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            100);
    }
}