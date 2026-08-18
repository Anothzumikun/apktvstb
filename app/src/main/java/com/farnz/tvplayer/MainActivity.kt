package com.farnz.tvplayer

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var recyclerChannels: RecyclerView
    private lateinit var txtEmpty: View
    private lateinit var progressLoading: View

    companion object {
        const val PREFS_NAME = "tvplayer_prefs"
        const val KEY_PLAYLIST_URL = "playlist_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        recyclerChannels = findViewById(R.id.recyclerChannels)
        txtEmpty = findViewById(R.id.txtEmpty)
        progressLoading = findViewById(R.id.progressLoading)
        recyclerChannels.layoutManager = LinearLayoutManager(this)

        findViewById<View>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val url = prefs.getString(KEY_PLAYLIST_URL, null)
        if (url.isNullOrBlank()) {
            showEmpty(true)
        } else {
            loadPlaylist(url)
        }
    }

    private fun loadPlaylist(url: String) {
        progressLoading.visibility = View.VISIBLE
        showEmpty(false)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val channels = withContext(Dispatchers.IO) {
                    M3UParser.fetchAndParse(url)
                }
                progressLoading.visibility = View.GONE

                if (channels.isEmpty()) {
                    showEmpty(true)
                } else {
                    recyclerChannels.adapter = ChannelAdapter(channels) { channel ->
                        val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                        intent.putExtra("stream_url", channel.streamUrl)
                        intent.putExtra("channel_name", channel.name)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                progressLoading.visibility = View.GONE
                showEmpty(true)
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.load_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showEmpty(show: Boolean) {
        txtEmpty.visibility = if (show) View.VISIBLE else View.GONE
        recyclerChannels.visibility = if (show) View.GONE else View.VISIBLE
    }
}
