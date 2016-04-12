<?php

$servername = "localhost";
$username = "root";
$password = "";


$counterID = $_GET['counterID'];
$counterName = $_GET['counterName'];
$startNo = $_GET['startNo'];
$locationName = $_GET['locationName'];

$done_flag = 0;

try 
{	
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$stmt = $conn->prepare("INSERT INTO mst_counter(counterID, counterName, locationName, currentNo) VALUES (:counterID,:counterName,:locationName,:startNo)");
	$stmt->execute(array(
					':counterID' => $counterID,
					':counterName' => $counterName,
					':locationName' => $locationName,
					':startNo' => $startNo));	
	
	$done_flag = 1;
}
catch(Exception $e)
{
    echo "Connection failed: " . $e->getMessage();
}

echo "done";

if ($done_flag){
	header("Location: http://10.194.123.233:8080/dummy_updater/counter_display.php?startNo=$startNo&counterName=$counterName&locationName=$locationName&counterID=$counterID");
}


//close the connection
$conn = null;

?>

