/*global angular, alert */
function registerGroupsController($scope, api, userService, $uibModal, util) {
    "use strict";
    angular.extend($scope, {
        util: util,
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
                for (var i = 0; i < $scope.currentUserChoreGroupUsers.length; i++) {
                    if ($scope.isActiveMember($scope.currentUserChoreGroupUsers[i])) {
                        $scope.selectChoreGroupUser($scope.currentUserChoreGroupUsers[i]);
                        break;
                    }
                }
            });
        },
        sendInvitation: function (choreGroup, recipientEmail) {
            api.choreGroupUserService.sendInvite(choreGroup, recipientEmail).success(function () {
                $scope.inviteErrorMessage = '';
                $scope.successfulInvite = recipientEmail;
                userService.reloadSentInvitations();
                $scope.getAllMembers(choreGroup);
            }).error(function (response) {
                $scope.errorMessages.inviteUser = response.message;
            });
        },
        selectChoreGroupUser: function (choreGroupUser) {
            $scope.selected.choreGroupUser = choreGroupUser;
            $scope.getAllMembers(choreGroupUser.choreGroup);
            $scope.getAllChoreSpecs(choreGroupUser.choreGroup);
            $scope.editingName = false;
        },
        getAllChoreSpecs: function (choreGroup) {
            api.choreGroupService.getChoreSpecs(choreGroup).success(function (choreSpecs) {
                choreGroup.choreSpecs = choreSpecs;
            });
        },
        getAllMembers: function (choreGroup) {
            var promise;
            if ($scope.selected.choreGroupUser.choreGroupUserRole === 'MEMBER') {
                promise = api.choreGroupService.getActiveMembers(choreGroup);
            } else {
                promise = api.choreGroupService.getAllMembers(choreGroup);
            }

            promise.success(function (allMembers) {
                for (var i = 0; i < $scope.currentUserChoreGroupUsers.length; i++) {
                    if ($scope.currentUserChoreGroupUsers[i].choreGroup.id === choreGroup.id) {
                        $scope.currentUserChoreGroupUsers[i].choreGroup.choreGroupUsers = allMembers;
                        return;
                    }
                }
            });
        },
        removeChoreGroupUser: function (choreGroup, choreGroupUser) {
            api.choreGroupUserService.removeChoreGroupUser(choreGroupUser).success(function () {
                api.choreGroupService.getAllMembers(choreGroup);
                choreGroupUser.status = 'REMOVED';
            });
        },
        updateChoreGroupUser: function (choreGroup, choreGroupUser) {
            api.choreGroupUserService.updateChoreGroupUser(choreGroupUser).success(function () {
                api.choreGroupService.getAllMembers(choreGroup);
            });
        },
        editChoreGroupName: function (choreGroup) {
            if ($scope.selected.updatedName === null || $scope.selected.updatedName === undefined || $scope.selected.updatedName.trim().length === 0) {
                $scope.selected.updatedName = choreGroup.choreGroupName;
                return;
            }
            var updatedName = $scope.selected.updatedName;
            choreGroup.choreGroupName = updatedName;
            api.choreGroupService.update(choreGroup).success(function (updatedChoreGroup) {
                $scope.editError = '';
                $scope.editingName = false;
            }).error(function (response) {
                $scope.editError = response.message;
            });
        },
        deleteChoreGroup: function (choreGroup) {
            api.choreGroupService.delete(choreGroup).success(function () {
                for (var i = 0; i < $scope.currentUserChoreGroupUsers.length; i++) {
                    if ($scope.currentUserChoreGroupUsers[i].choreGroup.id === choreGroup.id) {
                        $scope.currentUserChoreGroupUsers.splice(i, 1);
                        break;
                    }
                }
                if ($scope.currentUserChoreGroupUsers.length > 0) {
                    $scope.selected.choreGroupUser = $scope.currentUserChoreGroupUsers[0];
                } else {
                    $scope.selected.choreGroupUser = null;
                }
            });
        },
        createGroup: function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'createGroupModal.html',
                controller: 'choreGroupCreateModalController',
                size: 'md'
            });

            modalInstance.result.then(function (choreGroup) {
                api.choreGroupService.create(choreGroup).success(function (createdGroupUser) {
                    $scope.currentUserChoreGroupUsers.push(createdGroupUser);
                    $scope.selected.choreGroupUser = createdGroupUser;
                    api.choreGroupService.getActiveMembers(createdGroupUser.choreGroup).success(function (members) {
                        createdGroupUser.choreGroup.choreGroupUsers = members;
                    });
                });
            });
        },
        createChoreSpec: function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'createChoreSpecModal.html',
                controller: 'choreSpecCreateModalController',
                size: 'md',
                resolve: {
                    choreGroup: function () {
                        return $scope.selected.choreGroupUser.choreGroup;
                    }
                }
            });

            modalInstance.result.then(function (choreSpec) {
                api.choreSpecService.create(choreSpec).success(function (createdChoreSpec) {
                    if (!$scope.selected.choreGroupUser.choreGroup.choreSpecs) {
                        $scope.selected.choreGroupUser.choreGroup.choreSpecs = [];
                    }
                    $scope.selected.choreGroupUser.choreGroup.choreSpecs.push(createdChoreSpec);
                });
            });
        },
        editChoreSpec: function (choreSpec) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'createChoreSpecModal.html',
                controller: 'choreSpecEditModalController',
                size: 'md',
                resolve: {
                    choreGroup: function () {
                        return $scope.selected.choreGroupUser.choreGroup;
                    },
                    existingSpec: function () {
                        return choreSpec;
                    },
                    util: function () {
                        return util;
                    }
                }
            });

            modalInstance.result.then(function (choreSpec) {
                api.choreSpecService.update(choreSpec).success(function (editedChoreSpec) {
                    for (var i = 0; i < $scope.selected.choreGroupUser.choreGroup.choreSpecs.length; i++) {
                        if ($scope.selected.choreGroupUser.choreGroup.choreSpecs[i].choreSpecId === editedChoreSpec.choreSpecId) {
                            $scope.selected.choreGroupUser.choreGroup.choreSpecs[i] = editedChoreSpec;
                        }
                    }
                });
            });
        },
        toReadableFrequency: function (frequency) {
            if (frequency.timeBetweenRepeats) {
                switch (frequency.timeBetweenRepeats) {
                    case 1000 * 60 * 60 * 24:
                        return 'Daily';
                    case 1000 * 60 * 60 * 24 * 2:
                        return 'Every Other Day';
                    case 1000 * 60 * 60 * 24 * 5:
                        return 'Every Five Days';
                    case 1000 * 60 * 60 * 24 * 7:
                        return 'Weekly';
                    case 1000 * 60 * 60 * 24 * 14:
                        return 'Every Other Week';
                    case 1000 * 60 * 60 * 24 * 30:
                        return 'Monthly';
                    default:
                        return 'Every ' + frequency.timeBetweenRepeats / (1000 * 60 * 60) + ' hours.';
                }
            } else if (frequency.daysToRepeat && frequency.daysToRepeat.length > 0) {
                var readable = "";
                for (var i = 0; i < frequency.daysToRepeat.length; i++) {
                    readable += frequency.daysToRepeat[i] + (i < frequency.daysToRepeat.length - 1 ? ", " : "");
                }
                return readable;
            } else {
                return 'Once';
            }
        }
    });

    $scope.loadMyChoreGroups(null);
}

