//
//  LoginView.swift
//  iosApp
//
//  Created by Khalid Kadamani on 21/02/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var isLoggingIn = false
    @State private var errorMessage: String?

    var body: some View {
        VStack(spacing: 20) {
            Text("Login Form")
                .font(.largeTitle)
                .bold()

            TextField("Email Address", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.emailAddress)
                .autocapitalization(.none)
                .padding()

            SecureField("Password ", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()

            if let errorMessage = errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
            }

            Button(action: login) {
                if isLoggingIn {
                    ProgressView()
                } else {
                    Text("Login ... ")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
            .padding()
            .disabled(isLoggingIn)
        }
        .padding()
    }

    func login() {
        isLoggingIn = true
        errorMessage = nil
        
        // محاكاة طلب تسجيل دخول (سيتم استبداله لاحقًا بكود KMM)
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            if email == "test@example.com" && password == "123456" {
                print("Correct - Logged in")
            } else {
                errorMessage = "Email or password is incorrect"
            }
            isLoggingIn = false
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
