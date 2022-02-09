package com.filipradon.locationupdatessample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

typealias PermissionsWithRequestCode = Pair<Array<String>, Int>


fun isAndroid28OrBelow() = android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P

fun isAndroid29() = android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.Q

class MainActivity : AppCompatActivity() {


    private val android28Permissions: PermissionsWithRequestCode
        get() = arrayOf(RuntimePermissionsManager.ACCESS_FINE_LOCATION) to RuntimePermissionsManager.REQUEST_CODE_ACCESS_FINE_LOCATION

    private val android29Permissions: PermissionsWithRequestCode
        get() = arrayOf(
            RuntimePermissionsManager.ACCESS_FINE_LOCATION,
            RuntimePermissionsManager.ACCESS_BACKGROUND_LOCATION
        ) to RuntimePermissionsManager.REQUEST_CODE_ACCESS_FINE_AND_BACKGROUND_LOCATION

    private val locationController by lazy { LocationController.getInstance(applicationContext) }

    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)


        button.setOnClickListener {
            if (locationController.isStarted) {
                locationController.stop()
                button.text = "START LOCATION UPDATES"
            } else {
                locationController.start()
                button.text = "STOP LOCATION UPDATED"
            }
        }

        when {
            isAndroid28OrBelow() -> requestPermissions(android28Permissions)
            isAndroid29() -> requestPermissions(android29Permissions)
            else -> {
                requestPermissions(android28Permissions)
            }
        }
    }

    private fun requestPermissions(permissionsWithRequestCode: Pair<Array<String>, Int>) {
        with(RuntimePermissionsManager(this)) {
            requestPermissions(
                permissionsWithRequestCode.first,
                permissionsWithRequestCode.second
            )

        }
    }
}