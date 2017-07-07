'use strict';

/**
 * @description 前端分页实现
 * @author z11595
 * @example {{items|paging:1:10}}
 * @param {{list}} items 需要分页的集合
 * @param {{int}} index 当前页索引
 * @param {{int}} pageSize 每页显示条数
 * @return {[type]} 分页后的集合内容
 */
app.filter('paging', function () {
    return function (items, index, pageSize) {
        if (!items)
            return [];
        var offset = (index - 1) * pageSize;
        return items.slice(offset, offset + pageSize);
    }
});

/*
 *@author l13608
 *@description 对用户进行筛选
 *@param {{list}} items 需要筛选的集合
 *@date 20:10 2017/3/9
*/
app.filter('userScreen', function () {
    return function (items,condition) {
        var screenedItems = [];
        if (!items)
            return [];
        for (var i=0;i<items.length;i++){
            if (condition != undefined){
                if(items[i].uname != undefined && items[i].uname.indexOf(condition) != -1){
                    screenedItems.push(items[i]);
                    continue;
                }else if(items[i].rol_name != undefined && items[i].rol_name.indexOf(condition) != -1){
                    screenedItems.push(items[i]);
                    continue;
                }else if(items[i].department != undefined && items[i].department.indexOf(condition) != -1){
                    screenedItems.push(items[i]);
                    continue;
                }else if(items[i].email != undefined && items[i].email.indexOf(condition) != -1){
                    screenedItems.push(items[i]);
                    continue;
                }else if(items[i].descr != undefined && items[i].descr.indexOf(condition) != -1){
                    screenedItems.push(items[i]);
                    continue;
                }
            }else {
                screenedItems.push(items[i]);
            }

        }
        return screenedItems;
    }
});

/**
 * @description 筛选数目(在uib-pagination中使用)
 * @param {[collection]} items) 获取当前集合长度
 * @return {[int]} [如果集合为空返回0，否则返回集合的长度]
 */
app.filter('size', function () {
    return function (items) {
        if (!items)
            return 0;
        return items.length || 0
    }
});

/**
 * @description 定义工具栏图表模板
 * @param {[string]} fa字库图标的class类
 * @template template : 使用模板
 * @typedef replace: true : 使用模板替换原始标记
 * @typedef transclude: false 不复制原始html内容
 * @return  [编译后的结果]
 */
app.directive("toolbar", function () {
    return {
        restrict: 'E',
        scope: {
            icon: "@",
            tip: "@"
        },
        template: '<i class="fa {{icon}} m-left-7 pointer" uib-tooltip="{{tip}}"></i>',
        replace: true,
        transclude: false
    };
});

/**
 * @description 定义当前时钟时间指令
 */
app.directive('currentTime', ['$timeout', 'dateFilter', function ($timeout, dateFilter) {
    return function (scope, element) {
        var timeoutId; // timeoutId, so that we can cancel the time updates

        // used to update the UI
        function updateTime() {
            element.text(dateFilter(new Date(), 'yyyy-MM-dd h:mm:ss'));
        }

        // schedule update in one second
        function updateLater() {
            // save the timeoutId for canceling
            timeoutId = $timeout(function () {
                updateTime(); // update DOM
                updateLater(); // schedule another update
            }, 1000);
        }

        // listen on DOM destroy (removal) event, and cancel the next UI update
        // to prevent updating time ofter the DOM element was removed.
        element.bind('$destroy', function () {
            $timeout.cancel(timeoutId);
        });

        updateLater(); // kick off the UI update process.
    }
}]);

/**
 * @description 自定义小图标按钮
 * @param btn 按钮样式 eg: btn-primary
 * @param btn 按钮样式 eg: fa-ban
 * @param icon 按钮内的图标样式 eg : fa-pencil
 */
app.directive("toolbtn", function () {
    return {
        restrict: 'E',
        scope: {
            btn: "@",
            icon: "@"
        },
        template: '<button class="btn btn-xs {{btn}} toolbtn text-center m-left-7" data-dismiss="alert"><i class="fa {{icon}}"></i></button>',
        replace: true,
        transclude: false
    };
});

/**
 * @description 自定义超链接
 * @param val 值
 * @param sname 判断条件
 * @param detail 点击详情的事件
 */
