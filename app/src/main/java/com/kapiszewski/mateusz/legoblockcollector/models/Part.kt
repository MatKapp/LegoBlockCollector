package com.kapiszewski.mateusz.legoblockcollector.models

/**
 * Created by mateu on 5/26/2018.
 */
class Part {
    var id: Int = 0
    var typeId: Int = 0
    var code: String = ""
    var name: String = ""
    var namePl: String = ""
    var categoryId: Int = 0

    constructor(id: Int, typeId: Int, code: String, name: String, namePl: String, categoryId: Int) {
        this.id = id
        this.typeId = typeId
        this.code = code
        this.name = name
        this.namePl = namePl
        this.categoryId = categoryId
    }

    constructor(typeId: Int, code: String, name: String, namePl: String, categoryId: Int) {
        this.typeId = typeId
        this.code = code
        this.name = name
        this.namePl = namePl
        this.categoryId = categoryId
    }


}