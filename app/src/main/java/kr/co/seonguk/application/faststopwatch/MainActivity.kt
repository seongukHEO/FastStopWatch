package kr.co.seonguk.application.faststopwatch

import android.app.AlertDialog
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.seonguk.application.faststopwatch.databinding.ActivityMainBinding
import kr.co.seonguk.application.faststopwatch.databinding.DialogCountdownBinding
import java.util.Timer
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var countDownSecond = 10
    private var currentCountDownDeciSecond = countDownSecond * 10
    private var currentDeciSecond = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingEvent()
    }


    private fun settingEvent() {
        binding.apply {
            buttonStart.setOnClickListener {
                putStart()
                buttonStart.isVisible = false
                buttonStop.isVisible = false
                buttonLab.isVisible = true
                buttonPause.isVisible = true
            }

            buttonStop.setOnClickListener {
                showAlertDialog()
                buttonStart.isVisible = true
                buttonStop.isVisible = true
                buttonLab.isVisible = false
                buttonPause.isVisible = false
            }

            buttonLab.setOnClickListener {
                putLab()
            }

            buttonPause.setOnClickListener {
                putPause()
                buttonStart.isVisible = true
                buttonStop.isVisible = true
                buttonLab.isVisible = false
                buttonPause.isVisible = false
            }
            textviewCountDown.setOnClickListener {
                showCountDownDialog()
            }

        }
    }


    //시작 버튼을 눌렀을 때
    private fun putStart() {
        timer = timer(initialDelay = 0, period = 100) {

            if (currentCountDownDeciSecond == 0) {
                currentDeciSecond += 1

                val minutes = currentDeciSecond.div(10) / 60
                val second = currentDeciSecond.div(10) % 60
                val deciSecond = currentDeciSecond % 10

                runOnUiThread {
                    binding.textTimeView.text = String.format("%02d:%02d", minutes, second)
                    binding.textviewTic.text = deciSecond.toString()

                    binding.groupTop.isVisible = false
                }
            } else {
                currentCountDownDeciSecond -= 1
                val seconds = currentCountDownDeciSecond / 10
                val progress = (currentCountDownDeciSecond / (countDownSecond * 10f)) * 100

                binding.root.post {
                    binding.textviewCountDown.text = String.format("%02d", seconds)
                    binding.countDownProgressBar.progress = progress.toInt()
                }

            }
            if (currentDeciSecond == 0 && currentCountDownDeciSecond < 31 && currentCountDownDeciSecond % 10 == 0){
                val toneType = if (currentCountDownDeciSecond == 0) ToneGenerator.TONE_CDMA_HIGH_L else ToneGenerator.TONE_CDMA_ANSWER
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(toneType, 500)
            }
        }
    }

    //종료 버튼을 눌렀을 때
    private fun putStop() {
        currentDeciSecond = 0
        binding.textTimeView.text = "00:00"
        binding.textviewTic.text = "0"
        binding.lapContainerLinear.removeAllViews()
    }

    //일시정지
    private fun putPause() {
        timer?.cancel()
        timer = null
    }

    //기록
    private fun putLab() {

        if (currentDeciSecond == 0) return

        val container = binding.lapContainerLinear
        TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSecond = currentDeciSecond % 10
            text = "${container.childCount.inc()}. " + String.format(
                "%02d:%02d %01d", minutes, seconds, deciSecond
            )
            setPadding(30, 30, 30, 30)
        }.let {
            container.addView(it, 0)
        }
    }


    private fun showCountDownDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("CountDown 시간 설정")
            val dialogBinding = DialogCountdownBinding.inflate(layoutInflater)
            setView(dialogBinding.root)
            with(dialogBinding.countDownSecondPicker) {
                maxValue = 20
                minValue = 0
                value = countDownSecond
            }
            setPositiveButton("확인") { _, _ ->
                countDownSecond = dialogBinding.countDownSecondPicker.value
                currentCountDownDeciSecond = countDownSecond * 10
                binding.textviewCountDown.text = String.format("%02d", countDownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    //다이얼로그
    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("스톱워치 초기화")
            setMessage("스톱워치를 초기화 하시겠습니까?")
            setPositiveButton("확인") { _, _ ->
                putStop()
            }
            setNegativeButton("취소", null)
        }.show()
    }
}