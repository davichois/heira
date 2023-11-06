package com.heira.app.data.remote.dto

data class OpenRouteAPIResponse (
    val type: String,
    val features: List<Feature>,
    val bbox: List<Double>,
    val metadata: Metadata
)

data class Feature (
    val bbox: List<Double>,
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Geometry (
    val coordinates: List<List<Double>>,
    val type: String
)

data class Properties (
    val segments: List<Segment>,
    val summary: Summary,
    val wayPoints: List<Long>
)

data class Segment (
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)

data class Step (
    val distance: Double,
    val duration: Double,
    val type: Long,
    val instruction: String,
    val name: String,
    val wayPoints: List<Long>,
    val exitNumber: Long? = null
)

data class Summary (
    val distance: Double,
    val duration: Double
)

data class Metadata (
    val attribution: String,
    val service: String,
    val timestamp: Long,
    val query: Query,
    val engine: Engine
)

data class Engine (
    val version: String,
    val buildDate: String,
    val graphDate: String
)

data class Query (
    val coordinates: List<List<Double>>,
    val profile: String,
    val format: String
)
