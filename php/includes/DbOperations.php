<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function insertUser($friendname, $contact, $type, $username){
				$stmt = $this->con->prepare("INSERT INTO `women` (`id`, `friendname`, `contact`, `type`, `username`) VALUES (NULL, ?, ?, ?, ?);");
				$stmt->bind_param("ssss",$friendname, $contact, $type, $username);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			
		}

		public function userLogin($username, $password){
			$stmt = $this->con->prepare("SELECT id FROM user WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		

		
		public function createUser($username, $password){
			if($this->isUserExist($username)){
				return 0; 
			}else{
				$stmt = $this->con->prepare("INSERT INTO `user` (`id`, `username`, `password`) VALUES (NULL, ?, ?);");
				$stmt->bind_param("ss",$username, $password);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}
		
		private function isUserExist($username){
			$stmt = $this->con->prepare("SELECT id FROM user WHERE username = ?");
			$stmt->bind_param("s", $username);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}	

		public function getUserByregister($type, $username){
			if($this->isContactExist($type)){
			$stmt = $this->con->prepare("SELECT * FROM women WHERE type = ? AND username = ?");
			$stmt->bind_param("ss",$type,$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
				
			}else{
				return 0; 
			}
			
			
		}
		
			public function getUserByusername($username){
			
			$stmt = $this->con->prepare("SELECT * FROM user WHERE username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		
		}
		

		private function isContactExist($type){
			$stmt = $this->con->prepare("SELECT id FROM women WHERE type = ?");
			$stmt->bind_param("s", $type);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		
		public function adminLogin($register, $date){
			$stmt = $this->con->prepare("SELECT id FROM admin WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$register,$date);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function getAdminByUsername($register){
			$stmt = $this->con->prepare("SELECT * FROM admin WHERE username = ?");
			$stmt->bind_param("s",$register);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		
		public function getStaffList(){
			$stmt = $this->con->prepare("SELECT * FROM stafflist");
			$stmt->execute();
			$stmt->bind_result("ssssss",$id,$name,$dept,$charge,$mob,$image);
			return $stmt->get_result()->fetch_assoc();
		}
		
		public function createStaff($name, $dept, $charge, $mob, $image, $branch){
			if($this->isStaffExist($mob)){
				return 0; 
			}else{
				$stmt = $this->con->prepare("INSERT INTO `stafflist` (`id`, `name`, `dept`, `charge`, `mob`, `image`, `branch`) VALUES (NULL, ?, ?, ?, ?, ?, ?);");
				$stmt->bind_param("ssssss",$name, $dept, $charge ,$mob, $image, $branch);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}
		
		
		public function deleteUser($register, $username){
				$stmt = $this->con->prepare("DELETE FROM women WHERE contact = ? AND username = ?");
				$stmt->bind_param("ss",$register, $username);
				if($stmt->execute())
					return 3; 
				else
					return 4; 		
				$stmt->store_result(); 
				return $stmt->num_rows > 0;
			
			
		
		}

		public function updateUser($register, $date, $name, $dept, $semester, $address, $gender, $branch){
			if($this->isUserExist($register)){
				$stmt = $this->con->prepare("UPDATE user SET date = ?, name = ?, dept = ?, semester = ?, address = ?, gender = ?, branch = ?  WHERE register = ?");
				$stmt->bind_param("ssssssss",$date, $name, $dept, $semester, $address, $gender, $branch, $register);
				if($stmt->execute())
					return 3; 
				else
					return 4; 	
				$stmt->store_result(); 
				return $stmt->num_rows > 0; 				 
			}else{
				return 1;
			}

			
		}

	


		
		
	private function isBookExist($bookid){
		$stmt = $this->con->prepare("SELECT id FROM library WHERE book_id = ?");
		$stmt->bind_param("s", $bookid);
		$stmt->execute(); 
		$stmt->store_result(); 
		return $stmt->num_rows > 0; 
	}

	

	

		

	}