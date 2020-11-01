<?php 

//require_once '../includes/DbOperations.php';
//public $con; 

	//database constants
require_once '../includes/DbConnect.php';

			$db = new DbConnect();
			$con = $db->connect();
			
		$response = array(); 
	
//if($_SERVER['REQUEST_METHOD']=='POST'){
		
		
			 
			$username = $_POST['username'];
			$stmt = $con->prepare("SELECT * FROM women where type = 'message' AND username = '$username'");
			
			//executing the query 
			$stmt->execute();
			
			//binding results to the query 
			$stmt->bind_result($id, $friendname, $contact, $type, $username);

			//traversing through all the result 
			while($stmt->fetch()){
				$temp = array();
				$temp['id'] = $id; 
				$temp['friendname'] = $friendname; 
				$temp['contact'] = $contact; 
				$temp['type'] = $type; 
			 
				array_push($response, $temp);
			}
				
					
	
	
//}else{
	//$response['error'] = true; 
//	$response['message'] = "Invalid Request";
//}
	
	//displaying the result in json format 
	echo json_encode($response);

?>