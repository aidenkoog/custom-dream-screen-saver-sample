package io.github.aidenkoog.android.custom_dream

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.Message
import android.service.dreams.DreamService
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView

/**
 * AidenKooG as Author.
 * Code previously written in Java has been rewritten in Kotlin.
 * Handlers used in the existing Java world can be changed from Kotlin to coroutine solutions.
 * The source code was organized in a way to keep the past code as much as possible.
 * Please use the custom dream service code below for reference only.
 */

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class CustomDream: DreamService() {

    /* constants. */
    private val DREAMING = 1
    private val mMoveInterval: Long = 6560
    private var mStartTime: Long = 0
    private val SCREEN_TIMEOUT = 2 * 60 * 60 * 1000

    /* image view, animation. */
    private lateinit var mLogoImageView: ImageView
    private lateinit var mAnimationDrawable: AnimationDrawable

    /* handler */
    private lateinit var mHandler: Handler

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isFullscreen = true
        setContentView(R.layout.activity_main)

        Log.d("CustomDream", "screen saver activity is started")

        mLogoImageView = findViewById(R.id.logo)
        mAnimationDrawable = mLogoImageView.drawable as AnimationDrawable

        mHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun dispatchMessage(msg: Message) {

                if (msg.what === DREAMING) {
                    Log.d("CustomDream", "dispatch message which has 1 message what")
                    removeMessages(DREAMING)

                    mAnimationDrawable.stop()

                    val x = (Math.random() * 940).toInt() - 30
                    val y = (Math.random() * 380).toInt() - 30

                    mLogoImageView.x = x.toFloat()
                    mLogoImageView.y = y.toFloat()

                    mAnimationDrawable.start()

                    val currentTime = AnimationUtils.currentAnimationTimeMillis()
                    if (currentTime - mStartTime >= SCREEN_TIMEOUT) {
                        Log.d("CustomDream", "screen timeout !!!")
                        // TODO: Do something here.
                    }
                }
                sendEmptyMessageDelayed(DREAMING, mMoveInterval)
                super.dispatchMessage(msg)
            }
        }
    }

    override fun onDreamingStarted() {
        Log.d("CustomDream", "dreaming is started")

        mStartTime = AnimationUtils.currentAnimationTimeMillis()
        mHandler.sendEmptyMessage(DREAMING)

        super.onDreamingStarted()
    }

    override fun onDreamingStopped() {
        Log.d("CustomDream", "dreaming is stopped")

        mHandler.removeMessages(DREAMING)

        super.onDreamingStopped()
    }
}