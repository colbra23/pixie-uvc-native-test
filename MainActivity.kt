package com.pixielook.uvctest

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.jiangdg.ausbc.base.CameraActivity
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.camera.CameraRequest
import com.jiangdg.ausbc.camera.ICamera
import com.jiangdg.ausbc.render.env.RotateType
import com.jiangdg.ausbc.widget.AspectRatioSurfaceView
import com.jiangdg.ausbc.widget.IAspectRatio

class MainActivity : CameraActivity() {
    private lateinit var root: FrameLayout
    private lateinit var cameraView: AspectRatioSurfaceView
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterImmersiveMode()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enterImmersiveMode()
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        root = FrameLayout(this)
        root.setBackgroundColor(Color.BLACK)

        cameraView = AspectRatioSurfaceView(this)
        root.addView(cameraView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        ))

        statusText = TextView(this).apply {
            text = "Pixie UVC Test • plug in webcam, grant USB permission"
            setTextColor(Color.rgb(143, 229, 208))
            textSize = 18f
            setPadding(28, 18, 28, 18)
            setBackgroundColor(Color.argb(135, 0, 0, 0))
        }
        val statusParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.TOP or Gravity.START
        )
        statusParams.setMargins(22, 22, 22, 22)
        root.addView(statusText, statusParams)

        val hint = TextView(this).apply {
            text = "If Android asks: Allow Pixie UVC Test to access EMEET/USB device → OK"
            setTextColor(Color.WHITE)
            textSize = 14f
            setPadding(22, 12, 22, 12)
            setBackgroundColor(Color.argb(110, 0, 0, 0))
        }
        val hintParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        )
        hintParams.setMargins(22, 22, 22, 28)
        root.addView(hint, hintParams)

        return root
    }

    override fun getCameraView(): IAspectRatio? = cameraView

    override fun getCameraViewContainer(): ViewGroup? = root

    override fun getGravity(): Int = Gravity.CENTER

    override fun getCameraRequest(): CameraRequest {
        return CameraRequest.Builder()
            .setPreviewWidth(1280)
            .setPreviewHeight(720)
            .setRenderMode(CameraRequest.RenderMode.OPENGL)
            .setDefaultRotateType(RotateType.ANGLE_0)
            .setAudioSource(CameraRequest.AudioSource.NONE)
            .setPreviewFormat(CameraRequest.PreviewFormat.FORMAT_MJPEG)
            .setAspectRatioShow(true)
            .setCaptureRawImage(false)
            .setRawPreviewData(false)
            .create()
    }

    override fun onCameraState(self: ICamera, code: ICameraStateCallBack.State, msg: String?) {
        runOnUiThread {
            when (code) {
                ICameraStateCallBack.State.OPENED -> {
                    statusText.text = "Pixie UVC Test • camera opened"
                    Toast.makeText(this, "USB camera opened", Toast.LENGTH_SHORT).show()
                }
                ICameraStateCallBack.State.CLOSED -> {
                    statusText.text = "Pixie UVC Test • camera closed"
                }
                ICameraStateCallBack.State.ERROR -> {
                    statusText.text = "Pixie UVC Test • camera error: ${msg ?: "unknown"}"
                    Toast.makeText(this, "Camera error: ${msg ?: "unknown"}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun enterImmersiveMode() {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
        }
    }
}
