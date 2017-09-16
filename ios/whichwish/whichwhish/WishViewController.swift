//
//  WishViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit

class WishViewController: UIViewController {

    
    @IBOutlet weak var tableView: UITableView!
    
    var wishes : [WishModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        
        load_wishes()
    }
    
    func load_wishes(){
        Api.Wish.observeWishes(completion: {
            wishes in
            self.wishes = wishes
            self.tableView.reloadData()
        })
    }
}

extension WishViewController : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return wishes.count
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "WishTableViewCell", for: indexPath) as! WishTableViewCell
        cell.wish = wishes[indexPath.row]
        return cell
    }
}
