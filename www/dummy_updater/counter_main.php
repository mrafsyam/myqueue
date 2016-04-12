<!DOCTYPE html>
<html>

<head>
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
	<link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
	<link rel="stylesheet" href="counter_main.css">
</head>

<body>

<div class="pen-title">
  <h1>MyQueue</h1>
</div>	

<div class="container">
	<div class="card"></div>
	<div class="card">
    <h1 class="title">Register Counter</h1>
    
		<form action="register_counter.php" method="GET">
  
			<div class="input-container">
				<input type="text" name="counterName" required="required" value="Counter Outpatient A"/>
				<label for="counterName">Counter Name</label>
				<div class="bar"></div>
			</div>

			<div class="input-container">
				<input type="text" name="counterID" required="required"value="myqueue-KlinikAmpangOutpatientA" />
				<label for="counterID">Counter ID</label>
				<div class="bar"></div>
			</div>
			  
			<div class="input-container">
				<input type="text" name="locationName" required="required" value="Klinik Ampang"/>
				<label for="Location">Location</label>
				<div class="bar"></div>
			</div>
			  
			<div class="input-container">
				<input type="text" name="startNo" required="required" value="1001"/>
				<label for="startNumber">Start Number</label>
				<div class="bar"></div>
			</div>
		
			<div class="button-container" type="submit" value="Submit">
				<button><span>Go</span></button>
			</div>
		</form>
		
		
	</div> <!-- for card -->
</div>
<!-- for Container -->

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src="index.js"></script>


</body>
</html>