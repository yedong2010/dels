/**
 *@author l13608
 *@description 部门不作为慢作为行为控制器
 *@date 15:20 2017/3/14
 */
app.controller('analysisLowEffic', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @description 默认当前查询的是上月——本月的数据，格式为'2017-01'
     */
    var now = new Date();
    var thisMonth = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1);
    if( now.getMonth() == 0 ){
        var lastMonth = (now.getFullYear() - 1)+"-12";//上月
    }else{
        var lastMonth = now.getFullYear()+"-"+(now.getMonth()<10?"0":"")+now.getMonth();//上月
    }

    $scope.startTime = lastMonth;
    $scope.endTime = thisMonth;

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

    $("#startTimeCtrl").datetimepicker({
        startView: 'year',
        minView: "year",
        format: "yyyy-mm",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: false,
        pickerPosition: "bottom-left",
        startDate: '2016-01',   //不允许选择2016年的
        endDate: new Date()
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

    $("#endTimeCtrl").datetimepicker({
        startView: 'year',
        minView: "year",
        format: "yyyy-mm",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: false,
        pickerPosition: "bottom-left",
        startDate: lastMonth,  //默认截止日期的可选项需要小于起始日期，即上个月
        endDate: new Date()
    }).on('changeDate',function(){
        var endTime = $("#endTime").val();
        $("#startTimeCtrl").datetimepicker('setEndDate',endTime);
    });

    /**
     * @ngdoc controller
     * @description 拿到门部数据信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        // 拿到门部数据信息
        commonService.queryDataByP('dataMonitor/bzw', {
            startTime:$scope.startTime,
            endTime:$scope.endTime
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    $scope.queryByParam = function(){
        $scope.queryData();
    }
}]);

/**
 *@author l13608
 *@description 办结率、满意率、业务量同比环比控制器
 *@date 15:20 2017/3/14
 */
app.controller('yearRelativeCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 5,
        index: 1
    };

    /**
     * @description 默认当前查询的是今天日期——上月1号的数据，格式为'2017-01-01'
     */
    var now = new Date();
    if (now.getMonth() == 0) {
        var lastMonthNo1 = (now.getFullYear() - 1) + "-12-01";//上月1号
    } else {
        var lastMonthNo1 = now.getFullYear() + "-" + (now.getMonth() < 10 ? "0" : "") + now.getMonth();//上月
    }

    $scope.startTime = lastMonthNo1;

    /**
     * @description 日期选择器配置参数
     */
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["日", "一", "二", "三", "四", "五", "六", "日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
        meridiem: ["上午", "下午"],
        today: "今天"
    };

    $("#startTimeCtrl").datetimepicker({
        format: "yyyy-mm",
        language: 'zh-CN',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        endDate: new Date()
    });

    /**
     * @ngdoc controller
     * @description 拿到各数据的同比环比
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        // 拿到各数据的同比环比
        commonService.queryDataByP('dataMonitor/TBHB', {
            lastMonth:$scope.startTime
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    $scope.queryByParam = function(){
        $scope.queryData();
    }
}]);

/**
 *@author l13608
 *@description 部门不作为慢作为行为控制器
 *@date 15:20 2017/3/14
 */
app.controller('analysisDataAppliCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @description 默认当前查询的是上月——本月的数据，格式为'2017-01'
     */
    var now = new Date();
    var thisMonth = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1);
    if( now.getMonth() == 0 ){
        var lastMonth = (now.getFullYear() - 1)+"-12";//上月
    }else{
        var lastMonth = now.getFullYear()+"-"+(now.getMonth()<10?"0":"")+now.getMonth();//上月
    }

    $scope.startTime = lastMonth;
    $scope.endTime = thisMonth;

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

    $("#startTimeCtrl").datetimepicker({
        startView: 'year',
        minView: "year",
        format: "yyyy-mm",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: false,
        pickerPosition: "bottom-left",
        startDate: '2016-01',   //不允许选择2016年的
        endDate: new Date()
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

    $("#endTimeCtrl").datetimepicker({
        startView: 'year',
        minView: "year",
        format: "yyyy-mm",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: false,
        pickerPosition: "bottom-left",
        startDate: lastMonth,  //默认截止日期的可选项需要小于起始日期，即上个月
        endDate: new Date()
    }).on('changeDate',function(){
        var endTime = $("#endTime").val();
        $("#startTimeCtrl").datetimepicker('setEndDate',endTime);
    });

    /**
     * @ngdoc controller
     * @description 拿到门部数据信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        // 拿到门部数据信息
        commonService.queryDataByP('dataMonitor/SJJC', {
            startTime:$scope.startTime,
            endTime:$scope.endTime
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    $scope.queryByParam = function(){
        $scope.queryData();
    }
}]);