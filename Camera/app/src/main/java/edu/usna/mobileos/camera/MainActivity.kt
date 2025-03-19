package edu.usna.mobileos.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.usna.mobileos.camera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (allPermissionsGranted()) {
            startCamera()
        } else   {
            requestPermissions()
    }
}