package com.template.models

import com.yandex.mapkit.search.Address

data class GeoResponseModel(
    val response: Response
)

data class Response(
    val GeoObjectCollection: GeoObjectCollection
)

data class GeoObjectCollection(
    val featureMember: List<FeatureMember>,
    val metaDataProperty: MetaDataPropertyX
)

data class FeatureMember(
    val GeoObject: GeoObject
)

data class MetaDataPropertyX(
    val GeocoderResponseMetaData: GeocoderResponseMetaData
)

data class GeoObject(
    val Point: Point,
    val boundedBy: BoundedBy,
    val description: String,
    val metaDataProperty: MetaDataProperty,
    val name: String
)

data class Point(
    val pos: String
)

data class BoundedBy(
    val Envelope: Envelope
)

data class MetaDataProperty(
    val GeocoderMetaData: GeocoderMetaData
)

data class Envelope(
    val lowerCorner: String,
    val upperCorner: String
)

data class GeocoderMetaData(
    val Address: Address,
    val AddressDetails: AddressDetails,
    val kind: String,
    val precision: String,
    val text: String
)

data class Address(
    val Components: List<Component>,
    val country_code: String,
    val formatted: String,
    val postal_code: String
)

data class Component(
    val kind: String,
    val name: String
)

data class AddressDetails(
    val Country: Country
)


data class Country(
    val AddressLine: String,
    val AdministrativeArea: AdministrativeArea,
    val CountryName: String,
    val CountryNameCode: String
)

data class AdministrativeArea(
    val AdministrativeAreaName: String,
    val Locality: Locality
)

data class Locality(
    val DependentLocality: DependentLocality,
    val LocalityName: String,
    val Thoroughfare: Thoroughfare
)

data class DependentLocality(
    val DependentLocality: DependentLocalityX,
    val DependentLocalityName: String
)

data class Thoroughfare(
    val Premise: Premise,
    val ThoroughfareName: String
)

data class DependentLocalityX(
    val DependentLocalityName: String
)

data class Premise(
    val PostalCode: PostalCode,
    val PremiseNumber: String
)

data class PostalCode(
    val PostalCodeNumber: String
)

data class GeocoderResponseMetaData(
    val Point: PointX,
    val found: String,
    val request: String,
    val results: String
)

data class PointX(
    val pos: String
)