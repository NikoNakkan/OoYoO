package com.softeng.ooyoo.helpers

import kotlin.*
import kotlin.math.pow
import kotlin.math.sqrt

fun matching ( firstList : ArrayList<String> = arrayListOf<String>(), secondList : ArrayList<String> = arrayListOf<String>() ) : Double {
    val prototypeListOfHobbies = INTERESTS_ARRAY_LIST

    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()

    for (hobby in prototypeListOfHobbies) {
        if (hobby in firstList) {
            list1.add(1)
        } else {
            list1.add(0)

        }
    }
    for (hobby in prototypeListOfHobbies) {
        if (hobby in secondList) {
            list2.add(1)
        } else {

            list2.add(0)

        }
    }

    return computeSimilarity(list1,list2)

}


fun computeSimilarity(firstList: ArrayList<Int> = arrayListOf(), secondList: ArrayList<Int> = arrayListOf()): Double {
    var dotProduct = 0
    var norm1 = 0.0
    var norm2 = 0.0
    val norm : Double
    for (i in 0 until firstList.size) {
        dotProduct += firstList[i] * secondList[i]
    }
    for (i in 0 until firstList.size) {
        norm1 += (firstList[i]).toDouble().pow(2.0)
        norm2 += (secondList[i]).toDouble().pow(2.0)

    }
    norm1 = sqrt(norm1)
    norm2 = sqrt(norm2)

    norm = norm1*norm2

    return dotProduct / norm;
}





