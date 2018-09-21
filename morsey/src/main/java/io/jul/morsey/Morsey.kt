package io.jul.morsey

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

class Morsey {

    // Defining a Character Array of the English alphabets, Numbers and Symbols
    // so that we can use them as keys for the morseCode map
    private var englishAlphabets = arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '0', ',', '.', '?', ' ')

    // Defining an Array of String to hold the Morse Code value of Every English alphabet, Number
    // and Symbol in the same order as that of the character Array
    private var morse = arrayOf(".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".---.",
            "--.-", ".-.", "...", "-", "..-", "...-", ".--",
            "-..-", "-.--", "--..", ".----", "..---", "...--",
            "....-", ".....", "-....", "--...", "---..", "----.",
            "-----", "--..--", ".-.-.-", "..--..", " ")

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

        fun unMorse(entry: String): String {
            var english = ""

            var input = entry.toLowerCase()

            while (input.isNotEmpty()) {
                var workingChars = input.substring(0, 5)
                while (workingChars.isNotEmpty()) {
                    
                }
            }

//            for (i)

            for (letter: Char in entry) {
                english += inverseMorseCodes[letter.toString()]
            }

            return english
        }
    }

    private fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
        textWatcher.onTextChanged = onTextChanged
        this.addTextChangedListener(textWatcher)
    }
}