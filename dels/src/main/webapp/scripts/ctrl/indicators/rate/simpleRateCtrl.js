'use strict';
/**
 * 数据指标统计-指标统计（三率一数简化版）控制器
 * @author m13624
 * @date 2017-03-13
 */
app.controller('simpleRateCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    /**
     *
     * @type {number} 升序|降序标识符
     */
    $scope.desc = 0;

    /**
     *
     * @type {string} 当前排序字段
     */
    $scope.col = '';

    /**
     * @description 判断当前用户的类型
     * @default province 省级部门
     */
    var rolName = commonService.getRolName();
    $scope.rolId = commonService.getroleid();
    $scope.type = commonService.getUserCityType();

    /**
     * @description 创建当前页面主要对象控制页面数据、筛选和分页
     * @type {{}} vm为空对象
     */
    var vm = $scope.vm = {};
    /**
     * @description 定义当前页面每页显示10条记录，默认显示第1页
     * @define size 5
     * @define index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 10,
        index: 1
    };
    /**
     *
     * @type {Date} 获取当前日期、年份、月份、可以选择的最大月份
     * @description 用来判断，如果是当前年份，可选的最大月份只能是当前月份
     */
    var now = new Date();
    $scope.nowmonth = now.getMonth() + 1;
    $scope.maxmonth = now.getMonth() + 1;
    /**
     * @define years 选择年份数组从2016年到当前年份
     */
    $scope.years = _.range(2017, now.getFullYear() + 1);
    /**
     *
     * @default {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
    /**
     *
     * @default {number} 默认选择月份为1月
     */
    $scope.smonth = 1;
    /**
     *
     * @default {number} 默认选择月份为当前月
     */
    $scope.emonth = now.getMonth() + 1;

    /**
     * @description 切换数据视角
     * @param {string} type（province：省级部门；city：地市；county：区县；county)
     * @constructor 清空表格数据
     */
    $scope.togglePerspective = function (type) {
        vm.items = null;
        $scope.type = type;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据
     */
    $scope.queryData = function () {
        /**
         * @define items null 清空当前页面表格数据
         * @type {null}
         */
        vm.items = null;

        /**
         * @description 查询指标统计——三率一数简化版
         * @param type 类型(province:省级,city:地市,county:区县)
         * @param choseYear 查询年份
         * @param smonth 开始月份
         * @param emonth 结束月份
         * @callback 强制遍历数据转化为number，更新页面数据
         */
        commonService.queryDataByP('rate/simpleRate/' + $scope.type, {
            choseYear: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth
        }).then(function (data) {
            //将后台给的字符串形式的数据转换成number
            for (var i = 0; i < data.length; i++) {
                data[i].bms = parseInt(data[i].bms);
                data[i].sxzs = parseInt(data[i].sxzs);
                data[i].zywl = parseInt(data[i].zywl);
                data[i].wsqlcbll = parseFloat(data[i].wsqlcbll);
                data[i].swbll = parseFloat(data[i].swbll);
                data[i].wsbjl = parseFloat(data[i].wsbjl);
                data[i].zb0 = parseFloat(data[i].zb0);
                data[i].zb1 = parseFloat(data[i].zb1);
                data[i].zb2 = parseFloat(data[i].zb2);
                data[i].zb3 = parseFloat(data[i].zb3);
                data[i].index = i + 1;  //给每条数据添加序号
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };

    /**
     * @description 立即执行当前查询数据
     */
    $scope.queryData();

    /**
     * @description 设置变更年份是当前默认起止月份
     */
    $scope.defaultMonth = function () {
        if ($scope.choseyear == now.getFullYear()) {
            $scope.smonth = 1;
            $scope.emonth = now.getMonth() + 1;
            $scope.maxmonth = $scope.nowmonth;
        } else {
            $scope.smonth = 1;
            $scope.emonth = 12;
            $scope.maxmonth = 12;
        }
    };
}]);
