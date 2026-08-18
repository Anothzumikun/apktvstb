package com.farnz.tvplayer

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs: SharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
        val editUrl = findViewById<EditText>(R.id.editPlaylistUrl)

        editUrl.setText(prefs.getString(MainActivity.KEY_PLAYLIST_URL, ""))

        findViewById<android.widget.Button>(R.id.btnSave).setOnClickListener {
            val url = editUrl.text.toString().trim()
            if (url.isBlank()) {
                Toast.makeText(this, "URL tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            prefs.edit().putString(MainActivity.KEY_PLAYLIST_URL, url).apply()
            Toast.makeText(this, "Playlist disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
