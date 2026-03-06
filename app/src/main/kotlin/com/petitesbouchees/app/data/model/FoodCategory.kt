package com.petitesbouchees.app.data.model

enum class FoodCategory(val displayName: String, val emoji: String) {
    LEGUMES("Légumes", "\uD83E\uDD66"),
    FRUITS("Fruits", "\uD83C\uDF4E"),
    FECULENTS("Féculents", "\uD83C\uDF5E"),
    PROTEINES("Protéines", "\uD83C\uDF57"),
    PRODUITS_LAITIERS("Produits laitiers", "\uD83E\uDDC0"),
    MATIERES_GRASSES("Matières grasses", "\uD83E\uDED2"),
    EPICES_AROMATES("Épices & Aromates", "\uD83C\uDF3F");
}
