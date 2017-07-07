<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html ng-app="dms">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>广东省网上办事大厅数据监测系统</title>
    <link rel="shortcut icon" href="<%=path%>/assets/img/favicon.ico"/>
    <link rel="bookmark" href="<%=path%>/assets/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/css/AdminLTE.min.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/buttons.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/style.min.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/bootstrap/css/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/css/docs.min.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/patch.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/angular-toastr.min.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/textAngular/textAngular.css"/>
    <script type="text/javascript" src="<%=path%>/dist/vendor.min.js"></script>
    <script type="text/javascript" src="<%=path%>/dist/dim.min.js"></script>

    <script type="text/javascript" src="<%=path%>/assets/js/jQuery-MD5/jquery.md5.js"></script>

</head>
<body class="hold-transition skin-blue sidebar-mini">

<div class="wrapper" ng-controller="indexCtrl">
    <header class="main-header">
        <div id="top" style="width: 100%; background-color: #eaeff5; height: 100px; top: 0px; left: 0px; right: 0px; position: relative; text-align: center; background-image: url(assets/img/banner.jpg); background-position: left; background-repeat: no-repeat;"></div>
        <a class="logo">
            <span class="logo-mini">监测</span>
            <span class="logo-lg">网上办事大厅监测</span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">
            <a class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li ui-sref-active="active" ng-repeat="x in parentMenu">
                        <a ui-sref="{{x.url}}">{{x.name}}</a>
                    </li>
                    <li>
                        <!-- Single button -->
                        <div class="btn-group">
                                 <span style="color: #fff;position: relative;display: block;padding: 14px 15px;" class="btn dropdown-toggle" data-toggle="dropdown">
                                     <i class="fa fa-user"></i>{{loginName}}
                                 </span>
                            <ul class="dropdown-menu" role="menu" style="right: 0px;left: auto;min-width:inherit;">
                                <li ng-click="logout()"><a href="">注销</a></li>
                                <li><a style="cursor:pointer" aria-hidden="true" data-toggle="modal" data-target="#userModPassPage">修改密码</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <aside class="main-sidebar">
        <section class="sidebar">
            <ul class="sidebar-menu">
                <li class="header">{{item.title}}</li>
                <li ui-sref-active="active" ng-repeat="m in item">
                    <a ng-if="m.type=='single'" ui-sref="{{m.url}}"><i class="fa {{m.img}}"></i> <span>{{m.name}}</span></a>
                    <a ng-if="m.type=='parent'">
                        <i class="fa {{m.img}}"></i>
                        <span>{{m.name}}</span>
                        <span class="pull-right-container">
                          <i class="fa fa-angle-left pull-right"></i>
                        </span>
                    </a>
                    <ul ng-if="m.type=='parent'" class="treeview-menu">
                        <li ng-repeat="c in m.child">
                            <a ui-sref="{{c.url}}">{{c.name}}</a>
                        </li>
                    </ul>
                </li>
                <li>
                    {{twoLevelMenu}}
                </li>
                <li>
                    {{threeLevelMenu}}
                </li>
            </ul>
        </section>
    </aside>
    <div class="modal fade" id="userModPassPage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h6 class="modal-title" id="myModalLabel">
                        更新密码
                    </h6>
                </div>
                <div class="modal-body">
                    <form role="form" name="modPass" id="modPassForm">
                        <div class="form-group">
                            <label class="control-label">请输入当前登录用户密码</label>
                            <input type="password" autocomplete="off" id="oriPass1" class="form-control" ng-model="oriPass1" required>
                        </div>
                        <div class="form-group">
                            <label class="control-label">输入要修改的新密码</label>
                            <input type="password" autocomplete="off" class="form-control" id="modPass3" name="password1" ng-minlength="8" ng-maxlength="25" ng-pattern="/^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[_\-@&=!#$%^*])[a-zA-Z0-9_\-@&=!#$%^*]+$/" ng-model="modPass3" required>
                            <p ng-show="modPass.password1.$error.minlength && modPass.password1.$dirty" style="color:red" class="help-block">密码最少8位</p>
                            <p ng-show="modPass.password1.$error.pattern && modPass.password1.$dirty" style="color:red" class="help-block">密码要包含字母,数字以及特殊字符_\-@&=!#$%^*</p>
                        </div>
                        <div class="form-group">
                            <label class="control-label">再次输入修改的新密码</label>
                            <input type="password" autocomplete="off" class="form-control" id="modPass4" ng-compare="password1" name="confirmPassword1" ng-model="modPass4" required>
                            <p ng-show="!modPass.confirmPassword1.$error.required && modPass.confirmPassword1.$error.compare && modPass.confirmPassword1.$dirty" style="color:red" class="help-block">重输密码不相同.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" ng-click="modPassById1()" ng-disabled="modPass.$invalid">
                                提交更改
                            </button>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <div class="content-wrapper" ui-view></div>
</div>
<div id="pop" style="display:none;">
    <style type="text/css">
        *{margin:0;padding:0;}
        #pop{background:#fff;width:260px;border:1px solid #e0e0e0;font-size:12px;position: fixed;right:10px;bottom:10px;z-index: 100}
        #popHead{line-height:32px;background:#f6f0f3;border-bottom:1px solid #e0e0e0;position:relative;font-size:12px;padding:0 0 0 10px;}
        #popHead h2{font-size:14px;color:#666;line-height:32px;height:32px;}
        #popHead #popClose{position:absolute;right:10px;top:1px;}
        #popHead a#popClose:hover{color:#f00;cursor:pointer;}
        #popContent{padding:5px 10px;}
        #popTitle a{line-height:24px;font-size:14px;font-family:'微软雅黑';color:#333;font-weight:bold;text-decoration:none;}
        #popTitle a:hover{color:#f60;}
        #popIntro{text-indent:24px;line-height:160%;margin:5px 0;color:#666;}
        #popMore{text-align:right;border-top:1px dotted #ccc;line-height:24px;margin:8px 0 0 0;}
        #popMore a{color:#f60;}
        #popMore a:hover{color:#f00;}
    </style>
    <div id="popHead">
        <a id="popClose" title="关闭">关闭</a>
        <h2>系统消息</h2>
    </div>
    <div id="popContent">
        <dl>
            <dd id="popIntro">这里是内容简介</dd>
        </dl>
        <p ng-if="ifShow" id="popMore"><span style="cursor:pointer"  ui-sref="sysMng.messMonitor"><span style="cursor:pointer"  ui-sref="sysMng">查看 »</span></span></p>
    </div>
</div>
</body>
</html>
