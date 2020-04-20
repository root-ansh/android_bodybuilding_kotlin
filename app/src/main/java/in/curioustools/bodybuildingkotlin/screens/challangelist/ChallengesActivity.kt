package `in`.curioustools.bodybuildingkotlin.screens.challangelist

import `in`.curioustools.bodybuildingkotlin.databinding.ActivityChallangesBinding
import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import `in`.curioustools.bodybuildingkotlin.screens.MyDialogues
import `in`.curioustools.bodybuildingkotlin.screens.challangelist.ChallengesAdapter.ChallengeClickListener
import `in`.curioustools.bodybuildingkotlin.screens.start_exer.StartExerciseActivity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup

@SuppressLint("SetTextI18n")
class ChallengesActivity : AppCompatActivity() {
    private var binding: ActivityChallangesBinding? = null
    private var adp:ChallengesAdapter? =null

    private  var selectedExerciseList = ArrayList<Exercise>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallangesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        adp = ChallengesAdapter()

        val gridManager = GridLayoutManager(this, 2)
        gridManager.spanSizeLookup =
            object : SpanSizeLookup() { override fun getSpanSize(i: Int) = if (i == 0) 2 else 1 }

        binding?.rvChalList?.layoutManager = gridManager
        binding?.rvChalList?.adapter = adp

        selectedExerciseList.addAll(Exercise.getDaywise5ExercisesList())



    }

    override fun onStart() {
        super.onStart()

        updateUi()

        binding?.tvRandomize?.setOnClickListener {

            selectedExerciseList.clear()
            selectedExerciseList.addAll(Exercise.getRandom5Exercises())
            updateUi()
        }
        binding?.tvStart?.setOnClickListener {

            MyDialogues.showRepCountDialogue(this@ChallengesActivity,layoutInflater,selectedExerciseList)
        }

        adp?.listener = object : ChallengeClickListener{
            override fun onClick(exercise: Exercise) {
                MyDialogues.showDetailsDialogue(this@ChallengesActivity,layoutInflater,exercise)
            }
        }

    }

    private fun updateUi() {

        binding?.tvToday        ?.text    = "${Exercise.getToday()} \n Exercises For Today Are"
        binding?.tvExCount      ?.text    = "${selectedExerciseList.size}"
        binding?.tvMinuteCount  ?.text    = "${selectedExerciseList.map { it.timeInSeconds }.sum()}"
        binding?.tvStart        ?.text    = "START"
        binding?.tvRandomize    ?.text    = "RANDOMIZE"

       adp?.exerciseList=selectedExerciseList

    }


}