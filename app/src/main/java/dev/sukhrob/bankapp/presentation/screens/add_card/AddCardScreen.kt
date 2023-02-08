package dev.sukhrob.bankapp.presentation.screens.add_card

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.bankapp.databinding.ScreenAddCardBinding
import dev.sukhrob.bankapp.presentation.viewmodel.CardsViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCardScreen : Fragment() {

    private var _binding: ScreenAddCardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observeAddCardResponse()
        setTextWatchers()

    }

    private fun observeAddCardResponse() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.addCardResponse.collect { response ->
                if (response)
                    requireActivity().onBackPressed()
            }
        }
    }

    private fun setClickListeners() {
        binding.btnGoAhead.setOnClickListener {
            addCardToFirebase()
        }
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun addCardToFirebase() {
        if (
            !binding.etCardNumber.text.isNullOrEmpty() &&
            !binding.etCardExpiryDate.text.isNullOrEmpty() &&
            !binding.etCardName.text.isNullOrEmpty()
        ) {
            viewModel.addCard(
                binding.etCardNumber.text.toString(),
                binding.etCardExpiryDate.text.toString(),
                binding.etCardName.text.toString()
            )
        }
    }

    // Formatters for Card Number and Card Expiry Date
    private fun setTextWatchers() {
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            private val TOTAL_SYMBOLS = 19 // size of pattern 0000-0000-0000-0000
            private val TOTAL_DIGITS = 16 // max numbers of digits in pattern: 0000 x 4
            private val DIVIDER_MODULO =
                5 // means divider position is every 5th symbol beginning with 1
            private val DIVIDER_POSITION =
                DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0
            private val DIVIDER = ' '

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(
                        0,
                        s.length,
                        buildCorrectString(
                            getDigitArray(s, TOTAL_DIGITS),
                            DIVIDER_POSITION,
                            DIVIDER
                        )
                    )
                }
            }

            private fun isInputCorrect(
                s: Editable, totalSymbols: Int, dividerModulo: Int, divider: Char
            ): Boolean {
                var isCorrect = s.length <= totalSymbols // check size of entered string
                for (i in s.indices) { // check that every element is right
                    isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect and (divider == s[i])
                    } else {
                        isCorrect and Character.isDigit(s[i])
                    }
                }
                return isCorrect
            }

            private fun buildCorrectString(
                digits: CharArray, dividerPosition: Int, divider: Char
            ): String {
                val formatted = StringBuilder()
                for (i in digits.indices) {
                    if (digits[i].code != 0) {
                        formatted.append(digits[i])
                        if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                            formatted.append(divider)
                        }
                    }
                }
                return formatted.toString()
            }

            private fun getDigitArray(s: Editable, size: Int): CharArray {
                val digits = CharArray(size)
                var index = 0
                var i = 0
                while (i < s.length && index < size) {
                    val current = s[i]
                    if (Character.isDigit(current)) {
                        digits[index] = current
                        index++
                    }
                    i++
                }
                return digits
            }
        })

        binding.etCardExpiryDate.addTextChangedListener(object : TextWatcher {
            private val TOTAL_SYMBOLS = 5 // size of pattern 00/00
            private val TOTAL_DIGITS = 4 // max numbers of digits in pattern: 00 x 2
            private val DIVIDER_MODULO = 3
            private val DIVIDER_POSITION = DIVIDER_MODULO - 1
            private val DIVIDER = '/'
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(
                        0,
                        s.length,
                        buildCorrectString(
                            getDigitArray(s, TOTAL_DIGITS),
                            DIVIDER_POSITION,
                            DIVIDER
                        )
                    )
                }
            }

            private fun isInputCorrect(
                s: Editable, totalSymbols: Int, dividerModulo: Int, divider: Char
            ): Boolean {
                var isCorrect = s.length <= totalSymbols // check size of entered string
                for (i in s.indices) { // check that every element is right
                    isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect and (divider == s[i])
                    } else {
                        isCorrect and Character.isDigit(s[i])
                    }
                }
                return isCorrect
            }

            private fun buildCorrectString(
                digits: CharArray, dividerPosition: Int, divider: Char
            ): String {
                val formatted = StringBuilder()
                for (i in digits.indices) {
                    if (digits[i].code != 0) {
                        formatted.append(digits[i])
                        if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                            formatted.append(divider)
                        }
                    }
                }
                return formatted.toString()
            }

            private fun getDigitArray(s: Editable, size: Int): CharArray {
                val digits = CharArray(size)
                var index = 0
                var i = 0
                while (i < s.length && index < size) {
                    val current = s[i]
                    if (Character.isDigit(current)) {
                        digits[index] = current
                        index++
                    }
                    i++
                }
                return digits
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}