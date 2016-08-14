/*global angular, alert */
function registerHomeController($http, $scope, choreGroupService, choreGroupInvitationService, $interval) {
    "use strict";
    angular.extend($scope, {
        creatingChoreGroup: false,
        sentInvitations: [],
        receivedInvitations: [],
        recipientEmail: '',
        chores: [{
            name: 'Laundry',
            completed: false
        }, {
            name: 'Dishes',
            completed: true
        }],
        choreGroups: [],
        loadMyChoreGroups: function () {
            choreGroupService.readAll().then(function (response) {
                if (response.data) {
                    $scope.choreGroups = response.data;
                }
            });
            $scope.creatingChoreGroup = false;
        },
        loadSentInvitations: function () {
            choreGroupInvitationService.getSentInvitations().then(function (response) {
                if (response.data.status === 'Error') {
                    alert('Error fetching sent invitations!');
                } else {
                    $scope.sentInvitations = response.data;
                }
            });
        },
        loadReceivedInvitations: function () {
            choreGroupInvitationService.getReceivedInvitations().then(function (response) {
                if (response.data.status === 'Error') {
                    alert('Error fetching sent invitations!');
                } else {
                    $scope.receivedInvitations = response.data;
                }
            });
        },
        createNewChoreGroup: function () {
            if (!$scope.newChoreGroupName.length) {
                return;
            }
            choreGroupService.create($scope.newChoreGroupName).then($scope.loadMyChoreGroups);
        },
        sendInvitation: function (choreGroup) {
            choreGroupInvitationService.sendInvite(choreGroup.choreGroupName, choreGroup.recipientEmail);
            $scope.loadSentInvitations();
        },
        loadDefaultInfo: function () {
            $scope.loadReceivedInvitations();
            $scope.loadSentInvitations();
        },
        acceptInvitation: function (inviteId) {
            choreGroupInvitationService.acceptInvitation(inviteId).then(function () {
                $scope.loadDefaultInfo();
                $scope.loadMyChoreGroups();
            });
        },
        rejectInvitation: function (inviteId) {
            choreGroupInvitationService.rejectInvitation(inviteId).then($scope.loadDefaultInfo);
            $scope.loadReceivedInvitations();
        }
    });

    $interval($scope.loadDefaultInfo, 30000);
    $scope.loadMyChoreGroups();
    $scope.loadDefaultInfo();
}

angular.module('myChoresApp').controller('homeController', ['$http', '$scope', 'choreGroupService', 'choreGroupInvitationService', '$interval', registerHomeController]);
