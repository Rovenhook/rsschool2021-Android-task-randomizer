package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: OnGenerateButtonPressedListener? = null
    private var min_value: TextView? = null
    private var max_value: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnGenerateButtonPressedListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        min_value = view.findViewById(R.id.min_value)
        max_value = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            if (min_value?.text?.isNotEmpty() == true && max_value?.text?.isNotEmpty() == true) {
                try {
                    val min = min_value?.text.toString().toInt() ?: 0
                    val max = max_value?.text.toString().toInt() ?: 0
                    if (min > max) {
                        Toast.makeText(context, "Minimum should be equal or less than maximum", Toast.LENGTH_SHORT).show()
                    } else if (min < 0 || max < 0) {
                        Toast.makeText(context, "Numbers have to be greater than or equal to 0", Toast.LENGTH_SHORT).show()
                    } else {
                        listener?.onGenerateButtonPressed(min, max)
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "Numbers have to be integers in range from 0 to ${Integer.MAX_VALUE}", Toast.LENGTH_SHORT).show()
                    min_value?.text = ""
                    max_value?.text = ""
                }
            } else {
                Toast.makeText(context, "Both fields have to be filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface OnGenerateButtonPressedListener {
        fun onGenerateButtonPressed(min: Int, max: Int)
    }
}