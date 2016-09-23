angular.module('myChoresApp').controller('homeController', ['userService', '$scope', 'apiService', '$location', function (userService, $scope, api, $location) {
    'use strict';
    angular.extend($scope, {      
        taskList: [],
        userServiceData: userService.getUserServiceData(),
        hasNotifications: function() {
            return $scope.userServiceData.receivedInvitations && $scope.userServiceData.receivedInvitations.length > 0;
        },
        acceptInvitation: function (invitation) {
            api.choreGroupUserService.acceptInvitation(invitation).success(function () {
                userService.reloadReceivedInvitations();
            });
        },
        declineInvitation: function (invitation) {
            api.choreGroupUserService.declineInvitation(invitation).success(function () {
                userService.reloadReceivedInvitations();
            });
        },
        getMyChores: function () {
            api.choreService.getActiveChoresForCurrentUser().success(function (myChores) {
                $scope.taskList = myChores;
            });
        },
        updateChore: function (chore) {
            api.choreService.update(chore).success(function (updatedChore) {
                chore = updatedChore;
            });
        }
    });
    
    $scope.getMyChores();
}]);
