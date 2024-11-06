package com.mobilefiction.svgexample

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

/**
 * StateState - because it is state of the state ;)
 */
@Immutable
data class StateState(
    val id: String,
    val path: Path,
    val partyChoice: PartyChoice,
    val color: Color,
    val boundingBox: Rect
)

@Immutable
enum class PartyChoice {
    REP,
    DEM,
    UNKNOWN,
}

object PartyColor {
    val repRed = Color(0xFFDD2929)
    val demBlue = Color(0xFF0387E6)
    val unknownGrey = Color(0xFFA7A7A7)
}