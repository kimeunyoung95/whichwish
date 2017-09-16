//
//  WishApi.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation
import FirebaseDatabase

class WishApi{
    var WISH_REF = Database.database().reference().child("wish")
    
    func observeWishes(completion: @escaping ([WishModel]) -> Void){
        
        var WISH_REF_CURRENT_USER = WISH_REF.child(Api.User.CURRENT_USER!.uid)
        
        WISH_REF_CURRENT_USER.observeSingleEvent(of: .value, with: {
            snapshot in
            
            var array : [WishModel] = []
            snapshot.children.forEach({
                (s) in
                let child = s as! DataSnapshot
                if let dict = child.value as? [String: Any]{
                    let wish = WishModel.transformWish(dict: dict, key: child.key)
                    array.append(wish)
                }
            })
            completion(array)
        })
    }
}

extension WishApi{
    func uploadWish(title: String,content: String,place: String,latitude: String,longitude: String,date: String, completion: @escaping ()->Void){
        
        var WISH_REF_CURRENT_USER = WISH_REF.child(Api.User.CURRENT_USER!.uid)
        let dict = ["title":title, "content":content, "place":place, "latitude": latitude, "longitude": longitude,"date":date]
        
        let childId = WISH_REF_CURRENT_USER.childByAutoId().key
        WISH_REF_CURRENT_USER.child(childId).setValue(dict, withCompletionBlock: {
            (error,ref) in
            if error != nil{
                ProgressHUD.showError(error?.localizedDescription)
                return
            }
            completion()
        })
    }
}
