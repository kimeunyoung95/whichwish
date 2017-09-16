
//
//  Config.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import Foundation

struct CurrentTime{
    static func getCurrentTime() -> String{
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyyMMddHHmm"
        return formatter.string(from: date)
    }
}
