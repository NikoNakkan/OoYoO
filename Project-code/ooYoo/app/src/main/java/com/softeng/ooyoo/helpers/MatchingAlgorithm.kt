package com.softeng.ooyoo.helpers

import kotlin.*

/**
 * This algorithm is used to match users with similar interests.
 */
fun matching ( firstList : ArrayList<String> = arrayListOf(), secondList : ArrayList<String> = arrayListOf() ) :Int{
    val prototypeListOfHobbies = INTERESTS_ARRAY_LIST

    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    val finalList = arrayListOf<Int>()

    for (hobby in prototypeListOfHobbies) {
        if (hobby in firstList) {
            list1.add(1)
        }
        else {
            list1.add(0)

        }
    }

    for (hobby in prototypeListOfHobbies) {
        if (hobby in secondList) {
            list2.add(1)
        }
        else {
            list2.add(0)
        }
    }


    for (i in 0 until list1.size) {
        finalList.add(list1[i] * list2[i])
    }

    var k = 0

    for (i in 0 until finalList.size) {
        k += finalList[i]
    }

    return k
}


