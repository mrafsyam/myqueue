# MyQueue - "Don't queue. Use MyQueue"

<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?raw=true" width="200"/>
</p>

#### Description  
Remember when you go to government hospitals or anywhere else with a really long queue? You pick your ticket and it says your number is in the next 100. You want to go somewhere do something while waiting, but you don't want to risk having your turn called while you aren't there. Here comes MyQueue. **Check in into the App** and **get notified** whenever your turn is closing in. The App also shows the real time counter number, just in case you still worry.

#### Screenshot (App)
<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-42-23-446.png?raw=true" width="200"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-41-52-861.png?raw=true" width="200"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-41-47-957.png?raw=true" width="200"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-50-42-357.png?raw=true" width="200"/>
</p>

#### Screenshot (Dummy Counter)
This Dummy Counter, as the name suggests, is created to demonstrate how this system works seamlessly with the currently existing system because ultimately, that is what we would want to achieve - no scraping the old system is necessary.
<p align="center">
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/dummy_counter_main.png?raw=true" width="450"/>
  <img src="https://github.com/mrafsyam/myqueue/blob/master/Screenshot/dummy_counter_display.png?raw=true" width="450"/>
</p>


#### Current version
* Exiting user is able to log in via email and check in with counterID (unique) and ticket number
* App will authenticate the counterID. Upon success, App will store the user's ticket number into database.
* App shows the current number for the particular counter, along with the number of people in front and the user's waiting time.
* User is able to change the counterID and user number, and proceed to re-check in.
* App now generates Google Cloud Messaging (GCM) Registration Token (device unique ID to receive GCM Push Notification) upon launch and sends it to our server.
* Simple script to test GCM Push Notification is created (i.e. gcm_test.php). GCM Push Notification works well.

#### TO DO (First working version)
* Create a dummy script which acts as "Counter Updater" : Update_CurrentNo.php. This script accepts counterID, currentNo as its params. This script simply replace the CurrentNo in MST_COUNTER based on the given counterID. This script will call send_GCM.php upon successfully updating the CurrentNo.  
* Add function for User Registration (new user account)   
* Add function to get counterID by scanning QR code. Add simple tool to create QR code with given counterID.    

#### TO DO (After first working version is completed)  
* Documentation of first working version  
* Proposal Presentation for Crowd Funding & Investor Pitching  
* Implement MVC Design Pattern on Android APP  
* Implemment Framework or better codes for Server scripts  
