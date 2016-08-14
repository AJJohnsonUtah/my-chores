<?php 
    require_once '../api-functions.php';

    session_start();
    $response_data = array();
    // If already logged in, erorr
    if(isset($_SESSION['logged_in']) && $_SESSION['logged_in'] == true) {
        api_error('Already logged in.');
    }

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);
    
    // If email or password aren't set, error
    if(!isset($request->email) || !isset($request->password)) {
        api_error('Not enough data provided.');
    }

    $email = strtolower($request->email);

    require_once '../db/connection.php';
    $db_conn = get_std_conn();

    $select_query = "SELECT `user_id`, `email`, `password_hash` FROM `user_auth` WHERE `email`=?";

	try {        
		$statement = $db_conn->prepare ( $select_query );
		$statement->execute ( array (
				$email
		) );
	} catch ( PDOException $e ) {
        api_error($e->getMessage ());
	}

	$result = $statement->fetch ( PDO::FETCH_ASSOC );

    // If no match was found or the password verification fails, return an error
	if (! $result || ! $result ['user_id'] || ! $result ['email'] || ! $result ['password_hash']) {
        api_error('Invalid username/password.');
    } 
    
    if (! password_verify($request->password, $result['password_hash'])) {
        api_error('Invalid username/password.');
    } 

    $_SESSION['logged_in'] = true;
    $_SESSION['email'] = $result['email'];
    $_SESSION['user_id'] = $result['user_id'];
    
    api_success();
?>