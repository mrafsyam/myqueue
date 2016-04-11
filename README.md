# MyQueue - "Don't wait again"
##### Remember when you go to government hospitals or anywhere else with a really long queue? You pick your ticket and it says your number is in the next 100. You want to go somewhere do something while waiting, but you don't want to risk having your turn called while you aren't there. Here comes MyQueue. Check in your ticket into the App and get notified whenever your turn is closing. The App also shows the real time counter number, just in case you still worry.

#### Screenshot 
![alt tag](https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-41-47-957.png)
![alt tag](https://github.com/mrafsyam/myqueue/blob/master/Screenshot/Screenshot_2016-04-11-14-41-52-861.png?raw=true)


#### Current version
* Exiting user is able to log in, and check in with his counterID and number (user's number is from the ticket issued).     
* App will authenticate the counterID where upon success, App will store the user's number into database.   
* App will show the current number for the checked-in counter ID, along with the number of people in front of the user's number and the waiting time.   
* User is able to change the counterID and user number, and proceed to re-checkin.   
* App is now able to receive Push Notification initiated from server via GCM.    
 
 
#### TO DO (First working version)  
* App stores GCM Registration ID in SharedPref.   
* ChangeActivity, add function to insert Registration ID into MST_USER. Requires a new collumn.  
* Create a script to send GCM message : send_GCM.php. This script accepts Registration ID as a param.  
* Create a dummy script which acts as "Counter Updater" : Update_CurrentNo.php. This script accepts counterID, currentNo as its params. This script simply replace the CurrentNo in MST_COUNTER based on the given counterID. This script will call send_GCM.php upon successfully updating the CurrentNo.  
* Add function for User Registration (new user account)   
* Add function to get counterID by scanning QR code. Add simple tool to create QR code with given counterID.    

#### TO DO (After first working version is completed)  
* Documentation of first working version  
* Proposal Presentation for Crowd Funding & Investor Pitching  
* Implement MVC Design Pattern on Android APP  
* Implemment Framework or better codes for Server scripts  
