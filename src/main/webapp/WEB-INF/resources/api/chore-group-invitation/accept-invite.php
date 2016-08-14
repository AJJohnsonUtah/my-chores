<?php
    require_once '../api-functions.php';
    require_once 'actions.php';

    verify_api_security();

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    if(!isset($request->invitation_id) || ! $request->invitation_id ) {
        api_error('Invalid invite');
    }

    accept_invitation($request->invitation_id);

    api_success();

?>
