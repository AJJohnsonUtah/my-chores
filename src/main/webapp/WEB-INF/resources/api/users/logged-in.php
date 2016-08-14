<?php 
    require_once '../api-functions.php';

    session_start();
    $response_data = array();
    // If already logged in, erorr
    if(is_logged_in()) {
        api_success();
    } else {
        api_error('Not logged in.');        
    }
?>