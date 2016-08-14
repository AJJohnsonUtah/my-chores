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

    // If email or password are invalid, error
    if(strlen($request->password) < 8) {
        api_error('Password is invalid.');
    }

    $password = $request->password;
    $email = strtolower($request->email);

    require_once '../db/connection.php';
    $db_conn = get_std_conn();

    // Hash the password with bcrypt
    $pass_hash = password_hash($password, PASSWORD_DEFAULT);

    $insert_query = "INSERT INTO `user_auth` (`email`, `password_hash`) VALUES (?, ?)";

    try {
        $statement = $db_conn->prepare ( $insert_query );
        $statement->execute ( array (
                $email,
                $pass_hash
        ) );
    } catch ( PDOException $e ) {
        api_error('An account already exists for ' . $email);
    }
    api_success();
    
?>