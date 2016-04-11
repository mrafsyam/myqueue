<?php

$servername = "localhost";
$username = "root";
$password = "";

$email = $_GET['email'];
$token = $_GET['token'];

try 
{	
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	//check for valid counterID
	$stmt = $conn->prepare("SELECT email from mst_user WHERE email=?");
	$stmt->bindParam(1, $email, PDO::PARAM_STR, 30);
	$stmt->execute();
	$userExist = $stmt->rowCount(); 
	
	
	if ($userExist){
		
		//update mst_user with latest GCM Registration token
		$stmt = $conn->prepare("UPDATE mst_user SET `token` = ? WHERE email=?");
		$stmt->execute(array($token, $email));	
		//echo $stmt->rowCount(); 		
	} else {
		echo $userExist; //return 0 if user doesn't exist
	}
	
}
catch(Exception $e)
{
    echo "Connection failed: " . $e->getMessage();
}

//close the connection
$conn = null;

?>

