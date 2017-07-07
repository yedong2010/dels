'use strict';
var global = 1;
var underscore = angular.module('underscore', []);
underscore.factory('_', function () {
    return window._;
});
var app = angular.module('dms', ['ui.router', 'ui.bootstrap', "highcharts-ng", 'toastr', 'textAngular']);

/**
 * @description 定义当前ui-router的路由跳转
 */
app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    // 默认页
    //$urlRouterProvider.otherwise("/indicators/index");
    $urlRouterProvider.when('/quality/report', '/quality/report/index/province');
    $urlRouterProvider.when('/quality/business', '/quality/business/index');
    $urlRouterProvider.when('/quality/department', '/quality/department/index/all');
    $urlRouterProvider.when('/indicators/rate', '/indicators/rate/index/province');
    $urlRouterProvider.when('/indicators/enterSituation', '/indicators/enterSituation/index/province');
    $urlRouterProvider.when('/indicators/noBusMatters', '/indicators/noBusMatters/index/province');
    $urlRouterProvider.when('/indicators/busProcess', '/indicators/busProcess/index/province');
    $urlRouterProvider.when('/indicators/trend', '/indicators/trend/index');
    // 主页面
    $stateProvider.state('indicators', {
        url: "/indicators",
        templateUrl: './view/public/common.html',
        permission: 'indicators'
    }).state('indicators.welcome', {
        url: "/welcome",
        templateUrl: './view/indicators/welcome.html',
        controller: 'welcomeCtrl',
        permission: 'permissionWel'
    }).state('indicators.index', {
        url: "/index",
        templateUrl: './view/indicators/totality/main.html',
        controller: 'mainCtrl',
        permission: 'indicators'
    }).state('indicators.threeRate', {
        url: "/rate",
        templateUrl: './view/public/common.html',
        permission: 'threeRate'
    }).state('indicators.threeRate.index', {
        url: "/index/:type",
        params: {lasttype: null},
        templateUrl: './view/indicators/rate/rate.html',
        controller: 'rateCtrl',
        permission: 'threeRate'
    }).state('indicators.nounDefine', {  //名词解释和统计范围   l13595 2017/1/18 11:01
        url: "/nounDefine",
        templateUrl: './view/indicators/nounDefine.html',
        controller: 'nounDefineCtrl',
        permission: 'nounDefine'
    }).state('indicators.threeRate.detail', {
        url: "/detail/:ifsxzs/:type/:name/:choseYear/:emonth",
        templateUrl: './view/indicators/rate/rateDetail.html',
        controller: 'rateDetailCtrl',
        permission: 'threeRate'
    }).state('indicators.enterSituation', {
        url: "/enterSituation",
        templateUrl: './view/public/common.html',
        permission: 'enterSituation'
    }).state('indicators.enterSituation.index', {
        url: "/index/:type",
        params: {lasttype: null},
        templateUrl: './view/indicators/rate/enterSituation.html',
        controller: 'enterSituationCtrl',
        permission: 'enterSituation'
    }).state('indicators.enterSituation.departmentDetail', {
        url: "/department/:type/:name/:year/:emonth",
        templateUrl: './view/indicators/rate/enterDepartmentDetail.html',
        controller: 'enterDepartmentDetailCtrl',
        permission: 'enterSituation'
    }).state('indicators.enterSituation.BusDetail', {
        url: "/bus/:type/:name/:year/:emonth",
        templateUrl: './view/indicators/rate/enterBusDetail.html',
        controller: 'enterBusDetailCtrl',
        permission: 'enterSituation'
    }).state('indicators.busProcess', {
        url: "/busProcess",
        templateUrl: './view/public/common.html',
        permission: 'busProcess'
    }).state('indicators.busProcess.index', {
        url: "/index/:type",
        params: {lasttype: null},
        templateUrl: './view/indicators/business/busProcess.html',
        controller: 'busProcessCtrl',
        permission: 'busProcess'
    }).state('indicators.busProcess.abnormal', {
        url: "/abnormal/:type/:name",
        params: {year: null, smonth: null, emonth: null},
        templateUrl: './view/indicators/business/abnormal.html',
        controller: 'abnormalCtrl',
        permission: 'busProcess'
    }).state('indicators.busProcess.detail', {
        url: "/detail/:type/:name",
        params: {year: null, smonth: null, emonth: null},
        templateUrl: './view/indicators/business/busDetail.html',
        controller: 'busDetailCtrl',
        permission: 'busProcess'
    }).state('indicators.threeRate.itemdetail', {
        url: "/itemdetail/:type/:pdcs/:name/:choseYear/:emonth",
        templateUrl: './view/indicators/rate/itemDetail.html',
        controller: 'itemDetailCtrl',
        permission: 'threeRate'
    }).state('indicators.noBusMatters', {
        url: "/noBusMatters",
        templateUrl: './view/public/common.html',
        permission: 'noBusMatters'
    }).state('indicators.noBusMatters.index', {
        url: "/index/:type",
        params: {lasttype: null},
        templateUrl: './view/indicators/business/noBusMatters.html',
        controller: 'noBusMattersCtrl',
        permission: 'noBusMatters'
    }).state('indicators.noBusMatters.detail', {
        url: "/detail/:type/:name/:choseYear/:smonth/:emonth",
        templateUrl: './view/indicators/business/noBusDetail.html',
        controller: 'noBusMattersDetailCtrl',
        permission: 'noBusMatters'
    }).state('indicators.trend', {
        url: "/trend",
        templateUrl: './view/public/common.html',
        permission: 'trend'
    }).state('indicators.trend.index', {
        url: "/index",
        templateUrl: './view/indicators/business/trendAnalysis.html',
        controller: 'trendAnalysisCtrl',
        permission: 'trend'
    }).state('indicators.simpleRate', {
        url: "/simpleRate",
        templateUrl: './view/indicators/rate/simpleRate.html',
        controller: 'simpleRateCtrl',
        permission: 'simpleRate'
    }).state('indicators.rank', {
        url: "/rank",
        templateUrl: './view/indicators/rank.html',
        controller: 'rankCtrl',
        permission: 'rank'
    }).state('indicators.dataAbnormal', {
        url: "/dataAbnormal",
        templateUrl: './view/indicators/dataAbnormal.html',
        controller: 'dataAbnormalCtrl',
        permission: 'dataAbnormal'
    }).state('indicators.busRank', {
        url: "/busRank/:type",
        templateUrl: './view/indicators/busRank/busRank.html',
        controller: 'busRankCtrl',
        permission: 'busRank'
    }).state('indicators.busRankDetail', {
        url: "/busRankDetail/:type/:keyId",
        params:{startTime: null, endTime: null, name: null},
        templateUrl: './view/indicators/busRank/busRankDetail.html',
        controller: 'busRankDetailCtrl',
        permission: 'busRank'
    }).state('indicators.applyRank', {
        url: "/applyRank/:type",
        templateUrl: './view/indicators/applyRank/applyRank.html',
        controller: 'applyRankCtrl',
        permission: 'applyRank'
    }).state('indicators.applyRankDetail', {
        url: "/applyRankDetail/:type/:keyId",
        params:{startTime: null, endTime: null, name: null},
        templateUrl: './view/indicators/applyRank/applyRankDetail.html',
        controller: 'applyRankDetailCtrl',
        permission: 'applyRank'
    }).state('chat', {
        url: "/chat",
        templateUrl: './view/public/common.html',
        permission: 'chat'
    }).state('chat.list', {
        url: "/list",
        templateUrl: './view/chat/list.html',
        controller: 'noteListCtrl',
        permission: 'titlelist'
    }).state('chat.addNote', {
        url: "/add",
        templateUrl: './view/chat/editNote.html',
        controller: 'addNoteCtrl',
        permission: 'titlelist'
    }).state('chat.update', {
        url: "/update",
        params: {"item": null},
        templateUrl: './view/chat/editNote.html',
        controller: 'updateNoteCtrl',
        permission: 'chat'
    }).state('chat.discuss', {
        url: "/discuss",
        templateUrl: './view/chat/discuss.html',
        controller: 'discussCtrl',
        permission: 'discuss'
    }).state('chat.discussDetail', {
        url: "/detail/:id",
        templateUrl: './view/chat/discussDetail.html',
        controller: 'discussDetailCtrl',
        permission: 'discuss'
    }).state('chat.reply', {
        url: "/reply",
        templateUrl: './view/chat/reply.html',
        controller: 'replyCtrl',
        permission: 'reply'
    }).state('chat.replyDetail', {
        url: "/replyDetail/:id/:theme",
        templateUrl: './view/chat/replyDetail.html',
        controller: 'replyDetailCtrl',
        permission: 'reply'
    }).state('quality', {
        url: "/quality",
        templateUrl: './view/public/common.html',
        permission: 'quality'
    }).state('quality.main', {
        url: "/main",
        templateUrl: './view/quality/totality/main.html',
        controller: 'zlCtrl',
        permission: 'quality'
    }).state('quality.report', {
        url: "/report",
        templateUrl: './view/public/common.html',
        permission: 'quality'
    }).state('quality.report.index', {
        url: "/index/:type",
        params: {lasttype: null},
        templateUrl: './view/quality/report/main.html',
        controller: 'qualityCtrl',
        permission: 'quality'
    }).state('quality.report.detail', {
        url: "/detail/:type/:name",
        params: {year: null, smonth: null, emonth: null, cw: null},
        templateUrl: './view/quality/report/detail.html',
        controller: 'cwlbxqCtrl',
        permission: 'quality'
    }).state('quality.business', {
        url: "/business",
        templateUrl: './view/public/common.html',
        permission: 'qualitybus'
    }).state('quality.business.index', {
        url: "/index",
        templateUrl: './view/quality/business/allBusiness.html',
        controller: 'allBusinessCtrl',
        permission: 'qualitybus'
    }).state('quality.business.other', {
        url: "/enter/:place",
        templateUrl: './view/quality/business/otherBusiness.html',
        controller: 'otherBusinessCtrl',
        permission: 'qualitybus'
    }).state('quality.business.compare', {
        url: "/compare/:type/:name",
        templateUrl: './view/quality/business/busCompare.html',
        controller: 'busCompareCtrl',
        permission: 'qualitybus'
    }).state('quality.department', {
        url: "/department",
        templateUrl: './view/public/common.html',
        permission: 'qualitydep'
    }).state('quality.department.index', {
        url: "/index/:place",
        templateUrl: './view/quality/department/department.html',
        controller: 'departmentCtrl',
        permission: 'qualitydep'
    }).state('quality.history', {
        url: "/setting",
        templateUrl: './view/quality/totality/main.html',
        permission: 'qualitydep'
    }).state('quality.content', {
        url: "/setting",
        templateUrl: './view/quality/totality/main.html',
        permission: 'qualitydep'
    }).state('quality.feedback', {              //数据质量核查反馈列表
        url: "/feedback",
        templateUrl: './view/quality/handle/feedback.html',
        controller: 'qualityFeedbackCtrl',
        permission: 'feedback'
    }).state('quality.addFeedback', {           //录入数据质量核查反馈信息
        url: "/addFeedback",
        templateUrl: './view/quality/handle/addFeedback.html',
        controller: 'qualityAddFeedbackCtrl',
        permission: 'feedback'
    }).state('quality.showFeedback', {         //反馈查看
        url: "/showFeedback/:feedbackId",
        templateUrl: './view/quality/handle/showFeedback.html',
        controller: 'qualityShowFeedbackCtrl',
        permission: 'feedback'
    }).state('quality.editFeedback', {
        url: "/editFeedback/:feedbackId",
        templateUrl: './view/quality/handle/editFeedback.html',
        controller: 'qualityEditFeedbackCtrl',
        permission: 'feedback'
    }).state('quality.repre', {
        url: "/repre",
        templateUrl: './view/quality/handle/repre.html',
        controller: 'qualityRepreCtrl',
        permission: 'repre'
    }).state('quality.decision', {
        url: "/decision/:feedbackId",
        templateUrl: './view/quality/handle/decision.html',
        controller: 'qualityDecisionCtrl',
        permission: 'repre'
    }).state('quality.correct', {
        url: "/correct",
        templateUrl: './view/quality/handle/correct.html',
        controller: 'qualityCorrectCtrl',
        permission: 'correct'
    }).state('quality.correctById', {
        url: "/correctById/:feedbackId",
        templateUrl: './view/quality/handle/correctById.html',
        controller: 'qualityCorrectByIdCtrl',
        permission: 'correct'
    }).state('quality.dataProcess', {
        url: "/dataProcess",
        templateUrl: './view/quality/dataProcess/dataProcess.html',
        controller: 'dataProcessCtrl',
        permission: 'qualitydata'
    }).state('quality.buslogic', {
        url: "/buslogic",
        templateUrl: './view/quality/buslogic/buslogic.html',
        controller: 'buslogicCtrl',
        permission: 'qualitybuslogic'
    }).state('quality.account', {
        url: "/account",
        templateUrl: './view/quality/account/account.html',
        controller: 'accountCtrl',
        permission: 'account'
    }).state('analysis', {
        url: "/analysis",
        templateUrl: './view/public/common.html',
        permission: 'analysis'
    }).state('analysis.time', {
        url: "/time",
        templateUrl: './view/analysis/time/analysisTime.html',
        controller: 'analysisTimeCtrl',
        permission: 'analysis'
    }).state('analysis.flow', {
        url: "/flow",
        templateUrl: './view/analysis/flow/analysisFlow.html',
        controller: 'analysisFlowCtrl',
        permission: 'analysis'
    }).state('analysis.process', {
        url: "/process",
        templateUrl: './view/analysis/process/analysisProcess.html',
        controller: 'analysisProcessCtrl',
        permission: 'analysis'
    }).state('analysis.lowEffic', {     //不作为分析
        url: "/lowEffic",
        templateUrl: './view/analysis/mulData/analysisDepData.html',
        controller: 'analysisLowEffic',
        permission: 'analysis'
    }).state('analysis.yearRelative', {     //同比环比分析
        url: "/yearRelative",
        templateUrl: './view/analysis/mulData/analysisYearRelative.html',
        controller: 'yearRelativeCtrl',
        permission: 'analysis'
    }).state('analysis.report', {     //投诉举报分析
        url: "/report",
        templateUrl: './view/analysis/analysisReport.html',
        controller: 'analysisReportCtrl',
        permission: 'analysis'
    }).state('analysis.business', {     //业务能力分析
        url: "/business",
        templateUrl: './view/analysis/analysisBusiness.html',
        controller: 'analysisBusinessCtrl',
        permission: 'analysis'
    }).state('analysis.busTime', {     //业务能力分析
        url: "/busTime",
        templateUrl: './view/analysis/analysisBusTime.html',
        controller: 'analysisBusTimeCtrl',
        permission: 'analysis'
    }).state('analysis.dataApplication', {     //数据本身应用情况进行分析
        url: "/dataApplication",
        templateUrl: './view/analysis/mulData/analysisDataAppl.html',
        controller: 'analysisDataAppliCtrl',
        permission: 'analysis'
    }).state('sysMng', {
        url: "/sysMngIndex",
        templateUrl: './view/public/common.html',
        permission: 'sysMng'
    }).state('sysMng.userList', {  //用户列表
        url: "/userMng",
        templateUrl: './view/sysMng/userList.html',
        controller: 'userCtrl',
        permission: 'userList'
    }).state('sysMng.addUser', {  //增加用户
        url: "/addUser",
        templateUrl: "./view/sysMng/addUser.html",
        controller: 'addUser',
        permission:'userList'
    }).state('sysMng.editUser', {  //编辑用户
        url: "/editUser/:userId/:userName/:userRole/:userEmail/:userDesc/:userCity/:cityType/:phoneNumer",
        templateUrl: "./view/sysMng/editUser.html",
        controller: 'editUser',
        permission:'userList'
    }).state('sysMng.roleList', { // 角色列表
        url: "/roleList",
        templateUrl: './view/sysMng/roleList.html',
        controller: 'roleListCtr',
        permission:'roleList'
    }).state('sysMng.addRole', {   //增加角色
        url: "/roleAdd",
        templateUrl: './view/sysMng/addRole.html',
        controller: 'roleAddCtrl',
        permission:'roleList'
    }).state('sysMng.editRole', {   //编辑角色
        url: "/roleEdit/:roleId/:roleName",
        templateUrl: './view/sysMng/editRole.html',
        controller: 'roleEditCtrl',
        permission:'roleList'
    }).state('sysMng.roleAllot', {  //角色分配
        url: "/showUsers",
        templateUrl: "./view/sysMng/userShow.html",
        controller: 'roleAllotCtrl',
        permission:'roleAllot'
    }).state('sysMng.roleAllotPage', {  //角色分配页面
        url: "/roleAllotPage/:userId/:userRole/:userCity/:cityType",
        templateUrl: "./view/sysMng/roleAllot.html",
        controller: 'roleAllotPageCtrl',
        permission:'roleAllot'
    }).state('sysMng.logList', {    //展示日志
        url: "/logList",
        templateUrl: './view/sysMng/logList.html',
        controller: 'logListCtrl',
        permission:'logList'
    }).state('sysMng.logAnalysis', {    //日志分析
        url: "/logAnalysis",
        templateUrl: './view/sysMng/logAnalysis.html',
        controller: 'logAnalysisCtrl',
        permission:'logList'
    }).state('sysMng.baseDateList', {  //展示区域基础数据
        url: "/baseDateList",
        templateUrl: './view/sysMng/baseDataList.html',
        controller: 'baseDataListCtrl',
        permission:'baseDateList'
    }).state('sysMng.planList', {  //方案展示
        url: "/planList",
        templateUrl: './view/sysMng/planList.html',
        controller: 'planListCtrl',
        permission:'planList'
    }).state('sysMng.addPlan', {  //增加方案
        url: "/addPlan",
        templateUrl: './view/sysMng/addPlan.html',
        controller: 'addPlanCtrl',
        permission:'planList'
    }).state('sysMng.showPlan', {  //显示方案具体内容
        url: "/showPlan/:planId",
        templateUrl: './view/sysMng/showPlan.html',
        controller: 'showPlanCtrl',
        permission:'planList'
    }).state('sysMng.messMonitor', {  //显示监控消息主页
        url: "/messageMonitor",
        templateUrl: './view/sysMng/messMonitor.html',
        controller: 'MessMonitorCtrl',
        permission:'messMonitor'
    }).state('sysMng.securityAudit', {  //进行消息监控设置的安全审计
        url: "/securityAudit",
        templateUrl: './view/sysMng/securityAudit.html',
        controller: 'securityAuditCtrl',
        permission:'securityAudit'
    }).state('sysMng.file', {  //文件列表
        url: "/fileList",
        templateUrl: './view/sysMng/fileList.html',
        controller: 'fileCtrl',
        permission:'file'
    }).state('sysMng.editFileType', {  //跳转文件类型编辑界面
        url: "/editFileType",
        templateUrl: './view/sysMng/editFileType.html',
        controller: 'editFileTypeCtrl',
        permission:'file'
    }).state('sysMng.addArea', {  //跳转基础数据增加区域页面
        url: "/addArea",
        templateUrl: './view/sysMng/addArea.html',
        controller: 'addAreaCtrl',
        permission:'baseDateList'
    }).state('sysMng.monitor', {  //系统监控界面
        url: "/monitor",
        templateUrl: './view/sysMng/monitor.html',
        controller: 'monitorCtrl',
        permission:'sysMonitor'
    }).state('sysMng.userOnline', {  //展示在线用户界面
        url: "/userOnline",
        templateUrl: './view/sysMng/userOnline.html',
        controller: 'userOnlineCtrl',
        permission:'sysMonitor'
    }).state('sysMng.threadNum', {  //展示线程界面
        url: "/threadNum",
        templateUrl: './view/sysMng/threadNum.html',
        controller: 'threadNumCtrl',
        permission:'sysMonitor'
    }).state('sysMng.countChase', {  //展示线程界面
        url: "/countChase",
        templateUrl: './view/sysMng/countChase.html',
        controller: 'countChaseCtrl',
        permission:'countChase'
    }).state('sysError', {  //默认错误页
        url: "/sysError",
        templateUrl: './view/public/error.html',
        permission:'sysError'
    }).state('dataIndex', {
        url: "/dataIndex",
        templateUrl: './view/public/common.html',
        permission: 'dataIndex'
    }).state('dataIndex.pretreatment', {  //数据预处理
        url: "/pretreatment",
        templateUrl: './view/dataIndex/preprocessing/pretreatment.html',
        controller: 'dataIndexPretCtrl',
        permission: 'pretreatment'
    }).state('dataIndex.showRule', {  //数据预处理
        url: "/showRule/:ruleName/:ruleContent",
        templateUrl: './view/dataIndex/preprocessing/showRule.html',
        controller: 'showRuleCtrl',
        permission: 'pretreatment'
    }).state('dataIndex.addPretreatment', {  //增加数据预处理
        url: "/addPretreatment",
        templateUrl: './view/dataIndex/preprocessing/addPretreatment.html',
        controller: 'addPretreatmentCtrl',
        permission: 'pretreatment'
    }).state('dataIndex.group', {  //分组管理
        url: "/group",
        templateUrl: './view/dataIndex/preprocessing/group.html',
        controller: 'groupCtrl',
        permission: 'pretreatment'
    }).state('dataIndex.theme', {  //展示主题库
        url: "/theme",
        templateUrl: './view/dataIndex/theme/theme.html',
        controller: 'themeCtrl',
        permission: 'theme'
    }).state('dataIndex.showTheme', {  //展示主题
        url: "/showTheme/:themeId",
        templateUrl: './view/dataIndex/theme/showTheme.html',
        controller: 'showThemeCtrl',
        permission: 'theme'
    }).state('dataIndex.showThemeContent', {  //展示主题内容
        url: "/showThemeContent/:themeName/:themeContent/:themeId",
        templateUrl: './view/dataIndex/theme/showThemeContent.html',
        controller: 'showThemeContentCtrl',
        permission: 'theme'
    })/*.state('dataIndex.DyConf', {  //指标动态配置
        url: "/DyConfRates",
        templateUrl: './view/dataIndex/DyConf/DyConfRates.html',
        controller: 'DyConfRatesCtrl',
        permission: 'DyConf'
    })*/.state('dataIndex.DyConf', {  //指标对应的规则界面
        url: "/indexRules",
        templateUrl: './view/dataIndex/DyConf/indexRules.html',
        controller: 'indexRulesCtrl',
        permission: 'DyConf'
    }).state('dataIndex.calFormula', {  //指标规则公式编辑页面
        url: "/CalFormula/:ruleId",
        templateUrl: './view/dataIndex/DyConf/CalFormula.html',
        controller: 'calFormulaCtrl',
        permission: 'DyConf'
    }).state('sysMng.excelTmp', {  //excel 模板列表
        url: "/excelMng",
        templateUrl: './view/sysMng/excelTmp/excelList.html',
        controller: 'excelTmpCtrl',
        permission: 'excelTmp'
    }).state('sysMng.excelGroup', {  //excel 分组列表
        url: "/excelGroup",
        templateUrl: './view/sysMng/excelTmp/excelGroup.html',
        controller: 'excelGroupCtrl',
        permission: 'excelTmp'
    });
}]);

