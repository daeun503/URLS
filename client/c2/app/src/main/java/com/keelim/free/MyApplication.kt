package com.keelim.free

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.google.android.material.color.DynamicColors
import com.keelim.free.util.ComponentLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var componentLogger: ComponentLogger

    override fun onCreate() {
        super.onCreate()
        componentLogger.initialize(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}