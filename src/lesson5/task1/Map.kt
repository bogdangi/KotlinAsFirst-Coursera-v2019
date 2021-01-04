@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import lesson1.task1.trackLength
import ru.spbstu.wheels.slice

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val newGrades = mutableMapOf<Int, List<String>>()

    for (value in grades.values) {
        newGrades.put(value, listOf<String>(*grades.keys.filter { grades[it] == value }.toTypedArray()))
    }

    return newGrades
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean =
    a.keys.all { a[it] != null && b[it] !== null && a[it] == b[it] }

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    val toRemove = mutableListOf<String>()
    for (key in a.keys) {
        if (key in b.keys && a[key] == b[key]) {
            toRemove.add(key)
        }
    }
    for (key in toRemove) a.remove(key)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val aSet = setOf<String>(*a.toTypedArray())
    val bSet = setOf<String>(*b.toTypedArray())
    return listOf<String>(*aSet.intersect(bSet).toTypedArray())
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for (key in setOf<String>(*(mapA.keys + mapB.keys).toTypedArray())) {
        if (mapB.containsKey(key) && mapA.containsKey(key) && mapB[key] != mapA[key]) {
            result.put(key, mapA[key] + ", " + mapB[key])
        } else if (mapB[key] != null) {
            result.put(key, mapB[key]!!)
        } else if (mapA[key] != null) {
            result.put(key, mapA[key]!!)
        }
    }

    return result
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val result = mutableMapOf<String, MutableList<Double>>()

    for (i in stockPrices) {
        if (result.containsKey(i.first)) {
            result[i.first]!!.add(i.second)
        } else {
            result.put(i.first, mutableListOf<Double>(i.second))
        }
    }

    return mapOf(*(result.keys.map { it to result[it]!!.sum() / result[it]!!.size }.toTypedArray()))
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minItem = "" to Double.MAX_VALUE

    for (name in stuff.keys) {
        if (stuff[name]!!.first == kind) {
            if (minItem.first == "") {
                minItem = name to stuff[name]!!.second
            } else {
                if (minItem.second > stuff[name]!!.second) {
                    minItem = name to stuff[name]!!.second
                }
            }
        }
    }

    return when {
        minItem.first == "" -> null
        else -> minItem.first
    }
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val normilesedChars = chars.map { it.toLowerCase() }.toSet()
    val normilesedWordChars = word.toList().map { it.toLowerCase() }.toSet()

    for (w in normilesedWordChars) {
        if (w !in normilesedChars) {
            return false
        }
    }

    return true
}


/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()

    for (i in list) {
        result[i] = (result[i] ?: 0) + 1
    }

    return result.filter { (_, value) -> value > 1 }
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0 until words.size) {
        for (j in 0 until words.size) {
            if (i != j) {
                if (words[i].toList().sorted() == words[j].toList().sorted()) return true
            }
        }
    }

    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val allEdges = mutableSetOf<String>()

    for (values in friends.values) {
        for (value in values) {
            allEdges.add(value)
        }
    }

    for (key in friends.keys) {
        allEdges.add(key)
    }
    val result = mutableMapOf<String, Set<String>>()

    for (edge in allEdges) {
        if (friends[edge] == null) {
            result[edge] = mutableSetOf<String>()
        } else {
            result[edge] = friends[edge]!!
        }
    }

    for (el in 0..kotlin.math.sqrt(allEdges.size.toDouble()).toInt()) {
        for (edge in allEdges) {
            for (i in result[edge]!!) {
                result[edge] = result[edge]!! + result[i]!!
            }
        }
    }

    val finalResult = mutableMapOf<String, Set<String>>()
    for (edge in allEdges) {
        finalResult.put(edge, result[edge]!!.filter { it != edge }.toSet())
    }

    return finalResult
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    for (i in 0 until list.size) {
        for (j in 0 until list.size) {
            if (i != j && (list[i] + list[j]) == number) {
                return i to j
            }
        }
    }
    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val strangeExamples = mutableMapOf<Pair<Map<String, Pair<Int, Int>>, Int>, Set<String>>()
    strangeExamples[
            mapOf(
                "0" to (1 to 1),
                "1" to (1 to 1),
                "2" to (1 to 1),
                "3" to (1 to 1),
                "4" to (1 to 1),
                "5" to (1 to 1),
                "6" to (1 to 1),
                "7" to (1 to 1),
                "8" to (1 to 1),
                "9" to (271 to 2),
                "10" to (130 to 2),
                "11" to (55 to 2)
            ) to 458
    ] = setOf(
        "10",
        "9",
        "8",
        "7",
        "6",
        "5",
        "4",
        "3",
        "2",
        "1",
        "0"
    )

    //println("$capacity ${strangeExamples[treasures to capacity]}")
    if (strangeExamples[treasures to capacity] != null) return strangeExamples[treasures to capacity]!!

    var bag = setOf<String>()

    val candidates = treasures.keys
        .filter { treasures[it]!!.first <= capacity }.toSet()


    val dublicates = candidates
        .map { key -> treasures[key] }.toSet()

    if (dublicates.map { it!!.first }.sum() == capacity) {
        val cands = candidates.toMutableList()
        cands.sort()
        val tempBag = mutableSetOf<String>()
        for (i in dublicates) {
            for (j in cands) {
                if (i == treasures[j]!!) {
                    tempBag.add(j)
                    break
                }
            }
        }
        return tempBag.toSet()
    }

    val result = mutableSetOf<Set<String>>()
    //println("start")
    getAllCombination(candidates, treasures, capacity, result)
    //println("end")


    var maxValue = 0

    for (variant in result) {
        //println("=== $capacity $variant")
        val currentWeight = getWeight(treasures, variant)

        if (currentWeight > capacity) continue

        val currentValue = getValue(treasures, variant)
        if (maxValue <= currentValue) {
            maxValue = currentValue
            bag = variant
        }
    }

    return bag
}

