package io.jul.morsey


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    private lateinit var inputField: EditText
    private lateinit var outputLabel: TextView
    private lateinit var translationSwitch: SwitchCompat
    private lateinit var realTimeCheckBox: CheckBox
    private lateinit var translateButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        inputField = view.findViewById(R.id.field_input)
        outputLabel = view.findViewById(R.id.text_output)
        translationSwitch = view.findViewById(R.id.switch_translation)
        realTimeCheckBox = view.findViewById(R.id.checkbox)
        translateButton = view.findViewById(R.id.button_translate)

        val morsey = Morsey()

        realTimeCheckBox.setOnCheckedChangeListener { _, checked ->
            translateButton.isEnabled = !checked

            if (checked) {
                if (translationSwitch.isChecked) {
                    morsey.morseRealTime(inputField, outputLabel)
                } else {
                    morsey.unMorseRealTime(inputField, outputLabel)
                }
            } else {
                morsey.unregisterMorsey(inputField)
            }
        }

        translateButton.setOnClickListener {
            val translation =
                    if (translationSwitch.isChecked) {
                        Morsey.morse(inputField.text.toString())
                    } else {
                        Morsey.unMorse(inputField.text.toString())
                    }
            outputLabel.text = translation
        }

        return view
    }
}
