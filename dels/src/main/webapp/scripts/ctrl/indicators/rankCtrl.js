'use strict';
/**
 * @description 数据指标统计-综合排名
 * @author m13624
 * @date 2017-03-15
 */
app.controller('rankCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
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
        commonService.queryDataByP('rate/rateRank',{
            startTime: $scope.startTime,
            endTime: $scope.endTime
        }).then(function (data) {
            for(var i = 0; i<data.length; i++){
                data[i].wsqlcbll = Math.round( parseFloat(data[i].wsqlcbll) * 100)/100;
                data[i].swbll = Math.round( parseFloat(data[i].swbll) * 100)/100;
                data[i].wsbjl = Math.round( parseFloat(data[i].wsbjl) * 100)/100;
                data[i].zb0 = Math.round( parseFloat(data[i].zb0) * 100)/100;
                data[i].zb1 = Math.round( parseFloat(data[i].zb1) * 100)/100;
                data[i].zb2 = Math.round( parseFloat(data[i].zb2) * 100)/100;
                data[i].spsjysl = Math.round( parseFloat(data[i].spsjysl) * 100)/100;
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

}]);