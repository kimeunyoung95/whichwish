//
//  SearchTableViewCell.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import MapKit

class SearchTableViewCell: UITableViewCell {

    @IBOutlet weak var nameLabel: UILabel!
    
    @IBOutlet weak var addressLabel: UILabel!
    
    var place : MKMapItem?{
        didSet{
            nameLabel.text = place?.name
            let address = "\(place!.placemark.countryCode!) \(place!.placemark.title!)"
            addressLabel.text = address
        }
    }
}
