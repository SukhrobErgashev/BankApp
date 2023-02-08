package dev.sukhrob.bankapp.core

import dev.sukhrob.bankapp.domain.model.Card

// EXTENSION FUNCTIONS:

fun List<Card>.toUzCards(): List<Card> {
    return this.filter { it.cardNumber!!.startsWith("8600") }
}

fun List<Card>.toHumoCards(): List<Card> {
    return this.filter { it.cardNumber!!.startsWith("9860") }
}

fun List<Card>.toVisaCards(): List<Card> {
    return this.filter { it.cardNumber!!.startsWith("4") }
}