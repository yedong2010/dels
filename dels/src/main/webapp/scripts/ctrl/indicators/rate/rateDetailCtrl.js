'use strict';
/**
 * @ngdoc controller
 * @name app.controller.rateDetailCtrl
 * @description 三率一数统计详情控制器(展示当前网上全流程办理事项明细)
 * @author z11595
 * @date 2016-12-8
 */
app.controller('rateDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    /**
     * @description 创建当前页面主要对象控制页面数据、筛选和分页
     * @type {{}} vm为空对象
     */
    var vm = $scope.vm = {};
    /**
     * @requires $stateParam 路由参数控制
     * @description 通过路由传参获取是否为事项总数（1：加载已进驻事项总数；0：加载网上全流程事项数）
     */
    $scope.ifsxzs = $stateParams.ifsxzs;
    /**
     * @requires $stateParam 路由参数控制
     * @description 通过路由传参获取当前省级/地市名称
     */
    $scope.name = $stateParams.name;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取类型('province':省级,'city':地市,'county':区县)
     */
    $scope.type = $stateParams.type;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取当前选择的年份
     */
    $scope.choseYear = $stateParams.choseYear;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取当前选择的月份
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
     * @description 查询当前网上全流程办理事项明细记录
     */
    $scope.queryRateDetail = function () {
        /**
         * @description 清空表格记录
         * @type {null}
         */
        vm.items = null;
        /**
         * @description 网上全流程办理事项明细记录查询
         * @param {string} type 类型('province':省级,'city':地市,'county':区县)
         * @callback 更新当前表格数据
         */
        commonService.queryDataByP('rate/wsqlcsxsxq/' + $scope.type + '/' + $scope.ifsxzs, { name: $scope.name, choseYear:$scope.choseYear, emonth:$scope.emonth}).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };
    /**
     * @description 立即执行网上全流程事项明细查询
     */
    $scope.queryRateDetail();

    /**
     * @requires $state
     * @description 返回上级三率一数页面
     * @param {string} type 类型('province':省级,'city':地市,'county':区县)
     */
    $scope.goParent = function () {
        $state.go('indicators.threeRate.index', {type: $scope.type, lasttype: $scope.type});
    };
}]);
