package dev.sukhrob.bankapp.data.repository

import com.google.firebase.firestore.CollectionReference
import dev.sukhrob.bankapp.core.Constants
import dev.sukhrob.bankapp.domain.model.Card
import dev.sukhrob.bankapp.domain.repository.CardsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val ref: CollectionReference
): CardsRepository {
    override fun getCardsFromFirestore() = callbackFlow {
        val snapshotListener = ref.orderBy(Constants.CARD_NUMBER).addSnapshotListener { snapshot, _ ->
            val booksResponse = if (snapshot != null) {
                val books = snapshot.toObjects(Card::class.java)
                books
            } else {
                emptyList()
            }
            trySend(booksResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addCardToFirestore(
        cardNumber: String,
        expiryDate: String,
        cardName: String
    ): Boolean {
        return try {
            val id = ref.document().id
            val book = Card(
                id = id,
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                cardName = cardName
            )
            ref.document(id).set(book).await()
            //Response.Success(true)
            true
        } catch (e: Exception) {
            //Response.Failure(e)
            false
        }
    }

    override suspend fun deleteCardFromFirestore(cardId: String): Boolean {
        return try {
            ref.document(cardId).delete().await()
            //Response.Success(true)
            true
        } catch (e: Exception) {
            //Response.Failure(e)
            false
        }
    }

}