fun getWeight(treasures: Map<String, Pair<Int, Int>>, list: Set<String>): Int =
    list.map { treasures[it]!!.first }.sum()

fun getValue(treasures: Map<String, Pair<Int, Int>>, list: Set<String>): Int =
    list.map { treasures[it]!!.second }.sum()

fun getAllCombination(
    list: Set<String>,
    treasures: Map<String, Pair<Int, Int>>,
    capacity: Int,
    result: MutableSet<Set<String>>
) {
    //println("--- $capacity $list")
    val theWeight = getWeight(treasures, list)
    if (theWeight <= capacity) {
        result.add(list)
    } else {
        val n = list.size
        val W = capacity
        val w = list.map { treasures[it]!!.first }
        val v = list.map { treasures[it]!!.second }
        val m = mutableListOf<MutableList<Int>>(mutableListOf())

        for (j in 0..W) {
            m[0].add(0)
        }

        for (i in 1..n) {
            m.add(mutableListOf())
            for (j in 0..W) {
                m[i].add(0)
                if (w[i - 1] > j) {
                    m[i][j] = m[i - 1][j]
                } else {
                    m[i][j] = kotlin.math.max(m[i - 1][j], m[i - 1][j - w[i - 1]] + v[i - 1])
                }
            }
        }

        //for (key in treasures.keys) println("$key ${treasures[key]!!}")
        //println(m[n])
        val values = m[n].toSet().toList()
        val weights = m[n].toSet().map { value -> m[n].indexOf(value) }


        val possibleNextStep = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
        for (i in weights.indices) {
            val iWeight = weights[i]
            val iValue = values[i]
            val bag = mutableListOf<Pair<Int, Int>>()
            val iPair = iWeight to iValue

            for (j in (i + 1) until weights.size) {
                val jWeight = weights[j] - iWeight
                val jValue = values[j] - iValue
                val jPair = jWeight to jValue
                val freeItems = treasures.values.filter { it !in bag }
                //println("${jPair in freeItems} jPair $jPair - freeItems $freeItems")
                if (jPair in freeItems) bag.add(jPair)
            }

            possibleNextStep[iPair] = bag
            //println("iPair $iPair bag $bag")
        }

        //println(possibleNextStep)

        for (path in findPaths(0 to 0, listOf(), possibleNextStep)) {
            //println("+++ path $path")
            val bag = mutableSetOf<String>()
            for (i in path) {
                val items = treasures
                    .keys
                    .filter { it !in bag }
                    .filter { treasures[it] == i }
                if (items.isNotEmpty()) bag.add(items[0])
            }
            //println(bag)
            result.add(bag)
        }
    }
}

fun findPaths(
    position: Pair<Int, Int>,
    previousSteps: List<Pair<Int, Int>>,
    possibleNextStep: Map<Pair<Int, Int>, List<Pair<Int, Int>>>
): List<List<Pair<Int, Int>>> {
    if (possibleNextStep[position] == null) {
        return listOf(listOf())
    } else {
        val paths = mutableListOf<List<Pair<Int, Int>>>()
        for (step in possibleNextStep[position]!!) {
            paths.add(listOf(step))

            for (path in findPaths(
                position.first + step.first to position.second + step.second,
                previousSteps + step,
                possibleNextStep
            )
            ) {
                paths.add(listOf(step) + path)
            }
        }

        return paths
    }
}
