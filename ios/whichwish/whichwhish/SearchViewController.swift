//
//  SearchViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import MapKit
class SearchViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    var searchBar = UISearchBar()
    
    var searchResult : [MKMapItem] = []
    
    var localSearchRequest:MKLocalSearchRequest!
    var localSearch:MKLocalSearch!
    var localSearchResponse:MKLocalSearchResponse!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        settingSearchBar()


        
        
        
        tableView.delegate = self
        tableView.dataSource = self
    }

    func settingSearchBar(){
        searchBar.searchBarStyle = .prominent
        searchBar.placeholder = "장소 검색"
        searchBar.frame.size.width = view.frame.size.width - 100
        let searchItem = UIBarButtonItem(customView: searchBar)
        self.navigationItem.rightBarButtonItem = searchItem
        navigationController?.navigationBar.barTintColor = UIColor(red: 248/255, green: 214/255, blue: 22/255, alpha: 1)
        
        searchBar.delegate = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SearchVC_MapVC"{
            let VC = segue.destination as? MapViewController
            VC?.place = sender as! MKMapItem
        }
    }
    
    
}


extension SearchViewController : UISearchBarDelegate{
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        
        searchResult.removeAll()
        
        localSearchRequest = MKLocalSearchRequest()
        localSearchRequest.naturalLanguageQuery = searchBar.text
        localSearch = MKLocalSearch(request: localSearchRequest)
        localSearch.start { (localSearchResponse, error) -> Void in
            
            if localSearchResponse == nil{
                let alertController = UIAlertController(title: nil, message: "Place Not Found", preferredStyle: UIAlertControllerStyle.alert)
                alertController.addAction(UIAlertAction(title: "Dismiss", style: UIAlertActionStyle.default, handler: nil))
                self.present(alertController, animated: true, completion: nil)
                return
            }
            
            for place in localSearchResponse!.mapItems{
                self.searchResult.append(place)
            }
            
            self.tableView.reloadData()
        }
        
    }

}

extension SearchViewController : UITableViewDelegate, UITableViewDataSource{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "SearchVC_MapVC", sender: searchResult[indexPath.row])
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return searchResult.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SearchTableViewCell", for: indexPath) as! SearchTableViewCell
        cell.place = searchResult[indexPath.row]
        return cell
    }
}
