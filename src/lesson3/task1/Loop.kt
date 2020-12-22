@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var newN = n
    do{
        count++
        newN /= 10
    } while (kotlin.math.abs(newN) > 0)

    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n <= 2) return 1
    var n1 = 1
    var n2 = 1
    var result = 0

    for (i in 3..n) {
        result = n1 + n2
        n1 = n2
        n2 = result
    }
    return result
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val max = kotlin.math.max(m,n)
    var minNumber = max
    while (!((minNumber % n == 0) && (minNumber % m == 0))) {
        minNumber += max
    }
    return minNumber
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minNumber = 2
    while (n % minNumber != 0) {
        minNumber++
    }

    return minNumber
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    val maxNumber = n / 2
    for (i in maxNumber downTo 2) {
        if (n % i == 0) return i
    }

    return 1
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val maxNumber = kotlin.math.max(n,m) / 2
    for (i in 2..maxNumber) {
        if (n % i == 0 && m % i == 0) return false
    }

    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    if (m == Int.MAX_VALUE || n == Int.MAX_VALUE) return false

    val min = kotlin.math.min(m,n)
    val max = kotlin.math.max(m,n)
    var minToSquare = sqrt(min.toDouble()).toInt()

    while (minToSquare * minToSquare <= max) {
        if ((minToSquare * minToSquare) <= max && (minToSquare * minToSquare) >= min) {
            return true
        }
        minToSquare++
    }

    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var nextX = x
    var count = 0
    while (nextX != 1) {
        if (nextX % 2 == 0) nextX /= 2
        else nextX = 3 * nextX + 1
        count++
    }

    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    var result = 0.0
    var step = 1.0
    var portion: Double
    val newX = x % (kotlin.math.PI * 2)
    while (true) {
        portion = pow(newX, step) / factorial(step.toInt())
        result += portion
        if (eps > portion) return result
        step += 2
        portion = pow(newX, step) / factorial(step.toInt())
        result -= portion
        if (eps > portion) return result
        step += 2
    }
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    var result = 0.0
    var step = 0.0
    var portion: Double
    val newX = x % (kotlin.math.PI * 2)
    while (true) {
        portion = pow(newX, step) / factorial(step.toInt())
        result += portion
        if (eps > portion) return result
        step += 2
        portion = pow(newX, step) / factorial(step.toInt())
        result -= portion
        if (eps > portion) return result
        step += 2
    }}
/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    val len = kotlin.math.ceil(kotlin.math.log10(n.toDouble() + 1)).toInt()

    var result = 0
    var newN = n

    for (i in 1..len) {
        result += (newN % 10) * (pow(10.0, (len - i + 1).toDouble())).toInt()

        newN /= 10
    }

    return result / 10
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = revert(n) == n

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val len = kotlin.math.ceil(kotlin.math.log10(n.toDouble() + 1)).toInt()

    var newN = n
    var lastNumber = newN % 10

    for (i in 1..len) {
        if (lastNumber != newN % 10) return true
        lastNumber = newN % 10
        newN /= 10
    }

    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var i = 1
    var newN = n
    do {
        val len = kotlin.math.ceil(kotlin.math.log10(sqr(i).toDouble() + 1)).toInt()
        when {
            len == newN -> return sqr(i) % 10
            len > newN -> return (sqr(i) / (10.0.pow((len - newN).toDouble())) % 10).toInt()
            else -> newN -= len
        }
        i++
    } while (n > 0)

    return -1
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var i = 1
    var newN = n
    do {
        val len = kotlin.math.ceil(kotlin.math.log10(fib(i).toDouble() + 1)).toInt()
        when {
            len == newN -> return fib(i) % 10
            len > newN -> return (fib(i) / (10.0.pow((len - newN).toDouble())) % 10).toInt()
            else -> newN -= len
        }
        i++
    } while (n > 0)

    return -1
}
