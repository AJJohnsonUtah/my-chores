/*global angular, alert */
function registerLoginController($http, $scope, $location, authService, userService) {
    "use strict";
    angular.extend($scope, {
        newEmailText: '',
        newPasswordText: '',
        newPasswordVerifyText: '',
        emailText: '',
        passwordText: '',
        errorMessage: '',
        newErrorMessage: '',
        newUserDataIsValid: function () {
            if ($scope.newPasswordText.length < 8) {
                $scope.newErrorMessage = 'Passwords must be at least 8 characters long.';
                return false;
            } else if ($scope.newPasswordText !== $scope.newPasswordVerifyText) {
                $scope.newErrorMessage = 'Passwords do not match.';
                return false;
            }
            return true;
        },
        handleSuccessfulCreation: function (response) {
            $scope.submitLogin($scope.newEmailText, $scope.newPasswordText);            
        },
        handleSuccessfulLogin: function (response) {            
            $location.path('/');
            userService.setUser(response);            
        },
        handleUnsuccessfulLogin: function (response) {
            $scope.errorMessage = response.message;
        },
        handleUnsuccessfulCreation: function (response) {
            $scope.newErrorMessage = response.message;
        },
        submitNewUser: function () {
            var url, postData;
            url = '/api/user/create';
            postData = {
                "email": $scope.newEmailText,
                "password": $scope.newPasswordText
            };
            if (!$scope.newUserDataIsValid()) {
                return;
            }
            
            $http.post(url, postData)
                    .success($scope.handleSuccessfulCreation)
                    .error($scope.handleUnsuccessfulCreation);
        },
        submitLogin: function (em, pass) {
            var url, postData;
            url = '/api/user/login';
            if (em && pass) {
                postData = {
                    "email": em,
                    "password": pass
                };
            } else {
                postData = {
                    "email": $scope.emailText,
                    "password": $scope.passwordText
                };
            }
            
            $http.post(url, postData)
                    .success($scope.handleSuccessfulLogin)
                    .error($scope.handleUnsuccessfulLogin);
        }
    });

    authService.getCurrentSessionUser().then(function (response) {
        if (response.data.status === 'Okay') {
            $location.path('/');
        }
    });
}

angular.module('myChoresApp').controller('registerLoginController', ['$http', '$scope', '$location', 'authService', 'userService', registerLoginController]);
