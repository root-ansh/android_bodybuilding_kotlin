package `in`.curioustools.bodybuildingkotlin.modal

import `in`.curioustools.bodybuildingkotlin.R
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
data class Exercise(
    val id: String,
    val eName: String,
    val details: String,
    val imageRes: Int,
    val timeInSeconds: Int
) : Parcelable {


    constructor(eName: String, details: String, imageRes: Int, timeInSeconds: Int) :
            this(getID(), eName, details, imageRes, timeInSeconds)


    override fun toString() = "Exercise(" +
            "id='$id'," +
            " eName='$eName'," +
            " details='$details'," +
            "imageRes=$imageRes," +
            " timeInSeconds=$timeInSeconds)\n"


    //========================STATIC METHODS========================================================
    companion object {
        private fun getID() = UUID.randomUUID().toString()
        fun getRandomSong(): String {
            val songsList = arrayOf("s1.mp3", "s2.mp3", "s3.mp3", "s4.mp3")

            val random = Random().nextInt(songsList.size)
            println(random)
            return songsList[random]

        }
        fun timeToString(timeInSeconds: Int): String {
            val sec = timeInSeconds % 60
            val min = (timeInSeconds / 60) % 60
            val hours = (timeInSeconds / 60 / 60)

            var result = ""
            result += if (hours != 0) "$hours HRS" else ""
            result += if (min != 0) "$min MINS" else ""
            result += "$sec SECONDS"

            return result

        }
        fun getDefaultExerciseList(): MutableList<Exercise> {
            val e1 = Exercise(
                "Push Up",
                "Get down on all fours, placing your hands slightly wider than your shoulders." +
                        "Straighten your arms and legs.Lower your body until your chest nearly touches the " +
                        "floor.Pause, then push yourself back up. REPEAT",
                R.drawable.e1,
                4
            );
            val e2 = Exercise(
                "knee Crunches",
                "Lie down on your back. Plant your feet on the floor, hip-width apart. Bend " +
                        "your knees and place your arms across your chest. Contract your abs and inhale." +
                        "Exhale and lift your upper body, keeping your head and neck relaxed." +
                        "Inhale and return to the starting position.",
                R.drawable.e2,
                5
            );
            val e3 = Exercise(
                "Abdominal Crunches",
                "Lie down on your back. Plant your feet on the floor, hip-width apart. Bend " +
                        "your knees and place your arms across your chest. Contract your abs and inhale." +
                        "Exhale and lift your upper body, keeping your head and neck relaxed." +
                        "Inhale and return to the starting position.",
                R.drawable.e3,
                4
            );
            val e4 = Exercise(
                "Strech Cardio",
                "Stand straight. move your right arm from 90 degree to 180 degree while " +
                        "streching the stomach and keeping your left arm on it. Exhale and inhale likewise",
                R.drawable.e4,
                6
            );
            val e5 = Exercise(
                "Strech Cardio",
                "Lie on your back, legs straight and together. " +
                        "Keep your legs straight and lift them all the way up to the ceiling until your butt comes off the floor. " +
                        "Slowly lower your legs back down till they're just above the floor. Hold for a moment." +
                        " Raise your legs back up. Repeat.",
                R.drawable.e5,
                5
            );
            val e6 = Exercise(
                "Dumbel Pushups",
                "Position your dumbbells vertically underneath your upper chest. The outer" +
                        " edges of the dumbbells should line up with the outer edges of your chest. " +
                        "Get into a pushup position with your hands on each dumbbell. Lower yourself" +
                        " down as far as you can go, keeping your elbows tucked, then return to start",
                R.drawable.e6,
                5
            );

            val e7 = Exercise(
                "Dumbel Pushups",
                "Stand with a dumbbell in each hand with palms facing forward. Curl the " +
                        "weights as you turn your wrists so that your palms face away at the top.",
                R.drawable.e7,
                6
            );
            val e8 = Exercise(
                "Swiss Ball Pushups",
                "Lay with your chest on the stability ball. Place your hands on the ball at " +
                        "the sides of your chest. They will be shoulder-width apart. Place your toes" +
                        " on the floor, legs straight. Push your body up until your arms are almost " +
                        "straight (do not lock your elbows)",
                R.drawable.e8,
                4
            );
            val e9 = Exercise(
                "One Leg Balance",
                "stand on one leg without support of the upper extremities or bracing of the " +
                        "unweighted leg against the stance leg",
                R.drawable.e9,
                3
            );


            val e11 = Exercise(
                "Dumbel Press",
                "Lie back on a bench holding a dumbbell in each hand just to the sides of " +
                        "your shoulders.Press the weights above your chest by extending your elbows" +
                        "until your arms are straight, then bring the weights back down slowly.",
                R.drawable.e11,
                4
            );
            val e13 = Exercise(
                "Shoulder Press",
                "Stand tall with your feet hip-width apart, and hold a pair of dumbbells in " +
                        "front of your shoulders with your elbows tucked and palms facing each other." +
                        "Press the weights directly above your shoulders until your arms are straight" +
                        " and your biceps are next to your ears.Pause, and then lower the weights back " +
                        "to the starting position.",
                R.drawable.e13,
                6
            );
            val e14 = Exercise(
                "Crunches",
                "Lie on your back on an exercise mat.Bend your knees so your feet are " +
                        "flat on the floor.Cross your arms in front of your chest.Lift your shoulder" +
                        "blades off of the mat with a smooth, controlled motion.REPEAT",
                R.drawable.e14,
                2
            );

            return mutableListOf(e1, e2, e3, e4, e5, e6, e7, e8, e9, e11, e13, e14)


        }
        fun getRandom5Exercises(): MutableList<Exercise> {
            val defaultList = getDefaultExerciseList()
            val randomList = ArrayList<Exercise>()

            for (i in 1..5) {
                val r = Random().nextInt(defaultList.size)
                val e = defaultList[r]
                defaultList.remove(e)
                randomList.add(e)
            }
            return randomList
        }
        fun getDaywise5ExercisesList() = getRandom5Exercises() //todo change it to daywise logic
        fun getToday(): String {
            return SimpleDateFormat(
                "EEE, d MMM yyyy",
                Locale.ROOT
            ).format(Calendar.getInstance().time)
        }

        fun getTodayNum(): Int {
            val day: String =
                SimpleDateFormat("EEE", Locale.ROOT).format(Calendar.getInstance().time)
            return when (day) {
                "Mon" -> 1
                "Tue" -> 2
                "Wed" -> 3
                "Thu" -> 4
                "Fri" -> 5
                "Sat" -> 6
                "Sun" -> 7
                else -> 1
            }

        }
        fun getExerciseTypeString(): String? {
            return when (getTodayNum()) {
                1 -> "Shoulders"
                2 -> "Biceps"
                3 -> "Triceps"
                4 -> "Chest"
                5 -> "Legs"
                6 -> "Cardio"
                else -> "Mix Exercise"
            }
        }
    }



}