angular.module('myChoresApp').controller('groupsController', ['$scope', 'apiService', 'userService', '$uibModal', 'utilService', registerGroupsController]);

angular.module('myChoresApp').controller('choreGroupCreateModalController', function ($scope, $uibModalInstance) {
    angular.extend($scope, {
        choreGroup: {
            choreGroupName: ''
        },
        createChoreGroup: function () {
            $uibModalInstance.close($scope.choreGroup);
        }
    });
});

angular.module('myChoresApp').controller('choreSpecEditModalController', function ($scope, $uibModalInstance, choreGroup, existingSpec, util) {
    angular.extend($scope, {
        members: choreGroup.choreGroupUsers,
        repeatOption: null,
        datePickerOptions: {
            minDate: new Date()
        },
        selectedWeekdays: {},
        startDate: new Date(),
        daysOfWeek: [
            {id: 'SUNDAY', name: 'Sunday', selected: false},
            {id: 'MONDAY', name: 'Monday', selected: false},
            {id: 'TUESDAY', name: 'Tuesday', selected: false},
            {id: 'WEDNESDAY', name: 'Wednesday', selected: false},
            {id: 'THURSDAY', name: 'Thursday', selected: false},
            {id: 'FRIDAY', name: 'Friday', selected: false},
            {id: 'SATURDAY', name: 'Saturday', selected: false}
        ],
        selected: {
            frequency: null
        },
        repeatOptions: [
            {name: 'Daily', millis: 1000 * 60 * 60 * 24}, {name: 'Every Other Day', millis: 1000 * 60 * 60 * 24 * 2}, {name: 'Every Five Days', millis: 1000 * 60 * 60 * 24 * 5}, {name: 'Weekly', millis: 1000 * 60 * 60 * 24 * 7}, {name: 'Every Other Week', millis: 1000 * 60 * 60 * 24 * 14}, {name: 'Monthly', millis: 1000 * 60 * 60 * 24 * 30}
        ],
        createChoreSpec: function () {
            var choreSpec = {
                choreSpecId: existingSpec.choreSpecId,
                name: $scope.choreSpecName,
                preferredDoer: $scope.preferredDoer,
                nextInstance: $scope.startDate,
                choreGroup: {id: choreGroup.id, choreGroupName: choreGroup.choreGroupName},
                frequency: {}
            };
            if (choreSpec.nextInstance) {
                choreSpec.nextInstance = choreSpec.nextInstance.getTime();
            }
            if ($scope.repeatOption === 'fixed') {
                choreSpec.frequency.timeBetweenRepeats = $scope.selected.frequency.millis;
            } else if ($scope.repeatOption === 'weekdays') {
                var weekdays = [];
                for (var weekday in $scope.selectedWeekdays) {
                    if ($scope.selectedWeekdays.hasOwnProperty(weekday) && $scope.selectedWeekdays[weekday]) {
                        weekdays.push(weekday.toUpperCase());
                    }
                }
                choreSpec.frequency.daysToRepeat = weekdays;
            } else if ($scope.repeatOption === 'onetime') {
                choreSpec.frequency.daysToRepeat = [];
                
            }

            $uibModalInstance.close(choreSpec);
        }

    });

    if (existingSpec) {
        for(var i = 0; i < $scope.members.length; i++) {
            if(existingSpec.preferredDoer.choreSpecId === $scope.members[i].choreSpecId) {
                $scope.preferredDoer = $scope.members[i];
                break;
            }
        }
        if(existingSpec.frequency.daysToRepeat && existingSpec.frequency.daysToRepeat.length === 0 && existingSpec.frequency.timeBetweenRepeats === null) {
            $scope.repeatOption = 'onetime';
        } else if (existingSpec.frequency.timeBetweenRepeats !== null) {
            $scope.repeatOption = 'fixed';
            for(var i = 0; i < $scope.repeatOptions.length; i++) {
                if($scope.repeatOptions[i].millis === existingSpec.frequency.timeBetweenRepeats) {
                    $scope.selected.frequency = $scope.repeatOptions[i];
                    break;
                }
            }            
        } else {
            $scope.repeatOption = 'weekdays';
            $scope.selectedWeekdays = {};
            for(var i = 0; i < existingSpec.frequency.daysToRepeat.length; i++) {
                $scope.selectedWeekdays[existingSpec.frequency.daysToRepeat[i]] = true;
            }
        }
        $scope.choreSpecName = existingSpec.name;
        $scope.startDate = util.toDateObj(existingSpec.nextInstanceDate);
    }
});

