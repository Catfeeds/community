<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath(); %>
<html>
<head>
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Mobile Specific Metas ---->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- FONT -->
    <link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
    <!-- CSS --->
    <link rel="stylesheet" href="${path}/css/normalize.css">
    <link rel="stylesheet" href="${path}/css/skeleton.css">
    <link rel="stylesheet" href="${path}/css/custom.css">
</head>
<body>
<div class="section container"> <video width="100%" height="100%" id="vid1" muted></video>
    <video id="vid2" style="width: 150px;height: 150px;position: absolute;bottom: 20px;right: 20px;z-index:10000"></video>
</div><script type="text/javascript">
    var room = "/webSocket/${room}";
    var initCall = true;
</script>
<script src="${path}/js/app.js" type="text/javascript"></script>
</body>
</html>
