/**
 * @ngdoc controller
 * @name app.controller.logAnalysisCtrl
 * @author l13608
 * @description 日志分析控制器
 * @date 2017/5/23 14:59
 */

app.controller('logAnalysisCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    /**
     * @author l13608
     * @description 数据接收参数
     * @date 2017/5/23 16:12
    */
    var vm = $scope.vm = {};  //用户列表
    var vm2 = $scope.vm2 = {};  //角色列表

    /**
     * @author l13608
     * @description 配置分页基本参数
     * @date 2017/3/24 14:59
     */
    vm.page = {
        size: 10,
        index: 1
    };
    vm2.page = {
        size: 10,
        index: 1
    };

    $scope.logAnalysisBack = function (){
        $state.go('sysMng.logList');
    };

    /**
     * @author l13608
     * @description 获取分析数据
     * @date 2017/5/24 15:25
     */
    $scope.queryData = function () {
        vm.items = null;
        vm2.items = null;
        commonService.queryDataByP('sysLog/logData', {
            startTime:$scope.startTime,
            endTime:$scope.endTime
        }).then(function (data) {
            if(null != data){
                $scope.userNum=data.userNum;
                $scope.sumNum=data.sumNum;
                $scope.roleNum=data.roleNum;
                $scope.loginNum=data.loginNum;
                for(var i=0; i<data.perUser.length; i++){
                    data.perUser[i].index = i + 1;
                }
                for(var i=0; i<data.perRole.length; i++){
                    data.perRole[i].index = i + 1;
                }
                vm.items = data.perUser;
                vm2.items = data.perRole;
            }
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    /**
     * @description 默认当前查询的是今天日期——上月1号的数据，格式为'2017-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
    if( now.getMonth() == 0 ){
        var lastMonthNo1 = (now.getFullYear() - 1)+"-12-01";//上月1号
    }else{
        var lastMonthNo1 = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-01";//上月1号
    }

    $scope.startTime = today;
    $scope.endTime = today;

    /**
     * @author l13608
     * @description 配置日期插件参数
     * @date 2017/3/24 15:00
     */
    $.fn.datetimepicker.dates['zh'] = {
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
        startDate: '2017-01-01'   //不允许选择2016年的
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });
}]);


