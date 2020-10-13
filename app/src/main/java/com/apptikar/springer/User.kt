package com.apptikar.springer

class User {

    var age: String? = null
    var image: String? = null
    var gender: String? = null


    constructor() {}
    constructor(age: String?, image: String?, gender: String?) {
        this.age = age
        this.image = image
        this.gender = gender
    }


}