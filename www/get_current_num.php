<?php

$servername = "localhost";
$username = "root";
$password = "";

$counterID = $_GET['counterID'];

try 
{	
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	//get currentNo 
	$stmt = $conn->prepare("SELECT currentNo, counterName, locationName, lastSync FROM MST_COUNTER WHERE counterID=?");
	$stmt->bindParam(1, $counterID, PDO::PARAM_STR, 30);
	$stmt->execute();
	
	//get the currentNo from result
    $result = $stmt->fetchAll();
	
	//var_dump($result);
	
	if(isset($result[0])){
		$currentNo=$result[0][0];
		$counterName=$result[0][1];
		$locationName=$result[0][2];	
		$lastSync=$result[0][3];

		echo $currentNo;
		echo ',';
		echo $counterName;
		echo ',';
		echo $locationName;
		echo ',';
		echo $lastSync;
	} else {
		echo null; //send null response if invalid counterID is sent
	}
	
	
}
catch(Exception $e)
{
    echo "Connection failed: " . $e->getMessage();
}

//close the connection
$conn = null;

?>