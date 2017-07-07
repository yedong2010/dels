'use strict';
/**
 * @ngdoc controller
 * @name app.controller.mainCtrl
 * @description 数据指标统计-总体统计控制器
 * @author z11595
 * @date 2016-12-18
 */
app.controller('mainCtrl', ['$scope', '$state', '$timeout', 'commonService', '$templateCache', function ($scope, $state, $timeout, commonService, $templateCache) {
    /**
     * @description 创建当前页面主要对象控制页面数据、筛选和分页
     * @type {{}} vm为空对象
     */
    var vm = $scope.vm = {};
    /**
     * @description 定义当前页面每页显示10条记录，默认显示第1页
     * @define size 5
     * @define index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 7,
        index: 1
    };
    /**
     * @description 定义数据类型('all':全省,'province':全省,'city':地市,'county':区县),
     * @default  判断当前用户的type类型和用户id，确定默认加载的数据
     * @type {string}
     */
    $scope.userType = commonService.getUserCityType();
    $scope.rolId = commonService.getroleid();
    if( $scope.rolId == 5 ){
        $scope.type = 'all';
    }else{
        $scope.type = $scope.userType;
    }
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
    //柱状图目前近4个月的业务量情况。目前被业务量排名替换掉
    /*$scope.chartConfig = {
        options: {
            chart: {
                renderTo: 'container',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 22,
                    beta: 0,
                    depth: 54,
                    viewDistance: 25
                }
            },
            lang: {
                loading: "加载中...",
                noData: '暂无数据'
            },
            colors: ['#7cb5ec', '#434348'],
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            }
        },
        series: [],
        title: {
            text: '办理业务量统计',
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        xAxis: {
            type: 'category',
            categories: [],
            labels: {
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '业务量 (millions)'
            }
        },
        credits: {
            enabled: false
        },
        loading: true,
        noData: {
            position: {
                align: 'center',
                verticalAlign: 'middle'
            },
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#D3D3D3'
            }
        },
        size: {}
    };*/

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
                marginBottom: 100,
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 30,
                    beta: 0
                }
            },
            plotOptions: {
                pie: {
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        distance: 2,
                        format: '{point.name}<br>{point.percentage:.2f}%'
                    },
                    allowPointSelect: true
                }
            }
        },
        title: {
            text: '',
            align: 'left',
            x: 20,
            style: {
                "fontSize": "16px",
                "fontFamily": "Microsoft YaHei"
            }
        },
        series: [{
            name: '跑动次数',
            colorByPoint: true,
            innerSize: '50%',
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
    $scope.years = _.range(2016, now.getFullYear() + 1);
    /**
     *
     * @default {number} 默认选择年份为当前年份
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
     * @description 查询当前业务量变化趋势和三率 按照当前选择的年份和月份
     */
    $scope.queryByParam = function () {
        /**
         * @description 清空当前表格数据
         */
        vm.items = null;
        /**
         * @description 查询当前类型下的总业务量排名（全省显示事项排名）
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取数组，显示与table中
         */
        commonService.queryDataByParam('totality/ywlph/' + $scope.type, './scheme/ywlph/' + $scope.type + '.json',  {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function (data) {
            console.info('error in response');
        });

        /**
         * @description 查询当前类型下的三率汇总(网上全流程办理率，上网办理率，网上办结率,非2016年还会提供跑动次数)
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 更新当前三率信息
         */
        commonService.queryDataByParam('totality/rate/' + $scope.type, './scheme/totality/rate/' + $scope.choseyear + '/' + $scope.type + '.json', {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            $scope.wsqlcblv = data.wsqlcblv;
            $scope.wsbjl = data.wsbjl;
            $scope.swblv = data.swblv;
            if($scope.choseyear != 2016){
                var pieData = [];
                for(var i=0; i<4; i++){
                    var obj = {};
                    obj.name =  i + '次';
                    obj.y = data['pdcs' + i];
                    pieData.push(obj);
                }
                $scope.pieConfig.series[0].data = _.sortBy(pieData, 'y');
            }
        }, function (data) {
            console.info('error in response');
        });

        /**
         * @description 查询当前进驻部门、进驻事项、行政审批事项、公共服务事项、线上线下总业务量、行政审批业务总量、公共服务业务总量、线上业务量、行政审批线上业务量，公共服务线上业务量
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param{array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 更新当前survey概况数据，页面双向绑定更新显示内容
         */

        commonService.queryDataByParam('totality/itemsurvey/' + $scope.type, './scheme/totality/itemsurvey/' + $scope.choseyear + '/' + $scope.type + '.json', {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            $scope.survey = data;
        }, function (data) {
            console.info('error in response');
        });
        /**
         * @description 查询跑动次数 -- 如果是非2016年的静态数据，三率的接口已经提供跑动次数。所以只有2016年时，请求下面接口
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {string} choseyear 年份
         * @callback 更新跑动次数饼图pie的highchart图表数据
         */

        if($scope.choseyear == 2016){
            commonService.queryDataByP('./scheme/totality/times/' + $scope.choseyear + '/' + $scope.type + '.json').then(function (data) {
                $scope.pieConfig.series[0].data = _.sortBy(data, 'y');
            }, function(data){
                console.info('error in response');
            })
        }



        //以下柱状图显示近4个月的业务量情况。目前被业务量排名替换掉
        /**
         * @description 设置当前图标loading效果显示
         * @type {boolean}
         */
        //$scope.chartConfig.loading = true;
        /**
         * @description 查询当前类型下的上网业务量办理和线下业务量办理数值
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取当前返回集合的长度,取末尾四项数据更新当前chartConfig的highchart图表
         */
        /*commonService.queryDataByP('totality/handle/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            var len = _.size(data.internet);
            $scope.chartConfig.loading = false;
            if (len > 3) {
                $scope.chartConfig.xAxis.categories = data.month.slice(len - 4, len);
                $scope.chartConfig.series = [
                    {"name": "上网办理", "data": data.internet.slice(len - 4, len)},
                    {"name": "非上网办理", "data": data.non_internet.slice(len - 4, len)}
                ];
            } else {
                $scope.chartConfig.xAxis.categories = data.month;
                $scope.chartConfig.series = [
                    {"name": "上网办理", "data": data.internet},
                    {"name": "非上网办理", "data": data.non_internet}
                ];
            }

        }, function (data) {
            console.info('error in response');
        });*/
    };
    /**
     * @description 切换数据视角
     * @param {string} type（all：全省；province：省级部门；city：地市；county：区县；）
     */
    $scope.togglePerspective = function (type) {
        /**
         * @description 清空当前表格数据
         * @type {null}
         */
        vm.items = null;
        $scope.type = type;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';

        /**
         * @description 查询当前类型下的总业务量排名（全省显示事项排名）
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取数组，显示与table中
         */
        commonService.queryDataByParam('totality/ywlph/' + $scope.type, './scheme/ywlph/' + $scope.type + '.json',  {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function (data) {
            console.info('error in response');
        });

        /**
         * @description 查询当前进驻部门、进驻事项、行政审批事项、公共服务事项、线上线下总业务量、行政审批业务总量、公共服务业务总量、线上业务量、行政审批线上业务量，公共服务线上业务量
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 更新当前survey概况数据，页面双向绑定更新显示内容
         */
        commonService.queryDataByParam('totality/itemsurvey/' + $scope.type, './scheme/totality/itemsurvey/' + $scope.choseyear + '/' + $scope.type + '.json', {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            $scope.survey = data;
        }, function (data) {
            console.info('error in response');
        });
        /**
         * @description 查询跑动次数 -- 如果是非2016年的静态数据，三率的接口已经提供跑动次数。所以只有2016年时，请求下面接口
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {string} choseyear 年份
         * @callback 更新跑动次数饼图pie的highchart图表数据
         */
        if($scope.choseyear == 2016){
            commonService.queryDataByP('./scheme/totality/times/' + $scope.choseyear + '/' + $scope.type + '.json').then(function (data) {
                $scope.pieConfig.series[0].data = _.sortBy(data, 'y');
            }, function(data){
                console.info('error in response');
            })
        }

        /**
         * @description 查询当前类型下的三率汇总(网上全流程办理率，上网办理率，网上办结率，非2016年还会提供跑动次数)
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 更新当前三率信息
         */
        commonService.queryDataByParam('totality/rate/' + $scope.type, './scheme/totality/rate/' + $scope.choseyear + '/' + $scope.type + '.json', {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            $scope.wsqlcblv = data.wsqlcblv;
            $scope.wsbjl = data.wsbjl;
            $scope.swblv = data.swblv;
            if($scope.choseyear != 2016){
                var pieData = [];
                for(var i=0; i<4; i++){
                    var obj = {};
                    obj.name =  i + '次';
                    obj.y = data['pdcs' + i];
                    pieData.push(obj);
                }
                $scope.pieConfig.series[0].data = _.sortBy(pieData, 'y');
            }

        }, function (data) {
            console.info('error in response');
        });


        //以下柱状图显示近4个月的业务量情况。目前被业务量排名替换掉
        /**
         * @description 查询当前类型下的上网业务量办理和线下业务量办理数值
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} year 查询年份
         * @param {number} smonth 开始月份
         * @param {number} emonth 结束月份
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取当前返回集合的长度,取末尾四项数据更新当前chartConfig的highchart图表
         */

        /*commonService.queryDataByP('totality/handle/' + $scope.type, {
         year: $scope.choseyear,
         smonth: $scope.smonth,
         emonth: $scope.emonth,
         cparams: $scope.selection,
         category: $scope.category,
         area: $scope.area
         }).then(function (data) {
         var len = _.size(data.internet);
         $scope.chartConfig.loading = false;
         if (len > 3) {
         $scope.chartConfig.xAxis.categories = data.month.slice(len - 4, len);
         $scope.chartConfig.series = [
         {"name": "上网办理", "data": data.internet.slice(len - 4, len)},
         {"name": "非上网办理", "data": data.non_internet.slice(len - 4, len)}
         ];
         } else {
         $scope.chartConfig.xAxis.categories = data.month;
         $scope.chartConfig.series = [
         {"name": "上网办理", "data": data.internet},
         {"name": "非上网办理", "data": data.non_internet}
         ];
         }
         }, function (data) {
         console.info('error in response');
         });*/
    };
    /**
     * @description 设置当前highchart图表loading效果展示
     */
    //$scope.chartConfig.loading = true;

    $scope.togglePerspective($scope.type);
    /**
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市|区县集合
     * @callback 获取当前用户选择的地市|区县列表、数据长度、行政区划类别
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = $scope.type == 'city' ? _.size(result) : _.size(result.select);
            $scope.selection = $scope.type == 'city' ? result : result.select;
            $scope.category = $scope.type == 'city' ? null : result.type;
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
        /*$scope.smonth = 1;
        $scope.emonth = $scope.nowmonth;*/
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '';
        //$scope.togglePerspective($scope.type);
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
     * @description 判断是否选择包含新区的复选框
     * @return 如果选中$scope.area = 0 ,如果没有选中$scope.area = 1
     * @default {number} 默认不选中不包含新区，也就是area = 1
     */
    $scope.area = '1';
    $scope.chk = false;
    $scope.check = function (val) {
        !val ? $scope.area = '1' : $scope.area = '0';
    };
}]);