<?php
    function verify_api_security() {
        session_start();
        if(!is_logged_in()) {
            api_error('Access Restricted.');
        }            
    }

    function api_error($message) {
        header('Content-Type: application/json');
        $api_response = array();
        $api_response['status'] = 'Error';
        $api_response['reason'] = $message;
        echo json_encode($api_response);
        exit();
    }

    function api_success($opt_response_data = null) {
        header('Content-Type: application/json');
        $api_response = array();
        if($opt_response_data != null) {
            $api_response = $opt_response_data;
        } else {
            $api_response['status'] = 'Okay';
        }
        echo json_encode($api_response);
        exit();
    }

    function did_request_login_page() {
        return !strcmp($_SERVER ["REQUEST_URI"], '/register-login');
    }

    function is_logged_in() {
        return isset($_SESSION['logged_in']) && $_SESSION['logged_in'] == true;
    }
?>
