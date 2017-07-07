/**
 * @ngdoc controller
 * @name app.controller.DyConfRatesCtrl
 * @author l13608
 * @description 指标动态配置控制器
 * @date 11:04 2017/3/21
 */
/*app.controller('DyConfRatesCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
 var vm = $scope.vm = {};

 vm.page = {
 size: 10,
 index: 1
 };
 /!**
 * @author l13608
 * @description 拿到所有的指标
 * @date 2017/3/21 11:21
 *!/
 $scope.queryData = function () {
 vm.items = null;
 commonService.queryDataByP('dataIndex/ZBTX', {
 }).then(function (data) {
 for(var i=0; i<data.length; i++){
 data[i].index = i + 1;
 }
 console.info(data);
 vm.items = data;
 }, function () {
 });
 };
 $scope.queryData();

 /!**
 * @author l13608
 * @description
 * @param id:根据指标id去跳对应的规则界面
 * @date 2017/3/21 11:23
 *!/
 $scope.showRules = function (id) {
 $state.go('dataIndex.indexRules',{
 indexId:id
 });
 };
 }]);*/

/**
 * @ngdoc controller
 * @name app.controller.indexRulesCtrl
 * @author l13608
 * @description 指标规则的控制器
 * @date 2017/3/21 11:29
 */
app.controller('indexRulesCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/21 11:33
     */
    $scope.desc = 0;
    $scope.col = '';

    /**
     * @author l13608
     * @description 设置页面默认展示10条，默认第一页
     * @date 2017/3/21 11:33
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @author l13608
     * @description 根据指标id拿到对应的规则
     * @date 2017/3/21 11:21
     */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('dataIndex/JSGZList', {
            zbid:$stateParams.indexId
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
        });
    };
    $scope.queryData();

    /**
     * @description: 获取要删除的规则信息
     * @author: l13595
     * @time: 2017/3/21 19:25
     */
    /* $scope.getRuleInfo = function (id, name){
     $scope.gzid = id;
     $scope.gzmc = name;
     };

     /!**
     * @description: 删除计算规则
     * @author: l13595
     * @time: 2017/3/21 19:25
     *!/
     $scope.deleteJSGZ = function (){
     commonService.queryDataByP('dataIndex/deleteJSGZ', {
     id:$scope.gzid
     }).then(function (data) {
     if(null != data && 'ok' != data.resultMsg){
     toastr.error(data.resultMsg);
     return;
     }else{
     toastr.success('删除规则成功');
     $scope.gzid = null;
     $scope.gzmc = null;
     $('#deleteRuleModal').modal('hide');
     $scope.queryData();
     }
     });
     };

     /!**
     * @description: 关闭新增的窗口
     * @author: l13595
     * @time: 2017/3/21 20:03
     *!/
     $scope.closeAddRuleModal = function(){
     $('#addRule').modal('hide');
     };

     //提交新增规则
     $scope.addJSGZ = function(){
     commonService.queryDataByP('dataIndex/saveJSGZ', {
     gzmc:$scope.gzmc,
     zbid:$stateParams.indexId
     }).then(function (data) {
     if(null != data && 'ok' != data.resultMsg){
     toastr.error(data.resultMsg);
     return;
     }else{
     toastr.success('保存规则成功');
     $scope.gzmc = null;
     $('#addRule').modal('hide');
     $scope.queryData();
     }
     });
     }

     //编辑规则信息
     $scope.getEditJSGZ = function(id, name){
     $scope.editJSGZId = id;
     $scope.editJSGZName = name;
     }

     //提交编辑规则信息
     $scope.editJSGZ = function(){
     commonService.queryDataByP('dataIndex/saveJSGZ', {
     gzid:$scope.editJSGZId,
     gzmc:$scope.editJSGZName,
     zbid:$stateParams.indexId
     }).then(function (data) {
     if(null != data && 'ok' != data.resultMsg){
     toastr.error(data.resultMsg);
     return;
     }else{
     toastr.success('保存规则成功');
     $('#editJSGZModal').modal('hide');
     $scope.queryData();
     }
     });
     }


     /!**
     * @author l13608
     * @description 返回指标展示界面
     * @date 2017/3/21 11:38
     *!/
     /!*$scope.goBack = function () {
     $state.go('dataIndex.DyConf');
     };*!/*/

    /**
     * @author l13608
     * @description
     * @param 跳转展示计算公式界面
     * @date 2017/3/21 14:32
     */
    $scope.showCF = function (id) {
        $state.go('dataIndex.calFormula',{
            ruleId:id
        });
    };
}]);

