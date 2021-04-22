package com.chj.electronicboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chj.electronicboard.databinding.ActivitySettingBinding
import java.lang.Exception
import java.text.SimpleDateFormat


class SettingActivity: AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //부풀어라 얍!
        binding = ActivitySettingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            if(checkValue()){
                val context:Context = applicationContext

                //이제는 mainActivity로 돌아갈 시간
                val intent = Intent(context, MainActivity::class.java)

                var month : String

                //월 정보를 자리수를 맞춰 표기한다.
                //DatePicker의 month는 1~12월을 0~11로 표시한다는 것을 잊지말자
                if(binding.dpStartDate.month+1 < 10){
                    //한 자리수면 0을 붙여줘
                    month = "0"+(binding.dpStartDate.month+1).toString()
                }else{
                    month = (binding.dpStartDate.month+1).toString()
                }
                val Title = binding.txtTitle.text.toString()
                val DateString = binding.dpStartDate.year.toString() + "-"+month+"-"+binding.dpStartDate.dayOfMonth


                /*data store logic required*/
                writeFile(context,Title,DateString)

                startActivity(intent)
            }
        }
    }

    fun checkValue():Boolean{
        //title check
        if(binding.txtTitle.text.isBlank()||binding.txtTitle.text.isEmpty()){
            Toast.makeText(applicationContext,"제목을 입력하세요",Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    fun writeFile(context:Context, title:String, date:String){
        //파일을 열기 위해 Context를 입력받음.

        val FileName = "GoalData"
        val FileContents = title + "\n" + date

        try{
            val fos = openFileOutput(FileName,Context.MODE_PRIVATE)
            fos.write(FileContents.toByteArray())
        }catch (e:Exception){
            Log.e("File not writed",e.message.toString())
        }
    }

}