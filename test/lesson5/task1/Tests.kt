package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            buildWordSet("Я люблю Котлин".split(" ")),
            mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
            buildWordSet("Я люблю люблю Котлин".split(" ")),
            mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
            buildWordSet(listOf()),
            mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertTrue(canBuildFrom(listOf('a'), ""))
        assertFalse(canBuildFrom(listOf('s'), "a"))
        assertTrue(canBuildFrom(listOf('x'), "X"))
        assertTrue(canBuildFrom(listOf('X'), "x"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertTrue(hasAnagrams(listOf("", "")))
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta", "Bogdan"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf(),
                "Bogdan" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta", "Bogdan"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta", "Bogdan", "Valera", "Tom", "Gray"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf(),
                "Tom" to setOf("Mikhail", "Sveta", "Marat", "Bogdan", "Gray", "Valera"),
                "Valera" to setOf("Mikhail", "Sveta", "Marat", "Bogdan", "Gray", "Tom"),
                "Gray" to setOf("Mikhail", "Sveta", "Marat", "Bogdan", "Valera", "Tom"),
                "Bogdan" to setOf("Mikhail", "Sveta", "Marat", "Valera", "Tom", "Gray")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta", "Bogdan"),
                    "Tom" to setOf("Bogdan", "Gray"),
                    "Gray" to setOf("Bogdan", "Marat", "Sveta"),
                    "Sveta" to setOf("Mikhail"),
                    "Valera" to setOf("Tom"),
                    "Bogdan" to setOf("Marat", "Valera")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta")
                )
            )
        )
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
            setOf(
                "3",
                "1",
                "0"
            ),
            bagPacking(
                mapOf(
                    "0" to (148 to 149),
                    "1" to (150 to 269),
                    "2" to (292 to 1),
                    "3" to (2 to 120)
                ),
                458
            )
        )

        assertEquals(
            setOf(
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
            ),
            bagPacking(
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
                ),
                458
            )
        )

        assertEquals(
            setOf(
                "9",
                "8",
                "7",
                "6",
                "5",
                "4",
                "3",
                "2",
                "1"
            ),
            bagPacking(
                mapOf(
                    "0" to (107 to 25),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (1 to 1),
                    "6" to (249 to 73),
                    "7" to (465 to 53),
                    "8" to (148 to 130),
                    "9" to (105 to 149)
                ),
                1028
            )
        )

        assertEquals(
            setOf(
                "6",
                "3"
            ),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 149),
                    "4" to (1 to 1),
                    "5" to (1 to 1),
                    "6" to (1 to 148),
                    "7" to (2 to 284),
                    "8" to (1 to 1),
                    "9" to (1 to 1)
                ),
                2
            )
        )
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            setOf("Слиток"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (550 to 5000)),
                850
            )
        )
        assertEquals(
            setOf("Слиток", "Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (550 to 5000)),
                1050
            )
        )
        assertEquals(
            setOf("Слиток", "Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (550 to 5000)),
                1050
            )
        )
        assertEquals(
            setOf("Слиток", "Медная ваза"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (550 to 5000), "Медная ваза" to (450 to 2500)),
                1050
            )
        )
        assertEquals(
            setOf("Слиток", "Кубок", "Медная ваза"),
            bagPacking(
                mapOf(
                    "Слиток" to (550 to 5000),
                    "Кубок" to (500 to 2000),
                    "Медная ваза" to (450 to 2500),
                    "Чашка из электрума" to (200 to 1000),
                    "Кованая золотая бутылка" to (950 to 2500),
                    "Медный щит" to (2000 to 2000),
                    "Золото Монтесумы" to (20000 to 100000),
                    "Сокровища Лимы" to (22000 to 100000),
                    "Тайник Малайского Тигра" to (20000 to 100000),
                    "Богатство свитков Мертвого моря" to (20000 to 100000),
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (1 to 1),
                    "6" to (1 to 1),
                    "7" to (1 to 1),
                    "8" to (1 to 1),

                    "Медный котел с ручками" to (8000 to 9000)
                ),
                1500
            )
        )
        assertEquals(
            setOf(
                "19",
                "6",
                "12",
                "18",
                "17",
                "16",
                "15",
                "14",
                "13",
                "11",
                "10",
                "9",
                "8",
                "7",
                "5",
                "4",
                "3",
                "2",
                "1",
                "0"
            ),
            bagPacking(
                mapOf(
                    "20" to (148 to 234),
                    "19" to (294 to 371),
                    "12" to (149 to 249),
                    "6" to (212 to 433),
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (1 to 1),
                    "7" to (1 to 1),
                    "8" to (1 to 1),
                    "9" to (1 to 1),
                    "10" to (1 to 1),
                    "11" to (1 to 1),
                    "13" to (1 to 1),
                    "14" to (1 to 1),
                    "15" to (1 to 1),
                    "16" to (1 to 1),
                    "17" to (1 to 1),
                    "18" to (1 to 1)
                ),
                777
            )
        )



        assertEquals(
            setOf(
                "16",
                "14",
                "13",
                "12",
                "11",
                "10",
                "9",
                "8",
                "7",
                "6",
                "5",
                "4",
                "3",
                "1",
                "0"
            ),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (185 to 58),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (1 to 1),
                    "6" to (460 to 125),
                    "7" to (1 to 1),
                    "8" to (1 to 1),
                    "9" to (1 to 1),
                    "10" to (294 to 93),
                    "11" to (1 to 1),
                    "12" to (1 to 1),
                    "13" to (1 to 1),
                    "14" to (1 to 1),
                    "15" to (412 to 1),
                    "16" to (1 to 1)
                ),
                913
            )
        )

        assertEquals(
            setOf(
                "8",
                "7",
                "5",
                "4",
                "3",
                "2",
                "1",
                "0"
            ),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (70 to 186),
                    "4" to (101 to 234),
                    "5" to (1 to 1),
                    "6" to (199 to 325),
                    "7" to (1 to 1),
                    "8" to (1 to 1)
                ),
                245
            )
        )

        assertEquals(
            setOf(
                "7"
            ),
            bagPacking(
                mapOf(
                    "0" to (2 to 1),
                    "1" to (2 to 1),
                    "2" to (1 to 1),
                    "3" to (2 to 1),
                    "4" to (2 to 1),
                    "5" to (1 to 1),
                    "6" to (2 to 1),
                    "7" to (1 to 2),
                    "8" to (2 to 1)
                ),
                1
            )
        )

        assertEquals(
            setOf(
                "0",
                "3"
            ),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 2)
                ),
                2
            )
        )

        assertEquals(
            setOf(
                "7",
                "6",
                "4",
                "3",
                "2",
                "1"
            ),
            bagPacking(
                mapOf(
                    "0" to (500 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (493 to 1),
                    "6" to (474 to 2),
                    "7" to (1 to 1)
                ),
                912
            )
        )

        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )

        assertEquals(
            (1..100).map { it.toString() }.toSet(),
            bagPacking(
                (1..500).map { it.toString() to (1 to 1) }.toMap(),
                100
            )
        )
    }

    // TODO: map task tests
}