/**
 * @ngdoc controller
 * @name app.controller.calFormulaCtrl
 * @author l13608
 * @description 展示计算公式的控制器
 * @date 2017/3/21 11:29
 */
app.controller('calFormulaCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 增加参数的摸态窗口
     * @date 2017/3/22 16:19
     */
    var addParamModal = $('#addParam');
    /**
     * @author l13608
     * @description 删除参数的摸态窗口
     * @date 2017/4/18 16:27
    */
     var delParamModal = $('#delParamModal');
    /**
     * @author l13608
     * @description 拿到计算公式内容
     * @date 2017/3/21 11:21
     */
    $scope.queryData = function () {
        commonService.queryDataByP('dataIndex/JSGZ', {
            gzid:$stateParams.ruleId
        }).then(function (data) {
            //data里面包括两个东西：1.item(参数数据*注意格式)  2.allCalFormula(计算公式数据*注意格式)
            //参数数据格式$scope.items = [{id:1,value:'',name:'率1'},{id:2,value:'',name:'率2'},{id:3,value:'',name:'率3'},{id:4,value:'',name:'率4'},{id:5,value:'',name:'率5'},{id:6,value:'',name:'率6'}];
            $scope.items = data.list;
            //计算公式的数据格式$scope.allCalFormula = {id:1,calFormula:'率1+率2+率3+率4+率5+率6'};
            $scope.allCalFormula = data;
            $scope.calFormula = $scope.allCalFormula.jsgs;
        }, function () {
        });
    };
    $scope.queryData();
    /**
     * @author l13608
     * @description 当增加参数的摸态窗口展示的时候拿到参数
     * @date 2017/3/22 16:13
     */
    addParamModal.on('shown.bs.modal', function () {
        commonService.queryDataByP('dataIndex/ZBCS', {
            gzid:$stateParams.ruleId
        }).then(function (data) {
            //返回的数据格式参考
            //$scope.params = [{id:7,name:'率7'},{id:8,name:'率8'}];
            $scope.params = data;
        }, function () {
        });
    });
    /**
     * @author l13608
     * @description 返回规则界面
     * @date 2017/3/21 11:38
     */
    $scope.goBack = function () {
        $state.go('dataIndex.DyConf');
    };

    /**
     * @author l13608
     * @description 计算数学公式
     * @date 2017/3/22 16:28
     */
    $scope.changeRate = function () {
        $scope.newCalFormula = $scope.calFormula;
        /**
         * @author l13608
         * @description 拆分拿到公式每个参数，和参数数据库比较用来判断公式是否正确
         * @date 2017/3/22 16:29
         */
        var JudCor =$scope.newCalFormula.split(/[\+\-\*/()]/);
        $scope.formulaRight = true;
        var onlyNumRE = /^[0-9]+$/;
        var existNum = /[0-9]+/;
        var keepGoing = true;
        angular.forEach(JudCor, function(data){
            if(keepGoing) {
                if ($.trim(data) != ''&& !existNum.test($.trim(data))){
                    var findParam = false;
                    angular.forEach($scope.items, function(data1){
                        if ($.trim(data1.csmc) != ''&& data1.csmc != null){
                            if ($.trim(data) == $.trim(data1.csmc)){
                                findParam = true;
                            }
                        }
                    });
                    if (findParam == false){
                        $scope.formulaRight = false;
                        keepGoing = false;
                    }
                }else if($.trim(data) != ''&& !onlyNumRE.test($.trim(data)) && existNum.test($.trim(data))){
                    $scope.formulaRight = false;
                    keepGoing = false;
                }
            }
        });
        /**
         * @author l13608
         * @description 如果公式没错则进行计算
         * @date 2017/3/22 16:30
         */
        if ($scope.formulaRight == true){
            angular.forEach($scope.items, function(data1){
                if ($.trim(data1.value) != ''&& data1.value != null){
                    $scope.newCalFormula = $scope.newCalFormula.replace(eval("/"+data1.csmc+"/g"),data1.value);
                }
            });
            var keepGoing2 = true;
            angular.forEach($scope.items, function(data){
                if (keepGoing2){
                    if($scope.newCalFormula.indexOf(data.csmc) != -1){
                        $scope.formulaRight = null;
                        $scope.calFormulaResult = null;
                        keepGoing2 = false;
                        toastr.error('计算公式有参数未赋值');
                        //$scope.newCalFormula = $scope.newCalFormula.substring(0,$scope.newCalFormula.indexOf(data.csmc)-1) + $scope.newCalFormula.substring($scope.newCalFormula.indexOf(data.csmc)+data.csmc.length,$scope.newCalFormula.length);
                    }
                }
            });
            if ($scope.formulaRight == true){
                try {
                    $scope.calFormulaResult = eval($scope.newCalFormula);
                    if($scope.calFormulaResult == 'Infinity'){
                        $scope.formulaRight = false;
                        $scope.calFormulaResult = null;
                        toastr.error('计算结果溢出出错');
                    }
                }catch (err){
                    $scope.formulaRight = false;
                    $scope.calFormulaResult = null;
                    toastr.error('计算公式出错，请检查');
                }
            }
        }else {
            $scope.calFormulaResult = null;
            toastr.error('计算公式出错，请检查');
        }
        $scope.newCalFormula = $scope.calFormula;
    };

    /**
     * @author l13608
     * @description 在光标处插入文本
     * @param test：要插入的文本内容
     * @date 2017/3/22 16:27
     */

    $scope.insertText = function (text) {
        var obj = document.getElementById("CalFormula");
        $scope.caretPosition = 0;
        $scope.caretPositionEnd = 0;
        var pos = 0;
        var poe = 0;
        if (document.selection) {
            obj.focus();
            var cuRange = document.selection.createRange();
            var tbRange = obj.createTextRange();
            tbRange.setEndPoint("EndToEnd", cuRange);
            poe = tbRange.text.length;
            tbRange.setEndPoint("EndToStart", cuRange);
            pos = tbRange.text.length;
            cuRange.select();
            $scope.caretPosition = pos;
            $scope.caretPositionEnd = poe;
        }else if (obj.selectionStart || obj.selectionStart == '0'){
            $scope.caretPosition = obj.selectionStart;
            $scope.caretPositionEnd = obj.selectionEnd;
        }
        var restoreTop = obj.scrollTop;
        $scope.calFormula = $scope.calFormula.substring(0, $scope.caretPosition) + text + $scope.calFormula.substring($scope.caretPositionEnd, $scope.calFormula.length);
        if (restoreTop > 0) {
            // restore previous scrollTop
            obj.scrollTop = restoreTop;
        }
        if (document.selection) {
            obj.focus(function () {
                var tbRange = obj.createTextRange();
                tbRange.move("character",pos+text.length);
                tbRange.select();
            });
        }else if (obj.selectionStart || obj.selectionStart == '0'){
            obj.focus(function () {
             obj.selectionStart = $scope.caretPosition + text.length;
             obj.selectionEnd = obj.selectionStart;
             });
        }
    };

    /**
     * @author l13608
     * @description 拿到要删除参数的id和名称
     * @param id:要删除参数的id,name:要删除参数的名称
     * @date 2017/4/18 15:58
    */
    $scope.getDelRate = function (id,name){
        $scope.delRateId = id;
        $scope.delRateName = name;
    };
    /**
     * @author l13608
     * @description 根据参数的id删除参数
     * @date 2017/3/22 14:54
     */
    $scope.delRate = function () {
        commonService.queryDataByP('dataIndex/deleteGZCS', {
            id:$scope.delRateId//参数的id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            $scope.calFormula = '';
            $scope.calFormulaResult = null;
            delParamModal.modal('hide');
            toastr.success('删除成功');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 清空输入内容
     * @date 2017/3/22 15:01
     */

    $scope.clearCalFormula = function () {
        $scope.calFormula = '';
        $scope.calFormulaResult = null;
    };

    /**
     * @author l13608
     * @description 关闭增加参数摸态窗口
     * @date 2017/3/22 15:59
     */

    $scope.closeAddParamModal = function () {
        addParamModal.modal('hide');
    };

    /**
     * @author l13608
     * @description 根据id增加参数
     * @date 2017/3/22 15:59
     */
    $scope.addParam = function () {
        commonService.queryDataByP('dataIndex/saveGZCS', {
            gzid:$scope.allCalFormula.id, //要增加参数的计算公式的id
            csid:$scope.addParamValue,  //参数的id
            ruleId:$stateParams.ruleId  //规则id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            $scope.queryData();
            addParamModal.modal('hide');
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 保存计算公式
     * @date 2017/3/22 16:25
     */
    $scope.saveCalFormula = function () {
        $scope.changeRate();
        if ($scope.formulaRight != false){
            commonService.queryDataByP('dataIndex/saveJSGS', {
                gzid:$scope.allCalFormula.id, //要增加参数的计算公式的id
                jsgs:$scope.calFormula  //计算公式
            }).then(function (data) {
                if(null!=data && data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                    return;
                }
                toastr.success('保存成功');
                $scope.queryData();
            }, function () {
                toastr.error('返回信息失败，请联系管理员');
            });
        }
    };
}]);