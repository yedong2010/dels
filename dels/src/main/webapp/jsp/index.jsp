<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html ng-app="dels">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>delete-so-mini</title>
    <link rel="shortcut icon" href="<%=path%>/assets/img/favicon.ico"/>
    <link rel="bookmark" href="<%=path%>/assets/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/css/buttons.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/style.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/bootstrap/css/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/css/angular-toastr.min.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/textAngular/textAngular.css"/>
    <script type="text/javascript" src="<%=path%>/dist/vendor.js"></script>
    <script type="text/javascript" src="<%=path%>/dist/dim.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="site-wrapper" ng-controller="DelsCtrl">
    <div class="site-wrapper-inner">
        <div class="cover-container header-top">
            <div class="masthead" ng-controller="indexCtrl">
                <div >
                    <h2 class="masthead-brand">delete-mini</h2>
                    <nav>
                        <ul class="nav masthead-nav">
                            <li class="active"><a href ui-sref="index">搜索</a></li>
                            <li class="active"><a href ui-sref="message">吐槽</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="cover-container center">
            <div class="inner cover" ui-view>
            </div>
        </div>
        <div class="cover-container">
            <div class="mastfoot">
                <div class="inner">
                    <p>11年入A站，一直都只是看看，很少评论，很是怀恋2年前delete-so，临时起意想要做个简单版的delete-so，所以加了个mini,
                        前后也拖沓了1个多月，当然最近工作也比较忙，很感谢delete-so作者的同意，部分代码参考delete-so的开源代码,
                        大家低调使用, 说不定哪天就被封了。 by May-to-Marry.</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
