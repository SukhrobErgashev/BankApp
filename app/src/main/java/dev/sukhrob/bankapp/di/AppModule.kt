package dev.sukhrob.bankapp.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.bankapp.core.Constants
import dev.sukhrob.bankapp.data.repository.CardRepositoryImpl
import dev.sukhrob.bankapp.domain.repository.CardsRepository
import dev.sukhrob.bankapp.domain.use_case.AddCard
import dev.sukhrob.bankapp.domain.use_case.DeleteCard
import dev.sukhrob.bankapp.domain.use_case.GetCards
import dev.sukhrob.bankapp.domain.use_case.UseCases

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRef() = Firebase.firestore.collection(Constants.CARDS)

    @Provides
    fun provideCardsRepository(
        ref: CollectionReference
    ): CardsRepository = CardRepositoryImpl(ref)

    @Provides
    fun provideUseCases(
        repo: CardsRepository
    ) = UseCases(
        getCards = GetCards(repo),
        addCard = AddCard(repo),
        deleteCard = DeleteCard(repo)
    )
}