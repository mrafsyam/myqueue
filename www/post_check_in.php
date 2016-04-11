<?php

$servername = "localhost";
$username = "root";
$password = "";

$email = $_GET['email'];
$counterID = $_GET['counterID'];
$userNumber = $_GET['userNumber'];


try 
{	
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	//check for valid counterID
	$stmt = $conn->prepare("SELECT counterID from mst_counter WHERE counterID=?");
	$stmt->bindParam(1, $counterID, PDO::PARAM_STR, 30);
	$stmt->execute();
	$counterExist = $stmt->rowCount(); 
	
	if ($counterExist){
		
		//get check if the user is checking in the current queueu 
		$stmt = $conn->prepare("select count(*) from org_counter WHERE userID=? and counterID=? and userNo=?");
		$stmt->bindParam(1, $email, PDO::PARAM_STR, 30);
		$stmt->bindParam(2, $counterID, PDO::PARAM_STR, 30);
		$stmt->bindParam(3, $userNumber, PDO::PARAM_STR, 30);
		$stmt->execute(); //ignore successful or not
		$temp = $stmt->fetchAll(); 
		$queueExist = $temp[0][0];
		
		//if not the current queue, delete old queue record and add new queue
		if ($queueExist < 1){
			
			//delete old queueu record
			//echo "yes there is old queue";
			$stmt = $conn->prepare("DELETE from org_counter WHERE userID=? and counterID=?");
			$stmt->bindParam(1, $email, PDO::PARAM_STR, 30);
			$stmt->bindParam(2, $counterID, PDO::PARAM_STR, 30);
			$stmt->execute(); //ignore successful or not
			
			//add new queue
			$stmt = $conn->prepare("INSERT INTO org_counter(userID, counterID, userNo) VALUES (:email,:counterID,:userNumber)");
			$stmt->execute(array(
					':email' => $email,
					':counterID' => $counterID,
					':userNumber' => $userNumber));
		} else {
			//echo "same current queue";
		}
		
	} else {
		echo $counterExist; //return 0 if counter doesn't exist
	}
	
}
catch(Exception $e)
{
    echo "Connection failed: " . $e->getMessage();
}

//close the connection
$conn = null;

?>

