//
//  MapViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class Annotation : NSObject,MKAnnotation{
    var coordinate : CLLocationCoordinate2D
    var title: String?
    
    init(coordinate : CLLocationCoordinate2D, title: String){
        self.coordinate = coordinate
        self.title = title
    }
    
    
}


class MapViewController: UIViewController , MKMapViewDelegate,CLLocationManagerDelegate{

    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var floatingGo: UIImageView!
    @IBOutlet weak var currentImageView: UIImageView!
    
    var place : MKMapItem?
    let distanceSpan : CLLocationDegrees = 1000
    
    var searchBar = UISearchBar()
    
    var localSearchRequest:MKLocalSearchRequest!
    var localSearch:MKLocalSearch!
    var localSearchResponse:MKLocalSearchResponse!
    
    var locationManager : CLLocationManager = CLLocationManager()
    
    var latestCoordinate : CLLocationCoordinate2D?{
        didSet{
            locationManager.stopUpdatingLocation()
            setPinCurrentPosition()
        }
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.delegate = self
        let latitude = place!.placemark.coordinate.latitude
        let longitude = place!.placemark.coordinate.longitude
        
        let placeLocation : CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        
        mapView.setRegion(MKCoordinateRegionMakeWithDistance(placeLocation, distanceSpan, distanceSpan), animated: true)
        let pin = Annotation(coordinate: placeLocation,title: place!.name!)
        mapView.addAnnotation(pin)
        
        let tapGesture1 = UITapGestureRecognizer(target: self, action: #selector(self.handleCurrentImageView))
        currentImageView.addGestureRecognizer(tapGesture1)
        currentImageView.isUserInteractionEnabled = true
        
        let tapGesture2 = UITapGestureRecognizer(target: self, action: #selector(self.handleFloatingGo))
        floatingGo.addGestureRecognizer(tapGesture2)
        floatingGo.isUserInteractionEnabled = true
        
        let longPressGesture = UILongPressGestureRecognizer(target: self, action: #selector(addAnnotationOnLongPress(gesture:)))
        longPressGesture.minimumPressDuration = 1.0
        self.mapView.addGestureRecognizer(longPressGesture)
    }
    
    func addAnnotationOnLongPress(gesture: UILongPressGestureRecognizer) {
        
        if gesture.state == .ended {
            let point = gesture.location(in: self.mapView)
            let coordinate = self.mapView.convert(point, toCoordinateFrom: self.mapView)
            print(coordinate)
            //Now use this coordinate to add annotation on map.
            var annotation = MKPointAnnotation()
            annotation.coordinate = coordinate
            //Set title and subtitle if you want
            annotation.title = "New"
            self.mapView.addAnnotation(annotation)
        }
    }
    
    func getCurrentLocation(){
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let latestLocation : CLLocation = locations[locations.count-1]
        latestCoordinate = latestLocation.coordinate
    }
    
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {

        let info = AnnotationInfo(coordinate: view.annotation!.coordinate, place: "\(CurrentTime.getCurrentTime_Annotation()) 기록", address: "\(view.annotation!.coordinate.latitude) - \(view.annotation!.coordinate.longitude)")
        performSegue(withIdentifier: "MapVC_WishInputVCSegue", sender: info)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "MapVC_WishInputVCSegue"{
            let VC = segue.destination as? WishInputViewController
            VC?.info = sender as? AnnotationInfo
        }
    }
    
    func handleFloatingGo(){
        let info = AnnotationInfo(coordinate: place!.placemark.coordinate, place: place!.name!, address: place!.placemark.title!)
        performSegue(withIdentifier: "MapVC_WishInputVCSegue", sender: info)
    }

    func handleCurrentImageView(){
        getCurrentLocation()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
    
    func setPinCurrentPosition(){
        
        let coordinate = latestCoordinate!
        print(coordinate)
        //Now use this coordinate to add annotation on map.
        let annotation = MKPointAnnotation()
        annotation.coordinate = coordinate
        //Set title and subtitle if you want
        annotation.title = "New"
        print("new")
        self.mapView.addAnnotation(annotation)
        
        let latitude = coordinate.latitude
        let longitude = coordinate.longitude
        
        let placeLocation : CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        
        mapView.setRegion(MKCoordinateRegionMakeWithDistance(placeLocation, distanceSpan, distanceSpan), animated: true)
        
    }
}
