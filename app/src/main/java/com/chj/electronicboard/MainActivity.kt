package com.chj.electronicboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.chj.electronicboard.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding:ActivityMainBinding

    //kitkat을 위한 JAVA식 날짜 표현으로 오늘 날짜 구하기
    val time = System.currentTimeMillis()
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    //오늘은 영어로 today
    val today = sdf.format(time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize binding variable
        //부풀어라 얍!
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        //activity_main 연결
        /* == setContentView(R.layout.activity_main)*/
        setContentView(binding.root)


        //initialize date textview
        binding.lblDate.text = today.toString()

        //목표 제목과 날짜가 저장된 파일을 읽어 뷰에 데이터 전달
        readFile(applicationContext)

        //의지 단추를 누르면,
        binding.btnSet.setOnClickListener {
            //setting activity로
            val intent = Intent(applicationContext, SettingActivity::class.java)
            //출바알
            startActivity(intent)
        }

    }

    fun readFile(context:Context){
        //파일을 열려면 context가 필요하다.
        val FileName = "GoalData"
        try{
            //open file
            val fis = openFileInput(FileName)
            //데이터를 읽어서
            val isreader = InputStreamReader(fis)
            //버퍼에 살며시 넣어두고
            val br = BufferedReader(isreader)
            var array:ArrayList<String> = ArrayList<String>()

            br.forEachLine {
                /*
                   문자열형 ArrayList에 파일에서 읽어온 제목[0번]과 날짜[1번]를 저장
                * */
                array.add(it)
            }

            binding.lblTitle.text = array[0]

            /*
            오늘은 설정한 날짜로부터 얼마나 떨어져 있는가?
            설정한 날짜와 오늘 날짜를 Date형으로 변환*/
            val startDate = array[1]
            val startingPoint = sdf.parse(startDate)
            val current = sdf.parse(today)

            //날짜의 차이를 밀리초 단위로 계산 -> 절대값을 사용하지 않고 미래에 설정한 날까지 얼마나 남았는지 확인할 수 있도록 설계
            val diffInMillis = current.time - startingPoint.time
            //밀리초를 날짜로 변환
            val diff = TimeUnit.DAYS.convert(diffInMillis,TimeUnit.MILLISECONDS)

            binding.lblPeriod.text = "DAY "+diff.toString()
        }
        catch (fne:FileNotFoundException){
            binding.lblTitle.text = "Set your goal"
        }
        catch (e:Exception){
            Log.e("Cannot read file",e.message.toString())
        }
    }
}