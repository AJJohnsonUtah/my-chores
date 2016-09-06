angular.module('myChoresApp').controller('homeController', ['userService', '$scope', 'choreGroupInvitationService', '$location', function (userService, $scope, choreGroupInvitationService, $location) {
    'use strict';
    angular.extend($scope, {      
        userServiceData: userService.getUserServiceData(),
        hasNotifications: function() {
            return $scope.userServiceData.receivedInvitations && $scope.userServiceData.receivedInvitations.length > 0;
        },
        acceptInvitation: function (invitation) {
            choreGroupInvitationService.acceptInvitation(invitation).success(function () {
                userService.reloadReceivedInvitations();
            });
        },
        declineInvitation: function (invitation) {
            choreGroupInvitationService.declineInvitation(invitation).success(function () {
                userService.reloadReceivedInvitations();
            });
        }
    });
}]);
