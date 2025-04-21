package edu.usna.mobileos.p_alvistristen

import java.io.Serializable

/**
 * Stores data for an entire workout routine/circuit
 */
data class Routine(var title: String, var description: String, var routine: ArrayList<Set> ): Serializable

/**
 * Stores data for a set
 */
data class Set(var exercise: Exercise, var repCount: Int)

/**
 * Stores data for a single exercise
 */
data class Exercise(var title: String, var description: String, var videoLink: String = "")

