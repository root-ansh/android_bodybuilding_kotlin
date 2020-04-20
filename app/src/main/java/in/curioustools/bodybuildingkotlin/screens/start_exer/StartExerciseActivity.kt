package `in`.curioustools.bodybuildingkotlin.screens.start_exer

import `in`.curioustools.bodybuildingkotlin.R
import `in`.curioustools.bodybuildingkotlin.databinding.ActivityStartExerciseBinding
import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE


// TS = Thread Safe
class StartExerciseActivity : AppCompatActivity() {

    companion object {
        const val KEY_EXERCISE_LIST = "exercise_list"
        const val KEY_REP_COUNT = "repcount"
    }

    private val TAG = "StartExerciseAct>>"
    private val breakTime = 30
    private var totalRepsPerExercise = 0
    private var selectedArrayList = ArrayList<Exercise>()
    private var mediaPlayer: MediaPlayer? = null
    private var binding: ActivityStartExerciseBinding? = null
    private var bgThread:Thread? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mediaPlayer = MediaPlayer()
        setIntentDataVars()
        initInitialData();

    }
    override fun onStart() {
        super.onStart()

        binding?.fabMusic?.setOnClickListener{
            if(mediaPlayer?.isPlaying!!) stopCurrentSongTS()
            else startRandomSongTS()

            updateTSUiFabMusic()
        }

        binding?.tvStart?.setOnClickListener {
            if(bgThread==null){
                //actions
                initAndStartBgThread();
                startRandomSongTS()

                //ui updates
                updateTSBottomButtonUi(true)
                updateTSUiFabMusic()
            }
            else{
                //actions
                stopBgThread()
                stopCurrentSongTS()

                // ui updates
                updateTSBottomButtonUi(false)
                updateTSUiFabMusic()
            }
        }

    }

    //======================= initialisation data ================================
    private fun setIntentDataVars() {
        totalRepsPerExercise = intent.getIntExtra(KEY_REP_COUNT, 12)
        selectedArrayList = intent.getParcelableArrayListExtra(KEY_EXERCISE_LIST)
        if (selectedArrayList == null) {
            selectedArrayList = ArrayList();
        }
    }
    //======================ui modifications =====================================
    private fun initInitialData() {
        val currEx = selectedArrayList[0]
        updateTSTopCardUi(0,Highlight.INITIAL)
        updateTSMiddleCardsUi(false,currEx,totalRepsPerExercise*currEx.timeInSeconds,1,totalRepsPerExercise)
        updateTSUiFabMusic()
        updateTSBottomButtonUi(false)
    }

    enum class Highlight { INITIAL, CURRENT, COMPLETED }
    private fun updateTSTopCardUi(position: Int, state: Highlight) {
        // updates top card ui. mainly works with Highlight state
        // if state is initial, then exerciseNum does not play any role. we simply apply a background to all views
        // if state is current then based on exerciseNum we highlight the top imageview pink
        // if state is current then based on exerciseNum we highlight the top imageview with star and break with star


        runOnUiThread {
            when (state) {
                Highlight.INITIAL -> {
                    binding?.ivEx1?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.ivEx1?.setImageResource(selectedArrayList[0].imageRes)

                    binding?.ivEx2?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.ivEx2?.setImageResource(selectedArrayList[1].imageRes)

                    binding?.ivEx3?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.ivEx3?.setImageResource(selectedArrayList[2].imageRes)

                    binding?.ivEx4?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.ivEx4?.setImageResource(selectedArrayList[3].imageRes)

                    binding?.ivEx5?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.ivEx5?.setImageResource(selectedArrayList[4].imageRes)

                    binding?.viewBreak1?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.viewBreak2?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.viewBreak3?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.viewBreak4?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                    binding?.viewBreak5?.setBackgroundResource(R.drawable.bg_circle_gray_e5e5)
                }
                Highlight.CURRENT -> {
                    when (position) {
                        0 -> binding?.ivEx1?.setBackgroundResource(R.drawable.bg_circle_pink_ffebee)
                        1 -> binding?.ivEx2?.setBackgroundResource(R.drawable.bg_circle_pink_ffebee)
                        2 -> binding?.ivEx3?.setBackgroundResource(R.drawable.bg_circle_pink_ffebee)
                        3 -> binding?.ivEx4?.setBackgroundResource(R.drawable.bg_circle_pink_ffebee)
                        4 -> binding?.ivEx5?.setBackgroundResource(R.drawable.bg_circle_pink_ffebee)

                    }
                }
                Highlight.COMPLETED -> {
                    when (position) {
                        0 -> { binding?.ivEx1?.setBackgroundResource(R.drawable.ic_star);binding?.viewBreak1?.setBackgroundResource(R.drawable.ic_star) }
                        1 -> { binding?.ivEx2?.setBackgroundResource(R.drawable.ic_star);binding?.viewBreak2?.setBackgroundResource(R.drawable.ic_star) }
                        2 -> { binding?.ivEx3?.setBackgroundResource(R.drawable.ic_star);binding?.viewBreak3?.setBackgroundResource(R.drawable.ic_star) }
                        3 -> { binding?.ivEx4?.setBackgroundResource(R.drawable.ic_star);binding?.viewBreak4?.setBackgroundResource(R.drawable.ic_star) }
                        4 -> { binding?.ivEx5?.setBackgroundResource(R.drawable.ic_star);binding?.viewBreak5?.setBackgroundResource(R.drawable.ic_star) }
                    }
                }
            }

        }
    }
    private fun updateTSMiddleCardsUi(showBreakCard:Boolean, breakCounter:Int) {

        runOnUiThread {

            if(showBreakCard){
                binding?.cardBreak?.visibility = VISIBLE
                binding?.tvCardBreakcount?.text =Exercise.timeToString(breakCounter)
            }
            else{
                Log.e(TAG, "updateTSMiddleCardsUi: called the wrong function" )
            }

        }
    }
    private fun updateTSMiddleCardsUi(showBreakCard:Boolean, exercise: Exercise, curProg:Int, maxProg:Int, repsLeft:Int) {

        runOnUiThread {

            if(showBreakCard){
                Log.e(TAG, "updateTSMiddleCardsUi: called the wrong function" )
            }
            else{
                binding?.cardBreak?.visibility =GONE

                val progress:Float = curProg*100f/maxProg

                binding?.pcv?.setProgress(if(progress>100) 100f else progress,true)
                binding?.pcv?.textSize = 1f
                binding?.tvRepcount?.text = "$repsLeft Reps Left"
                binding?.tvCurrentExTime?.text = "$curProg"
                binding?.tvTitle?.text = exercise.eName
                binding?.tvDetails?.text =exercise.details
                binding?.ivDetailsImg?.setImageResource(exercise.imageRes)
                binding?.tvDetailsTime?.text =Exercise.timeToString(exercise.timeInSeconds)

            }

        }
    }
    private fun updateTSUiFabMusic() {
        runOnUiThread {
            binding?.fabMusic?.setImageResource(
                if(mediaPlayer?.isPlaying!!) R.drawable.ic_music_off
                else R.drawable.ic_music_on
            )

        }
    }
    private fun updateTSBottomButtonUi(isRoutineRunning: Boolean) {
        runOnUiThread {
            if(isRoutineRunning){
                binding?.tvStart?.text ="Finish Exercise"
            }
            else{
                binding?.tvStart?.text = "Start Exercise"
            }
        }

    }

    //================================media handling =============================
    private fun startRandomSongTS(){
        runOnUiThread {
            val songname = Exercise.getRandomSong()
            if(mediaPlayer?.isPlaying!!){ stopCurrentSongTS() }
            val afd= assets.openFd(songname)
            mediaPlayer?.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
            mediaPlayer?.isLooping =true
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
    }
    private fun stopCurrentSongTS() {
        runOnUiThread {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
        }


    }

    //=================================== thread handling ========================
    private fun initAndStartBgThread(){
        bgThread = Thread(
            Runnable {

                var i =0
                while (  i< selectedArrayList.size){
                    // roll some music
                    startRandomSongTS()
                    updateTSUiFabMusic()

                    //update top card ui to running
                    updateTSTopCardUi(i,Highlight.CURRENT)


                    val  curExercise = selectedArrayList[i]
                    val curExerciseTotalTime = curExercise.timeInSeconds*totalRepsPerExercise
                    var curExerciseRepCount =totalRepsPerExercise

                    //start updating middle card ui
                    for(j in curExerciseTotalTime downTo 0){
                        Thread.sleep(1000)
                        if(j% curExercise.timeInSeconds ==0){
                            curExerciseRepCount--
                        }
                        updateTSMiddleCardsUi(false, curExercise, j, curExerciseTotalTime, curExerciseRepCount
                        )
                    }

                    //update to card ui to completed(star bg) and middle card to break
                    updateTSTopCardUi(i,Highlight.COMPLETED)
                    updateTSMiddleCardsUi(true,breakTime)

                    //stop music as a sign of break
                    stopCurrentSongTS()

                    //start  breaktime changes
                    for (j in breakTime downTo 0){
                        Thread.sleep(1000)
                        updateTSMiddleCardsUi(true,j)
                    }
                    // hide card(i know its repeativite but is useful when we have to exit: it flips the card one final time)
                    updateTSMiddleCardsUi(false,curExercise,1,1,totalRepsPerExercise)
                    i++

                }
                closeActivity()

            }
        )
        bgThread?.start()

    }

    private  fun stopBgThread(){
        bgThread?.interrupt()
        bgThread = null
    }

    private fun closeActivity() {
        stopCurrentSongTS()
        finish()
        //todo show a dialogue
    }
}
