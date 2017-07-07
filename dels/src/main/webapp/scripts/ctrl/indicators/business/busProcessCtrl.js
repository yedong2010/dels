'use strict';
/**
 * @ngdoc controller
 * @name app.controller.busProcessCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 业务办理统计
 * @author z11595
 * @date 2016-10-18
 */
app.controller('busProcessCtrl', ['$scope', '$state', '$timeout', '$stateParams', 'commonService', function ($scope, $state, $timeout, $stateParams, commonService) {
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
     * @description 判断当前路由中key为type的参数是否存在;判断当前用户类型，从而判断是否显示可以选择行政区划
     * @default province 省级部门
     */
    $scope.rolId = commonService.getroleid();
    if($stateParams.lasttype == null || $stateParams.lasttype == '' || $stateParams.lasttype == undefined){
        $scope.type = commonService.getUserCityType();
    }else{
        $scope.type = $stateParams.lasttype;
    }
    /**
     *
     * @type {{}} 定义当前vm 用于控制数据、分页
     */
    var vm = $scope.vm = {};
    /**
     *
     * @type {{size: number, index: number}} 每页显示5条数据，默认显示第1页(首页)
     */
    vm.page = {
        size: 5,
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
     * @define years 选择年份数组从2014年到当前年份
     */
    $scope.years = _.range(2016, now.getFullYear() + 1);
    /**
     *
     * @type {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
    $scope.lastyear = now.getFullYear() - 1;
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
     * @requires highcharts-ng
     * @description 定义当前bar的highchart图标的配置文件
     * @property options 定义当前图表类型和该类型图标的配置信息
     * @property title 定义当前图标标题配置
     * @property xAxis 定义当前X轴配置信息
     * @property yAxis 定义当前Y轴配置信息
     * @property series 当前数据序列信息
     * @property credits 版权信息配置是否显示，默认显示
     * @property loading 当前loading效果是否展示
     */
    $scope.barConfig = {
        options: {
            chart: {
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 15,
                    beta: 7,
                    depth: 30
                }
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            }
        },
        title: {
            text: '业务办理情况',
            align: 'left',
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        xAxis: {
            categories: ['网上申办数', '正常办结数', '异常办结数'],
            labels: {
                rotation: -20,
                style: {
                    "fontSize": "12px",
                    "fontFamily": "Microsoft YaHei"
                }
            }
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: '当月业务',
            data: [1524, 33, 153]
        }],
        credits: {
            enabled: false
        },
        loading: true,
        size: {}
    };
    /**
     * @description 触发highcharts图标自适应
     */
    $scope.reflow = function () {
        $scope.$broadcast('highchartsng.reflow');
    };
    /**
     * @requires $timeout 延时执行
     * @description 监听父级控制器传递的relow触发highchart自使用，eg：延时触发主要由于页面css定义滑动延时
     */
    $scope.$on('reflow', function () {
        $timeout(function () {
            $scope.reflow();
        }, 500);
    });

    /**
     * @description 切换数据视角
     * @param {string} type 类型（all：全省；province：省级部门；city：地市；county：区县；）
     */
    $scope.togglePerspective = function (type) {
        $scope.type = type;
        $scope.queryBusProcess();
    };

    /**
     * @description 查询业务办理页面数据
     */
    $scope.queryBusProcess = function () {
        /**
         * @description 清空当前表格数据
         * @type {null}
         */
        vm.items = null;
        /**
         * @description 设置当前bar highchart图表当前显示loading状态
         * @type {boolean}
         */
        $scope.barConfig.loading = true;

        /**
         * @requires commonService queryDataByP
         * @description 业务办理查询
         * @param {{year: number, smonth: number, emonth: number, cparams: array}} 查询年份、开始月份、结束月份、地市名称数组
         * @callback 更新当前表格数据
         */
        commonService.queryDataByP('bus/ywbl/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
        /**
         * @requires commonService queryDataByP
         * @description 业务办理总体查询（省级总体|地市总体）
         * @param {{year: number, smonth: number, emonth: number, cparams: array}} 查询年份、开始月份、结束月份、地市名称数组
         * @callback 更新当前bar的highchart图表数据，取消loading效果
         */
        commonService.queryDataByP('bus/ywblgk/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            $scope.barConfig.series[0].data = [data.zs, data.zc, data.yc];
            $scope.barConfig.loading = false;
        }, function () {
            console.info('error in responce!');
        });
    };
    /**
     * @description 立即执行当前业务办理页面数据查询
     */
    $scope.queryBusProcess();

    /**
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市
     * @callback 获取当前用户选择的地市列表
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            /**
             * @description 当前用户选择地市的数量
             */
            $scope.selectedNum = _.size(result);
            /**
             * @description 用户在模态框中选择的地市名称集合
             * @type {array} 地市名称数组
             */
            $scope.selection = result;
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
        /*$scope.smonth = null;
        $scope.emonth = null;*/
        $scope.selectedNum = null;
        $scope.selection = [];
        //$scope.queryBusProcess();
    };
    /**
     * @description 设置变更年份是当前默认起止月份
     */
    $scope.defaultMonth = function () {
        if ($scope.choseyear != 2016) {
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