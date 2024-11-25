package com.example.p4g.model

data class Resistances(
    val physical: String,
    val fire: String,
    val ice: String,
    val electricity: String,
    val wind: String,
    val light: String,
    val dark: String,
    val almighty: String
) {
    // Constructor for the converter function
    constructor(list: ArrayList<String>) :
            this (list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4),
                list.get(5),
                list.get(6),
                list.get(7)
            )
}