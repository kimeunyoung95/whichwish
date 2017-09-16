//
//  WishInputViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit
import MapKit
class WishInputViewController: UIViewController {

    var info : AnnotationInfo?
    
    
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var contentTextView: UITextView!
    @IBOutlet weak var addressLabel: UILabel!
    @IBOutlet weak var saveButton: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.handleFloatingSave))
        saveButton.addGestureRecognizer(tapGesture)
        saveButton.isUserInteractionEnabled = true
        
        
        addressLabel.text = info!.address
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillShow(_:)), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillHide(_:)), name: NSNotification.Name.UIKeyboardWillHide, object: nil)

        
    }
    
    func keyboardWillShow(_ notification: NSNotification){
        print(notification)
        let keyboardFrame = (notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as AnyObject).cgRectValue
        //print(keyboardFrame)
        UIView.animate(withDuration: 0.25){
            self.view.frame.origin = CGPoint(x: 0, y: -keyboardFrame!.height/3)
        }
        
    }
    
    func keyboardWillHide(_ notification: NSNotification){
        print(notification)
        let keyboardFrame = (notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as AnyObject).cgRectValue
        //print(keyboardFrame)
        UIView.animate(withDuration: 0.25){
            
            self.view.frame.origin = CGPoint(x: 0, y: 0)
        }
    }

    func handleFloatingSave(){
        ProgressHUD.show("Waiting...")
        let latitude = info!.coordinate.latitude
        let longitude = info!.coordinate.longitude
        
        Api.Wish.uploadWish(title: titleTextField.text!, content: contentTextView.text, place: info!.place, latitude: "\(latitude)", longitude: "\(longitude)", date: CurrentTime.getCurrentTime(), completion: {
            ProgressHUD.showSuccess("Success")
            
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let wishVC = storyboard.instantiateViewController(withIdentifier: "returnVC")
            self.present(wishVC, animated: true, completion: nil)
        })
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

   
}
