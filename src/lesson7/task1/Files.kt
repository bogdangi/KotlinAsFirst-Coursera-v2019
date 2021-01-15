@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.Integer.max

fun times(str: String, t: Int): String {
    var result = ""
    repeat(t) { result += str }
    return result
}

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()

    for (line in File(inputName).readLines()) {
        for (substring in substrings.toSet().map { it.toLowerCase() }) {
            if (line.toLowerCase().contains(substring)) {
                var newLine = line.toLowerCase()

                do {
                    if (newLine.startsWith(substring)) {
                        result[substring] = (result[substring] ?: 0) + 1
                    }

                    newLine = newLine.slice(1 until newLine.length)
                } while (newLine.contains(substring))
            }
        }
    }

    return substrings.map { it to (result[it.toLowerCase()] ?: 0) }.toMap()
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val replaceTo = mapOf(
        "Ы" to "И",
        "Ы".toLowerCase() to "И".toLowerCase(),
        "Я" to "А",
        "Я".toLowerCase() to "А".toLowerCase(),
        "Ю" to "У",
        "Ю".toLowerCase() to "У".toLowerCase()
    )

    for (line in File(inputName).readLines()) {
        var updatedLine = line
        for (i in setOf("Ж", "Ч", "Ш", "Щ") + setOf("Ж", "Ч", "Ш", "Щ").map { it.toLowerCase() }) {
            for (j in setOf("Ы", "Я", "Ю") + setOf("Ы", "Я", "Ю").map { it.toLowerCase() }) {
                updatedLine = updatedLine.replace(i + j, i + replaceTo[j])
            }
        }
        outputStream.write(updatedLine)
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    val listOfLines = mutableListOf<String>()

    var maxLenght = 0
    for (line in File(inputName).readLines()) {
        listOfLines.add(line.trim())
        maxLenght = max(listOfLines.last().length, maxLenght)
    }

    for (line in listOfLines) {
        val lineLength = line.length

        outputStream.write(times(" ", (maxLenght - lineLength) / 2) + line)
        outputStream.newLine()
    }

    outputStream.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    val listOfLines = mutableListOf<String>()

    var maxLenght = 0
    for (line in File(inputName).readLines()) {
        listOfLines.add(line.trim())
        maxLenght = max(listOfLines.last().length, maxLenght)
    }

    for (line in listOfLines) {
        val words = line.split(" +".toRegex())
        val lineLength = words.joinToString(" ").length
        val numberOfWords = words.size

        var updatedLine = ""

        val numberOfSpaces = if (numberOfWords < 2) {
            0
        } else {
            (maxLenght - lineLength) / (numberOfWords - 1) + 2
        }

        var numberOfWordsWithAdditionalSpace = (words.joinToString(
            separator = times(
                " ",
                numberOfSpaces
            )
        ).length) - maxLenght

        if (numberOfWordsWithAdditionalSpace < 0) numberOfWordsWithAdditionalSpace = 0

        for (i in words.indices) {
            val word = words[i]
            when {
                i == 0 -> updatedLine += word
                i >= numberOfWords - numberOfWordsWithAdditionalSpace -> {
                    updatedLine += times(" ", numberOfSpaces - 1) + word
                }
                else -> {
                    updatedLine += times(" ", numberOfSpaces) + word
                }
            }
        }

        outputStream.write(updatedLine)
        outputStream.newLine()
    }

    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val result = mutableMapOf<String, Int>()

    for (line in File(inputName).readLines()) {
        for (word in line.toLowerCase().split("[^a-zа-яё]+".toRegex())) {
            if (word != "") result[word] = (result[word] ?: 0) + 1
        }
    }

    return result
        .toList()
        .sortedByDescending { it.second }
        .take(20)
        .toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val newDictionary = mutableMapOf<Char, String>()

    for ((k, v) in dictionary) {
        if (k.toLowerCase() == k.toUpperCase()) {
            newDictionary[k] = v
        } else {
            newDictionary[k.toLowerCase()] = v.toLowerCase()
            newDictionary[k.toUpperCase()] =
                v.mapIndexed { index, c -> if (index == 0) c.toUpperCase() else c.toLowerCase() }.joinToString("")
        }
    }

    for (c in File(inputName).readText()) {
        if (newDictionary[c] != null) {
            outputStream.write(newDictionary[c]!!)
        } else {
            outputStream.write(c.toString())
        }
    }

    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    val listOfWords = mutableListOf<Pair<String, Int>>()
    var maxLenght = 0
    for (line in File(inputName).readLines()) {
        if (line.length == line.toSet().size) {
            listOfWords.add(line to line.toLowerCase().toSet().size)
            maxLenght = max(listOfWords.last().second, maxLenght)
        }
    }
    outputStream.write(
        listOfWords
            .filter { it.second == maxLenght }
            .map { it.first }
            .joinToString(", ")
    )

    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    outputStream.write("<html>")
    outputStream.newLine()
    outputStream.write("<body>")
    outputStream.newLine()

    val inputText = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            for (l in markdownToHtmlSimple(inputText)) {
                if (l.isEmpty()) {
                    outputStream.newLine()
                } else {
                    outputStream.write(l)
                }
            }
            inputText.clear()
        } else {
            inputText.add(line)
        }
    }

    for (line in markdownToHtmlSimple(inputText)) {
        if (line.isEmpty()) {
            outputStream.newLine()
        } else {
            outputStream.write(line)
        }
    }
    inputText.clear()

    outputStream.write("</body>")
    outputStream.newLine()
    outputStream.write("</html>")
    outputStream.newLine()

    outputStream.close()
}

