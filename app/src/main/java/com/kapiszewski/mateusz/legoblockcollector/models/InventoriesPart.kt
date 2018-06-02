package com.kapiszewski.mateusz.legoblockcollector.models

/**
 * Created by mateu on 5/26/2018.
 */
class InventoriesPart {
    var id: Int = 0
    var inventoryId: Int = 0
    var typeId: Int = 0
    var itemId: Int = 0
    var quantityInSet: Int = 0
    var quantityInStore: Int = 0
    var colorId: Int? = 0
    var extra: String? = null

    constructor(id: Int, inventoryId: Int, typeId: Int, itemId: Int, quantityInSet: Int, quantityInStore: Int, colorId: Int?, extra: String?) {
        this.id = id
        this.inventoryId = inventoryId
        this.typeId = typeId
        this.itemId = itemId
        this.quantityInSet = quantityInSet
        this.quantityInStore = quantityInStore
        this.colorId = colorId
        this.extra = extra
    }

    constructor(inventoryId: Int, typeId: Int, itemId: Int, quantityInSet: Int, quantityInStore: Int, colorId: Int?, extra: String?) {
        this.inventoryId = inventoryId
        this.typeId = typeId
        this.itemId = itemId
        this.quantityInSet = quantityInSet
        this.quantityInStore = quantityInStore
        this.colorId = colorId
        this.extra = extra
    }


}