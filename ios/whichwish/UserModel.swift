//
//  UserModel.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation

class UserModel {
    var email: String?
    var username : String?
    var id: String?
}

extension UserModel{
    static func transformUser(dict: [String: Any], key: String) -> UserModel{
        let user = UserModel()
        user.email = dict["email"] as? String
        user.username = dict["username"] as? String
        user.id = key
        return user
    }
}
