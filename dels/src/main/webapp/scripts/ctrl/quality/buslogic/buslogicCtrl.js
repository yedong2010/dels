'use strict';
/**
 * @description 数据质量监测-业务逻辑异常
 * @author m13624
 * @date 2017-03-17
 */
app.controller('buslogicCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
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
     * @description 点击查询
     */
    $scope.togglePerspective = function(){
        vm.items = null;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据
     */
    $scope.queryData = function () {
        commonService.queryDataByP('quality/buslogic',{
            startTime: $scope.startTime,
            endTime: $scope.endTime
        }).then(function (data) {
            var tempTimer;
            for(var i = 0; i<data.length; i++){
                tempTimer = new Date(data[i].bjsj);
                data[i].bjsj = tempTimer.getFullYear()+"-"+((tempTimer.getMonth()+1)<10?"0":"")+(tempTimer.getMonth()+1)+"-"+(tempTimer.getDate()<10?"0":"")+tempTimer.getDate() + ' ' +((tempTimer.getHours())<10?"0":"")+(tempTimer.getHours()) + ':' + ((tempTimer.getMinutes())<10?"0":"")+(tempTimer.getMinutes())+':'+((tempTimer.getSeconds())<10?"0":"")+(tempTimer.getSeconds());
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('responce error,please call the administrator');
        });
    };
    $scope.queryData();

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
        endDate: new Date(),
        startDate: lastMonthNo1  //默认截止日期的可选项需要小于起始日期，即上个月1号
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
        endDate: new Date(),
        startDate: '2016-01-01'   //不允许选择2016年的
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

}]);