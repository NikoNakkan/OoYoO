import kotlin.*
import kotlin.math.sqrt

fun matching ( firstList : ArrayList<String> = arrayListOf<String>(), secondList : ArrayList<String> = arrayListOf<String>() ) :Int{
    val prototypeListofHobbies = arrayListOf<String>()
    prototypeListofHobbies.addAll(listOf<String>("Chess","Hiking","Meditation","Sex","Anime", "Tuvan throat singing","Books", "Science fiction","Gaming", "Sports","Music","Conspiracy Theories","Yoga","Board Games","Movies","Museusms","Laser Tag","Internet","Photography","Painting","Writing","Walking in nature","Cooking","Cocooning","Caligraphy","Calligraphy","Coin collection","Shopping","Making shock-bunnies"))

    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    var finalList = arrayListOf<Int>()
    for (hobby in prototypeListofHobbies) {
        if (hobby in firstList) {
            list1.add(1)
        } else {
            list1.add(0)

        }
    }
    for (hobby in prototypeListofHobbies) {
        if (hobby in secondList) {
            list2.add(1)
        } else {

            list2.add(0)

        }
    }
    for (i in 0..list1.size - 1) {
             finalList.addAll(listOf<Int>(list1[i] * list2[i]))
    }
    var k = 0
    for (i in 0..finalList.size - 1) {
       k += finalList[i]
    }
    return k
}





fun main(){
    val list0 = arrayListOf<String>()
    list0.addAll(listOf<String>("Chess","Hiking","Meditation","Sex","Anime", "Tuvan throat singing","Books", "Science fiction","Gaming", "Sports","Music","Conspiracy Theories","Yoga","Board Games","Movies","Museusms","Laser Tag","Internet","Photography","Painting","Writing","Walking in nature","Cooking","Cocooning","Caligraphy","Calligraphy","Coin collection","Shopping","Making shock-bunnies"))
    val list1 = arrayListOf<String>()
    list1.addAll(listOf<String>( "Science fiction","Gaming", "Sports","Music","Conspiracy Theories"))
    val list2 = arrayListOf<String>()
    list2.addAll(listOf<String>("Chess","Books"))
    val list3 = arrayListOf<String>()
    list3.addAll(listOf<String>("Chess","Hiking","Anime"))


   val ct = matching(list2,list1)

    print(ct)
}