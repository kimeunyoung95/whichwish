//
//  WishTableViewCell.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit

class WishTableViewCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var placeLabel: UILabel!
    
    var wish : WishModel?{
        didSet{
            titleLabel.text = wish!.title
            placeLabel.text = wish!.place
        }
    }
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        titleLabel.text = ""
        placeLabel.text = ""
    }


}
