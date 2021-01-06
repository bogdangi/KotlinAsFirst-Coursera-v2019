@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import kotlin.math.max

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val listOfStarts = listOf(
        "Января" to 1,
        "Февраля" to 2,
        "Марта" to 3,
        "Апреля" to 4,
        "Мая" to 5,
        "Июня" to 6,
        "Июля" to 7,
        "Августа" to 8,
        "Сентября" to 9,
        "Октября" to 10,
        "Ноября" to 11,
        "Декабря" to 12
    ).map { it.first.toLowerCase() to it.second }

    val parsedStr = str.split(" ")
    if (parsedStr.size != 3) return ""

    val monthVariants = listOfStarts.filter { parsedStr[1] == it.first }
    if (monthVariants.size != 1) return ""

    val month = monthVariants[0].second
    val year = parsedStr[2].toIntOrNull()
    val day = parsedStr[0].toIntOrNull()

    if (!validateDate(day, month, year)) return ""

    return String.format("%02d.%02d.%d", day, month, year)
}

fun validateDate(day: Int?, month: Int, year: Int?): Boolean {
    if (year == null) return false
    if (day == null) return false

    if (month !in 1..12) return false
    if (month in setOf(1, 3, 5, 7, 8, 10, 12) && day !in 1..31) return false
    if (month in setOf(4, 6, 9, 11) && day !in 1..30) return false
    if (month == 2) {
        if ((year % 4 == 0 || year % 100 == 0) && year % 400 != 0) {
            if (day !in 1..29) return false
        } else {
            if (day !in 1..28) return false
        }
    }

    return true
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val listOfStarts = listOf(
        "Января",
        "Февраля",
        "Марта",
        "Апреля",
        "Мая",
        "Июня",
        "Июля",
        "Августа",
        "Сентября",
        "Октября",
        "Ноября",
        "Декабря"
    ).map { it.toLowerCase() }

    val parsedStr = digital.split(".")
    if (parsedStr.size != 3) return ""

    val month = parsedStr[1].toIntOrNull() ?: return ""
    val year = parsedStr[2].toIntOrNull()
    val day = parsedStr[0].toIntOrNull()

    if (!validateDate(day, month, year)) return ""

    val monthStr = listOfStarts[month - 1]

    return "$day $monthStr $year"
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val plus = when {
        phone.startsWith("+") -> "+"
        else -> ""
    }

    val validChars = "+- ()0123456789".toSet()
    if (!phone.toSet().all { it in validChars }) return ""

    var startPerentasis = false
    var endPerentasis = false
    var code = ""
    var countryCode = ""
    var number = ""

    for (i in phone.toList()) {
        if (i == '(') {
            if (startPerentasis) return ""
            if (endPerentasis) return ""
            startPerentasis = true
            continue
        }
        if (i == ')') {
            if (endPerentasis) return ""
            if (!startPerentasis) return ""
            if (code.isEmpty()) return ""
            endPerentasis = true
            continue
        }
        if (!startPerentasis && !endPerentasis && i in "1234567890") countryCode += i
        if (startPerentasis && !endPerentasis && i in "1234567890") code += i
        if (startPerentasis && endPerentasis && i in "1234567890") number += i
    }

    return "$plus$countryCode$code$number"
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var result = -1

    val validChars = " -%0123456789".toSet()
    if (!jumps.toSet().all { it in validChars }) return result

    for (i in jumps.split(" ")) {
        try {
            result = max(result, i.toInt())
        } catch (e: NumberFormatException) {
            /* no-op */
        }
    }

    return result
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var result = -1
    var lastSuccessfulTry = -1
    var wasANumber = false

    val validChars = " -+%0123456789".toSet()
    if (!jumps.toSet().all { it in validChars }) return result

    for (i in jumps.split(" ")) {
        if (i == "+" && wasANumber) result = max(lastSuccessfulTry, result)

        try {
            lastSuccessfulTry = i.toInt()
            wasANumber = true
        } catch (e: NumberFormatException) {
            wasANumber = false
        }
    }

    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var result = 0
    val validChars = " -+0123456789".toSet()
    if (!expression.toSet().all { it in validChars })
        throw IllegalArgumentException("Not valid characters in '$expression'!!!")

    val cleanExpression = expression.split(" ").joinToString(separator = "")


    for (s in cleanExpression.split("+")) {
        if (s.isEmpty()) throw IllegalArgumentException("Not valid expression '$expression'!!!")

        try {
            result += s.toInt()
        } catch (e: NumberFormatException) {
            val minusList = s.split("-")
            if (minusList.size <= 1) throw IllegalArgumentException("Not valid expression '$expression'!!!")
            for (m in minusList.indices) {
                try {
                    val number = minusList[m].toInt()
                    if (m == 0) result += number
                    else result -= number
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException("Not valid expression '$expression'!!!")
                }
            }
        }
    }

    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val listOfWords = str.split(" ").map { it.toLowerCase() }

    for (i in 0..(listOfWords.size - 2)) {
        if (listOfWords[i] == listOfWords[i + 1])
            return listOfWords.subList(0, i).fold(0) { acc, it -> acc + it.length + 1 }
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val goods = mutableListOf<Pair<String, Double>>()

    for (i in description.split("; ")) {
        val items = i.split(" ")
        if (items.size != 2) return ""

        val itemName = items[0]
        val itemPrice = items[1].toDoubleOrNull() ?: return ""

        if (itemName.isEmpty()) return ""

        goods.add(itemName to itemPrice)
    }

    if (goods.isEmpty()) return ""

    return goods.fold("" to -1.0) { acc, it -> if (acc.second > it.second) acc else it }.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var result = 0
    val romanMap = mapOf(
        "I" to 1,
        "IV" to 4,
        "V" to 5,
        "IX" to 9,
        "X" to 10,
        "XL" to 40,
        "L" to 50,
        "XC" to 90,
        "C" to 100,
        "CD" to 400,
        "D" to 500,
        "CM" to 900,
        "M" to 1000
    )
    val validChars = romanMap.keys.toSet()
    if (!roman.toSet().all { it.toString() in validChars }) return -1

    val romanListOfChar = roman.toList()

    if (romanListOfChar.isEmpty()) return -1
    if (romanListOfChar.size == 1) return romanMap[romanListOfChar[0].toString()]!!

    var i = 0
    var left = romanListOfChar.slice(i until romanListOfChar.size).joinToString("")
    while (i < romanListOfChar.size) {
        val currentNumber = romanMap.keys
            .sortedByDescending { it.length }.first { left.startsWith(it) }

        result += romanMap[currentNumber]!!


        i += currentNumber.length
        left = romanListOfChar.slice(i until romanListOfChar.size).joinToString("")
        //println("$roman $currentNumber $left $result")
    }

    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = mutableListOf(*(0 until cells).map { 0 }.toTypedArray())
    var pointer = cells / 2
    var commandPointer = 0
    val cleanCommands = commands.toList()

    val validChars = " ><+-[]".toSet()
    if (!cleanCommands.toSet().all { it in validChars }) throw IllegalArgumentException("ошибочные символы")

    val checkBrackets = commands.toList().filter { it in "[]" }

    if (checkBrackets.size % 2 != 0) throw IllegalArgumentException("непарные скобки")
    var bracketsCounter = 0
    for (i in checkBrackets) {
        if (i == '[') bracketsCounter++
        else bracketsCounter--
        if (bracketsCounter < 0) throw IllegalArgumentException("непарные скобки")
    }
    if (bracketsCounter != 0) throw IllegalArgumentException("непарные скобки")

    var i = 1
    while (commandPointer < cleanCommands.size) {
//        println("$limit $i")
//        println("Start - $result")
//        println(
//            "        [${
//                result
//                    .indices.joinToString(", ") {
//                        if (it == pointer) times("^", result[it].toString().length)
//                        else times(" ", result[it].toString().length)
//                    }
//            }]"
//        )
//        println(cleanCommands.joinToString(separator = ""))
//        println(cleanCommands.mapIndexed { index, c -> if (index == commandPointer) c else ' ' }
//            .joinToString(separator = ""))


        when (cleanCommands[commandPointer]) {
            '>' -> pointer += 1
            '<' -> pointer -= 1
            '+' -> result[pointer] += 1
            '-' -> result[pointer] -= 1
            '[' -> {
                if (result[pointer] == 0) {
                    var counter = 1
                    while (true) {
                        commandPointer++
                        if (cleanCommands[commandPointer] == ']') {
                            counter--
                            if (counter == 0) {
                                break
                            }
                        }
                        if (cleanCommands[commandPointer] == '[') counter++
                    }
                }
            }
            ']' -> {
                if (result[pointer] != 0) {
                    var counter = 1
                    while (true) {
                        commandPointer--
                        if (cleanCommands[commandPointer] == '[') {
                            counter--
                            if (counter == 0) {
                                break
                            }
                        }
                        if (cleanCommands[commandPointer] == ']') counter++
                    }
                }
            }
            ' ' -> {
                /* no-op */
            }
            else -> {
            }
        }
        if (pointer !in result.indices) throw IllegalStateException("Выход за границу конвейера")

//        println("End   - $result")
//        println(
//            "        [${
//                result
//                    .indices.joinToString(", ") {
//                        if (it == pointer) times("^", result[it].toString().length)
//                        else times(" ", result[it].toString().length)
//                    }
//            }]"
//        )

        i++
        commandPointer++

        if (i > limit) break
    }

    return result
}
