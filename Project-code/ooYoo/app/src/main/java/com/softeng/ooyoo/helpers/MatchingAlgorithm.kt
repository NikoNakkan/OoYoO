package com.softeng.ooyoo.helpers

import com.softeng.ooyoo.helpers.INTERESTS_ARRAY_LIST
import kotlin.*
import kotlin.math.pow
import kotlin.math.sqrt

fun matching ( firstList : ArrayList<String> = arrayListOf<String>(), secondList : ArrayList<String> = arrayListOf<String>() ) : Double{
    val prototypeListOfHobbies = INTERESTS_ARRAY_LIST
    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    for (hobby in prototypeListOfHobbies){
        if (hobby in firstList) {
            list1.add(1)
        }
        else{
            list1.add(0)

        }
    }
    for (hobby in prototypeListOfHobbies){
        if (hobby in secondList) {
            list2.add(1)
        }
        else{
            list2.add(0)
        }
    }
    var a = 0.0
    for(i in 0 until list1.size){
         a += (list1[i] - list2[i]).toDouble().pow(2.0)
    }
    a = sqrt(a)
   return a

}