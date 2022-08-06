package id.one.backgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btn_start)
        val tvStatus: TextView = findViewById(R.id.tv_status)

        // Membuat sebuah thread baru
        val executor = Executors.newSingleThreadExecutor()
        // Membuat handler untuk update hasil ke UI dari background thread/process
        val handler = Handler(Looper.getMainLooper()) // getMainLooper agar handler di jalankan di main/UI thread

        btnStart.setOnClickListener {
            executor.execute {
                try {
                    // simulate process compressing
                    for(i in 0..10){
                        Thread.sleep(500)
                        val persentage =  i * 10

                        handler.post {
                            // update ui in main thread
                            if(persentage == 100){
                                tvStatus.setText(R.string.task_completed)
                            }else{
                                tvStatus.text = String.format(getString(R.string.compressing),persentage)
                            }
                        }

                    }
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }
}