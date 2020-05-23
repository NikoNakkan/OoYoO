import kotlin.*
import kotlin.math.sqrt

fun matching ( firstList : ArrayList<String> = arrayListOf<String>(), secondList : ArrayList<String> = arrayListOf<String>() ) : Double {
    val prototypeListofHobbies = arrayListOf<String>()
    //prototypeListofHobbies.addAll(listOf<String>("Chess","Hiking","Meditation","Sex","Anime", "Tuvan throat singing","Books", "Science fiction","Gaming", "Sports","Music","Conspiracy Theories","Yoga","Board Games","Movies","Museusms","Laser Tag","Internet","Photography","Painting","Writing","Walking in nature","Cooking","Cocooning","Caligraphy","Calligraphy","Coin collection","Shopping","Making shock-bunnies"))
    prototypeListofHobbies.addAll(listOf<String>("Chess", "Hiking", "Meditation", "Sex"))

    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()

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

    //for(i in 0 .. list1.size-1){
    //   a += Math.pow((list1[i] - list2[i]).toDouble(), 2.0)
    //}
    //a= sqrt(a)
    //return a
    fun computeSimilarity(firstList: ArrayList<Int> = arrayListOf<Int>(), secondList: ArrayList<Int> = arrayListOf<Int>()): Double {
        var dotProduct = 0
        var norm1 = 0.0
        var norm2 = 0.0
        var norm : Double
        for (i in 0..firstList.size - 1) {
            dotProduct += list1[i] * list2[i]
        }
        for (i in 0..list1.size - 1) {
            norm1 += Math.pow((firstList[i]).toDouble(), 2.0)
            norm2 += Math.pow((secondList[i]).toDouble(), 2.0)

        }
        norm1 = sqrt(norm1)
        norm2 = sqrt(norm2)
        print(norm2)
        print("//////////////////////")
        norm = norm1*norm2

        print("////////////////////////////////////")

        return dotProduct / norm;
    }
    var k : Double
    k = computeSimilarity(list1,list2)

    return k

}





fun main(){
    val list0 = arrayListOf<String>()
    list0.addAll(listOf<String>("Chess","Hiking","Meditation","Sex"))
    val list1 = arrayListOf<String>()
    list1.addAll(listOf<String>("Chess"))
    val list2 = arrayListOf<String>()
    list2.addAll(listOf<String>("Hiking"))
   val ct = matching(list0,list2)

    print(ct)
}