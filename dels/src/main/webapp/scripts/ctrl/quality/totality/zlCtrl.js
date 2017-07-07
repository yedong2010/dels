'use strict';
/**
 * @ngdoc controller
 * @name app.controller.zlCtrl
 * @description 数据质量检测-总体统计控制器
 * @author z11595
 * @date 2016-12-9
 */
app.controller('zlCtrl', ['$scope', 'commonService', '$timeout', function ($scope, commonService, $timeout) {
    /**
     *
     * @type {Date} 获取当前日期、年份、月份
     * @description 用来判断，如果是当前年份，月份只能到当前月份
     */
    var now = new Date();
    $scope.nowmonth = now.getMonth() + 1;
    $scope.maxmonth = now.getMonth() + 1;
    /**
     * @define years 选择年份数组从2016年到当前年份
     */
    $scope.years = _.range(2016, now.getFullYear() + 1);
    /**
     *
     * @default {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
    $scope.lastyear = now.getFullYear() -1 ;
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
     * @default all 判断当前用户的类型，从而判断加载的数据
     */
    $scope.userType = commonService.getUserCityType();
    $scope.rolId = commonService.getroleid();
    if( $scope.rolId == 5 ){
        $scope.type = 'all';
    }else{
        $scope.type = $scope.userType;
    }

    /**
     * @description 切换数据视角
     * @param {string} type（all：全省；province,省级部门；city：地市,county：区县）
     * @return 查询当前质量信息
     */
    $scope.togglePerspective = function (type) {
        /**
         * @description 清空当前表格数据
         * @type {null}
         */
        $scope.type = type;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';
        $scope.queryZL();
    };

    /**
     * @description 质量信息查询
     */
    $scope.queryZL = function () {
        /**
         * #description 质量信息查询实现
         * @param {string} type（province：省级部门,city：地市)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市|区县选择集合
         * @param {string} category 行政区划类别(0:珠三角，1：粤东西北)
         * @return 返回当前质量信息概况
         * @callback 更新当前survey数据模型(正确记录数、总记录数、错误记录数、正确率)，双向绑定更新页面显示
         */
        commonService.queryDataByP('quality/zl/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            //console.info(data);
            $scope.survey = data;
        }, function () {
            $scope.survey = {
                zq: 94955,
                cw: 5705,
                zs: 68893,
                zql: 96.73
            };// 获取当前概要信息
        });
        /**
         * @description 错误分类详情查询
         * @param {string} type（province：省级部门,city：地市)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市|区县选择集合
         * @callback 更新错误类别详情highchart图表数据
         */
        commonService.queryDataByP('quality/cwfl/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            //console.info(data);
            $scope.cwConfig.series[0].data = _.pluck(data, 'y');
            var p_data = [];
            _.each(data, function (d, index) {
                p_data.push({
                    name: d.name,
                    y: d.y,
                    color: Highcharts.getOptions().colors[index]
                });
            });
            $scope.cwConfig.series[1].data = p_data;
        }, function () {
            console.log('error in responced!');
        });
    };
    /**
     * @description 查询当前质量概况信息
     */
    $scope.queryZL();
    $scope.cwConfig = {
        options: {
            chart: {
                renderTo: 'container',
                type: 'column'
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            }
        },
        title: {
            text: '错误记录类别分析',
            align: 'left',
            style: {
                fontSize: '24px',
                fontWeight: 500,
                lineHeight: 1.1,
                color: 'inherit',
                fontFamily: 'Microsoft YaHei'
            }
        },
        xAxis: {
            title: {
                text: '错误类别',
                align: 'high',
                margin: 0
            },
            type: 'category',
            categories: ['行政区划', '金额单位', '组织机构代码', '环节状态码', '时序']
        },
        series: [{
            type: 'column',
            name: '错误记录量',
            colorByPoint: true,
            showInLegend: false,
            data: [],
            dataLabels: {
                align: 'center',
                enabled: true
            }
        }, {
            type: 'pie',
            name: '记录量',
            center: ['85%', '5%'],
            size: 120,
            showInLegend: true,
            dataLabels: {
                enabled: true,
                format: '{point.percentage:.1f}%'
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
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市|区县集合
     * @callback 获取当前用户选择的地市|区县列表、数据长度、行政区划类别
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('jc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = _.size(result);
            $scope.selection = result;
        }, function (reason) {
            console.log(reason);//点击空白区域，总会输出backdrop click，点击取消，则会暑促cancel
        });
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

    /**
     * @description 清空当前选择的起止月份和筛选地市|区县并重新查询业务办理页面的数据
     * @define selectedNum 设置选择地市数量为空
     * @define selection 清空选择的地市
     */
    $scope.clearParam = function () {
        /*$scope.smonth = null;
        $scope.emonth = null;*/
        $scope.selectedNum = null;
        $scope.selection = [];
        //$scope.queryZL();
    };

}]);