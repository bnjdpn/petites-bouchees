package com.giulia.diversification.data.model

enum class FoodCategory(val displayName: String, val emoji: String) {
    LEGUMES("Legumes", "\uD83E\uDD66"),
    FRUITS("Fruits", "\uD83C\uDF4E"),
    FECULENTS("Feculents", "\uD83C\uDF5E"),
    PROTEINES("Proteines", "\uD83C\uDF57"),
    PRODUITS_LAITIERS("Produits laitiers", "\uD83E\uDDC0"),
    MATIERES_GRASSES("Matieres grasses", "\uD83E\uDED2"),
    EPICES_AROMATES("Epices & Aromates", "\uD83C\uDF3F");
}
