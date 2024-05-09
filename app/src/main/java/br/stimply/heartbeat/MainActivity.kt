package br.stimply.heartbeat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = Api.createApiAccess()

        lifecycleScope.launch {
            while (true) {
                var index = 0
                val bpms = api.getAllBpm()
                Log.i("DEBUG", bpms.size.toString())
                val lc = findViewById<LineChart>(R.id.lineChart)
                val entries: ArrayList<Entry> = arrayListOf()
                for (bpm in bpms) {
                    entries.add(index++, Entry(index.toFloat(), bpm.beat.toFloat()))
                }
                val set = LineDataSet(entries, "Batimentos")
                set.setDrawIcons(false)
                set.enableDashedLine(1f, 1f, 0f)
                set.setColor(Color.WHITE)
                set.lineWidth = 1f;

                set.valueTextSize = 9f
                set.enableDashedHighlightLine(1f, 1f, 0f)
                set.setDrawFilled(false)
                set.notifyDataSetChanged()
                val lineData = LineData(set)
                lineData.notifyDataChanged()
                lc.clear()
                lc.data = lineData
                lc.notifyDataSetChanged()
                Thread.sleep(5000)
            }
        }
    }
}