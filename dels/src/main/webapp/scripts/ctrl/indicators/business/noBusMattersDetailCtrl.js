'use strict';
/**
 * @ngdoc controller
 * @name app.controller.noBusMattersDetailCtrl
 * @description 数据指标统计-无业务事项详情控制器
 * @author z11595
 * @date 2016-12-09
 */
app.controller('noBusMattersDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
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
     * @description 通过路由传参获取类型('province':省级,'city':地市)
     */
    $scope.type = $stateParams.type;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取年份
     */
    $scope.choseYear = $stateParams.choseYear;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取起始月份
     */
    $scope.smonth = $stateParams.smonth;
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取截止月份
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
     * @description 获取当前日期
     * @type {Date}
     */
    var now = new Date();
    /**
     * @define years 选择年份数组从2016年到当前年份
     */
    $scope.years = _.range(2016, now.getFullYear() + 1);


    /**
     * @description 通过路由传参获取当前省级/地市名称查询当前无业务事项分布类型详情
     * @author z11595
     */
    $scope.queryNoBusMatterDetail = function () {
        /**
         * @define vm.items为空，清空当前数据
         * @type {null}
         */
        vm.items = null;
        /**
         * @description 通过路由传参获取当前省级/地市名称查询当前无业务事项详情
         * @param {{type:string,year:number,name:string}} 类型、查询年份、省级/地市名称
         * @callback 更新当前表格数据
         */
        commonService.queryDataByP('bus/wywsxxq/' + $scope.type, {
            choseYear: $scope.choseYear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            name: $scope.name
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };

    /**
     * @description 立即执行当前无业务事项明细通过省级|地市名称
     */
    $scope.queryNoBusMatterDetail();

    /**
     * @description 无业务事项明细上级跳转
     * @property {{type:string}} 传入当前类型
     */
    $scope.goParent = function () {
        $state.go('indicators.noBusMatters.index', {type: $scope.type, lasttype: $scope.type});
    };
    /**
     * @description 设置变更年份是当前默认起止月份
     */
    $scope.defaultMonth = function () {
        if ($scope.choseyear == now.getFullYear()) {
            $scope.smonth = 1;
            $scope.emonth = now.getMonth() + 1;
        } else {
            $scope.smonth = 1;
            $scope.emonth = 12;
        }
    };
}])
;