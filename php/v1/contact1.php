<?php 

require_once '../includes/DbOperations.php';

$response = array(); 


$db = new DbOperations(); 


			$call = "call";
			$username = $_POST['username'];
            $user = $db->getUserByregister($call, $username);
			
			if($user == 0){
				$response['contact'] = "";
			}else{
				$response['error'] = false; 
			$response['id'] = $user['id'];
			$response['contact'] = $user['contact'];
				
			}
			
			
			echo json_encode($response);
			
				
                        
       
    
    


?>