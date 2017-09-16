//
//  WishModel.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation

class WishModel {
    var id : String?
    var title : String?
    var content : String?
    var place : String?
    var date : String?
    var latitude : String?
    var longitude : String?
}

extension WishModel{
    static func transformWish(dict: [String: Any], key: String) -> WishModel{
        let wish = WishModel()
        wish.title = dict["title"] as? String
        wish.content = dict["content"] as? String
        wish.place = dict["place"] as? String
        wish.date = dict["date"] as? String
        wish.latitude = dict["latitude"] as? String
        wish.longitude = dict["longitude"] as? String
        wish.id = key
        return wish
    }
}
