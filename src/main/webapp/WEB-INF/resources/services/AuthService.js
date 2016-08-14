/*global angular*/

angular.module('myChoresApp').factory('authService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        var authServiceData = {'isLoggedIn' : false};

        return {
            login: function (username, password) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/users/login.php';
                postData = {
                    username: username
                };

                promise = $http.post(url, postData);
                return promise;
            },
            logout: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/users/logout.php';
                promise = $http.get(url);
                authServiceData.isLoggedIn = false;
                return promise;
            },
            checkLoginStatus: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/users/logged-in.php';
                promise = $http.get(url);
                return promise;
            },
            isLoggedIn: function () {
                return authServiceData.isLoggedIn;
            },
            setIsLoggedIn: function (loggedInStatus) {
                authServiceData.isLoggedIn = loggedInStatus;
            },
            getAuthServiceData: function () {
                return authServiceData;
            }
        };
    }]);

angular.module('myChoresApp').run(['authService', '$location', function (authService, $location) {
    'use strict';
    authService.checkLoginStatus().then(function (response) {
        if (response.data.status === 'Okay') {
            authService.setIsLoggedIn(true);
        } else {
            authService.setIsLoggedIn(false);
            $location.path('/register-login');
        }
    });
}]);

angular.module('myChoresApp').controller('authController', ['authService', '$scope', '$location', function (authService, $scope, $location) {
    'use strict';
    angular.extend($scope, {
        authData: authService.getAuthServiceData(),
        logout: function () {
            authService.logout().then(function (response) {
                $location.path('/register-login');
            });
        }
    });
}]);
