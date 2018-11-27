package io.jul.morsey

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView

class Morsey {

    // Defining a Character Array of the English alphabets, Numbers and Symbols
    // so that we can use them as keys for the morseCode map
    private var englishAlphabets = arrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            ',', '.', '?', ' ')

    // Defining an Array of String to hold the Morse Code value of Every English alphabet, Number
    // and Symbol in the same order as that of the character Array
    private var morse = arrayOf(
            ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.", "...", "-",
            "..-", "...-", ".--", "-..-", "-.--", "--..",
            ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----",
            "--..--", ".-.-.-", "..--..", " ")

    init {
        for (i in 0 until englishAlphabets.size) {
            morseCodes[englishAlphabets[i]] = morse[i]
            inverseMorseCodes[morse[i]] = englishAlphabets[i]
        }
    }

    fun morseRealTime(inputField: EditText, outputLabel: TextView) {
        inputField.onTextChanged {
            outputLabel.text = morse(it)
        }
    }

    fun unMorseRealTime(inputField: EditText, outputLabel: TextView) {
        inputField.onTextChanged {
            outputLabel.text = unMorse(it)
        }
    }

    fun unregisterMorsey(inputField: EditText) {
        inputField.removeTextChangedListener(textWatcher)
    }

    private val textWatcher = (object : TextWatcher {
        lateinit var onTextChanged: (String) -> Unit

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(chars.toString())
        }

    })

    companion object {

        private val morseCodes: LinkedHashMap<Char, String> = LinkedHashMap()
        private val inverseMorseCodes: LinkedHashMap<String, Char> = LinkedHashMap()

        fun morse(entry: String): String {
            var morseCode = ""
            val input = entry.toLowerCase()
            for (letter in input.toCharArray()) {
                morseCode += if (morseCodes[letter] == null) {
                    letter
                } else {
                    morseCodes[letter]
                }
            }

            return morseCode
        }

        /**
         * Static method to convert a Morse coded string to plain English.
         *
         * @param entry refers to the Morse string
         * @param charDelimiter refers to the optional delimiter used to separate each morse character. The default is a single whitespace character.
         * @param wordDelimiter refers to the optional delimiter used to separate each morse word. The default is a double whitespace character.
         */
        fun unMorse(entry: String, charDelimiter: String = " ", wordDelimiter: String = ";"): String {
            var english = ""

            var input = entry.toLowerCase()

            while (input.isNotEmpty()) {

                if (input.last().toString() !== charDelimiter) {
                    input += charDelimiter
                }

                if (charDelimiter == " " && wordDelimiter == ";") {
                    var subIndex = 0
                    var subInput = input

                    for (index in input.indices) {
                        if (input[index].toString() == charDelimiter
                                || input[index].toString() == wordDelimiter
                        ) {
                            val workingChars = input.substring(subIndex, index)
                            Log.e("MORSEY", "Working Chars is: $workingChars")

                            english += if (inverseMorseCodes[workingChars] == null) {
                                ""
                            } else {
                                inverseMorseCodes[workingChars]
                            }
                            Log.e("MORSEY", "Conversion is: " + inverseMorseCodes[workingChars])
                            if (input[index].toString() == wordDelimiter) {
                                english += " "
                            }

                            subIndex = index + 1
                            subInput = input.drop(subIndex)
                            Log.e("MORSEY", "Input now is: $input")

                            if (subInput.isEmpty()) {
                                input = ""
                                break
                            }
                        }
                    }
                }

//                val subStringLimit = when (input.length) {
//                    1, 2, 3, 4, 5 -> input.length
//                    else -> 6
//                }
//
//                var workingChars = input.substring(0, subStringLimit)
//
//                for (charIndex in input.indices) {
//                    if (input[charIndex].toString() == charDelimiter
//                            && charIndex + 1 < input.lastIndex
//                            && input[charIndex + 1].toString() != charDelimiter) {
//                        workingChars = input.substring(0, charIndex + 1)
////                        input = input.drop(1)
//                    } else {
//                        workingChars = input
//                    }
//                }
//
//                while (workingChars.isNotEmpty()) {
//                    if (inverseMorseCodes[workingChars] == null) {
//                        workingChars = workingChars.dropLast(1)
//                    } else {
////                        input = input.drop(workingChars.length)
//                        workingChars = workingChars.dropLast(1)
//                        english += inverseMorseCodes[workingChars]
//                        break
//                    }
//                }
            }

            return english
        }
    }

    private fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
        textWatcher.onTextChanged = onTextChanged
        this.addTextChangedListener(textWatcher)
    }
}