angular.module('myChoresApp').controller('choreSpecCreateModalController', function ($scope, $uibModalInstance, choreGroup) {
    angular.extend($scope, {
        members: choreGroup.choreGroupUsers,
        repeatOption: null,
        datePickerOptions: {
            minDate: new Date()
        },
        selectedWeekdays: {},
        startDate: new Date(),
        daysOfWeek: [
            {id: 'SUNDAY', name: 'Sunday', selected: false},
            {id: 'MONDAY', name: 'Monday', selected: false},
            {id: 'TUESDAY', name: 'Tuesday', selected: false},
            {id: 'WEDNESDAY', name: 'Wednesday', selected: false},
            {id: 'THURSDAY', name: 'Thursday', selected: false},
            {id: 'FRIDAY', name: 'Friday', selected: false},
            {id: 'SATURDAY', name: 'Saturday', selected: false}
        ],
        selected: {
            frequency: null
        },
        repeatOptions: [
            {name: 'Daily', millis: 1000 * 60 * 60 * 24}, {name: 'Every Other Day', millis: 1000 * 60 * 60 * 24 * 2}, {name: 'Every Five Days', millis: 1000 * 60 * 60 * 24 * 5}, {name: 'Weekly', millis: 1000 * 60 * 60 * 24 * 7}, {name: 'Every Other Week', millis: 1000 * 60 * 60 * 24 * 14}, {name: 'Monthly', millis: 1000 * 60 * 60 * 24 * 30}
        ],
        createChoreSpec: function () {
            var choreSpec = {
                name: $scope.choreSpecName,
                preferredDoer: $scope.preferredDoer,
                nextInstance: $scope.startDate,
                choreGroup: {id: choreGroup.id, choreGroupName: choreGroup.choreGroupName},
                frequency: {}
            };
            if (choreSpec.nextInstance) {
                choreSpec.nextInstance = choreSpec.nextInstance.getTime();
            }
            if ($scope.repeatOption === 'fixed') {
                choreSpec.frequency.timeBetweenRepeats = $scope.selected.frequency.millis;
            } else if ($scope.repeatOption === 'weekdays') {
                var weekdays = [];
                for (var weekday in $scope.selectedWeekdays) {
                    if ($scope.selectedWeekdays.hasOwnProperty(weekday) && $scope.selectedWeekdays[weekday]) {
                        weekdays.push(weekday.toUpperCase());
                    }
                }
                choreSpec.frequency.daysToRepeat = weekdays;
            } else if ($scope.repeatOption === 'onetime') {
                choreSpec.frequency.daysToRepeat = [];
            }

            $uibModalInstance.close(choreSpec);
        }

    });
});