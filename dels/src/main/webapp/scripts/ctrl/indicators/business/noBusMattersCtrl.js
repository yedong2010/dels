'use strict';
/**
 * @ngdoc controller
 * @name app.controller.noBusMattersCtrl
 * @description 数据指标统计-无业务事项控制器
 * @author z11595
 * @date 2016-10-04
 */
app.controller('noBusMattersCtrl', ['$scope', '$state', '$stateParams', '$timeout', 'commonService', function ($scope, $state, $stateParams, $timeout, commonService) {
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
     * @description 判断当前路由中key为type的参数是否存在;判断当前用户的类型
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
     * @description 定义当前无业务事项数值
     * @type {{wywsxs: number, jzsxs: number, wywsxl: string}} 无业务事项数、进驻事项数、无业务事项率
     */
    $scope.bus = {
        wywsxs: 1826,
        jzsxs: 5702,
        wywsxl: '38%'
    };
    /**
     *
     * @type {{size: number, index: number}} 每页显示5条数据，默认显示第1页(首页)
     */
    vm.page = {
        size: 5,
        index: 1
    };
    /**
     * @requires highcharts-ng
     * @description 定义当前bar的highchart图标的配置文件
     * @property options 定义当前图表类型和该类型图标的配置信息
     * @property title 定义当前图标标题配置
     * @property series 当前数据序列信息
     * @property credits 版权信息配置是否显示，默认显示
     * @property loading 当前loading效果是否展示
     */
    $scope.pieConfig = {
        options: {
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        distance: -30,
                        format: '{point.name}: <br>{point.percentage:.1f}%'
                    }
                }
            }
        },
        title: {
            text: '无业务情况统计',
            align: 'left',
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        series: [{
            name: '无业务情况统计',
            type: 'pie',
            tooltip: {
                pointFormat: '{point.value} <b>{point.percentage:.1f}%</b>'
            },
            data: [['无业务事项', 76.86], ['有业务事项', 23.14]]
        }],
        credits: {
            enabled: false
        },
        loading: false,
        size: {}
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
     * @define years 选择年份数组从2016年到当前年份
     */
    $scope.years = _.range(2017, now.getFullYear() + 1);
    /**
     *
     * @default {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
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
     * @description 切换数据视角
     * @param {string} type（all：全省；province：省级部门；city：地市；county：区县；）
     */
    $scope.togglePerspective = function (type) {
        $scope.type = type;
        $scope.queryNoBusMatter();
    };

    /**
     * @description 查询无业务事项数据
     * @author z11595
     */
    $scope.queryNoBusMatter = function () {
        vm.items = null;
        /**
         * @description 查询当前省级/地市无业务事项明细数据
         * @type {{type:string,year:number,cparams:array}} 类型、当前查询年份、地市名称集合
         * @callback 遍历获取的数据强制转number数值，更新当前表格数据
         */
        commonService.queryDataByP('bus/wywsx/' + $scope.type + '/' + $scope.choseyear, {
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            for (var i = 0; i < data.length; i++) {
                data[i].wywsxs = parseInt(data[i].wywsxs);
                data[i].jzsxs = parseInt(data[i].jzsxs);
                data[i].wywsxl = parseFloat(data[i].wywsxl);
                data[i].index = i + 1; //给每条数据添加序号
            }
            vm.items = data;

            /**
             * @description 查询当前省级/地市无业务事项汇总数据
             * @type {{type:string,year:number,cparams:array}} 类型、当前查询年份、地市名称集合
             * @callback 更新bus的数据模型和pie的highchart图表数据
             */
            var datagk = data[data.length-1];
            $scope.bus = datagk;
            var judge = parseInt(datagk.jzsxs) - parseInt(datagk.wywsxs);
            if(judge == 0){
                $scope.pieConfig.series[0].data = [{name: '无业务事项', y: parseInt(datagk.wywsxs)}];
            }else{
                $scope.pieConfig.series[0].data = [{name: '无业务事项', y: parseInt(datagk.wywsxs)}, {name: '有业务事项', y: parseInt(datagk.jzsxs) - parseInt(datagk.wywsxs)}];
            }
        }, function () {
            console.info('error in responce!');
        });

        /*commonService.queryDataByP('bus/wywsxgk/' + $scope.type + '/' + $scope.choseyear, {
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            $scope.bus = data;
            var judge = parseInt(data.jzsxs) - parseInt(data.wywsxs);
            if(judge == 0){
                $scope.pieConfig.series[0].data = [{name: '无业务事项', y: parseInt(data.wywsxs)}];
            }else{
                $scope.pieConfig.series[0].data = [{name: '无业务事项', y: parseInt(data.wywsxs)}, {name: '有业务事项', y: parseInt(data.jzsxs) - parseInt(data.wywsxs)}];
            }
        }, function () {
            console.info('error in responce!');
        });*/
    };

    /**
     * @description 立即执行查询当前无业务事项数据
     */
    $scope.queryNoBusMatter();
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
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市
     * @callback 获取当前用户选择的地市列表
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = _.size(result);
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
        //$scope.smonth = null;
        //$scope.emonth = null;
        $scope.selectedNum = null;
        $scope.selection = [];
        //$scope.queryNoBusMatter();
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
}])
;