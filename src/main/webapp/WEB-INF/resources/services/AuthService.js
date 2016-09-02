/*global angular*/

angular.module('myChoresApp').factory('authService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        var authServiceData = {'isLoggedIn' : false, 'user': null};

        return {
            login: function (username, password) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/user/login';
                postData = {
                    username: username
                };

                promise = $http.post(url, postData);
                return promise;
            },
            logout: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/user/logout';
                promise = $http.get(url);
                authServiceData.isLoggedIn = false;
                return promise;
            },
            checkLoginStatus: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/user/current';
                promise = $http.get(url);
                return promise;
            },
            isLoggedIn: function () {
                return authServiceData.isLoggedIn;
            },
            setIsLoggedIn: function (loggedInStatus) {
                authServiceData.isLoggedIn = loggedInStatus;
            },
            getUser: function () {
                return authServiceData.user;
            },
            setUser: function (user) {
                authServiceData.user = user;
            },
            getAuthServiceData: function () {
                return authServiceData;
            }
        };
    }]);

angular.module('myChoresApp').run(['authService', '$location', function (authService, $location) {
    'use strict';
    authService.checkLoginStatus().then(function (response) {
        if(response.data) {
            authService.setIsLoggedIn(true);  
            authService.setUser(response.data);
        } else {
            authService.setIsLoggedIn(false);
            authService.setUser(null);
            $location.path('/register-login');
        }
    });
}]);
