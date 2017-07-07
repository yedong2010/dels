/*
 *@author l13608
 *@description 数据质量反馈的控制器
 *@date 14:12 2017/3/13
*/
app.controller('qualityFeedbackCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 跳转到增加质量反馈界面
     *@date 14:15 2017/3/13
    */
     $scope.addFeedBack = function () {
         $state.go('quality.addFeedback');
     };

     /*
      *@author l13608
      *@description 跳转查看反馈具体内容界面
      *@param item对应的反馈内容对象
      *@date 16:13 2017/3/13
     */
      $scope.showFeedback = function (item) {
        $state.go('quality.showFeedback',{
            feedbackId:item.id
        });
      };

    /*
     *@author l13608
     *@description 跳转编辑反馈具体内容界面
     *@param item对应的反馈内容对象
     *@date 16:13 2017/3/13
     */
    $scope.editFeedback = function (item) {
        $state.go('quality.editFeedback',{
            feedbackId:item.id
        });
    };

    /**
     * @description 拿到所有的反馈问题
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/feedList', {
        }).then(function (data) {
            vm.items = data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();
}]);

/*
 *@author l13608
 *@description 增加数据质量反馈的控制器
 *@date 14:12 2017/3/13
 */
app.controller('qualityAddFeedbackCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 跳转到增加质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.feedback');
    };
    $scope.addFeedbackBack = function () {
        $state.go('quality.feedback');
    };

    /**
     * @description 拿到所有的反馈问题
     */
    $scope.addFeedbackSub = function () {
        commonService.queryDataByP('qualityCheck/addFeedInfo', {
            title:$scope.feedbackName,//增加反馈的名称
            question:$scope.feedbackDes//增加反馈的描述
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('增加反馈成功');
            $state.go('quality.feedback');
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
}]);

/*
 *@author l13608
 *@description 展示反馈内容控制器
 *@date 16:16 2017/3/13
*/
app.controller('qualityShowFeedbackCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 跳转到质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.feedback');
    };

    /*
     *@author l13608
     *@description 拿到反馈的名称和描述
     *@date 16:39 2017/3/13
    */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/feedList', {
            id:$stateParams.feedbackId //反馈的id
        }).then(function (data) {
            $scope.feedbackTitle = data[0].title;
            $scope.feedbackContent = data[0].question;
            $scope.correctUser = data[0].resolveUser;
            $scope.correctContent = data[0].resultMsg;
            $scope.editUser = data[0].editUser;

        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();
}]);

/*
 *@author l13608
 *@description 编辑反馈内容控制器
 *@date 16:16 2017/3/13
 */
app.controller('qualityEditFeedbackCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };
    /*
     *@author l13608
     *@description 跳转到质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.feedback');
    };

    /*
     *@author l13608
     *@description 根据反馈的id拿到反馈的具体内容
     *@date 16:39 2017/3/13
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/feedList', {
            id:$stateParams.feedbackId //反馈的id
        }).then(function (data) {
            $scope.feedbackName = data[0].title;
            $scope.feedbackDes = data[0].question;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();

    $scope.editFeedbackSub = function () {
        commonService.queryDataByP('qualityCheck/updateQualityCheck', {
            id:$stateParams.feedbackId ,//反馈的id
            title:$scope.feedbackName,
            question:$scope.feedbackDes,
            state:'feed'
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('编辑反馈成功');
            $state.go('quality.feedback');
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    }
}]);
/*
 *@author l13608
 *@description 数据质量申诉的控制器
 *@date 14:12 2017/3/13
 */
app.controller('qualityRepreCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 跳转到申诉质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.feedbackDecision = function (id) {
        $state.go('quality.decision',{
            feedbackId:id
        });
    };

    /**
     * @description 拿到所有的反馈问题
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/repreList', {
        }).then(function (data) {
            vm.items = data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();
}]);

/*
 *@author l13608
 *@description 数据质量修正的控制器
 *@date 14:12 2017/3/13
 */
app.controller('qualityDecisionCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /*
     *@author l13608
     *@description 根据反馈的id拿到反馈的具体内容
     *@date 16:39 2017/3/13
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/repreList', {
            id:$stateParams.feedbackId //反馈的id
        }).then(function (data) {
            $scope.feedbackTitle = data[0].title;
            $scope.feedbackContent = data[0].question;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    $scope.queryData();
    /*
     *@author l13608
     *@description 跳转到质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.repre');
    };

    /*
     *@author l13608
     *@description 把反馈提交给申诉，反馈状态变为待修正
     *@date 18:29 2017/3/13
    */
     $scope.appeal = function () {
         commonService.queryDataByP('qualityCheck/updateQualityCheck', {
             id:$stateParams.feedbackId,
              state:'wait'//反馈的id
         }).then(function (data) {
             if(null!=data && data.resultMsg!='ok'){
                 toastr.error(data.resultMsg);
                 return;
             }
             toastr.success('申述成功，提交修正');
             $state.go('quality.repre');
         }, function () {
             toastr.error('返回信息失败，请联系管理员');
         });
     };

     /*
      *@author l13608
      *@description 驳回操作
      *@date 19:00 2017/3/13
     */
      $scope.reject = function () {
          commonService.queryDataByP('qualityCheck/updateQualityCheck', {
              id:$stateParams.feedbackId, //反馈的id
              state:'reject'
          }).then(function (data) {
              if(null!=data && data.resultMsg!='ok'){
                  toastr.error(data.resultMsg);
                  return;
              }
              toastr.success('驳回成功');
              $state.go('quality.repre');
          }, function () {
              toastr.error('返回信息失败，请联系管理员');
          });
      };

}]);
/*
 *@author l13608
 *@description 数据质量修正的控制器
 *@date 14:12 2017/3/13
 */
app.controller('qualityCorrectCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /*
     *@author l13608
     *@description 跳转到增加质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.correct');
    };

    /*
     *@author l13608
     *@description 跳转到申诉质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.feedbackDecision = function (id) {
        $state.go('quality.correctById',{
            feedbackId:id
        });
    };


    /**
     * @description 拿到所有的反馈状态为待修正的反馈信息
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/correctList', {
        }).then(function (data) {
            vm.items = data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();
}]);

/*
 *@author l13608
 *@description 根据数据质量Id修正的控制器
 *@date 14:12 2017/3/13
 */
app.controller('qualityCorrectByIdCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /*
     *@author l13608
     *@description 根据反馈的id拿到反馈的具体内容
     *@date 16:39 2017/3/13
     */
    $scope.queryData = function () {
        commonService.queryDataByP('qualityCheck/correctList', {
            id:$stateParams.feedbackId //反馈的id
        }).then(function (data) {
            $scope.feedbackTitle = data[0].title;
            $scope.feedbackContent = data[0].question;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();
    /*
     *@author l13608
     *@description 跳转到质量反馈界面
     *@date 14:15 2017/3/13
     */
    $scope.goFeedback = function () {
        $state.go('quality.correct');
    };

    /*
     *@author l13608
     *@description 把反馈提交给申诉，反馈状态变为待修正
     *@date 18:29 2017/3/13
     */
    $scope.appeal = function () {
            commonService.queryDataByP('qualityCheck/updateQualityCheck', {
                id:$stateParams.feedbackId, //反馈的id
                resultMsg:$scope.correctDes,
                state:'corrected'
            }).then(function (data) {
                if(null!=data && data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                    return;
                }
                toastr.success('修正成功');
                $state.go('quality.correct');
            }, function () {
                toastr.error('返回信息失败，请联系管理员');
            });
        };
}]);