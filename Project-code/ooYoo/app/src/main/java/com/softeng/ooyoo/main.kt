import kotlin.*
import kotlin.math.sqrt

fun matching ( firstList : ArrayList<String> = arrayListOf<String>(), secondList : ArrayList<String> = arrayListOf<String>() ) : Double{
val prototypeListofHobbies = arrayListOf<String>()
    prototypeListofHobbies.addAll(listOf<String>("Chess","Hiking","Meditation","Sex","Anime", "Tuvan throat singing","Books", "Science fiction","Gaming", "Sports","Music","Conspiracy Theories","Yoga","Board Games","Movies","Museusms","Laser Tag","Internet","Photography","Painting","Writing","Walking in nature","Cooking","Cocooning","Caligraphy","Calligraphy","Coin collection","Shopping","Making shock-bunnies"))
    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    for (hobby in prototypeListofHobbies){
        if (hobby in firstList) {
            list1.add(1)
        }
        else{
            list1.add(0)

        }
    }
    for (hobby in prototypeListofHobbies){
        if (hobby in secondList) {
            list2.add(1)
        }
        else{
            list2.add(0)

        }
    }
    var a= 0.0
    for(i in 0 .. list1.size-1){
         a += Math.pow((list1[i] - list2[i]).toDouble(), 2.0)
    }
    a= sqrt(a)
   return a

}


fun main(){
    val prototypeListofHobbies = arrayListOf<String>()
    prototypeListofHobbies.addAll(listOf<String>("Chess","Hiking","Meditation","Sex","Anime", "Tuvan throat singing","Books", "Science fiction","Gaming", "Sports","Music","Conspiracy Theories","Yoga","Board Games","Movies","Museusms","Laser Tag","Internet","Photography","Painting","Writing","Walking in nature","Cooking","Cocooning","Caligraphy","Calligraphy","Coin collection","Shopping","Making shock-bunnies"))
    val prototypeListofHobbies2 = arrayListOf<String>()
    prototypeListofHobbies2.addAll(listOf<String>("Chess","Meditation","Sex","Anime", "Books", "Science fiction","Gaming", "Music","Conspiracy Theories","Board Games","Movies","Museusms","Internet","Photography","Painting","Writing","Walking in nature","Cocooning","Caligraphy","Calligraphy","Shopping","Making shock-bunnies"))

   val ct = matching(prototypeListofHobbies,prototypeListofHobbies2 )

    print(ct)
}