package dev.sukhrob.bankapp.domain.use_case

import dev.sukhrob.bankapp.domain.repository.CardsRepository

class GetCards(
    private val repo: CardsRepository
) {
    operator fun invoke() = repo.getCardsFromFirestore()
}