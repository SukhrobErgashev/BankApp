package dev.sukhrob.bankapp.domain.use_case

import dev.sukhrob.bankapp.domain.repository.CardsRepository

class DeleteCard(
    private val repo: CardsRepository
) {
    suspend operator fun invoke(cardId: String) = repo.deleteCardFromFirestore(cardId)
}