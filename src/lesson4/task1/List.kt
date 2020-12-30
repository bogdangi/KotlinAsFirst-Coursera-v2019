@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { acc, el -> acc + el * el })


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = when {
    list.isEmpty() -> 0.0
    else -> list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> = when {
    list.isEmpty() -> list
    else -> {
        val listMean = mean(list.toList())
        for (i in 0 until list.size) {
            list[i] -= listMean
        }
        list
    }
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int = when {
    a.isEmpty() || b.isEmpty() -> 0
    else -> (a.indices).fold(0) { acc, i -> acc + a[i] * b[i] }
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int = when {
    p.isEmpty() -> 0
    else -> (1 until p.size).fold(p[0]) { acc, i -> acc + x.toDouble().pow(i).toInt() * p[i] }
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> = when {
    list.isEmpty() -> list
    else -> {
        for (i in list.size - 1 downTo 0) {
            list[i] = list.subList(0, i + 1).sum()
        }
        list
    }
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var list = listOf<Int>()
    var i = 2
    while (list.fold(1) { acc, el -> acc * el } != n) {
        if (n % (list.fold(1) { acc, el -> acc * el } * i) == 0) {
            list = list + i
        } else {
            i++
        }
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convertToDecimal(list: List<Int>, base: Int): Int =
    list.indices.reversed().fold(0) { acc, el -> acc + list[list.size - 1 - el] * base.toDouble().pow(el).toInt() }

fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var rest = n

    do {
        list.add(0, rest % base)
        rest /= base
    } while (convertToDecimal(list, base) != n)

    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
@JvmName("convertToDecimal1")
fun convertToDecimal(list: List<Char>, base: Int): Int {
    val mapList = "0123456789abcdefghijklmnopqrstuvwxyz".toList()
    return list.indices.reversed()
        .fold(0) { acc, el -> acc + mapList.indexOf(list[list.size - 1 - el]) * base.toDouble().pow(el).toInt() }
}

fun convertToString(n: Int, base: Int): String {
    val mapList = "0123456789abcdefghijklmnopqrstuvwxyz".toList()
    val list = mutableListOf<Char>()
    var rest = n

    do {
        list.add(0, mapList[rest % base])
        rest /= base
    } while (convertToDecimal(list, base) != n)

    return list.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = convertToDecimal(digits, base)

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = convertToDecimal(str.toList(), base)

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val delimiters = listOf(1, 5, 10, 50, 100, 500, 1000)
    var buff = listOf<Char>()

    return when (n) {
        0 -> ""
        1 -> "I"
        4 -> "IV"
        5 -> "V"
        9 -> "IX"
        10 -> "X"
        40 -> "XL"
        50 -> "L"
        90 -> "XC"
        100 -> "C"
        400 -> "CD"
        500 -> "D"
        900 -> "CM"
        1000 -> "M"
        else -> {
            var numbers = 1
            while (n / numbers >= 10) {
                numbers *= 10
            }

            val theN = n / numbers * numbers
            val rest = n % (theN)

            for (delimiter in delimiters.indices.reversed()) {
                if (theN / delimiters[delimiter] in 1..3 && theN % delimiters[delimiter] == 0) {
                    buff = times(
                        roman(delimiters[delimiter]),
                        theN / delimiters[delimiter]
                    ).toList() + roman(rest).toList()
                    break
                } else if (theN + numbers == delimiters[delimiter]) {
                    buff = roman(numbers).toList() + roman(delimiters[delimiter]).toList() + roman(rest).toList()
                    break
                } else if (theN - numbers == delimiters[delimiter]) {
                    buff =
                        roman(delimiters[delimiter]).toList() + times(roman(numbers), 1).toList() + roman(rest).toList()
                    break
                } else if (theN - numbers - numbers == delimiters[delimiter]) {
                    buff =
                        roman(delimiters[delimiter]).toList() + times(roman(numbers), 2).toList() + roman(rest).toList()
                    break
                } else if (theN - numbers - numbers - numbers == delimiters[delimiter]) {
                    buff =
                        roman(delimiters[delimiter]).toList() + times(roman(numbers), 3).toList() + roman(rest).toList()
                    break
                }
            }

            if (buff.isEmpty()) {
                buff = roman(theN).toList() + roman(rest).toList()
            }
            buff.joinToString(separator = "")
        }
    }
}

fun times(value: String, times: Int): String {
    val buff = mutableListOf<Char>()
    for (i in 0 until times) {
        buff += value.toList()
    }
    return buff.joinToString(separator = "")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun russian(n: Int): String {
    val result = when {
        n > 999 && n / 1000 % 100 in 10..20 -> listOf(russian(n / 1000), getThousands(n), russian(n % 1000))
        n >= 1000 && n / 1000 % 10 == 2 -> listOf(russian((n - 2000) / 1000), "две тысячи", russian(n % 1000))
        n >= 1000 && n / 1000 % 10 == 1 -> listOf(russian((n - 1000) / 1000), "одна тысяча", russian(n % 1000))
        n > 999 -> listOf(russian(n / 1000), getThousands(n), russian(n % 1000))
        n / 10 == 9 -> listOf("девяносто", russian(n % 10))
        n / 10 == 8 -> listOf("восемьдесят", russian(n % 10))
        n / 10 == 7 -> listOf("семьдесят", russian(n % 10))
        n / 10 == 6 -> listOf("шестьдесят", russian(n % 10))
        n / 10 == 5 -> listOf("пятьдесят", russian(n % 10))
        n / 10 == 4 -> listOf("сорок", russian(n % 10))
        n / 10 == 3 -> listOf("тридцать", russian(n % 10))
        n / 10 == 2 -> listOf("двадцать", russian(n % 10))
        n == 19 -> listOf("девятнадцать")
        n == 18 -> listOf("восемнадцать")
        n == 17 -> listOf("семнадцать")
        n == 16 -> listOf("шестнадцать")
        n == 15 -> listOf("пятнадцать")
        n == 14 -> listOf("четырнадцать")
        n == 13 -> listOf("тринадцать")
        n == 12 -> listOf("двенадцать")
        n == 11 -> listOf("одиннадцать")
        n == 10 -> listOf("десять")
        n / 100 == 9 -> listOf("девятьсот", russian(n % 100))
        n / 100 == 8 -> listOf("восемьсот", russian(n % 100))
        n / 100 == 7 -> listOf("семьсот", russian(n % 100))
        n / 100 == 6 -> listOf("шестьсот", russian(n % 100))
        n / 100 == 5 -> listOf("пятьсот", russian(n % 100))
        n / 100 == 4 -> listOf("четыреста", russian(n % 100))
        n / 100 == 3 -> listOf("триста", russian(n % 100))
        n / 100 == 2 -> listOf("двести", russian(n % 100))
        n / 100 == 1 -> listOf("сто", russian(n % 100))
        n == 9 -> listOf("девять")
        n == 8 -> listOf("восемь")
        n == 7 -> listOf("семь")
        n == 6 -> listOf("шесть")
        n == 5 -> listOf("пять")
        n == 4 -> listOf("четыре")
        n == 3 -> listOf("три")
        n == 2 -> listOf("два")
        n == 1 -> listOf("один")
        else -> listOf("")
    }

    return result.filter { it != "" }.joinToString(" ")
}

fun getThousands(n: Int): String = when {
    n / 1000 % 100 in 10..19 -> "тысяч"
    n / 1000 % 10 in 5..9 -> "тысяч"
    n / 1000 % 10 == 0 -> "тысяч"
    else -> "тысячи"
}