package com.kapiszewski.mateusz.legoblockcollector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kapiszewski.mateusz.legoblockcollector.models.Singleton
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        save_settings_button.setOnClickListener {
            Singleton.URL = inventory_url_edit_text.text.toString()
            this.finish()
        }
    }
}
