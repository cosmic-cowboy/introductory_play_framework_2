$(document).ready(function() {

    var target = ".draggable";
    
	// WebSocketオブジェクト生成
	// WS_LOCATION：WebSocket用の接続先
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
    var wsSocket = new WS(WS_LOCATION);
    
    // メッセージが発生したときの処理
    // event:送信されてきたJSON
    wsSocket.onmessage = function(event) {
        var data = JSON.parse(event.data);
        // エラーの場合は閉じる
        if(data.error) {
            wsSocket.close();
        }
        // X座標、Y座標、ユーザ名があれば変更された値を反映する
        if(data.x != undefined && data.y != undefined && data.username != USERNAME) {
            $(target).css("top", data.x);
            $(target).css("left", data.y);
        }
    };

    // メッセージの送信関数
    // 引数のX座標、Y座標をJSON化して送る
    var sendMessage = function(x, y) {
        wsSocket.send(JSON.stringify(
            {
                x: x,
                y: y
            }
        ));
    };

    // ドラッグ時の処理
    $(target).draggable({
        drag: function() {
            sendMessage($(this).css("top"), $(this).css("left"));
        }
    });

});