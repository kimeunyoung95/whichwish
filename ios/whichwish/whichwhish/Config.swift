
//
//  Config.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation
import MapKit
struct CurrentTime{
    static func getCurrentTime() -> String{
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyyMMddHHmm"
        return formatter.string(from: date)
    }
    
    static func getCurrentTime_Annotation() -> String{
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd-HH-mm"
        return formatter.string(from: date)
    }
}

class AnnotationInfo{
    var coordinate : CLLocationCoordinate2D

    var place : String // 위치명
    var address : String // 정확한 주소 -> 위도경도 혹은 긴 주소
    
    init(coordinate: CLLocationCoordinate2D,place:String,address:String) {
        self.coordinate = coordinate
        self.place = place
        self.address = address
    }
}
