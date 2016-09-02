/*global angular, alert */
function registerHomeController($http, $scope, choreGroupService, choreGroupInvitationService, $interval) {
    "use strict";
    var startedPolling;
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
        sendInvitation: function (choreGroup, recipientEmail) {
            choreGroupInvitationService.sendInvite(choreGroup, recipientEmail);
            $scope.loadSentInvitations();
        },
        loadDefaultInfo: function () {
            $scope.loadReceivedInvitations();
            $scope.loadSentInvitations();
        },
        acceptInvitation: function (invitation) {
            choreGroupInvitationService.acceptInvitation(invitation).then(function () {
                $scope.loadDefaultInfo();
                $scope.loadMyChoreGroups();
            });
        },
        declineInvitation: function (invitation) {
            choreGroupInvitationService.declineInvitation(invitation).then($scope.loadDefaultInfo);
            $scope.loadReceivedInvitations();
        },
        startPolling: function() {
            if(angular.isDefined(startedPolling)) {
                return;
            }            
            startedPolling = $interval($scope.loadDefaultInfo, 30000);
        },
        stopPolling: function() {
            if(angular.isDefined(startedPolling)) {
                $interval.cancel(startedPolling);
                startedPolling = undefined;
            }
        },
        hasNotifications: function() {
            return $scope.receivedInvitations && $scope.receivedInvitations.length > 0;
        }
    });

    $scope.$on('$destroy', function() {
        $scope.stopPolling();
    });    

    $scope.startPolling();
    $scope.loadMyChoreGroups();
    $scope.loadDefaultInfo();
}

angular.module('myChoresApp').controller('homeController', ['$http', '$scope', 'choreGroupService', 'choreGroupInvitationService', '$interval', registerHomeController]);
