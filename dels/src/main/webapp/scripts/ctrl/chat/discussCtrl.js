'use strict';
/**
 * @ngdoc controller
 * @name app.controller.discussCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 楼主发布帖--主题帖按照当前评论数和创建时间排序
 * @author z11595
 * @date 2017-2-8
 */
app.controller('discussCtrl', ['$scope', 'commonService', '$state', function ($scope, commonService, $state) {
    /**
     *
     * @type {{}} 定义当前vm 用于控制数据、分页
     */
    var vm = $scope.vm = {};
    /**
     *
     * @type {{size: number, index: number}} 每页显示5条数据，默认显示第1页(首页)
     */
    vm.page = {
        size: 5,
        index: 1
    };

    commonService.queryData('chat/publishedList').then(function (data) {
        vm.items = data;
    }, function (error) {
        console.log('error in responce!');
    });
}]);