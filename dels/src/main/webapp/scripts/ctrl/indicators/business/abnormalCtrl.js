'use strict';
/**
 * @ngdoc controller
 * @name app.controller.abnormalCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 业务办理--异常办结统计
 * @author z11595
 * @date 2016-10-18
 */
app.controller('abnormalCtrl', ['$scope', '$state', '$stateParams', '$timeout', 'commonService', function ($scope, $state, $stateParams, $timeout, commonService) {
    /**
     * @description 设置分页每一页显示多少条
     * @default 默认每页显示10条
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 5,
        index: 1
    };
    /**
     * @description 用于切换显示的图表类型
     * @default 默认柱状图
     */
    $scope.tabType = '柱状图';

    /**
     * @type {Array} 数据字典集合
     */
    var dict;
    /**
     * @type {Array} 用于存放bar中series[0].data数组
     */
    var l = [];

    /**
     * @requires $stateParams
     * @description 获取当前路由中传递的参数，通过key值
     */
    $scope.name = $stateParams.name;
    $scope.type = $stateParams.type;
    $scope.year = $stateParams.year;
    $scope.smonth = $stateParams.smonth;
    $scope.emonth = $stateParams.emonth;

    /**
     * @requires commonService 公共service
     * @description 获取当前数据字典中办结机构代码数据字典
     * @return {collection} 字典数组
     */
    commonService.getDict().then(function (data) {
        dict = data['SWYW_COUNT']['BJJGDM'];
    });

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
            categories: ['退回办结'],
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
            name: '部门',
            data: [1524]
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
            name: '异常办结',
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
     * @description 监听父级控制器传递的relow触发highchart自使用，eg：延时触发主要由于页面css定义滑动延时
     */
    $scope.$on('reflow', function () {
        $timeout(function () {
            $scope.reflow();
        }, 500);
    });

    /**
     * @requires commonService
     * @param {string} name 地市名称或省级部门名称
     * @param {number} year 查询年份
     * @param {number} smonth 开始月份
     * @param {number} emonth 结束月份
     * @description 异常办结数据查询
     * @callback 遍历data数组，生成name,y对象结构的数组，更新bar的图表配置
     */
    $scope.queryAbnormal = function () {

        commonService.queryDataByP('bus/ycbj/' + $scope.type, {
            name: $scope.name,
            year: $scope.year,
            smonth: $scope.smonth,
            emonth: $scope.emonth
        }).then(function (data) {
            _.each(data, function (d, index) {
                var obj = _.where(dict, {key: d.code});
                if (_.isEmpty(obj)) {
                    l.push({
                        name: '其他' + index,
                        y: d.zs
                    });
                } else {
                    l.push({
                        name: obj[0].value,
                        y: d.zs
                    });
                }
            });
            $scope.barConfig.loading = false;
            $scope.barConfig.xAxis.categories = _.pluck(l, 'name');
            $scope.barConfig.series[0].name = $scope.name;
            $scope.barConfig.series[0].data = _.pluck(l, 'y');
            $scope.pieConfig.series[0].data = l;
        }, function () {
            console.info('error in responce!');
        });

        commonService.queryDataByP('bus/ycbjxq/' + $scope.type, {
            name: $scope.name,
            year: $scope.year,
            smonth: $scope.smonth,
            emonth: $scope.emonth
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data
        }, function () {
            console.info('error in responce!');
        });
    };
    /**
     * @description 查询当前异常办结数据
     */
    $scope.queryAbnormal();

    /**
     * @requires $state ui-route
     * @description 面包屑跳转上级菜单
     * @param {string} type(province:省级,city:地市)
     */
    $scope.goParent = function () {
        $state.go('indicators.busProcess.index', {type: $scope.type, lasttype: $scope.type});
    };

}])
;