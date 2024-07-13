package com.example.jetpackcomposedemo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MedicationResponse(
    @Json(name = "problems")
    val problems: List<Problem>
)

@JsonClass(generateAdapter = true)
data class Problem(
    @Json(name = "Diabetes")
    val diabetes: List<Diabetes>?,
//    @Json(name = "Asthma")
//    val asthma: List<Asthma>?
)

@JsonClass(generateAdapter = true)
data class Diabetes(
    @Json(name = "medications")
    val medications: List<Medication>?,
    @Json(name = "labs")
    val labs: List<Lab>?
)

@JsonClass(generateAdapter = true)
data class Medication(
    @Json(name = "medicationsClasses")
    val medicationClasses: List<MedicationClass>?
)

@JsonClass(generateAdapter = true)
data class MedicationClass(
    @Json(name = "className")
    val className: List<AssociatedDrug>?,
    @Json(name = "className2")
    val className2: List<AssociatedDrug>?
)

@JsonClass(generateAdapter = true)
data class AssociatedDrug(
    @Json(name = "associatedDrug")
    val associatedDrug: List<Drug>?,
    @Json(name = "associatedDrug#2")
    val associatedDrug2: List<Drug>?
)

@JsonClass(generateAdapter = true)
data class Drug(
    @Json(name = "name")
    val name: String,
    @Json(name = "dose")
    val dose: String,
    @Json(name = "strength")
    val strength: String
)

@JsonClass(generateAdapter = true)
data class Lab(
    @Json(name = "missing_field")
    val missingField: String
)

/*
@JsonClass(generateAdapter = true)
data class Asthma(
//    val placeholder: String? = null
)*/