app.directive("dlink", ['$compile', '$state', function ($compile, $state) {
    return {
        restrict: 'E',
        scope: {
            val: "@",
            kname: "@",
            flag: "@",
            eve: "@"
        },
        link: function (scope, element, attrs) {
            var keys = ['总计', '全省', '省级部门', '地市', '区县', '珠三角地市', '粤东西北地市', '珠三角区县', '粤东西北区县', '全省总计'];
            scope.disclick = false;
            if ((!_.isEmpty(scope.flag) && scope.flag == 'true') || scope.val == 0 || _.indexOf(keys, scope.kname) > -1) {
                scope.disclick = true;
                element.attr('class', 'disclick');
            } else {
                element.on("click", function () {
                    scope.$apply(attrs.eve)
                });
            }
            /**
             * @description 跳转三率一数详情
             * @param name 表格三率一数数据模型给路由提供省级|地市名称
             * @param type 表格三率一数数据模型给路由提供类型
             * @param choseYear 表格三率一数数据模型给路由提供当选下拉选择的年份
             * @param emonth 表格三率一数数据模型给路由提供当选下拉选择的截至月份
             * @param ifsxzs 表格三率一数数据模型给路由，0：网上全流程事项数的详情列表；1：行政审批的进驻事项数详情列表；2：公共服务的进驻事项数详情列表
             */
            scope.goDetail = function (ifsxzs, name, type, choseYear, emonth) {
                $state.go('indicators.threeRate.detail', {
                    ifsxzs: ifsxzs,
                    name: name,
                    type: type,
                    choseYear:choseYear,
                    emonth:emonth
                });
            };
            /**
             * @description 设置当前进驻事项
             * @param pdcs 跑动次数
             * @param type 业务量类型所属行政审批|公共服务|总业务量|上网申办
             * @param name 当前查看省级部门名称
             */
            scope.itemDetail = function (pdcs, name, type, choseYear, emonth) {
                $state.go('indicators.threeRate.itemdetail', {
                    type: type,
                    pdcs: pdcs,
                    name: name,
                    choseYear: choseYear,
                    emonth: emonth
                });
            };
            /**
             * @description 设置当前业务量明细数据跳转
             * @param type 业务量类型所属行政审批|公共服务|总业务量|上网申办
             * @param name 当前查看省级部门名称
             * @param year 年份
             * @param smonth 开始月份
             * @param emonth 结束月份
             */
            scope.busDetail = function (type, name, year, smonth, emonth) {
                $state.go('indicators.busProcess.detail', {
                    type: type,
                    name: name,
                    year: year,
                    smonth: smonth,
                    emonth: emonth
                });
            };

            /**
             * @description 业务办理数据明细中跳转异常办结类别
             * @param type 业务量类型所属行政审批|公共服务|总业务量|上网申办
             * @param name 当前查看省级部门名称
             * @param year 年份
             * @param smonth 开始月份
             * @param emonth 结束月份
             */
            scope.abnormalItem = function (type, name, year, smonth, emonth) {
                $state.go('indicators.busProcess.abnormal', {
                    type: type,
                    name: name,
                    year: year,
                    smonth: smonth,
                    emonth: emonth
                });
            };
            /**
             * @description 无业务事项详情跳转
             * @param {object} item 无业务事项数据模型，传输路由跳转需要的省级/地市名称和当前类型('province','city')
             */
            scope.noBusItem = function (type, name, choseYear, smonth, emonth) {
                $state.go('indicators.noBusMatters.detail', {
                    type: type,
                    name: name,
                    choseYear: choseYear,
                    smonth: smonth,
                    emonth: emonth
                });
            };
            /**
             * @description 跳转已进驻部门详情
             * @param type（city：地市；county：区县；）
             */
            scope.goDepartmentDetail = function (sjmc, xzqhqc, typa) {
                if (sjmc) {
                    $state.go('indicators.enterSituation.departmentDetail', {name: sjmc, type: type});
                } else if (xzqhqc) {
                    $state.go('indicators.enterSituation.departmentDetail', {name: xzqhqc, type: type});
                }
            };
        },
        template: '<a>{{val}}</a>',
        replace: true,
        transclude: false
    };
}]);

/**
 * @description 待执行队列状态显示标签样式
 * @param stn 状态number eg: 0
 * @param stc 需要显示的状态码  eg: 待执行
 */
