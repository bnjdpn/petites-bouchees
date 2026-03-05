package com.giulia.diversification.data.model

enum class Reaction(val displayName: String, val emoji: String) {
    LIKED("Aime", "\u2764\uFE0F"),
    NEUTRAL("Neutre", "\uD83D\uDE10"),
    DISLIKED("Pas aime", "\uD83D\uDE45");
}
