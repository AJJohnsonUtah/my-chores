<?php
    require_once '../api-functions.php';
    require_once 'actions.php';

    verify_api_security();

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $chore_group_rows = get_chore_group_invitations_with_sender($_SESSION['user_id']);

	echo json_encode ( $chore_group_rows, JSON_NUMERIC_CHECK);
?>
