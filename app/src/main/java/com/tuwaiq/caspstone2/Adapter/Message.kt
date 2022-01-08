package com.tuwaiq.caspstone2.Adapter

class Message {
    var message :String? = null
    var sendId : String? = null
    var imageUrl :String? =null
  //  var receiverId :String?=null
     constructor(){}

    constructor(message: String? , sendId: String?){
        this.message=message
        this.sendId= sendId
        //this.imageUrl=imageUrl


        
    }
}
