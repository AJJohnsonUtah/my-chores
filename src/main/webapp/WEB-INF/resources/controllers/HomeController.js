angular.module('myChoresApp').controller('homeController', ['userService', '$scope', 'apiService', '$location', function (userService, $scope, api, $location) {
    'use strict';
    angular.extend($scope, {      
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
        }
    });
}]);
