package com.skoda.launcher.model

import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion

data class SearchData(
    var addressDistance: String,
    var coordinate: Point?,
    var raw: PlaceAutocompleteSuggestion?
)
