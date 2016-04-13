<!DOCTYPE html>
<html>

<head>
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
	<link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
	<link rel="stylesheet" href="counter_display.css">
</head>

<?php 

    include('phpqrcode/qrlib.php'); 
     
    // outputs image directly into browser, as PNG stream 
    QRcode::png($_GET['counterID'], 'qrcode.png'); // creates file 

?>

<body>

<div class="pen-title">
  <h1>MyQueue</h1> <h1 class="title"><img src="qrcode.png" alt="qrcode" style="width:150px;height:150px;"></h1>
</div>	


<div class="container">
	<div class="card"></div>
	<div class="card">
    <h1 class="title" id="counterName"><?php echo $_GET['counterName']; echo " - "; echo $_GET['locationName']; ?></h1>
	
	
	
	
    
	<h1 class="number" id="currentNum"><?php echo $_GET['startNo']; ?></h1>
	
		<form action="update_counter.php" method="get">		
			<div class="button-container" typse="submit" value="Submit">
				<button><span>Call Next</span></button>
			</div>
			
			<input type="hidden" name="counterName" value="<?php echo $_GET['counterName']; ?>" />
			<input type="hidden" name="counterID" value="<?php echo $_GET['counterID']; ?>" />
			<input type="hidden" name="locationName" value="<?php echo $_GET['locationName']; ?>" />
			<input type="hidden" name="currentNo" value="<?php echo $_GET['startNo']; ?>" />
			
		</form>
		
		<form action="counter_main.php" method="get">		
			<div class="button-container" typse="submit" value="Submit">
				<button><span>RESET</span></button>
			</div>	
		</form>
		
	
		
	</div> <!-- for card -->
</div>
<!-- for Container -->







<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>


</body>
</html>