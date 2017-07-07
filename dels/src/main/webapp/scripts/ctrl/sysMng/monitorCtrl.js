/**
 * @ngdoc controller
 * @name app.controller.monitorCtrl
 * @author l13608
 * @description 系统监控控制器
 * @date 2017/3/24 15:07
*/
app.controller('monitorCtrl', ['$scope', '$state', '$location', 'commonService', '$document', '$rootScope','toastr','$interval', function ($scope, $state, $location, commonService, $document, $rootScope,toastr,$interval) {

    var vm = $scope.vm = {};
    /*
     *@author l13608
     *@description CPU的数据
     *@date 16:54 2017/3/6
    */
    var serviceRate = [];
    var CPUData = [];

    /*
     *@author l13608
     *@description 拿到服务器数据进行配置
     *@date 15:43 2017/3/4
    */
    $scope.toConfigure = function (CPUData) {
        /*
         *@author l13608
         *@description 加载CPU的highchar配置
         *@date 15:42 2017/3/4
         */
        $scope.CPUChartConfig = {
            options: {
                subtitle: {
                    text: '监测系统cpu使用率'
                },
                xAxis: {
                    tickPositions: []
                },
                yAxis: {
                    title: {
                        text: '使用率 (%)'
                    },
                    tickPositioner: function () {
                        return [0,10,20,30,40,50,60,70,80,90,100];
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true
                        },
                        enableMouseTracking: false
                    }
                },
                legend:{
                    enabled:false
                },
                credits:{
                    enabled:false
                }
            },
            title: {
                text: 'CPU 使用率'
            },
            series: [{
                data: CPUData
            }]
        };
    };

    /*
     *@author l13608
     *@description 拿到在线用户数，线程数和cpu使用率
     *@date 10:16 2017/3/6
    */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMonitor/onlineUser', {
        }).then(function (data) {
            vm.items = data;
            $scope.getServiceRate();
            $scope.toConfigure(serviceRate);
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };
    $scope.queryData();

    /*
     *@author l13608
     *@description 点击在线用户数跳转在线用户列表
     *@date 17:35 2017/3/4
    */
     $scope.showOnlineUsers = function () {
         $state.go('sysMng.userOnline');
     };

     /*
      *@author l13608
      *@description 点击线程数跳转系统线程界面
      *@date 10:01 2017/3/6
     */

     $scope.showThreadNum = function () {
         $state.go('sysMng.threadNum');
     };

     /*
      *@author l13608
      *@description 定时刷新系统数据（1秒）
      *@date 10:35 2017/3/6
     */
     var timingObject = $interval(function(){
        $scope.queryData();
     },4000);

    /**
     * @author l13608
     * @description 当控制器销毁时，同时销毁该计时器
     * @date 2017/3/24 15:08
    */
    $scope.$on('$destroy', function() {
        $interval.cancel(timingObject);
    });

    /**
     * @author l13608
     * @description 加载最新的cpu数据
     * @date 2017/3/24 15:09
    */
     $scope.getServiceRate = function () {
         commonService.queryDataByP("sysMonitor/cpuInfo", {
         }).then(function (data) {
             if(serviceRate.length < 5){
                 serviceRate.unshift(data);
             }else {
                 serviceRate.unshift(data);
                 serviceRate.pop();
             }
         }, function () {
             console.log("监测cpu出错");
         });
     }
}]);
/**
 * @ngdoc controller
 * @name app.controller.userOnlineCtrl
 * @author l13608
 * @description 展示在线用户控制器
 * @date 2017/3/24 15:09
*/

app.controller('userOnlineCtrl', ['$scope', '$state', '$location', 'commonService', '$document', '$rootScope','toastr', function ($scope, $state, $location, commonService, $document, $rootScope,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 15:09
    */

    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};

    /**
     * @author l13608
     * @description 配置分页基本参数
     * @date 2017/3/24 15:10
    */

    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 返回系统监控界面
     *@date 17:52 2017/3/4
    */
     $scope.backSysMonitor = function () {
         $state.go('sysMng.monitor');
     };

     /*
      *@author l13608
      *@description 拿到在线用户的数据
      *@date 17:54 2017/3/4
     */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMonitor/onlineUser', {
        }).then(function (data) {
            for(var i=0; i<data.userlist.length; i++){
                data.userlist[i].index = i + 1;
            }
            vm.items = data.userlist;
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };
    $scope.queryData();
}]);

/**
 * @ngdoc controller
 * @name app.controller.threadNumCtrl
 * @author l13608
 * @description 系统线程数控制器
 * @date 2017/3/24 15:10
*/

app.controller('threadNumCtrl', ['$scope', '$state', '$location', 'commonService', '$document', '$rootScope','toastr', function ($scope, $state, $location, commonService, $document, $rootScope,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 15:10
    */

    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};

    /**
     * @author l13608
     * @description 配置分页基本参数
     * @date 2017/3/24 15:11
    */

    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 返回系统监控界面
     *@date 17:52 2017/3/4
     */
    $scope.backSysMonitor = function () {
        $state.go('sysMng.monitor');
    };

    /*
     *@author l13608
     *@description 拿到系统线程的数据
     *@date 17:54 2017/3/4
     */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMonitor/onlineUser', {
        }).then(function (data) {
            for(var i=0; i<data.threadList.length; i++){
                data.threadList[i].index = i + 1;
            }
            vm.items = data.threadList;
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };
    $scope.queryData();
}]);