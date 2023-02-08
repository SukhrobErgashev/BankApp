package dev.sukhrob.bankapp.domain.use_case

import dev.sukhrob.bankapp.domain.repository.CardsRepository

class AddCard(
    private val repo: CardsRepository
) {
    suspend operator fun invoke(
        cardNumber: String,
        expiryDate: String,
        cardName: String
    ) = repo.addCardToFirestore(cardNumber, expiryDate, cardName)
}