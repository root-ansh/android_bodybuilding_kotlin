package `in`.curioustools.bodybuildingkotlin

import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import org.junit.Test

import org.junit.Assert.*

class UnitTestKotlin {
    @Test
    fun addition_isCorrect() {

        //        Exercise e9 = new Exercise("", "e9", "details", 9, 3);

        val e9 = Exercise(
            eName = "e9",
            details = "details",
            imageRes = 24,
            timeInSeconds = 22
        )

        val e91 = Exercise(
            "",
            "e9",
            details = "details",
            timeInSeconds = 24,
            imageRes = 22
        )
        println(e9)

        val e10 = Exercise(
            "e10",
            "Hi",
            21,
            22
        );
        print(e10)

        assertEquals(4, 2 + 2)
    }

    @Test
    fun test2() {
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());
        println(Exercise.getRandomSong());

        assertEquals(2,2);
    }

    @Test
    fun test3() {
        println(Exercise.getRandom5Exercises());
        println(Exercise.getRandom5Exercises());
        println(Exercise.getRandom5Exercises());
        println(Exercise.getRandom5Exercises());

        assertEquals(2,2);
    }

    @Test
    fun test4() {

        val exerList= Exercise.getRandom5Exercises();
        println(exerList)

        var sum1 = 0;exerList.forEach{sum1+=it.timeInSeconds}
        val sum2 = exerList.map { it->it.timeInSeconds }.sum()
        val sum3 = exerList.map { it.timeInSeconds }.sum()

        println("$sum1 $sum2 $sum3")


        assertEquals(2,2);

    }






}