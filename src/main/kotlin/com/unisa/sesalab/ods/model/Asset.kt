package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.enum.AssetType
import javax.persistence.*

/**
 * The single table strategy maps all entities of the inheritance structure to the same database table.
 * This approach makes polymorphic queries very efficient and provides the best performance.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Asset(
        @Column(unique = true)
        protected var name: String,
        @Column(name = "CAN_BE_BOOKED", nullable = false)
        protected var canBeBooked: Boolean,
        @Column(name = "ASSET_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        protected var assetType: AssetType
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}