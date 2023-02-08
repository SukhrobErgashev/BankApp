package dev.sukhrob.bankapp.domain.use_case

data class UseCases(
    val getCards: GetCards,
    val addCard: AddCard,
    val deleteCard: DeleteCard
)