'use strict';
/**
 * 三率一数详情-按照跑动次数统计已进驻部门事项控制器
 * @author z11595
 * @date 2016-12-27
 */
app.controller('itemDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    /**
     * @description 定义当前页面控制对象，控制页面数据、筛选、分页
     * @type {{}}
     */
    var vm = $scope.vm = {};
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
     * @description 获取路由传参当前跑动次数(0|1|2|3)
     */
    $scope.pdcs = $stateParams.pdcs;
    /**
     * @requires $stateParam
     * @description 获取路由传参当前选择的年份
     */
    $scope.choseYear = $stateParams.choseYear;
    /**
     * @requires $stateParam
     * @description 获取路由传参当前选择的结束月份
     */
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

    $scope.queryDetail = function (val) {
        vm.items = null;
        commonService.queryDataByP('rate/sxjzBypd/' + $scope.type, {
            pdcs: $scope.pdcs,
            name: $scope.name,
            choseYear: $scope.choseYear,
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
    $scope.queryDetail();

    $scope.goParent = function () {
        $state.go('indicators.threeRate.index', {type: $scope.type, lasttype: $scope.type});
    };
}]);
