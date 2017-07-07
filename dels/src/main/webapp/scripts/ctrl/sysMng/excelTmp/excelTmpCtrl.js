/**
 * @ngdoc controller
 * @name app.controller.fileCtrl
 * @author l13608
 * @description 文件管理控制器
 * @date 2017/3/24 14:55
 */

app.controller('excelTmpCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {

    $scope.desc = 0;
    $scope.col = '';
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };


    $scope.ifAdmin=false;
    var roleid = commonService.getroleid();
    if(roleid=='1'){
        $scope.ifAdmin=true;
    }

    $scope.queryFileType = function () {
        commonService.queryDataByP('sysMng/excelGroup', {
        }).then(function (data) {
            $scope.typeList=data;
            $scope.groupList=data;
        }, function () {
            toastr.error("获取文件类型失败，请联系管理员");
        });
    };
    $scope.queryFileType();

    /**
     * @ngdoc controller
     * @description 拿到所有文件信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('sysMng/excelTmp', {
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

    /**
     * @ngdoc controller
     * @description 通过id下载文件
     * @author l13608
     * @date 2017-2-16
     */
    $scope.downloadFile = function (fileId) {
        commonService.queryDataByP('sysMng/ifDownloadExcelFile',{
            id:fileId
        }).then(function(data){
            if(null != data && data.resultMsg == "ok"){
                window.location.href = 'sysMng/downloadExcel?id=' + fileId;
            }else{
                toastr.error('模板文件不存在');
            }
        },function(){
            toastr.error("返回信息失败，请联系管理员");
        });


    };

    /**
     * @description 点击删除按钮的时候拿到要删除文件的id和文件名
     * @author l13608
     * @date 2017-2-20
     */
    $scope.showDeleteFileModel = function (fileId,fileName) {
        $scope.delFileName = fileName;
        $scope.fileId = fileId;
    };

    /**
     * @description 通过id删除文件
     * @author l13608
     * @date 2017-2-16
     */
    $scope.delFileFun = function () {
        commonService.queryDataByP('sysMng/deleteExcelTmp', {
            id:$scope.fileId
        }).then(function (data) {
            $("#delFileModal").modal('hide');
            $scope.queryData();
            if(data.resultMsg=='ok'){
                toastr.success('删除成功');
            }else{
                toastr.error(data.resultMsg);
            }
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };

    $scope.uploadExcelTmp = function(id, name){
        $scope.uploadTmpId = id;
        $scope.tmplateName = name;
    }
    /**
     * @author l13608
     * @description 通过form上传文件提交
     * @date 2017/3/24 14:57
     */
    $scope.submit = function() {
        var delFile;
        if ($scope.form.file.$valid && $scope.file) {
            var fileName = $scope.file.name;
            var ext=fileName.substr(fileName.lastIndexOf('.')+1);
            var fileSize = $scope.file.size;
            if(fileSize <= 10485760 &&(ext=='xls'||ext=='xlsx') && fileName == $scope.tmplateName){
                delFile = $scope.file;
                $scope.upload(delFile);
            }else if(fileSize > 10485760){
                toastr.error('文件大小超出10M');
                $scope.file = null;
            }else if(fileName != $scope.tmplateName){
                toastr.error('上传模板名称不对');
                $scope.file = null;
            }
        }
    };

    /**
     * @author l13608
     * @description 关闭上传窗口
     * @date 2017/4/26 11:00
    */
    $scope.closeUpFileModal = function () {
        $('#excelUploadForm').resetForm();
        $("#uploadFileModal").modal('hide');
    };
     /**
      * @description: 报表模板文件上传
      * @author: l13595
      * @time: 2017/4/25 9:51
      */
    $scope.submit2 = function() {
        var options = {
            url: 'sysMng/uploadExcel?t=' + Math.random(),
            success: function (data) {
                if(data=='ok'){
                    toastr.success('上传成功');
                    $scope.queryData();
                    $("#uploadFileModal").modal('hide');
                }else{
                    toastr.error(data);
                }
            },
            resetForm: true
        };
        $("#excelUploadForm").ajaxSubmit(options);
    };

    /**
     * @author l13608
     * @description 上传文件方法
     * @date 2017/3/24 14:58
     */
    $scope.upload = function (file) {
        var excelContent = $scope.excelContent;
        var uploadTmpId = $scope.uploadTmpId;
        if(null == excelContent){
            excelContent = '';
        }
        var fileName = $scope.file.name;
        Upload.upload({
            url: 'sysMng/uploadExcel?content='+excelContent+'&fileName='+fileName+'&uploadTmpId='+uploadTmpId,
            data: {file: file}
        }).then(function (resp) {
            if(resp.data.resultMsg=='ok'){
                toastr.success('上传成功');
                $scope.queryData();
                $('#uploadFileModal').modal('hide');
                $scope.file = null;
            }else{
                toastr.success(resp.data.resultMsg);
            }
        }, function (resp) {
            toastr.error("上传失败");
        });
    };

    /*
     *@author l13608
     *@description 跳转编辑文件类型
     *@param
     *@date 11:05 2017/2/28
     */

    $scope.fileTypeEdit = function () {
        $state.go('sysMng.excelGroup');
    }

    $scope.getExcelInfo = function(id, content, groupid){
        $scope.tmplateId = id;
        $scope.excelTmpContent = content;
        $scope.excelGroup = groupid;
    }
    $scope.saveEditExcel = function(){
        commonService.queryDataByP('sysMng/updateExcelTmp', {
            id:$scope.tmplateId,
            content:$scope.excelTmpContent,
            groupId:$scope.excelGroup
        }).then(function (data) {
            $scope.queryData();
            $('#editContentModal').modal('hide');
            if(null != data && data.resultMsg == 'ok'){
                toastr.success('修改成功');
            }else{
                toastr.error(data.resultMsg);
            }
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    }


    $scope.showExcel = function(groupname, content){
        $scope.groupShow = groupname;
        $scope.contentShow = content;
    }



}]);




app.controller('excelGroupCtrl', ['$scope', '$state', 'commonService','toastr', function ($scope, $state, commonService,toastr) {
    $scope.desc = 0;
    $scope.col = '';
    

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @ngdoc controller
     * @description 拿到所有文件信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('sysMng/excelGroup', {

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


    /**
     * @description: 获取删除记录的id
     * @author: l13595
     * @time: 2017/3/29 14:56
     */
    $scope.getDelFType = function(id, name){
        $scope.groupid = id;
        $scope.delGroupName = name;
    };
    /**
     * @description 通过id删除文件
     * @author l13608
     * @date 2017-2-16
     */
    $scope.deleteExcelGroup = function () {
        commonService.queryDataByP('sysMng/deleteExcelGroup', {
            id:$scope.groupid
        }).then(function (data) {
            $scope.queryData();
            $("#delGroupModel").modal('hide');
            if(data.resultMsg=='ok'){
                toastr.success('删除成功');
            }else{
                toastr.error(data.resultMsg);
            }
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };


    /**
     * @description: 获取编辑分组的信息
     * @author: l13595
     * @time: 2017/3/29 15:02
     */
    $scope.getEditFType = function(id, name){
        $scope.editId = id;
        $scope.editName = name;
    };
    /**
     * @description: 保存编辑的信息
     * @author: l13595
     * @time: 2017/3/29 15:03
     */
    $scope.saveEdit = function(){
        commonService.queryDataByP('sysMng/saveExcelGroup', {
            groupId:$scope.editId,
            groupName:$scope.editName
        }).then(function (data) {
            $("#editTypeModal").modal('hide');
            $scope.queryData();
            if(data.resultMsg=='ok'){
                toastr.success('保存成功');
            }else{
                toastr.error(data.resultMsg);
            }
            $scope.editName = null;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    /**
     * @description: 保存新增的分组
     * @author: l13595
     * @time: 2017/3/29 14:53
     */
    $scope.addFType = function () {
        commonService.queryDataByP('sysMng/saveExcelGroup', {
            groupName:$scope.addFTypeName
        }).then(function (data) {
            $("#addFType").modal('hide');
            $scope.queryData();
            if(data.resultMsg=='ok'){
                toastr.success('保存成功');
            }else{
                toastr.error(data.resultMsg);
            }
            $scope.addFTypeName = null;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };

    $scope.goExcelTmpList = function(){
        $state.go('sysMng.excelTmp');
    };

}]);