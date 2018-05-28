package com.kapiszewski.mateusz.legoblockcollector.models

import java.sql.Blob

/**
 * Created by mateu on 5/26/2018.
 */
class Code {
    var id: Int = 0
    var itemId: Int = 0
    var colorId: Int = 0
    var code: Int = 0
    var image: Blob? = null

    constructor(id: Int, itemId: Int, colorId: Int, code: Int, image: Blob?) {
        this.id = id
        this.itemId = itemId
        this.colorId = colorId
        this.code = code
        this.image = image
    }

    constructor(itemId: Int, colorId: Int, code: Int, image: Blob?) {
        this.itemId = itemId
        this.colorId = colorId
        this.code = code
        this.image = image
    }


}