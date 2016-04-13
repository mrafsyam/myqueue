# MyQueue - "Don't queue. Use MyQueue"

<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?raw=true" width="200"/>
</p>

#### Description  
Remember when you go to government hospitals or anywhere else with a really long queue? You pick your ticket and it says your number is in the next 100. You want to go somewhere do something while waiting, but you don't want to risk having your turn called while you aren't there. Here comes MyQueue. **Check in into the App** and **get notified** whenever your turn is closing in. The App also shows the real time counter number, just in case you still worry.

#### Screenshot (App)
<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-42-23-446.png?raw=true" width="250"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/change.jpg?raw=true" width="250"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/qrcodescanner.jpg?raw=true" width="250"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-41-47-957.png?raw=true" width="250"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-50-42-357.png?raw=true" width="250"/>
</p>

#### Screenshot (Dummy Counter)
This Dummy Counter, as the name suggests, is created to demonstrate how this system works seamlessly with the currently existing system because ultimately, that is what we would want to achieve - no scraping the old system is necessary.
<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/dummy_counter_main.png?raw=true" width="750"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/dummy_counter_display.png?raw=true" width="750"/>
</p>


#### Current version
* Exiting user is able to log in via email and check in with counterID (unique) and ticket number -  works well but need to cater for offline case.
* App will authenticate the counterID. Upon success, App will store the user's ticket number into database - works well.
* App shows the current number for the particular counter, along with the number of people in front and the user's waiting time - works well but need to add real time refresh/update on the currentNo if the App is in foreground.
* User is able to change the counterID and user number, and proceed to re-check in - works well.
* App now generates Google Cloud Messaging (GCM) Registration Token (device unique ID to receive GCM Push Notification) upon launch and sends it to our server - works well.
* Added script for GCM Push Notification - works well.
* Created a Dummy Counter to demonstrate the system - works well. 
* App now has "built-in" QRCode Scanner - works well but might want to limit to QRCode only?
* Added QR Code on Dummy Counter, so user can just use the App to scan the QRCode and get the counterID easily (no need for typing them into the App) - fine, but need to tweak UI. Current version looks bad.

#### TO DO (First working version)
* Add function for User Registration (new user account) by email
* Decorate UI for both App and Dummy Counter
* Implement MVC Design Pattern on Android APP
* TEST, TEST, TEST & TEST
   
#### TO DO (After first working version is completed) 
* Proposal Presentation for Crowd Funding & Investor Pitching  
* Implement Framework or better codes for Server scripts 
* Documentation of first working version
 

