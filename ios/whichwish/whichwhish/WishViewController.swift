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
    @IBAction func logOutBtn_TouchUpInside(_ sender: Any) {
        AuthService.logout(onSuccess: {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let wishVC = storyboard.instantiateViewController(withIdentifier: "SignInViewController")
            self.present(wishVC, animated: true, completion: nil)
        }, onError: {
            error in
            ProgressHUD.showError(error)
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
