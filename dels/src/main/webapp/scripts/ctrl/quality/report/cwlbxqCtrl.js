'use strict';
/**
 * @ngdoc controller
 * @name app.controller.cwlbxqCtrl
 * @description 数据质量统计--错误类别详情
 * @author z11595
 * @date 2016-12-18
 */
app.controller('cwlbxqCtrl', ['$scope', '$state', '$stateParams', '$timeout', 'commonService', function ($scope, $state, $stateParams, $timeout, commonService) {
    /**
     * @description 建立空数组存放bar的highchart图表series中data数据
     * @type {Array}
     */
    var l = [];

    /**
     * @requires $stateParams
     * @description 获取路由传递参数省级|地市名称
     */
    $scope.name = $stateParams.name;
    /**
     * @requires $stateParams
     * @description 获取路由传递参数类型('province':省级,'city':地市')
     */
    $scope.type = $stateParams.type;
    /**
     * @requires $stateParams
     * @description 获取路由传递参数查询年份
     * @type {number}
     */
    $scope.year = $stateParams.year;
    /**
     * @requires $stateParams
     * @description 获取路由传递参数开始月份
     * @type {number}
     */
    $scope.smonth = $stateParams.smonth;
    /**
     * @requires $stateParams
     * @description 获取路由传递参数结束月份
     * @type {number}
     */
    $scope.emonth = $stateParams.emonth;
    /**
     * @requires $stateParams
     * @description 获取路由传递参数错误数量，用以判断是否加载详情列表
     * @type {number}
     */
    $scope.cw = $stateParams.cw;
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
                    alpha: 18,
                    beta: 2,
                    depth: 50
                }
            },
            plotOptions: {
                column: {
                    depth: 25,
                    dataLabels: {
                        enabled: true
                    }
                }
            }
        },
        title: {
            text: '',
            align: 'center',
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        xAxis: {
            categories: [],
            labels: {
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
            name: '错误记录',
            data: []
        }],
        credits: {
            enabled: false
        },
        loading: true,
        size: {}
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
                        /*distance: 0,*/
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            }
        },
        title: {
            text: '',
            align: 'left',
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        series: [{
            name: '错误类别',
            type: 'pie',
            tooltip: {
                pointFormat: '{point.value} <b>{point.percentage:.1f}%</b>'
            },
            data: []
        }],
        credits: {
            enabled: false
        },
        loading: false,
        size: {}
    };
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
     *
     * @type {{}} 定义当前vm 用于控制数据、分页
     */
    var vm = $scope.vm = {};
    /**
     * @description 每页显示10条数据，默认显示第1页(首页)
     * @default size 10
     * @default index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 5,
        index: 1
    };

    /**
     * @requires commonService
     * @description 查询当前错误记录类别详情
     */
    $scope.queryErrorType = function () {
        /**
         * @description 查询错误分类详情
         * @param {string} name 省级部门|地市名称
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @callback 遍历返回值集合生成name,y对象集合，更新bar的highchart图表数据
         */
        commonService.queryDataByP('quality/cwflxq/' + $scope.name, {
            year: $scope.year,
            smonth: $scope.smonth,
            emonth: $scope.emonth
        }).then(function (data) {
            _.each(data, function (d) {
                l.push({
                    name: d.name,
                    y: d.zs
                });
            });
            $scope.barConfig.loading = false;
            $scope.barConfig.xAxis.categories = _.pluck(data, 'name');
            $scope.barConfig.series[0].name = $scope.name;
            $scope.barConfig.series[0].data = _.pluck(data, 'zs');
            $scope.pieConfig.series[0].data = l;
        }, function () {
            console.info('error in responce!');
        });

        //if($scope.cw<=200000){
            commonService.queryDataByP('quality/cwflxqlb/' + $scope.name, {
                year: $scope.year,
                smonth: $scope.smonth,
                emonth: $scope.emonth
            }).then(function (data) {
                //将后台给的字符串形式的数据转换成number
                /*for (var i = 0; i < data.length; i++) {
                 data[i].index = i + 1; //每条数据添加序号
                 }*/
                vm.items = data;
            }, function () {
                console.info('error in responce!');
            });
        //}
    };
    /**
     * @description 立即执行当前查询错误记录分类数据
     */
    $scope.queryErrorType();

    /**
     * @requires $state
     * @description 返回上级 数据质量报告
     * @param {string} type 类型('province':省级,'city':地市')
     */
    $scope.goParent = function () {
        $state.go('quality.report.index', {type: $scope.type, lasttype: $scope.type});
    };
}])
;