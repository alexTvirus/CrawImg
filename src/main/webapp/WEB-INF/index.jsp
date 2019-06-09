<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lbcong/SaveFileTemp/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lbcong/SaveFileTemp/css/AdminLTE.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lbcong/SaveFileTemp/css/_all-skins.min.css">
    </head>
    <body>
        <noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
            Javascript and reload this page!</h2></noscript>
            <s:url value="getImg" var="getImg"/>
            <s:url value="KeepGoogleLive" var="KeepGoogleLive"/>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Nhập url</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <!--<form role="form">-->
                        <div class="box-body">
                            <div class="form-group">
                                <label for="url">URL</label>
                                <input type="text" class="form-control" id="url" placeholder="Url">
                            </div>
                        </div>
                        <!-- /.box-body -->

                        <div class="box-footer">
                            <button id="start_auto" type="submit" class="btn btn-primary">Submit</button>
                        </div>
                        <!--</form>-->
                    </div>
                </div>
            </div>
            <div id="loader" class="loader"></div>
            <input id="Keep_live" type="button" value="Keep live"/>
        </div>
        
        <div id="img_list">

        </div> 
        <img id="screen_shot"/>
        <div>
            <textarea style="height: 300px;width: 400px" id="error"></textarea>
        </div>
        <style>
            .loader {
                border: 16px solid #f3f3f3; /* Light grey */
                border-top: 16px solid #3498db; /* Blue */
                border-radius: 50%;
                width: 50px;
                height: 50px;
                animation: spin 2s linear infinite;
                display: none;
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        </style>
        <script type="text/javascript">
            var stompClient = null;
            function connect() {
                var url = (window.location.protocol === "https:" ? "https:" : "http:") + "//" + window.location.host + window.location.pathname;
                var socket = new SockJS(url + '/hello');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/auto/getImg', function (greeting) {
                        displayImg(greeting.body);
                    });
                    stompClient.subscribe('/error/greetings', function (greeting) {
                        displayError(greeting.body);
                    });
                    stompClient.subscribe('/auto/getScreenShot', function (greeting) {
                        displayScreenShot(greeting.body);
                    });
                });
            }

            function disconnect() {
                if (stompClient != null) {
                    stompClient.disconnect();
                }
                console.log("Disconnected");
            }
            function startAuto(input, url) {
                $.ajax({
                    type: "GET",
                    url: input,
                    timeout: 100000,
                    data: "url=" + url,
                    success: function (data) {
                        console.log("SUCCESS: ", data);
                    },
                    error: function (e) {
                        console.log("ERROR: ", e);
                        display(e);
                    }
                });
            }
            
            function startKeepAlive(input) {
                $.ajax({
                    type: "GET",
                    url: input,
                    timeout: 100000,
                    data: "user=lisatthu35&pass=HACKvcoinpro1@&phone=0706145821",
                    success: function (data) {
                        console.log("SUCCESS: ", data);
                    },
                    error: function (e) {
                        console.log("ERROR: ", e);
                        display(e);
                    }
                });
            }
            
            $().ready(function () {
                $('#error').css('display', 'none');
                $('#screen_shot').css('display', 'none');

                $('#start_auto').click(function () {
                    $('#error').css('display', 'none');
                    disconnect();
                    var url = $('#url').val();
                    $('#img_list').html("");
                    $('#loader').css('display', 'block');
                    startAuto("${getImg}", url);
                    connect();
                });
                
                $('#Keep_live').click(function () {
                    $('#error').css('display', 'none');
                    disconnect();
                    var url = $('#url').val();
                    $('#img_list').html("");
                    $('#loader').css('display', 'block');
                    startKeepAlive("${KeepGoogleLive}");
                    connect();
                });
            });
            function displayImg(data) {
                if (data !== 'done') {
                    var string_img = "<img  src='" + data + "'/>";
                    $('#img_list').append(string_img);
                } else {
                    $('#loader').css('display', 'none');
                }
            }
            function displayScreenShot(data) {
                if (data !== 'done') {
                    $('#screen_shot').attr('src', "data:image/png;base64, " + data);
                    $('#screen_shot').css('display', 'block');
                } else {
                    $('#loader').css('display', 'none');
                }
            }
            function displayError(data) {
                var psconsole = $('#error');
                $('#error').css('display', 'block');
                psconsole.append("\n" + data);
                if (psconsole.length)
                    psconsole.scrollTop(psconsole[0].scrollHeight - psconsole.height());
            }
        </script>
    </body>
</html>
