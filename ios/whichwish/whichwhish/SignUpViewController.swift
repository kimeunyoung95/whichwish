//
//  SignUpViewController.swift
//  whichwhish
//
//  Created by 장용수 on 2017. 9. 16..
//  Copyright © 2017년 Kumchurk. All rights reserved.
//

import UIKit

class SignUpViewController: UIViewController {

    @IBOutlet weak var userNameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var signUpButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        signUpButton.isEnabled = false
        handleTextField()
        
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

    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }
    
    func handleTextField(){
        userNameTextField.addTarget(self, action: #selector(self.textFieldDidChange), for: UIControlEvents.editingChanged)
        emailTextField.addTarget(self, action: #selector(self.textFieldDidChange), for: UIControlEvents.editingChanged)
        passwordTextField.addTarget(self, action: #selector(self.textFieldDidChange), for: UIControlEvents.editingChanged)
    }
    
    func textFieldDidChange(){
        guard let username = userNameTextField.text, !username.isEmpty, let email = emailTextField.text , !email.isEmpty,
            let password = passwordTextField.text, !password.isEmpty else{
                signUpButton.setTitleColor(UIColor.lightText, for: UIControlState.normal)
                signUpButton.isEnabled = false
                return
        }
        
        signUpButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        signUpButton.isEnabled = true
    }

    @IBAction func dismiss_onClick(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func signUpBtn_TouchUpInside(_ sender: Any) {
        view.endEditing(true)
        ProgressHUD.show("Waiting...", interaction: false)
        
        AuthService.signUp(username: userNameTextField.text!, email: emailTextField.text!, password: passwordTextField.text!, onSuccess: {
            ProgressHUD.showSuccess("Success")
            self.performSegue(withIdentifier: "SignUp_WishVCSegue", sender: nil)
        }, onError: {
            (errorString) in ProgressHUD.showError(errorString!)
        })
        
    }
}
