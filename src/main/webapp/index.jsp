<!DOCTYPE html>
<html>
<head>
    <title>WebSocket/SockJS Echo Sample (Adapted from Tomcat's echo sample)</title>

    <script type="text/javascript">
        var  wsServer = 'ws://localhost:8080/webSocket';
        var  websocket = new WebSocket(wsServer);
        websocket.onopen = function (evt) { onOpen(evt) };
        websocket.onclose = function (evt) { onClose(evt) };
        websocket.onmessage = function (evt) { onMessage(evt) };
        websocket.onerror = function (evt) { onError(evt) };
        function onOpen(evt) {
            console.log("Connected to WebSocket server.");
        }
        function onClose(evt) {
            console.log("Disconnected");
        }
        function onMessage(evt) {
            console.log('Retrieved data from server: ' + evt.data);
        }
        function onError(evt) {
            console.log('Error occured: ' + evt.data);
        }
    </script>
</head>
<body>

</body>
</html>
