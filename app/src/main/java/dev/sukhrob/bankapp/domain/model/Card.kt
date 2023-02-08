package dev.sukhrob.bankapp.domain.model

data class Card(
    var id: String? = null,
    var cardNumber: String? = null,
    var expiryDate: String? = null,
    var cardName: String? = null
)
