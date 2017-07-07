'use strict';
/**
 * @description 系统管理-计算过程追溯
 * @author m13624
 * @date 2017-03-16
 */
app.controller('countChaseCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @description 默认当前查询的是昨天的数据，格式为'2017-01-01'
     */
    var now = new Date();
    var yes = new Date(now.getTime() - 3600*24*1000);
    var yesterday = yes.getFullYear()+"-"+((yes.getMonth()+1)<10?"0":"")+(yes.getMonth()+1)+"-"+(yes.getDate()<10?"0":"")+yes.getDate();
    $scope.dateTime = yesterday;

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
        commonService.queryDataByP('totality/countChase',{
            dateTime: $scope.dateTime
        }).then(function (data) {
            var tempTimer;
            for(var i = 0; i<data.length; i++){
                tempTimer = new Date(data[i].bzwcsj);
                data[i].bzwcsj = ((tempTimer.getHours())<10?"0":"")+(tempTimer.getHours()) + ':' + ((tempTimer.getMinutes())<10?"0":"")+(tempTimer.getMinutes())+':'+((tempTimer.getSeconds())<10?"0":"")+(tempTimer.getSeconds())
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

    $("#dateTime").datetimepicker({
        startView: 'month',
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: false,
        pickerPosition: "bottom-left",
        startDate: '2017-01-01',   //不允许选择2016年的
        endDate: yesterday
    }).on('changeDate',function(){
        $scope.dateTime = $("#dateTime").val();
    });

}]);