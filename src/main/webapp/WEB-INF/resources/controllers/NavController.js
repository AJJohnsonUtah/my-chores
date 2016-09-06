angular.module('myChoresApp').controller('navController', ['authService', 'userService', '$scope', '$location', function (authService, userService, $scope, $location) {
    'use strict';
    angular.extend($scope, {
        userServiceData: userService.getUserServiceData(),
        logout: function () {
            authService.logout().then(function (response) {
                $location.path('/register-login');
            });
        }        
    });
}]);
