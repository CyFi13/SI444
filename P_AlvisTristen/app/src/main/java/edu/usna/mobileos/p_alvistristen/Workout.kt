package edu.usna.mobileos.p_alvistristen

/**
 * Stores data for an entire workout routine/circuit
 */
data class Routine(var title: String, var description: String, var routine: ArrayList<Set> )

/**
 * Stores data for a set
 */
data class Set(var exercise: Exercise, var repCount: Int)

/**
 * Stores data for a single exercise
 */
data class Exercise(var title: String, var description: String, var videoLink: String = "")

