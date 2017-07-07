'use strict';
/**
 * @ngdoc controller
 * @name app.controller.applyRankDetailCtrl
 * @description 数据指标统计-申办业务量排名-详情页
 * @author m13624
 * @date 2017-03-27
 */
app.controller('applyRankDetailCtrl', ['$scope', '$state', '$timeout', '$stateParams', 'commonService', '$templateCache', function ($scope, $state, $timeout, $stateParams, commonService, $templateCache) {
    /**
     * @define 定义当前事项类型(全部、行政审批、公共服务)
     * @type {string[]}
     */
    $scope.sxtypes = ['全部', '行政审批', '公共服务'];
    /**
     * @description 定义当前页面控制对象，控制页面数据、筛选、分页
     * @type {{}}
     */
    var vm = $scope.vm = {};
    $scope.sxlxmcvalue = $scope.sxtypes[0];
    /**
     * @requires $stateParam
     * @description 获取路由传参当前类型('province':省级,'city':地市)
     */
    $scope.type = $stateParams.type;
    /**
     * @requires $stateParam
     * @description 获取路由传参具体某个省级部门或者地市名称
     */
    $scope.name = $stateParams.name;
    /**
     * @requires $stateParam
     * @description 获取路由传参具体某个省级部门或者地市的标识
     */
    $scope.keyId = $stateParams.keyId;
    /**
     * @requires $stateParam
     * @description 查询的申办业务量排名的年份和月份
     */
    $scope.startTime = $stateParams.startTime;
    $scope.endTime = $stateParams.endTime;

    /**
     * @description 定义当前分页默认每页显示10条记录，第1页显示
     * @default size 10
     * @default index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 10,
        index: 1
    };

    $scope.queryBusDetail = function () {
        /**
         * @description
         */
        vm.items = null;
        commonService.queryDataByP('totality/sbywlphDetail/' + $scope.type , {
            id: $scope.keyId,
            startTime: $scope.startTime,
            endTime: $scope.endTime
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };
    $scope.queryBusDetail();

    $scope.goParent = function () {
        $state.go('indicators.applyRank', {type: $scope.type});
    };
}]);