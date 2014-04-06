$(function () {
	$("#sendbtn").click(function  () {
		var jsondata = {
			'input' : $("#input").val()
		};

		$.post("/ajax",
			jsondata,
			function (result) {

				// 名前情報取得
				var nm     = result.getElementsByTagName('name').item(0);
				var nm_txt = nm.childNodes[0].data;

				// メール情報取得
				var ml     = result.getElementsByTagName('mail').item(0);
				var ml_txt = ml.childNodes[0].data;

				// 電話情報
				var tel     = result.getElementsByTagName('tel').item(0);
				var tel_txt = tel.childNodes[0].data;

				var res = "name : " + nm_txt + "mail : " + ml_txt + "tel : " + tel_txt;

				$("#message").text(res);

			},"xml");
	});
});