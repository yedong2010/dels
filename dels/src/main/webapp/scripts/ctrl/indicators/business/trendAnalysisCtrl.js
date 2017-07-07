'use strict';
/**
 * @ngdoc controller
 * @name app.controller.trendAnalysisCtrl
 * @description 数据指标统计-业务趋势分析控制器
 * @author m13624
 * @date 2017-02-27
 */
app.controller('trendAnalysisCtrl', ['$scope', '$state', '$timeout', 'commonService', '$templateCache', function ($scope, $state, $timeout, commonService, $templateCache) {
    /**
     * @description 存放业务量，包含日期数组，网上办结总业务量数组，行政审批数组，公共服务数组
     * @type {}
     */
    $scope.data = {};

    /**
     * @description 定义数据类型('all':全省,'province':全省,'city':地市,'county':区县),
     * @default  判断当前用户的type类型和用户id，确定默认加载的数据
     * @type {string}
     */
    $scope.userType = commonService.getUserCityType();
    $scope.rolId = commonService.getroleid();
    if( $scope.rolId == 5 ){
        $scope.type = 'all';
    }else{
        $scope.type = $scope.userType;
    }

    /**
     * @description 触发highcharts图标自适应
     */
    $scope.reflow = function () {
        $scope.$broadcast('highchartsng.reflow');
    };
    /**
     * @requires $timeOut
     * @description 监听父级控制器传递的relow触发highchart自使用，eg：延时触发主要由于页面css定义滑动延时
     */
    $scope.$on('reflow', function () {
        $timeout(function () {
            $scope.reflow();
        }, 500);
    });

    /**
     * @description 判断是否选择包含新区的复选框
     * @return 如果选中$scope.area = 0 ,如果没有选中$scope.area = 1
     * @default {number} 默认不选中，也就是area = 1,含非行政区划
     */
    $scope.area = 1;
    $scope.chk = false;
    $scope.check = function (val) {
        !val ? $scope.area = '1' : $scope.area = '0';
    };

    /**
     * @description 默认当前查询的是今天日期——上月1号的数据，格式为'2017-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
    if( now.getMonth() == 0 ){
        var lastMonthNo1 = (now.getFullYear() - 1)+"-12-01";//上月1号
    }else{
        var lastMonthNo1 = now.getFullYear()+"-"+(now.getMonth()<10?"0":"")+now.getMonth()+"-01";//上月1号
    }

    $scope.startTime = lastMonthNo1;
    $scope.endTime = today;

    /**
     * @description 查询当前网上办理业务量，和所选时间区间内对应每天的业务量
     */
    $scope.togglePerspective = function(val){
        $scope.clearParam();
        $scope.type = val;
        $scope.queryByParam();
    };
    $scope.queryByParam = function () {
        /**
         * @description 查询当前类型下的上网办理业务量总数，以及包含的行政审批和公共服务量
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {string} startTime 起始日期
         * @param {string} endTime 截止日期
         * @param {int} area 是否包含非行政区划（0：不包含；1：包含）
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取数据，显示在前端页面
         */
        commonService.queryDataByP('bus/trendwsbj/' + $scope.type + '/' + $scope.startTime + '/' + $scope.endTime, {
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            $scope.shfwswbjzs = data[0].shfwswbjzs;
            $scope.xzspswbjzs = data[0].xzspswbjzs;
            $scope.swbjzs = data[0].swbjzs;
        }, function () {
            console.info('error in response');
        });
        /**
         * @description 查询当前类型下，所选时间间隔内，每天的业务量
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {string} startTime 起始日期
         * @param {string} endTime 截止日期
         * @param {int} area 是否包含非行政区划（0：不包含；1：包含）
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 更新当前三率信息
         */
        commonService.queryDataByP('bus/trend/' + $scope.type + '/' + $scope.startTime + '/' + $scope.endTime, {
             cparams: $scope.selection,
             category: $scope.category,
             area: $scope.area
        }).then(function (data) {
            var arr0 = []; //存放统计时间的数组
            var arr1 = []; //存放上网办结总数数组
            var arr2 = []; //存放公共服务数组
            var arr3 = []; //存放行政审批数组
            for(var i = 0; i<data.length; i++){
                arr0.push(data[i].tjrq);
                arr1.push(data[i].swbjzs);
                arr2.push(data[i].shfwswbjzs);
                arr3.push(data[i].xzspswbjzs);
            }
            $scope.data.tjrq = arr0;
            $scope.data.swbjzs = arr1;
            $scope.data.shfwswbjzs = arr2;
            $scope.data.xzspswbjzs = arr3;
            var minTick = (Math.ceil(data.length/10)) % 2 == 0 ? Math.ceil(data.length/10) : Math.ceil(data.length/10) + 1;//横坐标间隔，如果10-20个，就以2为1个间隔，如果30-40.即以4为1个间隔

            Highcharts.chart('container', {
                chart: {
                    type: 'spline'
                },
                credits: {
                    enabled : false  //去掉右下角的版权文字，默认是highcharts.com，可以改成自己想要的文字
                },
                title: {
                    text: '网上办结业务量趋势图'
                },
                subtitle: {

                },
                plotOptions: {
                    series: {
                        marker: {
                            enabled: false
                        }
                    }
                },
                xAxis: {
                    crosshair: true,
                    categories: arr0,
                    minTickInterval: minTick
                },
                yAxis: {
                    title: {
                        text: '业务量'
                    }
                },
                tooltip:{
                    shared: true
                },
                series: [{
                    name: '网上办结业务量',
                    data: arr1,
                    color: '#00adef'
                },{
                    name: '行政审批',
                    data: arr3,
                    color: '#ee9978'
                },{
                    name: '公共服务',
                    data: arr2,
                    color: '#60cfbb'
                }]
            });
        }, function () {
            console.info('error in response');
        });
    };
    $scope.queryByParam();
    /**
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市|区县集合
     * @callback 获取当前用户选择的地市|区县列表、数据长度、行政区划类别
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = $scope.type != 'county' ? _.size(result) : _.size(result.select);
            $scope.selection = $scope.type != 'county' ? result : result.select;
            $scope.category = $scope.type != 'county' ? null : result.type;
        }, function (reason) {
            console.log(reason);//点击空白区域，总会输出backdrop click，点击取消，则会暑促cancel
        });
    };

    /**
     * @description 清空当前选择的起止月份和筛选地市|区县并重新查询业务办理页面的数据
     * @define smonth 开始查询时间为空
     * @define emonth 结束查询时间为空
     * @define selectedNum 设置选择地市数量为空
     * @define selection 清空选择的地市
     */
    $scope.clearParam = function () {
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
    };

    /**
     * @description 日期选择器配置参数
     */
    $.fn.datetimepicker.dates['zh-CN'] = {
        days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],
        daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],
        daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],
        months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],
        monthsShort:  ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
        meridiem:    ["上午", "下午"],
        today:       "今天"
    };
    $("#endTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        startDate: lastMonthNo1,  //默认截止日期的可选项需要小于起始日期，即上个月1号
        endDate: new Date()
    }).on('changeDate',function(){
        var endTime = $("#endTime").val();
        $("#startTimeCtrl").datetimepicker('setEndDate',endTime);
    });

    $("#startTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        startDate: '2017-01-01',   //不允许选择2016年的
        endDate: new Date()
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

}]);