app.directive("sqslabel", function () {
    return {
        restrict: 'E',
        scope: {
            stn: "@",
            stc: "@"
        },
        controller: function ($scope) {
            switch ($scope.stn) {
                case '0':
                    $scope.css = 'label-success';
                    break;
                case '1':
                    $scope.css = 'label-success';
                    break;
                case '2':
                    $scope.css = 'label-primary';
                    break;
                case '3':
                    $scope.css = 'label-warning';
                    break;
                case '4':
                    $scope.css = 'label-important';
                    break;
                case '5':
                    $scope.css = 'label-primary';
                    break;
                default:
                    break;
            }
        },
        template: '<span class="label {{css}}">{{stc}}</span>',
        replace: true,
        transclude: true
    };
});


/**
 * @description 运行实例队列状态显示标签样式
 * @param stn: 状态number eg: 0
 * @param stc: 需要显示的状态码  eg: 待执行
 */
app.directive("rislabel", function () {
    return {
        restrict: 'E',
        scope: {
            stn: "@",
            stc: "@"
        },
        controller: function ($scope) {
            switch ($scope.stn) {
                case 'COMPLETED':
                    $scope.css = 'label-success';
                    break;
                case 'CANCELLED':
                    $scope.css = 'label-primary';
                    break;
                case 'EXPIRED':
                    $scope.css = 'label-warning';
                    break;
                case 'LOCKED':
                    $scope.css = 'label-warning';
                    break;
                case 'STOPPED':
                    $scope.css = 'label-important';
                    break;
                case 'ERROR':
                    $scope.css = 'label-important';
                    break;
                default:
                    break;
            }
        },
        template: '<span class="label {{css}}">{{stc}}</span>',
        replace: true,
        transclude: true
    };
});

/**
 *
 * @param fmt 格式
 * @return 返回将Date按照指定格式的格式化字符串
 * @example (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * @example (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 * @constructor 对Date的扩展 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * @description 比较密码是否相等自定义标签
 * @requires ng-model
 * @link scope、ctrl、attrs、currentEL
 * @return 在keyon、keyup中触发当前密码校验，返回compare true|false
 */
app.directive('ngCompare', function () {
    return {
        require: 'ngModel',
        link: function (scope, currentEl, attrs, ctrl) {
            var comparefield = document.getElementsByName(attrs.ngCompare)[0];
            var compareEl = angular.element(comparefield);
            currentEl.on('keyup', function () {
                if (compareEl.val() != "") {
                    var isMatch = currentEl.val() === compareEl.val();
                    ctrl.$setValidity('compare', isMatch);
                    scope.$digest();
                }
            });
            compareEl.on('keyup', function () {
                if (currentEl.val() != "") {
                    var isMatch = currentEl.val() === compareEl.val();
                    ctrl.$setValidity('compare', isMatch);
                    scope.$digest();
                }
            });
        }
    }
});

/**
 * @description 各种输入框唯一校验
 * @param type:校验类型 eg: ng-Exists = "username" (校验username的唯一性)
 * @return keyup触发返回exists true|false
 */
app.directive('ngExists',['$http', function ($http) {
    return {
        require: 'ngModel',
        link: function (scope, currentEl, attrs, ctrl) {
            var type = attrs.ngExists;
            var roleId = null;
            var url;
            if (type == 'uname'){
                url = 'sysMng/checkUserName';
            }else if (type == 'addPlanName'){
                url = 'sysMng/checkPlanName';
            }else if (type == 'XZQHDM'){
                url = 'sysMng/checkXZQHDM';
            }else if (type == 'addFType'){
                url = 'sysMng/checkFileType';
            }else if (type == 'addRuleName'){
                url = 'dataIndex/checkRName';
            }else if (type == 'addGroup'){
                url = 'dataIndex/checkGName';
            }else if (type == 'addThemeLibName'){
                url = 'dataIndex/checkInsertFXZTK';
            }else if (type == 'addThemeName'){
                url = 'dataIndex/checkInsertFXZT';
            }else if (type == 'gzmc'){
                url = 'dataIndex/checkJSGZ';
            }else if(type == "addExcelGroup"){
                url = 'sysMng/checkExcelGroup';
            }else if(type == "addRoleName"){
                url = 'sysMng/checkAddRole';
            }else if(type == "editRoleName"){
                roleId = attrs.roleid;
                url = 'sysMng/checkUpdateRole?roleId='+roleId;
            }
            currentEl.on('keyup', function () {
                var val = currentEl.val();
                if (val) {
                    if (roleId ==  null){
                        $.ajax({
                            url: url+'?'+ type + '=' + val,
                            type: 'GET',
                            timeout: 2000,
                            error: function () {
                                console.info('Error checking unique');
                            },
                            success: function (data) {
                                ctrl.$setValidity('exists', data.resultMsg);
                                scope.$digest();
                            }
                        });
                    }else {
                        $.ajax({
                            url: url+'&'+ type + '=' + val,
                            type: 'GET',
                            timeout: 2000,
                            error: function () {
                                console.info('Error checking unique');
                            },
                            success: function (data) {
                                ctrl.$setValidity('exists', data.resultMsg);
                                scope.$digest();
                            }
                        });
                    }
                }
            });
        }
    }
}]);

