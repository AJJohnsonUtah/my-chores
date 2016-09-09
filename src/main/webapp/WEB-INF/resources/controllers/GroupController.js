/*global angular, alert */
function registerGroupController($scope, choreGroupService, choreGroupInvitationService, userService, $timeout) {
    "use strict";
    angular.extend($scope, {
        selected: {
            choreGroup: null,
            updatedName: null
        },
        errorMessages: {
            editName: '',
            inviteUser: '',
            removeUser: '',
            removeGroup: ''
        },
        editingName: false,
        userServiceData: userService.getUserServiceData(),
        creatingChoreGroup: false,
        choreGroups: [],
        isPendingMember: function (choreGroupUser) {
            return choreGroupUser.status === 'PENDING';
        },
        isActiveMember: function (choreGroupUser) {
            return choreGroupUser.status === 'ACCEPTED';
        },
        isCurrentUser: function (choreGroupUser) {
            return $scope.userServiceData.user.email === choreGroupUser.choreUser.email;
        },
        loadMyChoreGroups: function (updatedChoreGroup) {
            choreGroupInvitationService.findAll().success(function (currentChoreGroupUsers) {                
                $scope.currentUserChoreGroupUsers = currentChoreGroupUsers;
                if(!updatedChoreGroup) {
                    $scope.selected.choreGroupUser = ($scope.currentUserChoreGroupUsers.length > 0 ? $scope.currentUserChoreGroupUsers[0] : null);
                    updatedChoreGroup = $scope.selected.choreGroupUser.choreGroup;
                }
                $scope.getAllMembers(updatedChoreGroup);                
            });            
        },        
        createNewChoreGroup: function () {
            if (!$scope.newChoreGroupName.length) {
                return;
            }
            choreGroupService.create($scope.newChoreGroupName).success(function(choreGroup) {
                $scope.loadMyChoreGroups(choreGroup);
                $scope.creatingChoreGroup = false;
            });
        },
        sendInvitation: function (choreGroup, recipientEmail) {
            choreGroupInvitationService.sendInvite(choreGroup, recipientEmail).success(function() {
                $scope.inviteErrorMessage = '';
                $scope.successfulInvite = recipientEmail;
                userService.reloadSentInvitations();
                $scope.getAllMembers(choreGroup);
            }).error(function(response) {
                $scope.errorMessages.inviteUser = response.message;
            });
        },
        getAllMembers: function (choreGroup) {
            var promise;
            if($scope.selected.choreGroupUser.choreGroupUserRole === 'MEMBER') {
                promise = choreGroupService.getActiveMembers(choreGroup);
            } else {
                promise = choreGroupService.getAllMembers(choreGroup);
            }
            
            promise.success(function(allMembers) {
                for(var i = 0; i < $scope.currentUserChoreGroupUsers.length; i++) {
                    if($scope.currentUserChoreGroupUsers[i].choreGroup.id === choreGroup.id) {
                        $scope.currentUserChoreGroupUsers[i].choreGroup.choreGroupUsers = allMembers;
                        return;
                    }
                }
                
            });
        },
        removeChoreGroupUser: function (choreGroup, choreGroupUser) {
            choreGroupInvitationService.removeChoreGroupUser(choreGroupUser).success(function() {
                choreGroupService.getAllMembers(choreGroup);
                choreGroupUser.status = 'REMOVED';
            });
        },
        updateChoreGroupUserRole: function (choreGroup, choreGroupUser) {
            choreGroupInvitationService.updateChoreGroupUserRole(choreGroupUser).success(function() {
                choreGroupService.getAllMembers(choreGroup);
            });
        },
        beginCreatingChoreGroup: function () {
            $scope.selected.choreGroupUser = {choreGroup: {}};
            $scope.editingName=true;
            $scope.selected.updatedName = '';
            $timeout(function() {
                document.getElementById('chore-group-name-textbox').focus();
            }, 300);
        },
        beginEditingChoreGroup: function () {
            $scope.editingName=true;
            $scope.selected.updatedName =$scope.selected.choreGroup.choreGroupName;
            $timeout(function() {
                document.getElementById('chore-group-name-textbox').focus();
            }, 300);
        },
        editOrCreateChoreGroup: function (choreGroup) {
            var updatedName = $scope.selected.updatedName;
            if(updatedName === null || updatedName === undefined || updatedName.trim() === '') {
                $scope.editError = "Group name must not be empty.";
                document.getElementById('chore-group-name-textbox').focus();
                return;
            } else if((choreGroup.choreGroupName !== undefined && choreGroup.choreGroupName !== null) && choreGroup.choreGroupName.trim() === updatedName.trim()) {                
                $scope.editError = '';
                $scope.editingName = false;
                return;
            } else {
                choreGroup.choreGroupName = updatedName;
                if(choreGroup.id) {                   
                    choreGroupService.update(choreGroup).success(function (updatedChoreGroup) {
                        $scope.editError = '';
                        $scope.editingName = false;
                    }).error(function (response) {
                        $scope.editError = response.message;
                    });                    
                } else {
                    $scope.tryingToCreate = true;
                    choreGroupService.create(choreGroup).success(function (createdChoreGroup) {
                        $scope.getAllMembers(createdChoreGroup);
                        $scope.editError = '';
                        $scope.editingName = false;                        
                        $scope.choreGroups.push(createdChoreGroup);
                        $scope.selected.choreGroup = $scope.choreGroups[$scope.choreGroups.length - 1];

                    }).error(function (response) {
                        $scope.editError = response.message;
                    });     
                }
            }
        }
    });

    $scope.loadMyChoreGroups(null);
}

angular.module('myChoresApp').controller('groupController', ['$scope', 'choreGroupService', 'choreGroupInvitationService', 'userService', '$timeout', registerGroupController]);
