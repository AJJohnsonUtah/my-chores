/*global angular, alert */
function registerGroupsController($scope, api, userService, $timeout, $uibModal) {
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
        loadMyChoreGroups: function () {
            api.choreGroupUserService.findAll().success(function (currentChoreGroupUsers) {
                $scope.currentUserChoreGroupUsers = currentChoreGroupUsers;
                if($scope.currentUserChoreGroupUsers.length > 0) {
                    $scope.selected.choreGroupUser = ($scope.currentUserChoreGroupUsers.length > 0 ? $scope.currentUserChoreGroupUsers[0] : null);
                    $scope.getAllMembers($scope.selected.choreGroupUser.choreGroup);
                }
            });
        },
        sendInvitation: function (choreGroup, recipientEmail) {
            api.choreGroupUserService.sendInvite(choreGroup, recipientEmail).success(function() {
                $scope.inviteErrorMessage = '';
                $scope.successfulInvite = recipientEmail;
                userService.reloadSentInvitations();
                $scope.getAllMembers(choreGroup);
            }).error(function(response) {
                $scope.errorMessages.inviteUser = response.message;
            });
        },
        selectChoreGroupUser: function (choreGroupUser) {
            $scope.selected.choreGroupUser = choreGroupUser;
            $scope.getAllMembers(choreGroupUser.choreGroup);
            $scope.editingName = false;
        },
        getAllMembers: function (choreGroup) {
            var promise;
            if($scope.selected.choreGroupUser.choreGroupUserRole === 'MEMBER') {
                promise = api.choreGroupService.getActiveMembers(choreGroup);
            } else {
                promise = api.choreGroupService.getAllMembers(choreGroup);
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
            api.choreGroupUserService.removeChoreGroupUser(choreGroupUser).success(function() {
                api.choreGroupService.getAllMembers(choreGroup);
                choreGroupUser.status = 'REMOVED';
            });
        },
        updateChoreGroupUserRole: function (choreGroup, choreGroupUser) {
            api.choreGroupUserService.updateChoreGroupUserRole(choreGroupUser).success(function() {
                api.choreGroupService.getAllMembers(choreGroup);
            });
        },
        beginEditingChoreGroup: function () {
            $scope.editingName = true;
            $scope.selected.updatedName = $scope.selected.choreGroupUser.choreGroup.choreGroupName;
            $timeout(function() {
                document.getElementById('chore-group-name-textbox').focus();
            }, 300);
        },
        editChoreGroupName: function (choreGroup) {
            var updatedName = $scope.selected.updatedName || choreGroup.choreGroupName;
            choreGroup.choreGroupName = updatedName;
            api.choreGroupService.update(choreGroup).success(function (updatedChoreGroup) {
                $scope.editError = '';
                $scope.editingName = false;
            }).error(function (response) {
                $scope.editError = response.message;
            });                                                
        },
        deleteChoreGroup: function(choreGroup) {
            api.choreGroupService.delete(choreGroup).success(function() {
                for(var i = 0; i < $scope.currentUserChoreGroupUsers.length; i++) {
                    if($scope.currentUserChoreGroupUsers[i].choreGroup.id === choreGroup.id) {
                        $scope.currentUserChoreGroupUsers.splice(i, 1);
                        break;
                    }
                }
                if($scope.currentUserChoreGroupUsers.length > 0) {
                    $scope.selected.choreGroupUser = $scope.currentUserChoreGroupUsers[0];
                } else {
                    $scope.selected.choreGroupUser = null;
                }
            });
        },        
        createGroup: function() {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'createGroupModal.html',
                controller: 'choreGroupCreateModalController',
                size: 'md'	    	
            });

            modalInstance.result.then(function (choreGroup) {	
                api.choreGroupService.create(choreGroup).success(function(createdGroupUser) {
                    $scope.currentUserChoreGroupUsers.push(createdGroupUser);
                    $scope.selected.choreGroupUser = createdGroupUser;
                    api.choreGroupService.getActiveMembers(createdGroupUser.choreGroup).success(function(members) {
                        createdGroupUser.choreGroup.choreGroupUsers = members;
                    });
                });
            });			            		
        }
    });

    $scope.loadMyChoreGroups(null);
}

angular.module('myChoresApp').controller('groupsController', ['$scope', 'apiService', 'userService', '$timeout', '$uibModal', registerGroupsController]);

angular.module('myChoresApp').controller('choreGroupCreateModalController', function ($scope, $uibModalInstance) {
   angular.extend($scope, {
      choreGroup: {
          choreGroupName: ''
      },
      createChoreGroup: function() {
          $uibModalInstance.close($scope.choreGroup);
      }
   });
});