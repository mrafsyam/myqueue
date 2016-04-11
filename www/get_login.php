<?php

$servername = "localhost";
$username = "root";
$password = "";

$email = $_GET['email'];

try 
{		
	//set PDO connection
    $conn = new PDO("mysql:host=$servername;dbname=test2", $username, $password);
	
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	//prepare SQL statement to execute
	$stmt = $conn->prepare("SELECT email, password, paid FROM MST_USER WHERE email=?");
	$stmt->bindParam(1, $email, PDO::PARAM_STR, 30);
	$stmt->execute();
	
	//get the currentNo from result
	$result = $stmt->fetch(PDO::FETCH_ASSOC); 
	
	if ($result > 0) {
		echo json_encode($result);
	} else {
		echo json_encode("Invalid User");
	}
	
}
catch(PDOException $e)
{
    echo "Connection failed: " . $e->getMessage();
}

//close the connection
$conn = null;

?>