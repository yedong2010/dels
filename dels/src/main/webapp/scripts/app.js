'use strict';
var global = 1;
var underscore = angular.module('underscore', []);
underscore.factory('_', function () {
    return window._;
});
var app = angular.module('dels', ['ui.router', 'ui.bootstrap', "highcharts-ng", 'toastr', 'textAngular']);

/**
 * @description 定义当前ui-router的路由跳转
 */
app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    // 默认页
    $urlRouterProvider.otherwise("/index/main");
    // 主页面
    $stateProvider.state('index', {
        url: "/index/main",
        controller: "mainCtrl",
        templateUrl: './view/index/main.html'
    })
}]);

/**
 * @description 设置当前http请求拦截
 */
app.factory('httpInterceptor', ['$q', '$injector', function ($q, $injector) {
    var httpInterceptor = {
        'responseError': function (response) {
            // 若身份验证失败，则刷新页面
            if (response.status == 401) {
                document.location.href = document.location.origin + document.location.pathname;
            }
        },
        'response': function (response) {
            return response;
        }
    };
    return httpInterceptor;
}]);

/**
 * @description 添加一个拦截器，用于处理ajax的请求认证失效
 */
app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}])
