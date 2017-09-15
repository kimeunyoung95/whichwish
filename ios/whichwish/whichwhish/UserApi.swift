//
//  UserApi.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation

import FirebaseDatabase
import FirebaseAuth

class UserApi{
    var REF_USERS = Database.database().reference().child("users")
    
    func observeUser(withId uid: String, completion: @escaping (UserModel) -> Void){
        REF_USERS.child(uid).observeSingleEvent(of: .value, with: {
            snapshot in
            if let dict = snapshot.value as? [String: Any]{
                let user = UserModel.transformUser(dict: dict, key: snapshot.key)
                completion(user)
            }
        })
    }
    
    func observeCurrentUser(completion: @escaping (UserModel) -> Void){
        guard let currentUser = Auth.auth().currentUser else{
            return
        }
        REF_USERS.child(currentUser.uid).observeSingleEvent(of: .value, with: {
            snapshot in
            if let dict = snapshot.value as? [String: Any]{
                let user = UserModel.transformUser(dict: dict, key: snapshot.key)
                completion(user)
            }
        })
    }
    
    var CURRENT_USER : User?{
        let currentUser = Auth.auth().currentUser
        return currentUser
    }
    var REF_CURRENT_USER : DatabaseReference? {
        guard let currentUser = Auth.auth().currentUser else{
            return nil
        }
        
        return REF_USERS.child(currentUser.uid)
    }
}