/**
 * @description 设置当前http请求拦截
 */
app.factory('httpInterceptor', ['$q', '$injector', function ($q, $injector) {
    var httpInterceptor = {
        'responseError': function (response) {
            // 若身份验证失败，则刷新页面
            if (response.status == 401) {
                document.location.href = document.location.origin + document.location.pathname;
            }
        },
        'response': function (response) {
            return response;
        }
    };
    return httpInterceptor;
}]);

/**
 * @description 添加一个拦截器，用于处理ajax的请求认证失效
 */
app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]);

/**
 * @description 设置页面跳转刷新左侧菜单栏
 * 增加url跳转权限设置   l13595 2017/3/3 15:39
 *   1.从后台获取当前用户的所有权限
 *   2.url跳转时拦截连接，进行对比判断，并作出处理
 */
app.run(['$rootScope', '$log', function ($rootScope) {
    var permissionList='';      //用户权限列表
    $.ajax({
        type : "post",
        url:'sysMng/permissionsList',
        async : false,
        success : function(data){
            permissionList = data.list;
        }
    });
    $rootScope.$on('$stateChangeSuccess',function (event, toState, fromState) {
        var flag=false;
        if(toState.permission=='permissionWel'){
            flag=true;
        }else{
            for(var i=0;i<permissionList.length;i++){
                if(toState.permission==permissionList[i].permission || 'sysError'==toState.permission){
                    flag=true;
                    break;
                }
            }
        }
        if(!flag){
            $rootScope.$broadcast('to-menu', {state:'sysError' });
        }else{
            if (!_.isEmpty(toState.name) && toState.name.indexOf('.') == -1) {
                $rootScope.$broadcast('to-menu', {state: toState.name});
            }
        }
        sessionStorage.url = toState.name;
    });
}]);