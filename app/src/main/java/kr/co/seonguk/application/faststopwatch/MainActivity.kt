package kr.co.seonguk.application.faststopwatch

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.seonguk.application.faststopwatch.databinding.ActivityMainBinding
import kr.co.seonguk.application.faststopwatch.databinding.DialogCountdownBinding
import java.util.Timer
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var countDownSecond = 10
    private var currentDeciSecond = 0
    private var timer : Timer? = null

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
            currentDeciSecond += 1

            val minutes = currentDeciSecond.div(10) / 60
            val second = currentDeciSecond.div(10) % 60
            val deciSecond = currentDeciSecond % 10

            runOnUiThread {
                binding.textTimeView.text = String.format("%02d:%02d", minutes, second)
                binding.textviewTic.text = deciSecond.toString()
            }
        }
    }

    //종료 버튼을 눌렀을 때
    private fun putStop() {

    }

    //일시정지
    private fun putPause() {
        timer?.cancel()
        timer = null
    }

    //기록
    private fun putLab() {

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
        }
    }
}