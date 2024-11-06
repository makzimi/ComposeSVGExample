# Compose SVG Example

## Overview
The goal of this project is to show how to parse SVG files in Android, convert them into a format compatible with Jetpack Compose, and render it as list of Paths with customizable colors and interactions.

<img src="https://github.com/user-attachments/assets/3ea9e09b-10c7-470a-bd46-6c6648ffe526" width="400px">

## Core Logic
SVG Parsing: The SVG file is loaded using XmlPullParser, which reads each <path> element in the SVG. Each path element represents a state in the map.

The d attribute in each <path> element defines the drawing instructions for that shape. It contains a series of commands (like M for "move to", L for "line to", C for "curve to", etc.) followed by coordinates. Together, these commands form the outline of each state.

Path Conversion: Each SVG path (d attribute) is parsed and converted into a Jetpack Compose Path format, making it compatible with Compose's drawing system.

Bounding Box Hit Detection: While the sample includes interaction with each state, determining if a click is within a state uses rectangular bounding boxes around each path. This is a simplified hit-test approach. While it’s not pixel-perfect, it serves educational purposes to demonstrate state-specific interactions and color changes.

## License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.