fun markdownToHtmlSimple(inputText: List<String>): List<String> {
    val result = mutableListOf<String>()

    result.add("<p>")

    val lineUpdated = inputText.joinToString("")

    var newLine = ""
    val stack = mutableListOf<Pair<Int, String>>()

    var c = 0

    while (c < lineUpdated.length) {
        when {
            "^~~".toRegex().containsMatchIn(lineUpdated.slice(c until lineUpdated.length)) -> {
                if (stack.isNotEmpty() && stack.last().second == "<s>") {
                    stack.remove(stack.last())
                    newLine += "</s>"
                } else {
                    stack.add(c to "<s>")
                    newLine += "<s>"
                }
                c += 2
            }
            "^\\*\\*".toRegex().containsMatchIn(lineUpdated.slice(c until lineUpdated.length)) -> {
                if (stack.isNotEmpty() && stack.last().second == "<b>") {
                    stack.remove(stack.last())
                    newLine += "</b>"
                } else {
                    stack.add(c to "<b>")
                    newLine += "<b>"
                }
                c += 2
            }
            "^\\*".toRegex().containsMatchIn(lineUpdated.slice(c until lineUpdated.length)) -> {
                if (stack.isNotEmpty() && stack.last().second == "<i>") {
                    stack.remove(stack.last())
                    newLine += "</i>"
                } else {
                    stack.add(c to "<i>")
                    newLine += "<i>"
                }
                c += 1
            }
            else -> {
                newLine += lineUpdated[c]
                c += 1
            }
        }
    }

    result.add(newLine)
    result.add("</p>")
    result.add("")


    return result
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    outputStream.write("<html>")
    outputStream.newLine()
    outputStream.write("<body>")
    outputStream.newLine()

    val inputText = mutableListOf<String>()

    for (line in File(inputName).readLines()) {
        inputText.add(line)
    }

    for (line in markdownToHtmlLists(inputText)) {
        if (line.isEmpty()) {
            outputStream.newLine()
        } else {
            outputStream.write(line)
        }
    }

    outputStream.write("</body>")
    outputStream.newLine()
    outputStream.write("</html>")
    outputStream.close()

}

