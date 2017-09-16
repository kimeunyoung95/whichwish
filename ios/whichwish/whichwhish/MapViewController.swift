//
//  MapViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import MapKit

class Annotation : NSObject,MKAnnotation{
    var coordinate : CLLocationCoordinate2D
    var title: String?
    
    init(coordinate : CLLocationCoordinate2D, title: String){
        self.coordinate = coordinate
        self.title = title
    }
}

class MapViewController: UIViewController {

    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var floatingGo: UIImageView!
    
    var place : MKMapItem?
    let distanceSpan : CLLocationDegrees = 1000
    
    var searchBar = UISearchBar()
    
    var localSearchRequest:MKLocalSearchRequest!
    var localSearch:MKLocalSearch!
    var localSearchResponse:MKLocalSearchResponse!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let latitude = place!.placemark.coordinate.latitude
        let longitude = place!.placemark.coordinate.longitude
        
        let placeLocation : CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        
        mapView.setRegion(MKCoordinateRegionMakeWithDistance(placeLocation, distanceSpan, distanceSpan), animated: true)
        let pin = Annotation(coordinate: placeLocation,title: place!.name!)
        mapView.addAnnotation(pin)
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.handleFloatingGo))
        floatingGo.addGestureRecognizer(tapGesture)
        floatingGo.isUserInteractionEnabled = true
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "MapVC_WishInputVCSegue"{
            let VC = segue.destination as? WishInputViewController
            VC?.place = sender as? MKMapItem
        }
    }
    
    func handleFloatingGo(){
        performSegue(withIdentifier: "MapVC_WishInputVCSegue", sender: place!)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
}
