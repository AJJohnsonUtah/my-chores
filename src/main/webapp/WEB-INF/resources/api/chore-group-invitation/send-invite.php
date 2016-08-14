<?php
    require_once '../api-functions.php';
    require_once 'actions.php';

    verify_api_security();

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    if(!isset($request->chore_group_name) || ! $request->chore_group_name || strlen($request->chore_group_name) > 35) {
        api_error('Invalid chore group name');
    }

    if(!isset($request->recipient_email) || ! $request->recipient_email || strlen($request->recipient_email) > 128 || !strcmp($request->recipient_email, $_SESSION['email'])) {
        api_error('Invalid invite recipient');
    }

    create_chore_group_invitation($request->chore_group_name, $request->recipient_email);

    api_success();

?>
