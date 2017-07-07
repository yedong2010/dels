'use strict';
/**
 * @description 数据指标统计-事项进驻情况
 * @author m13624
 * @date 2016-12-27
 */
app.controller('enterSituationCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     */
    $scope.desc = 0;
    $scope.col = '';

    /**
     * @description 1：2016年静态数据，不能点击数字进入详情列表页面；0：非2016年静态数据，可以点击数字进入详情列表页面;
     */
    $scope.static_2016 = 0;

    /**
     * @description 判断当前用户的类型
     */
    $scope.rolId = commonService.getroleid();
    if($stateParams.lasttype == null || $stateParams.lasttype == '' || $stateParams.lasttype == undefined){
        $scope.type = commonService.getUserCityType();
    }else{
        $scope.type = $stateParams.lasttype;
    }

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    var now = new Date();
    $scope.years = _.range(2016, now.getFullYear() + 1);
    $scope.choseyear = now.getFullYear();
    $scope.lastyear = now.getFullYear() - 1;
    /**
     *
     * @default {number} 默认选择月份为1月
     */
    $scope.smonth = 1;
    /**
     *
     * @type {Date} 获取当前日期、年份、月份、可以选择的最大月份
     * @description 用来判断，如果是当前年份，可选的最大月份只能是当前月份
     */
    $scope.emonth = now.getMonth() + 1;
    $scope.nowmonth = now.getMonth() + 1;
    $scope.maxmonth = now.getMonth() + 1;

    /**
     * @description 切换数据视角
     * @param type（city：地市；county：区县；）
     */
    $scope.togglePerspective = function (type) {
        vm.items = null;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';
        $scope.type = type;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据,点击查询之后要
     * @param type（city：地市；county：区县；）
     */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('rate/sxjz/' + $scope.type, {
            choseYear: $scope.choseyear,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            if($scope.choseyear == 2016){
                $scope.static_2016 = 1;
            }else{
                $scope.static_2016 = 0;
            }
            if($scope.type == 'province'){
                for(var i = 0; i<data.length; i++){
                    data[i].index = i + 1;
                    data[i].bmzs = 1;
                }
                data[data.length - 1].bmzs = data.length - 1;
            }else{
                for(var i = 0; i<data.length; i++){
                    data[i].index = i + 1;
                }
            }
            vm.items = data;
        }, function () {
            console.info('responce error,please call the administrator');
        });
    };

    $scope.queryData();

    /**
     * @description 跳转已进驻部门详情
     * @param type（city：地市；county：区县；）
     */
    $scope.goDepartmentDetail = function (item) {
        if(item.sjmc){
            $state.go('indicators.enterSituation.departmentDetail', {name: item.sjmc, type: $scope.type, year: $scope.choseyear, emonth: $scope.emonth});
        }else if(item.xzqhqc){
            $state.go('indicators.enterSituation.departmentDetail', {name: item.xzqhqc, type: $scope.type, year: $scope.choseyear, emonth: $scope.emonth});
        }
    };

    /**
     * @description 跳转至已进驻事项详情
     * @param type（city：地市；county：区县；）
     */
    $scope.goBusDetail = function (item) {
        if(item.zzjgmc){
            $state.go('indicators.enterSituation.BusDetail', {name: item.zzjgmc, type: $scope.type, year: $scope.choseyear, emonth: $scope.emonth});
        } else if(item.sjmc){
            $state.go('indicators.enterSituation.BusDetail', {name: item.sjmc, type: $scope.type, year: $scope.choseyear, emonth: $scope.emonth});
        }else if(item.xzqhqc){
            $state.go('indicators.enterSituation.BusDetail', {name: item.xzqhqc, type: $scope.type, year: $scope.choseyear, emonth: $scope.emonth});
        }
    };

    /**
     * @description 清空当前查询条件
     */
    $scope.clearParam = function () {
        //$scope.smonth = null;
        //$scope.emonth = null;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';
        //$scope.queryData();
    };
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = $scope.type == 'city' ? _.size(result) : _.size(result.select);
            $scope.selection = $scope.type == 'city' ? result : result.select;
            $scope.category = $scope.type == 'city' ? null : result.type;
        }, function (reason) {
            console.log(reason);//点击空白区域，总会输出backdrop click，点击取消，则会暑促cancel
        });
    };
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

    /**
     * @description 判断是否选择包含新区的复选框
     * @return 如果选中$scope.area = 0 ,如果没有选中$scope.area = 1
     */
    /**
     *
     * @default {number} 默认不选中不包含新区，也就是area = 1
     */
    $scope.area = '1';
    $scope.chk = false;
    $scope.check = function(val){
        !val ? $scope.area = '1' : $scope.area = '0';
    };
}]);