<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['password'])){
		$db = new DbOperations(); 
		
		

		if($db->userLogin($_POST['username'], $_POST['password'])){
			$user = $db->getUserByusername($_POST['username']);
			$response['error'] = false; 
			$response['id'] = $user['id'];
			$response['username'] = $user['username'];
			$response['password'] = $user['username'];
	
		}else{
			$response['error'] = true; 
			$response['message'] = "Invalid username or password";			
		}

	}else{
		$response['error'] = true; 
	$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);

?>