/**
 * @description range功能 输入从start到end的数 loop
 * @param input 输出
 * @param {int} start 开始索引
 * @param {int} end 结束索引
 * @example [] | range:1:13
 * @author z11595
 * @returns {List<int>} 循环数组
 */
app.filter('range', function () {
    return function (input, start, end) {
        start = parseInt(start);
        end = parseInt(end);
        for (var i = start; i < end; i++)
            input.push(i);
        return input;
    };
});

/**
 * @description 百分率处理函数保留两位有效小数
 * @author z11595
 * @param input 待处理百分率
 * @return 有小数位百分率自动截取两位，0/100不含两位小数
 */
app.filter('cut', ['$filter', function ($filter) {
    return function (input, param) {
        if (param == 0 || param == '0') {
            return '--';
        }
        if (input != null && input != 'null') {
            if (isNaN(input)) {
                if (_.isEmpty(input)) {
                    return 0 + '%';
                }
                return $filter('number')(input, 2) + '%';
            }
            return $filter('number')(input, 2) + '%';
        }
        return 0 + '%';
    };
}]);

/**
 * @description 字符串截取函数，处理过长字段
 * @author z11595
 * @param input 待处理字段
 * @return 不超过20长度字段返回本身，否则截取前20位剩余使用'...'代替
 */
app.filter('strCut', function () {
    return function (input) {
        if (input.length > 20) {
            return input.substring(0, 20) + '...';
        }
        return input;
    };
});

/**
 * @description 字符串截取函数，处理过长字段
 * @author m13624
 * @param input 待处理字段  param 需要截取的长度
 * @return 不超过param长度字段返回本身，否则截取前param位剩余使用'...'代替
 */
app.filter('strCutNum', function () {
    return function (input, param) {
        if (input.length > param) {
            return input.substring(0, param) + '...';
        }
        return input;
    };
});

/**
 * @description 判断当前行政区划类别
 * @author z11595
 * @param input 类别（string类型）
 * @returns 0：珠三角，1：粤东西北，other：返回本身
 */
app.filter('toggle', function () {
    return function (input) {
        if (input == '0') {
            return '珠三角行政区划';
        } else if (input == '1') {
            return '粤东西北行政区划';
        }
        return input;
    };
});

/**
 * @description 页面排序指令(点击触发，降序|升序|不排序)
 * @author m13624
 * @requires scope 作用域，element 元素，attrs 元素属性
 * @returns scope.desc 排序（升序|降序） element元素 up|down class值
 */
app.directive('changerank', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.bind('click', function () {
                /**
                 * @description 获取表头的信息,按照该信息排序
                 */
                var datatype = attrs.datatype;
                scope.col = datatype;
                if (!element.attr('class')) {
                    /**
                     * @description 升序
                     */
                    element.siblings().attr('class', '');
                    element.attr('class', 'up');
                    scope.desc = true;
                } else if (element.attr('class') == 'up') {
                    /**
                     * @description 降序
                     */
                    element.attr('class', 'down');
                    scope.desc = false;
                } else if (element.attr('class') == 'down') {
                    /**
                     * @description 清空col，还原成乱序
                     */
                    element.attr('class', '');
                    scope.desc = '';
                    scope.col = '';
                }
                scope.$digest();

            })
        }
    }
});

