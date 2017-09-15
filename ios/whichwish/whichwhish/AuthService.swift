//
//  AuthService.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

//
//  AuthService.swift
//  InstagramClone
//
//  Created by 장용수 on 2017. 7. 6..
//  Copyright © 2017년 장용수. All rights reserved.
//

import Foundation
import FirebaseAuth

import FirebaseDatabase

class AuthService {
    
    static func signIn(email: String, password: String, onSuccess: @escaping ()->Void, onError: @escaping (_ errorMessage: String?)->Void){
        
        Auth.auth().signIn(withEmail: email, password: password, completion: {(user,error) in
            if error != nil {
                onError(error!.localizedDescription)
                return
            }
            onSuccess()
        })
    }
    
    static func signUp(username: String, email: String, password: String , onSuccess: @escaping ()->Void, onError: @escaping (_ errorMessage: String?)->Void){
        
        Auth.auth().createUser(withEmail: email, password:password, completion: {
            (user: User?, error: Error?) in
            if error != nil{
                onError(error!.localizedDescription)
                return
            }
            let uid = user?.uid
            
            setUserInfomation(username: username, email: email, uid: uid!, onSuccess: {
                onSuccess()
            })
        })
    }
    
    static func setUserInfomation(username: String, email: String, uid: String, onSuccess: @escaping ()->Void){
        let ref = Database.database().reference()
        let usersReference = ref.child("users")
        let newUserReference = usersReference.child(uid)
        newUserReference.setValue(["username":username, "email":email])
        onSuccess()
    }
    
    static func logout(onSuccess: @escaping ()->Void, onError: @escaping (_ errorMessage: String?)->Void){
        do{
            try Auth.auth().signOut()
            onSuccess()
        }catch let logoutError{
            onError(logoutError.localizedDescription)
        }
    }
}
