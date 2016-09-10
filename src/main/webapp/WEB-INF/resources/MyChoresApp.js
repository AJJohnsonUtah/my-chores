/*global angular */

angular.module("myChoresApp", ['ngRoute', 'ngCookies', 'ui.bootstrap', 'ngAnimate']).config(
    function ($routeProvider, $locationProvider) {
        'use strict';
        $routeProvider.when('/', {
            templateUrl : '/resources/views/home.html',
            controller: 'homeController',
            controllerAs: 'homeCtrl'
        }).when('/register-login', {
            templateUrl : '/resources/views/register-login.html',
            controller: 'registerLoginController',
            controllerAs: 'loginCtrl'
        }).when('/groups', {
            templateUrl : '/resources/views/groups.html',
            controller: 'groupsController',
            controllerAs: 'groupsCtrl'
        }).when('/group/:choreGroupUserId', {
            templateUrl : '/resources/views/group.html',
            controller: 'groupController',
            controllerAs: 'groupCtrl'
        }).otherwise({
            redirectTo : '/'
        });
                
        $locationProvider.html5Mode(true);
    }
);

