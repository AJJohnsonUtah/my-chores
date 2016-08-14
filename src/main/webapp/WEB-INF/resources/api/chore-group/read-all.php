<?php
    require_once '../api-functions.php';
    require_once 'actions.php';

    verify_api_security();

    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $chore_group_rows = get_chore_groups_with_user($_SESSION['user_id']);

	echo json_encode ( $chore_group_rows, JSON_NUMERIC_CHECK);
?>
