<?php 

require_once '../includes/DbOperations.php';

$response = array(); 


$db = new DbOperations(); 
			$username = $_POST['username'];
            $result = $db->deleteUser($_POST['contact'],$username);
            if ($result == 3){ 
				$response['error'] = false;
				$response['message'] = "Delete Succesfully";
			}
			if ($result == 4){
				$response['error'] = false;
				$response['message'] = "Some error occurred please try again";	
			}
				
                        
       
    
    


?>