/**
 * @description 下拉选择进行筛选
 * @author m13624
 * @example {{items|Selected:sxlxmcvalue:'sxlxmc'}}  sxlxmcvalue对应值，如社会审批   sxlxmc是key值
 * @param {{list}} items 需要进行筛选的数据集合  {{string}} 筛选的内容，如公共服务  {{string}} key值，如公共服务是属于sxlxmc(事项类型名称)
 * @return {[list]} 筛选之后的集合内容
 */
app.filter('choseSelect', function () {
    return function (items, val, key) {
        var output = [];
        if (val == '全部') {
            output = items;
        }else if(items!=null) {
            for (var i = 0; i < items.length; i++) {
                if (items[i][key] == val) {
                    output.push(items[i]);
                }
            }
        }
        return output;
    };
});

/**
 * @description 下拉选择进行筛选——专属差异类型（因为差异类型不是通过新增，删除等文字判断，而是通过日期人为判断,即key对应的是2017-01-01，而不是'新增、删除'）
 * @author m13624
 * @example {{items|diffTypeSelect:sxlxmcvalue:'sxlxmc'}}  sxlxmcvalue对应值，如社会审批   sxlxmc是key值
 * @param {{list}} items 需要进行筛选的数据集合  {{string}} 筛选的内容，如公共服务  {{string}} key值，如公共服务是属于sxlxmc(事项类型名称)
 * @return {[list]} 筛选之后的集合内容
 */
app.filter('diffTypeSelect', function () {
    return function (items, val, stime, etime) {
        var output = [];
        if (val == '全部') {
            output = items;
        } else if (val == '新增') {
            for (var i = 0; i < items.length; i++) {
                if (items[i].version == etime) {
                    output.push(items[i]);
                }
            }
        } else if (val == '删除') {
            for (var j = 0; j < items.length; j++) {
                if (items[j].version == stime) {
                    output.push(items[j]);
                }
            }
        }
        return output;
    };
});

/**
 * @description 自定义型过滤器-使用后台字典数据中的keyword替换keyid
 */
app.filter("wordToVal", function () {
        return function (input, tableName, data) {
            if (data == null) {
                return input;
            }
            return data[tableName][input];
        }
    }
);

/**
 * @description datetimepicker指令(点击选择日期)
 * @author m13624
 * @requires scope 作用域，element 元素，attrs 元素属性
 * @returns scope.desc
 */
app.directive('datepicker', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.on('change', function () {
                if (attrs.id == 'startTime') {
                    scope.startTime = element.val();
                    scope.$digest();//重新刷新scope
                } else {
                    scope.endTime = element.val();
                    scope.$digest();//重新刷新scope
                }
            });
        }
    }
});

/**
 * @description 为自己新增合格率做预留
 * @author l13608
 * @requires scope 作用域，element 元素，attrs 元素属性
 * @returns scope.desc 排序（升序|降序） element元素 up|down class值
 */
app.directive('addQuaRate', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.bind('click', function () {
                var ratesOpt = '';
                $.each(scope.rates, function (index, value) {
                    console.log('这是循环内容', index, value);
                    ratesOpt = ratesOpt + '<option>' + value + '</option>';
                });
                $('#overTwoRate').after('<div class="form-group"><label class="col-sm-2 col-md-offset-2 control-label" ><select ng-model="" class="form-control" ng-change="">' + ratesOpt + '</select></label><div class="col-sm-7"><input ng-model=""  type="text" class="form-control" ></div></div>');
            })
        }
    }
});

/**
 * @description 搜素框筛选
 * @author m13624
 * @param items 待处理的items  val 搜素框的内容字符串  keys 用于筛选搜获的字段，比如搜素框只针对theme，name两列进行筛选
 * @return 搜素框筛选之后的items
 */
app.filter('search', function () {
    return function (items, val, keys) {
        var output = [];
        var ele = '';
        if(val!=null && val != ''){
            for (var i = 0; i < items.length; i++) {
                for(var j = 0; j< keys.length; j++){
                    ele = keys[j];
                    if(items[i][ele] !=null &&items[i][ele]!=''){ //如果该字段是null，就不做判断，直接跳过
                        if( items[i][ele].indexOf(val) >= 0 ) {  //只能针对字符串字段进行筛选，如果是整数，不能筛选，应该indexOf()只有字符串有
                            output.push(items[i]);
                            break;  //如果该条记录存在，就跳出循环,不需要检索其他字段了
                        }
                    }
                }
            }
            return output;
        }else{
            return items;
        }
    };
});

