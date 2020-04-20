package `in`.curioustools.bodybuildingkotlin.screens.dashboard

import `in`.curioustools.bodybuildingkotlin.screens.aboutme.AboutMeActivity
import `in`.curioustools.bodybuildingkotlin.screens.challangelist.ChallengesActivity
import `in`.curioustools.bodybuildingkotlin.databinding.ActivityDashboardBinding
import `in`.curioustools.bodybuildingkotlin.modal.DailyLog
import `in`.curioustools.bodybuildingkotlin.modal.Exercise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


@SuppressLint("SetTextI18n")

class DashBoardActivity : AppCompatActivity() {
    private var binding:ActivityDashboardBinding?=null


    private val hybridList = ArrayList<Any>()
    val adp =DashBoardHybridAdapter()
    val TAG ="MainAct>>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        initUi()

    }

    private fun initUi() {

        val gridManager = GridLayoutManager(this, 2)
        gridManager.spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(i: Int)= if(hybridList[i] is DailyLog) 1 else 2
            }

        binding?.rvMergedDashboard?.layoutManager =gridManager
        binding?.rvMergedDashboard?.adapter=adp

    }

    override fun onStart() {
        super.onStart()
        updateTopTextUi()
        updateChallengeUi(isTodayChallengeDone())
        updateHybridRvUi()
        attachListener()

    }


    private fun updateTopTextUi() {
        Log.e(TAG, "updateTopTextUi: called" )


        val streak = abs(Random().nextInt()%100)
        binding?.tvToday?.text =
            "Day #$streak : ${Exercise.getToday()}" //instead use resource(R.string.blah) string with args

        binding?.tvChallange?.text = " ${Exercise.getExerciseTypeString()} DAY!!!"

    }
    private fun isTodayChallengeDone() = false //to do change to checking logic
    private fun updateChallengeUi(isChallengeDone: Boolean) {
        Log.e(TAG, "updateChallengeUi: called" )

        if (isChallengeDone) {
            binding?.clCompleted?.visibility = VISIBLE
            binding?.tvChallange?.visibility = GONE
        }
        else {
            binding?.clCompleted?.visibility = GONE
            binding?.tvChallange?.visibility = VISIBLE
        }
    }

    private fun updateHybridRvUi() {


        hybridList.add("Daily Logs")

        hybridList.add(DailyLog(DailyLog.getRandomDate(), 0))
        hybridList.add(DailyLog(DailyLog.getRandomDate(), 1))
        hybridList.add(DailyLog(DailyLog.getRandomDate(), 2))
        hybridList.add(DailyLog(DailyLog.getRandomDate(), 3))
        hybridList.add(DailyLog(DailyLog.getRandomDate(), 4))
        hybridList.add(DailyLog(DailyLog.getRandomDate(), 5))

        hybridList.add("Exercise Details")
        hybridList.addAll(Exercise.getDefaultExerciseList())

        adp.dataList=hybridList



    }
    private fun attachListener() {
        Log.e(TAG, "attachListener: called" )

        binding?.tvChallange?.setOnClickListener {
            updateChallengeUi(true)//to-do no need to call this, attach listener should attach a pref change listerner here
            startActivity(Intent(this@DashBoardActivity, ChallengesActivity::class.java))
        }

        binding?.ivAboutMe?.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, AboutMeActivity::class.java))
        }

        // TO DO: 19/04/20 attach preference listener
    }


}

