import SwiftUI
import shared


@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            LoginView()
        }
    }
    
    init(){
        let greeting = Greeting().greet()
        print(greeting)
    }
}
