package edu.usna.mobileos.p_alvistristen
import java.io.Serializable

/**
 * Stores data for an entire workout routine/circuit
 */
@kotlinx.serialization.Serializable
data class Routine(var title: String, var description: String, var routine: ArrayList<Set> ): Serializable

/**
 * Stores data for a set
 */
@kotlinx.serialization.Serializable
data class Set(var exercise: Exercise, var repCount: Int): Serializable

/**
 * Stores data for a single exercise
 */
@kotlinx.serialization.Serializable
data class Exercise(var title: String, var description: String, var videoLink: String = ""): Serializable

