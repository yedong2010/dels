/**
 * @ngdoc controller
 * @name app.controller.editFileTypeCtrl
 * @author l13608
 * @description 编辑文件类型控制器
 * @date 2017/3/24 14:53
*/
app.controller('editFileTypeCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 14:54
    */

    $scope.desc = 0;
    $scope.col = '';

    var addFTypeModal = $('#addFType');
    var delFTypeModal = $('#delFileTypeModal');
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @ngdoc controller
     * @description 拿到所有文件类型信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        // 拿到所有文件类型
        commonService.queryDataByP('sysMng/fileTypeList', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    /*
     *@author l13608
     *@description增加文件类型
     *@date 17:20 2017/2/28
    */
     $scope.addFType = function () {
         // 增加文件类型
         commonService.queryDataByP('sysMng/addFileType', {
             fileType:$scope.addFTypeName
         }).then(function (data) {
             if(data.resultMsg=='ok'){
                 toastr.success('增加文件类型成功');
                 $scope.queryData();
                 addFTypeModal.modal('hide');
                 $scope.addFTypeName = '';
             }else{
                 toastr.success(data.resultMsg);
             }
         }, function () {
             toastr.error("增加文件类型失败，请联系管理员");
         });
     };

     /*
      *@author l13608
      *@description 拿到要删除文件类型的id和类型名
      *@param typeId:要删除文件类型的id，typeName要删除的文件类型名
      *@date 17:34 2017/2/28
     */
    $scope.getDelFType = function (typeId,typeName) {
        $scope.DelFTypeId = typeId;
        $scope.DelFTypeName = typeName;
    };
    /**
     * @description: 获取要编辑的类型信息
     * @author: l13595
     * @param typeId: 类型id
     * @param typeName: 类型名称
     * @time: 2017/3/18 15:30
     */
    $scope.getEditFType = function (typeId,typeName) {
        $scope.EditFTypeId = typeId;
        $scope.editTypeName = typeName;
    };
    /*
     *@author l13608
     *@description
     *@param delFTypeId:要删除的文件类型id
     *@date 17:37 2017/2/28
    */
    $scope.delFType = function () {
        // 删除文件类型
        commonService.queryDataByP('sysMng/delFielType', {
            id:$scope.DelFTypeId
        }).then(function (data) {
            if(data.resultMsg=='ok'){
                delFTypeModal.modal('hide');
                $scope.queryData();
                toastr.success('删除文件类型成功');
            }else{
                toastr.error(data.resultMsg);
            }
            $scope.queryData();
        }, function () {
            toastr.error("删除文件类型失败，请联系管理员");
        });
    };

    /**
     * @author l13608
     * @description 编辑文件类型
     * @date 2017/3/24 14:54
    */

    $scope.editType = function () {
        commonService.queryDataByP('sysMng/editFileType', {
            typeId:$scope.EditFTypeId,
            fileType:$scope.editTypeName
        }).then(function (data) {
            if(data.resultMsg=='ok'){
                $('#editTypeModal').modal('hide');
                $scope.queryData();
                toastr.success('编辑文件类型成功');
            }else{
                toastr.error(data.resultMsg);
            }
            $scope.queryData();
        }, function () {
            toastr.error("提交失败，请联系管理员");
        });
    };
    /*
     *@author l13608
     *@description 返回文件列表界面
     *@date 19:21 2017/2/28
    */
     $scope.goFileList = function () {
         $state.go('sysMng.file');
     }
}]);

