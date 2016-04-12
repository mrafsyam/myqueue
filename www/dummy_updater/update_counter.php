<?php

$servername = "localhost";
$username = "root";
$password = "";

$counterID = $_GET['counterID'];
$counterName = $_GET['counterName'];
$locationName = $_GET['locationName'];
$currentNo = $_GET['currentNo'];

$currentNo = $currentNo + 1;
$done_flag=0;

try 
{	
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$stmt = $conn->prepare("UPDATE mst_counter SET `currentNo` = ? WHERE counterID=?");
	$stmt->execute(array($currentNo, $counterID));	
	
	
	
	
	//check if notification really need to be send. If yes, retrieve user's GCM Registration Token

	//set PDO connection
	$conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
		
	// set the PDO error mode to exception
	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		
	 
	$stmt = $conn->prepare("select userID from org_counter WHERE userID=? and counterID=?");
	$stmt->bindParam(1, $email, PDO::PARAM_STR, 30);
	$stmt->bindParam(2, $counterID, PDO::PARAM_STR, 30);
	$success = $stmt->execute(); //check successful or not
	$userID = $stmt->fetch(PDO::FETCH_ASSOC); 	
		
	if ($success){
		$stmt = $conn->prepare("select token from mst_user WHERE email=?");
		$stmt->bindParam(1, $userID, PDO::PARAM_STR, 30);
		$stmt->execute(); //ignore successful or not
		$token = $stmt->fetch(PDO::FETCH_ASSOC);
		
		$registrationIds = $token;
	
		// API access key from Google API's Console
		define( 'API_ACCESS_KEY', 'AIzaSyAiaxxFjeim2IIBdMOOIV2wXVVXEaQJ1us' );


		// prep the bundle
		$msg = array
		(
			'message'       => 'Reminder: There are 10 people left in front of you at Counter Jamban A',
			'title'         => 'MyQueue',
			'subtitle'      => 'Do not queue. Use MyQueue.',
			'tickerText'    => 'Ticker text',
			'vibrate'   => 1,
			'sound'     => 1
		);

		$fields = array
		(
			'registration_ids'  => $registrationIds,
			'data'              => $msg
		);

		$headers = array
		(
			'Authorization: key=' . API_ACCESS_KEY,
			'Content-Type: application/json'
		);

		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );

		echo $result;	
		
		$done_flag=1;
	}
	

	
}
catch(Exception $e)
{
    echo "Connection failed: " . $e->getMessage();
}

if ($done_flag){
	header("Location: http://10.194.123.233:8080/dummy_updater/counter_display.php?startNo=$currentNo&counterName=$counterName&locationName=$locationName&counterID=$counterID");
}


//close the connection
$conn = null;

?>

