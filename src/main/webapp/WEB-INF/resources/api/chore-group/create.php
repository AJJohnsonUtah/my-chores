<?php
    require_once '../api-functions.php';
    require_once 'actions.php';

    verify_api_security();

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    if(!isset($request->chore_group_name) || ! $request->chore_group_name || strlen($request->chore_group_name) > 35) {
        api_error('Invalid chore group name');
    }

    create_chore_group($request->chore_group_name);

    api_success();

?>
