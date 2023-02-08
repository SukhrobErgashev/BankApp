package dev.sukhrob.bankapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.bankapp.domain.model.Card
import dev.sukhrob.bankapp.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> get() = _cards

    private val _deleteCardResponse = MutableStateFlow(false)
    val deleteCardResponse: StateFlow<Boolean> get() = _deleteCardResponse

    private val _addCardResponse = MutableStateFlow(false)
    val addCardResponse: StateFlow<Boolean> get() = _addCardResponse

    fun getCards() = viewModelScope.launch {
        useCases.getCards().collect { response ->
           _cards.value = response
        }
    }

    fun deleteCard(cardId: String) = viewModelScope.launch {
        _deleteCardResponse.value = useCases.deleteCard(cardId)
    }

    fun addCard(cardNumber: String, expiryDate: String, cardName: String) = viewModelScope.launch {
        _addCardResponse.value = useCases.addCard(cardNumber, expiryDate, cardName)
    }

}