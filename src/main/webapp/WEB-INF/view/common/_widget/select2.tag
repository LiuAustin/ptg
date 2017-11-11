@ var _token = token!'';
@ if (!isEmpty(value)){
@ 	_token = "";
@ }
<input type="hidden"  class="${class!} form-control-select2 " name="${_token}${name!}" value="${value!}" id="${id}" title="${name!}" data-type="select2" onchange="_${id!}_selectChanged()" ${required!}>
	<script type="text/javascript">
		$(function(){
        	var data = {source: "${source}", where: "${where!}"};
			$.post("${ctxPath}/cache/getDiySelectJson", data, function (result) {
				var dataList = result.data;
				if (result.code === 0) {
					$("#${id}").select2({
						data: dataList,
						placeholder: '请选择',//默认文字提示
						language: "zh-CN",//汉化
						formatNoMatches:'没有匹配到结果',
						formatSearching:'搜索中…',
						allowClear: true//允许清空
					})
				} else {
					layer_alert("数据加载失败", "error");
				}
			}, "json");
		});

        function _${id!}_selectChanged() {
            var _name = $("#${id}").attr("name").replace("token_", "");
            $("#${id}").attr("name", _name);
            $('#form_token').val(1);
        }
	</script>
