import kotlin.system.exitProcess

class Reader {
    val idRegex = "[a-z]|[A-Z]|[0-9]|[_]".toRegex()
    val numRegex = "[0-9]".toRegex()
    val opRegex = "(\\+|-|\\*|/)".toRegex()

    val FIRST_STATE = 1
    val SECOND_STATE = 2
    val THIRD_STATE = 3
    val FOURTH_STATE = 4
    val FIFTH_STATE = 5
    val SIXTH_STATE = 6
    val SEVEN_STATE = 7
    val EIGHTH_STATE = 8
    val NINETH_STATE = 9

    var space = false // For when '=' is found
    var numFound = false
    var currentState = FIRST_STATE

    fun start(expression: String) {
        expression.forEach {
            when (currentState) {
                FIRST_STATE -> validateFirst(it)
                SECOND_STATE -> validateSecond(it)
                THIRD_STATE -> validateThird(it)
                FOURTH_STATE -> validateFourth(it)
                FIFTH_STATE -> validateFifth(it)
                SIXTH_STATE -> validateSixth(it)
                SEVEN_STATE -> validateSeventh(it)
                EIGHTH_STATE -> validateEighth(it)
            }
        }
    }

    private fun validateFirst(char: Char) {
        if (char == ' ') {
            // ignore
            return
        }
        if (char.isLowerCase()) {
            currentState = SECOND_STATE
            this.print(char)
        }
        else endProgram()
    }

    private fun validateSecond(char: Char) {
        if (char == '=') {
            currentState = THIRD_STATE
            this.print(char)
            currentState = FOURTH_STATE
            space = false
        } else if (char == ' ') {
            space = true
        } else if (idRegex.matches(char.toString()) && !space) {
            this.print(char)
            space = false
        } else {
            endProgram()
        }
    }

    private fun validateThird(char: Char) {
        if (char == ' ') {
            space = true
        } else if (char == '=') {
            this.print(char)
            currentState = FOURTH_STATE
            space = false
        } else {
            endProgram()
        }
    }

    private fun validateFourth(char: Char) {
        if (numRegex.matches(char.toString()) && !space && !numFound) {
            numFound = true
            this.print(char)
        } else if (char == ' ') {
            space = numFound
        } else if (opRegex.matches(char.toString())) {
            space = false
            numFound = false
            currentState = SIXTH_STATE
            this.print(char)
        } else if (char.isLowerCase()) {
            space = false
            numFound = true
            currentState = FIFTH_STATE
            this.print(char)
        } else if (char == ';') {
            finishProgram()
        } else {
            endProgram()
        }
    }

    private fun validateFifth(c: Char) {
        if (idRegex.matches(c.toString()) && !space) {
            numFound = true
            this.print(c)
        } else if (c == ' ') {
            space = numFound
        } else if (opRegex.matches(c.toString())) {
            space = false
            numFound = false
            currentState = SIXTH_STATE
            this.print(c)
        } else if (c == ';') {
            finishProgram()
        } else {
            endProgram()
        }
    }

    private fun validateSixth(c: Char) {
        if (numRegex.matches(c.toString()) && !space) {
            numFound = true
            currentState = SEVEN_STATE
            this.print(c)
        } else if (c.isLowerCase() && !space) {
            numFound = true
            currentState = EIGHTH_STATE
            this.print(c)
        } else if (c == ' ') {
            space = numFound
        } else {
            endProgram()
        }
    }

    private fun validateSeventh(c: Char) {
        if (numRegex.matches(c.toString()) && !space && !numFound) {
            numFound = true
            this.print(c)
        } else if (c == ' ') {
            space = numFound
        } else if (opRegex.matches(c.toString())) {
            space = false
            numFound = false
            currentState = SIXTH_STATE
            this.print(c)
        } else if (c == ';') {
            finishProgram()
        } else {
            endProgram()
        }
    }

    private fun validateEighth(c: Char) {
        if (idRegex.matches(c.toString()) && !space) {
            numFound = true
            this.print(c)
        } else if (c == ' ') {
            space = numFound
        } else if (opRegex.matches(c.toString())) {
            space = false
            numFound = false
            currentState = SIXTH_STATE
            this.print(c)
        } else if (c == ';') {
            finishProgram()
        } else {
            endProgram()
        }
    }

    private fun print(letter: Char) {
        println("Փ($letter, $currentState)")
    }

    fun finishProgram() {
        println("Փ(;, 9)")
        println("String válida.")
    }

    fun endProgram() {
        println("String inválida.")
        exitProcess(1)
    }
}