fun markdownToHtmlLists(inputText: List<String>): List<String> {
    val result = mutableListOf<String>()

    val stack = mutableListOf<String>()

    var currentLevel = -1
    for (line in inputText) {
        val type = if (line.trim().startsWith("*")) "ul" else "ol"
        var level = 0
        //println(line)
        val newLine = if (type == "ul") {
            line.replace(Regex("^ *\\* "), "")
        } else {
            line.replace(Regex("^ *[0-9]+\\."), "")
        }

        while (true) {
            if (line[level] != ' ') break
            level++
        }

        when {
            level > currentLevel -> {
                //println("${times(" ", stack.size * 2)}<$type>")
                result.add("${times(" ", stack.size * 2)}<$type>")
                result.add("")

                stack.add(type)

                //println("${times(" ", stack.size * 2)}<li>")
                result.add("${times(" ", stack.size * 2)}<li>")
                result.add("")

                stack.add("li")

                //println("${times(" ", (stack.size) * 2)}$newLine")
                result.add("${times(" ", (stack.size) * 2)}$newLine")
                result.add("")

                currentLevel = level
            }
            level < currentLevel -> {
                for (i in 0 until ((currentLevel - level) / 4)) {
                    if (stack.isNotEmpty() && stack.last() == "li") {
                        //println("${times(" ", (stack.size - 1) * 2)}</li>")
                        result.add("${times(" ", (stack.size - 1) * 2)}</li>")
                        result.add("")

                        stack.removeAt(stack.size - 1)
                    }
                    //println("${times(" ", (stack.size - 1) * 2)}</${stack.last()}>")
                    result.add("${times(" ", (stack.size - 1) * 2)}</${stack.last()}>")
                    result.add("")

                    stack.removeAt(stack.size - 1)
                }

                if (stack.isNotEmpty() && stack.last() == "li") {
                    //println("${times(" ", (stack.size - 1) * 2)}</li>")
                    result.add("${times(" ", (stack.size - 1) * 2)}</li>")
                    result.add("")

                    stack.removeAt(stack.size - 1)
                }

                //println("${times(" ", stack.size * 2)}<li>")
                result.add("${times(" ", stack.size * 2)}<li>")
                result.add("")

                stack.add("li")

                //println("${times(" ", (stack.size) * 2)}$newLine")
                result.add("${times(" ", (stack.size) * 2)}$newLine")
                result.add("")

                currentLevel = level
            }
            else -> {
                if (stack.isNotEmpty() && stack.last() == "li") {
                    //println("${times(" ", (stack.size - 1) * 2)}</li>")
                    result.add("${times(" ", (stack.size - 1) * 2)}</li>")
                    result.add("")

                    stack.removeAt(stack.size - 1)
                }

                //println("${times(" ", stack.size * 2)}<li>")
                result.add("${times(" ", stack.size * 2)}<li>")
                result.add("")

                stack.add("li")

                //println("${times(" ", (stack.size) * 2)}$newLine")
                result.add("${times(" ", (stack.size) * 2)}$newLine")
                result.add("")
            }
        }
        //println(stack)
    }

    while (stack.isNotEmpty()) {
        //println("${times(" ", (stack.size - 1) * 2)}</${stack.last()}>")

        result.add("${times(" ", (stack.size - 1) * 2)}</${stack.last()}>")
        result.add("")

        stack.removeAt(stack.size - 1)
        currentLevel -= 4
    }

    return result
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    outputStream.write("<html>")
    outputStream.newLine()
    outputStream.write("<body>")
    outputStream.newLine()

    val inputText = mutableListOf<String>()
    var currentType = "null"
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            if (currentType == "list") {
                for (l in markdownToHtmlLists(inputText)) {
                    if (l.isEmpty()) {
                        outputStream.newLine()
                    } else {
                        outputStream.write(l)
                    }
                }
            } else {
                for (l in markdownToHtmlSimple(inputText)) {
                    if (l.isEmpty()) {
                        outputStream.newLine()
                    } else {
                        outputStream.write(l)
                    }
                }
            }
            inputText.clear()
            currentType = "null"
        } else {
            if (currentType == "null") {
                currentType = when {
                    line.matches(Regex("^ *\\*.*")) || line.matches(Regex("^ *[0-1].*")) -> "list"
                    else -> "paragraph"
                }
            }
            inputText.add(line)
        }
    }

    if (currentType == "list") {
        for (line in markdownToHtmlLists(inputText)) {
            if (line.isEmpty()) {
                outputStream.newLine()
            } else {
                outputStream.write(line)
            }
        }
    } else {
        for (line in markdownToHtmlSimple(inputText)) {
            if (line.isEmpty()) {
                outputStream.newLine()
            } else {
                outputStream.write(line)
            }
        }
    }
    inputText.clear()

    outputStream.write("</body>")
    outputStream.newLine()
    outputStream.write("</html>")
    outputStream.newLine()

    outputStream.close()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val lhvLen = lhv.toString().length
    val rhvLen = rhv.toString().length
    val totalLen = (lhv * rhv).toString().length + 1
    val maxLen = (lhv * rhv).toString().length

    outputStream.write("${times(" ", totalLen - lhvLen)}$lhv")
    outputStream.newLine()

    outputStream.write("*${times(" ", totalLen - rhvLen - 1)}$rhv")
    outputStream.newLine()

    outputStream.write(times("-", totalLen))
    outputStream.newLine()

    for (i in rhvLen - 1 downTo 0) {
        val result = rhv.toString()[i].toString().toInt() * lhv
        if (i == rhvLen - 1) {
            outputStream.write(" ${times(" ", totalLen - (rhvLen - i) - result.toString().length)}$result")
        } else {
            outputStream.write("+${times(" ", totalLen - (rhvLen - i) - result.toString().length)}$result")
        }
        outputStream.newLine()
    }

    outputStream.write(times("-", totalLen))
    outputStream.newLine()

    outputStream.write("${times(" ", totalLen - maxLen)}${lhv * rhv}")
    outputStream.newLine()

    outputStream.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    val steps = giveMeDivisionSteps(lhv, rhv)

    val filteredSteps = if (steps.indexOf(steps.find { it["Multiplier"]!! > 0 }) >= 0) {
        steps
            .slice(steps.indexOf(steps.find { it["Multiplier"]!! > 0 }) until steps.size)
    } else {
        steps
    }

    val lines = mutableListOf<String>()

    var first = true

    val addSpace = addOneMoreSpace(lhv, rhv)

    for (step in filteredSteps) {
        if (first) {
            first = false
            val firstLine = giveMeDivisionProcessFirstLine(lhv, rhv)

            lines.add(firstLine)

            lines.add(
                "-${step["Result"]}${
                    times(
                        " ",
                        lhv.toString().length - "${step["Result"]}".length - if (addSpace) 0 else 1
                    )
                }   ${lhv / rhv}"
            )


            val delimiter = times("-", step["Result"].toString().length + 1)

            lines.add(delimiter)
        } else {
            val spacesB = times(" ", step["Position"]!! - step["Buffer"].toString().length + 1)


            lines.add(
                "%${step["Position"]!! + 1 + if (addSpace) 1 else 0}s"
                    .format("$spacesB-${step["Result"]}")
            )

            val delimiter = "%${step["Position"]!! + 1 + if (addSpace) 1 else 0}s"
                .format(times("-", step["Result"].toString().length + 1))

            lines.add(delimiter)

        }


        if (step == filteredSteps.last()) {

            lines.add("%${step["Position"]!! + 1 + if (addSpace) 1 else 0}s".format(step["Rest"]))
        } else {
            if (step["Rest"]!! < 10) {
                lines.add(
                    "%${step["Position"]!! + 2 + if (addSpace) 1 else 0}s".format(
                        "0" + step["Rest"]!!.toString()
                    )
                )
            } else {
                lines.add("%${step["Position"]!! + 2 + if (addSpace) 1 else 0}s".format(step["Rest"]))
            }
        }
    }

