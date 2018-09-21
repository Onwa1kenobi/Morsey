package io.jul.morsey

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("font/raleway_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}
