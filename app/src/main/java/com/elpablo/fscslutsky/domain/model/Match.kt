package com.elpablo.fscslutsky.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Match(
    val title: String? = null,
    val homeId: String? = null,
    val awayId: String? = null,
    val homeScore: Int? = null,
    val awayScore: Int? = null,
    val location: GeoPoint? = null,
    val locationName: String? = null,
    val date: Timestamp? = null
)