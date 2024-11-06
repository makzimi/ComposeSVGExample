package com.mobilefiction.svgexample

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

fun parseSvgFile(
    context: Context,
    fileName: String,
): List<StateState> {
    val stateStates = mutableListOf<StateState>()

    val inputStream = context.assets.open(fileName)
    val parser = XmlPullParserFactory.newInstance().newPullParser()
    parser.setInput(inputStream, null)

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
            val id = parser.getAttributeValue(null, "id") ?: continue
            val pathData = parser.getAttributeValue(null, "d") ?: continue

            val composePath = PathParser.createPathFromPathData(pathData).asComposePath()

            val bounds = composePath.getBounds()
            stateStates.add(
                StateState(
                    id = id,
                    path = composePath,
                    partyChoice = PartyChoice.UNKNOWN,
                    color = PartyColor.unknownGrey,
                    boundingBox = bounds
                )
            )
        }
        eventType = parser.next()
    }

    return stateStates
}