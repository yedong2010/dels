<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String error = (String)session.getAttribute("message");
    String phoneNum = (String)session.getAttribute("phoneNum");
    String sendMsg = (String) session.getAttribute("sendMsg");

    if(null == phoneNum){
        phoneNum = "";
    }

    if(null != sendMsg && sendMsg.equals("1")){
        session.setAttribute("sendMsg", null);
    }

    session.setAttribute("message", null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>广东省网上办事大厅数据监测系统</title>
    <link rel="shortcut icon" href="<%=path%>/assets/img/favicon.ico"/>
    <link rel="bookmark" href="<%=path%>/assets/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=path%>/assets/js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/assets/css/font-awesome/css/font-awesome.min.css"/>
    <script type="text/javascript" src="<%=path%>/assets/js/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=path%>/assets/js/jQuery-MD5/jquery.md5.js"></script>
</head>
<style>
    html{
        width: 100%;
        height: 100%;}
    body {
        font-family: 'Microsoft YaHei', sans-serif;
    }

    .login-body {
        width: 100%;
        height: 100%;
        color: #fff;
        overflow: hidden;
        background: url("../assets/img/loginbg.jpg") no-repeat left;
        background-size: 100% auto;
    }

    .login-modern {
        border: 1px solid transparent;
        border-radius: 10px;
        background: rgba(0, 0, 0, .6);
    }

    .login {
        padding: 50px 50px 50px 50px;
        position: relative;
        overflow: hidden;
    }

    .login .btn {
        width: 300px;
        font-weight: 700;
        letter-spacing: 2px;
    }

    .login a {
        color: #FFCC00;
    }

    .cover-container {
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -132px auto 0 -200px;
        height: 265px;
        width: 400px;
    }
</style>

<body class="login-body" onload="loadaction()">
<div class="cover-container">
    <div class="login login-modern">

        <% if (error != null && error.length() > 0){%>
            <div class="text-center">
                <div class="well" >
                    <span style="color: red" ><%= error%></span>
                </div>
            </div>
        <%}%>

        <form id="loginForm" accept-charset="UTF-8" role="form" action="<%=path%>/j_spring_security_check" method="post">

            <input type="hidden" id="sendflag" value="<%=sendMsg%>" title="短信是否发送标识">
            <input type="hidden" id="phonenumber" value="<%=phoneNum%>" title="手机号缓存" >

            <!--账号登录-->
            <div class="pass-login" >
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><i class="fa fa-user"></i></div>
                        <input class="form-control" type="text" placeholder="用户名" name="uname" value=""  id="uname" required autofocus="autofocus">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><i class="fa fa-lock"></i></div>
                        <input type="hidden" id="passwd" name="passwd" title="加密后密码" />
                        <input class="form-control" type="password"  autocomplete="off" placeholder="密码" id="passwds"  required autofocus="autofocus" onchange="userLogin();" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><i class="fa fa-picture-o"></i></div>
                        <input class="form-control" type="text" placeholder="验证码" name="randomCode" id="randomCode"  autofocus="autofocus">
                    </div>
                </div>
                <div class="form-group;">
                    <div class="input-group">
                        <img id="vimg"  title="点击更换" onclick="changeCode();" src="<%=path%>/home/verificationImg" style="width: 200px;height: 34px;display: inline">
                        <p style="color: white;display: inline;cursor: pointer" onclick="changeCode();">看不清,换一张</p>
                    </div>
                </div>

            </div>

            <!--短信登录-->
            <div class="sms-login" style="display: none" disabled="disabled">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><i class="fa fa-phone-square"></i></div>
                        <input class="form-control" type="text" placeholder="手机号" disabled="disabled" id="phonenum" value="<%=phoneNum%>" name="phonenum" required autofocus="autofocus">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-commenting"></i>
                        </div>
                        <input class="form-control" type="text" id="onetimepd" name="onetimepd" style="width: 130px;" placeholder="动态口令"  disabled="disabled"   autofocus="autofocus"/>
                        <span class="input-group-btn">
                        <button class="btn btn-default" style="width: 120px;" id="btnonetimepd" disabled="disabled"  onclick="sendOneTimePasswd()">发送动态密码</button>
                    </span>
                    </div>
                </div>
            </div>
            <div class="checkbox">
                <%--<label>
                    <input type="checkbox">记住密码
                </label>
                <label style="float: right;"><a href="#">忘了密码</a></label>--%>
            </div>
            <button type="submit" class="btn btn-info">登 录</button>


            <div class="switchLogin">
                <p id="switchSms" style="float: right;cursor: pointer" onclick="switchPhone()">短信快捷登录</p>
                <p id="switchUser" style="float: right;cursor: pointer" onclick="switchUserFun()">账号密码登录</p>
                <%--<i class="fa fa-phone-square" style="float: right"></i>--%>
            </div>
        </form>
    </div>
</div>
<script>

    //登录加载函数
    function loadaction(){

        var sendflag = $('#sendflag').val();
        var phonenumber = $('#phonenumber').val();

        if(phonenumber == ''){
            switchUserFun();
        }else{
            switchPhone();
            if(sendflag == '1'){
                timeAction(document.getElementById('btnonetimepd'));
            }

        }

    }


    /**
     * @author l13608
     * @description 登录加密验证
     * @date 2017/4/25 16:47
     */
    function userLogin() {
        //md5加密传输
        var salt="user";
        var pwd= $('#passwds').val();
        var md5Pwd1=$.md5(pwd);
        var md5Pwd2=$.md5(md5Pwd1+salt);
        $("#passwd").val(md5Pwd2);
    }

     /**
      * @description: 点击更换验证码图片
      * @author: l13595
      * @time: 2017/4/19 17:27
      */
    function  changeCode() {
        var imgNode = document.getElementById("vimg");
        imgNode.src = "<%=path%>/home/verificationImg?t=" + Math.random();
    }

    function  send2() {
        var phoneNum = $("#phonenum").val();
        if(null == phoneNum){
            alert("请输入手机号");
        }
        var inputbutton = document.getElementById("btnonetimepd");
        inputbutton.src="<%=path%>/home/oneTimePasswd?phoneNumber="+phoneNum+"&t=" + Math.random();
    }

     /**
      * @description: 访问后台，发送动态口令
      * @author: l13595
      * @time: 2017/5/25 17:05
      */
     var waitTime = 60;
     function timeAction(o) {

         if (waitTime == 0) {
             o.removeAttribute("disabled");
             o.innerHTML = "发送动态密码";
             waitTime = 60;
         } else {
             o.setAttribute("disabled", true);
             o.innerHTML = waitTime + "秒后重发";
             waitTime--;
             setTimeout(function() {
                 timeAction(o)
             }, 1000)
         }
     };
    function sendOneTimePasswd(){
        var phoneNum = $("#phonenum").val();
        var  wait = 60;

        $.ajax({
            url:"<%=path%>/home/oneTimePasswd?phoneNumber="+phoneNum+"&t=" + Math.random(),
            type: "POST"
        });

    }

    /**
     *@author m13624
     *@description 获取当前用户的浏览器版本和屏幕分辨率,提醒用户
     *@date 14:58 2017/3/14
     */
    function appInfo(){
        var browser = {appname: 'unknown', version: 0},
                userAgent = window.navigator.userAgent.toLowerCase();
        var screen = {width: window.screen.width, height: window.screen.height};
        //IE,firefox,opera,chrome,netscape
        if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){
            browser.appname = RegExp.$1;
            browser.version = RegExp.$2;
        } else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari
            browser.appname = 'safari';
            browser.version = RegExp.$2;
        }
        return {browser: browser, screen: screen};
    }
    var browserInfo = appInfo();
    if( browserInfo.browser.appname == 'msie'){
        browserInfo.browser.version = parseInt(browserInfo.browser.version);
        if(browserInfo.browser.version >= 6 && browserInfo.browser.version <= 8){
            alert('您的IE浏览器版本过低,请更新浏览器至IE9以上，或者使用谷歌、火狐等浏览器');
            window.history.back(-1);
        }

    }else if( browserInfo.screen.width < 1360 || browserInfo.screen.height < 700){
        alert('您的电脑屏幕分辨率，可能会影响视觉体验，请尽量使用分辨率1366*768以上的显示器，或者降低浏览器的显示百分比');
    }

    /**
     * @author l13608
     * @description 切换到手机短信登录
     * @date 2017/5/25 15:09
    */
    function switchPhone() {
        $('.pass-login').css('display','none');
        $('.sms-login').css('display','block');
        $('#switchSms').css('display','none');
        $('#switchUser').css('display','block');

        $("#phonenum").attr("disabled", false);
        $("#onetimepd").attr("disabled", false);
        $("#btnonetimepd").attr("disabled", false);

        $("#uname").attr("disabled", true);
        $("#passwds").attr("disabled", true);
        $("#randomCode").attr("disabled", true);


    }

    /**
     * @author l13608
     * @description 切换到密码登录
     * @date 2017/5/25 15:09
     */
    function switchUserFun() {
        $('.pass-login').css('display','block');
        $('.sms-login').css('display','none');
        $('#switchSms').css('display','block');
        $('#switchUser').css('display','none');

        $("#phonenum").attr("disabled", true);
        $("#onetimepd").attr("disabled", true);
        $("#btnonetimepd").attr("disabled", true);
//        $('#errordiv').style.display='none';

        $("#uname").attr("disabled", false);
        $("#passwds").attr("disabled", false);
        $("#passwd").attr("disabled", false);
        $("#randomCode").attr("disabled", false);
    }
    /**
     * @author l13608
     * @description 点击enter键登录
     * @date 2017/6/1 16:04
    */
    $(document).keypress(function(e) {
        if (e.charCode == 13){
            $("#loginForm").submit();
        }
    });
</script>
</body>
</html>