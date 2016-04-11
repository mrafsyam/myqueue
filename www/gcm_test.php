<?php


// API access key from Google API's Console
define( 'API_ACCESS_KEY', 'AIzaSyAiaxxFjeim2IIBdMOOIV2wXVVXEaQJ1us' );


$registrationIds = array("fNocRigmNSw:APA91bGwsMjt3czcKmhZwslsAYBI6wnNHdKbqke-_t3AZLJQsT6yKREvOzre-CIAXWwd8kRSh7SW9rKPwRQHcZxj7iyeJlRWCLbM2lVGN0FY2DjRNfPNVZIDnJGFXswwAyYoteBBl8MI" );

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
?>