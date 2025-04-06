package edu.usna.mobileos.a3_alvistristen
/**
 * Filename: ToDo.kt
 * Author: MIDN Tristen Alvis (260102)
 * Date: 6Apr2025
 */
import java.io.Serializable

/**
 * ToDo data class that stores a title, description, and date created as strings
 */
data class ToDo(var title: String, var description: String, var dateCreated: String): Serializable
