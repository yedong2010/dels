'use strict';
/**
 * 事项进驻情况-已进驻部门详情控制器
 * @author m13624
 * @date 2016-12-27
 */
app.controller('enterBusDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
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
     * @description 获取路由传参省级|地市名称
     */
    $scope.name = $stateParams.name;
    /**
     * @requires $stateParam
     * @description 获取路由传参当前类型('province':省级,'city':地市)
     */
    $scope.type = $stateParams.type;
    /**
     * @requires $stateParam
     * @description 查询的已进驻事项的年份和月份
     */
    $scope.year = $stateParams.year;
    $scope.emonth = $stateParams.emonth;

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

    $scope.queryBusDetail = function (val) {
        if (typeof(val) == "undefined" || typeof(value) == null) {
            val = '';
        }
        vm.items = null;
        commonService.queryDataByP('rate/sxjzsxxq/' + $scope.type + '/', {
            name: $scope.name,
            sxlxmc: val,
            year: $scope.year,
            emonth: $scope.emonth
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
        $state.go('indicators.enterSituation.index', {type: $scope.type, lasttype: $scope.type});
    };
}]);
