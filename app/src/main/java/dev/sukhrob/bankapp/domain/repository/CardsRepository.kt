package dev.sukhrob.bankapp.domain.repository

import dev.sukhrob.bankapp.domain.model.Card
import kotlinx.coroutines.flow.Flow

//typealias Cards = List<Card>
//typealias CardsResponse = Response<Cards>

interface CardsRepository {
    fun getCardsFromFirestore(): Flow<List<Card>>

    suspend fun addCardToFirestore(cardNumber: String, expiryDate: String, cardName: String): Boolean

    suspend fun deleteCardFromFirestore(cardId: String): Boolean
}