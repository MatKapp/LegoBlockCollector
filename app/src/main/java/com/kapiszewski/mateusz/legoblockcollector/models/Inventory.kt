package com.kapiszewski.mateusz.legoblockcollector.models

/**
 * Created by mateu on 5/26/2018.
 */
class Inventory {
    var id: Int = 0
    var name: String = ""
    var active: Boolean = false
    var lastAccessed: Int = 0

    constructor(id: Int, name: String, active: Boolean, lastAccessed: Int) {
        this.id = id
        this.name = name
        this.active = active
        this.lastAccessed = lastAccessed
    }

    constructor(name: String, active: Boolean, lastAccessed: Int) {
        this.name = name
        this.active = active
        this.lastAccessed = lastAccessed
    }
}