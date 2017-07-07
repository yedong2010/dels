'use strict';
/**
 * @description 数据质量监测-业务总量对账
 * @author m13624
 * @date 2017-03-23
 */
app.controller('accountCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 默认查询前天和昨天的业务量数据,格式为'2017-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
    var yes = new Date(now.getTime() - 3600*24*1000);
    var yesterday = yes.getFullYear()+"-"+((yes.getMonth()+1)<10?"0":"")+(yes.getMonth()+1)+"-"+(yes.getDate()<10?"0":"")+yes.getDate();
    var beforeyes = new Date(now.getTime() - 3600*24*1000*2);
    var beforeyesterday = beforeyes.getFullYear()+"-"+((beforeyes.getMonth()+1)<10?"0":"")+(beforeyes.getMonth()+1)+"-"+(beforeyes.getDate()<10?"0":"")+beforeyes.getDate();
    $scope.dateNo1 = beforeyesterday;
    $scope.dateNo2 = yesterday;
    $scope.startTime = beforeyesterday.split('-')[0] + '-01-01';

    $scope.versionDateNo1 = $scope.dateNo1;
    $scope.versionDateNo2 = $scope.dateNo2;
    /**
     * @description 点击查询
     */
    $scope.togglePerspective = function(){
        $scope.queryData();
    };

    /**
     * @description 全局获取数据
     */
    $scope.queryData = function () {
        $scope.versionDateNo1 = $scope.dateNo1;
        $scope.versionDateNo2 = $scope.dateNo2;
        commonService.queryDataByP('quality/ywlaccount',{
            startTime: $scope.startTime,
            dateTime: $scope.dateNo1
        }).then(function (data) {
            $scope.v1 = data[0].value;
        }, function () {
            console.info('responce error,please call the administrator');
        });

        commonService.queryDataByP('quality/ywlaccount',{
            startTime: $scope.startTime,
            dateTime: $scope.dateNo2
        }).then(function (data) {
            $scope.v2 = data[0].value;
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

    $("#dateNo1Ctrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: '2017-01-01'   //不允许选择2016年的
    }).on('changeDate',function(){
        $scope.dateNo1 = $("#dateNo1").val();
        $scope.startTime = $scope.dateNo1.split('-')[0] + '-01-01';
        $("#dateNo2Ctrl").datetimepicker('setStartDate',$scope.dateNo1);
    });

    $("#dateNo2Ctrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: yesterday  //默认昨天
    }).on('changeDate',function(){
        $scope.dateNo2 = $("#dateNo2").val();
        $("#dateNo1Ctrl").datetimepicker('setEndDate',$scope.dateNo2);
    });

}]);