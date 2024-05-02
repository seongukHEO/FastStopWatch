package kr.co.seonguk.application.faststopwatch

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.seonguk.application.faststopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingEvent()
    }


    private fun settingEvent(){
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
        }
    }


    //시작 버튼을 눌렀을 때
    private fun putStart(){

    }

    //종료 버튼을 눌렀을 때
    private fun putStop(){

    }

    //일시정지
    private fun putPause(){

    }

    //기록
    private fun putLab(){

    }


    //다이얼로그
    private fun showAlertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("스톱워치 초기화")
            setMessage("스톱워치를 초기화 하시겠습니까?")
            setPositiveButton("확인"){_, _ ->
                putStop()
            }
            setNegativeButton("취소", null)
        }
    }
}