//    filteredSteps
//        .forEach { println(it) }
//    lines
//        .forEach { println(it) }

    lines.forEach {
        outputStream.write(it)
        outputStream.newLine()
    }

    outputStream.close()
}

fun giveMeDivisionProcessFirstLine(lhv: Int, rhv: Int): String = when {
    addOneMoreSpace(lhv, rhv) -> " $lhv | $rhv"
    else -> "$lhv | $rhv"
}

fun addOneMoreSpace(lhv: Int, rhv: Int): Boolean = when {
    (lhv / rhv * rhv).toString().length < lhv.toString().length -> false
    else -> true
}

fun giveMeDivisionSteps(lhv: Int, rhv: Int): List<Map<String, Int>> {
    val steps = mutableListOf<Int>()

    val lhvList = lhv.toString().map { it.toString().toInt() }
    var buff = lhvList[0].toString()
    for (i in lhvList.indices) {
        steps.add(i)

        steps.add(buff.toInt())

        val r = buff.toInt() / rhv

        steps.add(r * rhv)

        steps.add(r)

        var rest = buff.toInt() - r * rhv

        if (i + 1 < lhvList.size) rest = (rest.toString() + lhvList[i + 1].toString()).toInt()

        steps.add(rest)

        buff = rest.toString()
    }

    return steps
        .chunked(5)
        .map {
            mapOf(
                "Position" to it[0],
                "Buffer" to it[1],
                "Result" to it[2],
                "Multiplier" to it[3],
                "Rest" to it[4]
            )
        }
}
