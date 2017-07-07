'use strict';
/**
 * @ngdoc controller
 * @name app.controller.busDetailCtrl
 * @description 三率一数业务量统计详情控制器(展示当前业务明细)
 * @author z11595
 * @date 2017-1-8
 */
app.controller('busDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    /**
     * @description 获取数据字典
     */
    commonService.getDict().then(function (data) {
        $scope.dict = data;
    });
    /**
     * @description 创建当前页面主要对象控制页面数据、筛选和分页
     * @type {{}} vm为空对象
     */
    var vm = $scope.vm = {};
    /**
     * @requires $stateParam 路由参数控制
     * @description 通过路由传参获取当前省级/地市名称
     */
    $scope.name = $stateParams.name;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取类型('swsbx':行政审批的上网申办业务量,'bjzsx':行政审批的办结总量,'wsqlcbjx':行政审批的网上全流程办结量,'swsbs':公共服务的上网申办总量,'bjzss':公共服务的办结总量)
     */
    $scope.type = $stateParams.type;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取查询年份
     */
    $scope.year = $stateParams.year;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取开始月份
     */
    $scope.smonth = $stateParams.smonth;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取结束月份
     */
    $scope.emonth = $stateParams.emonth;
    /**
     * @description 定义当前页面每页显示10条记录，默认显示第1页
     * @define size 10
     * @define index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @description 查询当前省级部门的业务办理明细数据
     */
    $scope.queryDetail = function () {
        /**
         * @description 清空表格记录
         * @type {null}
         */
        vm.items = null;
        /**
         * @description 省级部门的业务办理明细数据记录查询
         * @param {string} type 类型('swsbx':行政审批的上网申办业务量,'bjzsx':行政审批的办结总量,'wsqlcbjx':行政审批的网上全流程办结量,'swsbs':公共服务的上网申办总量,'bjzss':公共服务的办结总量)
         * @callback 更新当前表格数据
         */
        commonService.queryDataByP('bus/detail/' + $scope.type + '/' + $scope.name, {
            year: $scope.year,
            smonth: $scope.smonth,
            emonth: $scope.emonth
        }).then(function (data) {
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };
    /**
     * @description 立即执行网上全流程事项明细查询
     */
    $scope.queryDetail();

    /**
     * @requires $state
     * @description 返回上级三率一数页面
     * @param {string} type 类型('province':省级,'city':地市,'county':区县)
     */
    $scope.goParent = function () {
        $state.go('indicators.threeRate.index', {type: 'province'});
    };
}]);
