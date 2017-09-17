//
//  WishViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import CoreLocation
import UserNotifications

class WishViewController: UIViewController,CLLocationManagerDelegate {

    
    @IBOutlet weak var tableView: UITableView!
    var locationManager : CLLocationManager = CLLocationManager()
    
    var wishes : [WishModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        
        load_wishes()
        getCurrentLocation()
        
        locationManager.allowsBackgroundLocationUpdates = true 
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.navigationBar.barTintColor = UIColor.white
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
    
    func getCurrentLocation(){
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        print(locations)
        
        let curCoordinate = locations.first!
        for wish in wishes{
            let placeCoordinate = CLLocation(latitude: Double(wish.latitude!)!, longitude: Double(wish.longitude!)!)
            let distance = curCoordinate.distance(from: placeCoordinate)
            if distance < 500{ // 500미터
                let content = UNMutableNotificationContent()
                content.title = "※ Wish Alarm ※"
                content.subtitle = "제목: \(wish.title!)"
                content.body = "할일: \(wish.content!)"
                let request = UNNotificationRequest(identifier: "alarm", content: content, trigger: nil)
                UNUserNotificationCenter.current().add(request, withCompletionHandler: nil)
            }
        }
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
