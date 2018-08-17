/**
 * @author suliang
 */
$(function(){
	$.extend({
		// _scopeSelector : 使用验证的区域; autoScroll:调用doValidate时，是否自动将滚动条滚动到第一个不通过校验的控件位置
		doRegistValidator: function(_scopeSelector,autoScroll) {
			$.each($("#"+_scopeSelector).find("[data-vali]"),function(i, n) {
				//初始化绑定时，并不设定任何错误信息，由后面的验证程序动态设定
				$(n).poshytip({
					className: 'tip-yellowsimple',
					content: '',
					showOn: 'none',
					alignTo: 'target',
					alignX: 'right',
					alignY: 'center',
					offsetX: 5,
					offsetY: 10
				});
				$(n).bind('change',validateOnBlur);
			});

			//定义一个验证器
			$.Validator=function(para) {}

			$.Validator["ajaxValidate"+_scopeSelector]=function() {
				return beforeSubmit(_scopeSelector,autoScroll);
			}
		}
	});
	//为jquery扩展一个doValidate方法，对所有带有valType的元素进行表单验证，可用于ajax提交前自动对表单进行验证
	$.extend({
		doValidate: function(_scopeSelector) {
			return $.Validator["ajaxValidate"+_scopeSelector]();
		}
	});
	//为jquery扩展一个doValidate方法，对所有带有valType的元素进行表单验证，可用于ajax提交前自动对表单进行验证
	$.extend({
		doDestroyValidator: function(_scopeSelector) {
			$.each($(_scopeSelector).find("[data-vali]"),function(i, n) {
				$(n).unbind("change");
			});
		}
	});
});

//输入框焦点离开后对文本框的内容进行格式验证
function validateOnBlur() {
	var flag = true;
	//清除可能已有的提示信息
	$(this).poshytip('hide');
	var _valiObjStr = $(this).attr("data-vali");
	var _elmVal = $(this).val();
	if(_valiObjStr && /^\[.*\]$/.test(_valiObjStr)){
		var _valiObjArr = eval(_valiObjStr);
		for(var idx in _valiObjArr){
			var type = _valiObjArr[idx]["type"]; //校验类型
			var msg = _valiObjArr[idx]["msg"];//提示消息
			if(msg){
				msg = '<font color=\'#a94442\'>' + msg + '</font>'
			}
			if(type){
				switch (type) {
					case 'required':
						if(!_elmVal.trim()) {
							//显示tips
							$(this).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
							$(this).poshytip('show');
							flag = false;
						}
						break;
					case 'maxLength':
						var max = _valiObjArr[idx]["max"];
						if(!isNaN(max) && parseInt(max) > 0 && _elmVal && _elmVal.length > parseInt(max)){
							//显示tips
							$(this).poshytip('update',msg || '<font color=\'#a94442\'>最多输入'+parseInt(max)+'个字符</font>');
							$(this).poshytip('show');
							flag = false;
						}
						break;
					case 'minLength':
						var min = _valiObjArr[idx]["min"];
						if(!isNaN(min) && parseInt(min) > 0 && _elmVal && _elmVal.length < parseInt(min)){
							//显示tips
							$(this).poshytip('update',msg || '<font color=\'#a94442\'>最少输入'+parseInt(max)+'个字符</font>');
							$(this).poshytip('show');
							flag = false;
						}
						break;
					case 'remote':
						var dfd   = new $.Deferred();
						var that = $(this);
						var _url = _valiObjArr[idx]["url"];
						var _elmName = _valiObjArr[idx]["attrName"] || $(this).attr("name") || "data";
						var dataObj = {};
						dataObj[_elmName] = _elmVal;
						// 额外向后台传递用户指定的一个或多个值
						var _fieldIds = _valiObjArr[idx]["fieldIds"];
						var _fieldIdsArr = null;
						if(_fieldIds && /^\[.*\]$/.test(_fieldIds)){
							_fieldIdsArr = eval(_fieldIds);
							if(Object.prototype.toString.call(_fieldIdsArr) === '[object Array]'){
								for(var k in _fieldIdsArr){
									dataObj[_fieldIdsArr[k]] = $("#"+_fieldIdsArr[k]).val();
								}
							}
						}
						function runCallback() {
							var xhr = $.ajax({
								type: "GET",
								url: _url,
								async:false,
								dataType: 'json',
								data: dataObj
							});
							xhr.then(function(response) {
								response.valid = response.valid === true || response.valid === 'true';
								if(response.valid){
									that.poshytip('update',msg || '<font color=\'#a94442\'>服务器端验证未通过</font>');
									that.poshytip('show');
									flag=false;
								}
							});
							dfd.fail(function() {
								xhr.abort();
							});
						}
						var validateResult = runCallback();
						if ('object' === typeof validateResult && validateResult.resolve) {
							validateResult.done(function($f, v, response) {});
						}
						break;
					case 'regExp':
						var regExpString = _valiObjArr[idx]["regExpString"];
						if(regExpString){
							//显示tips
							var regExpflag = new RegExp(regExpString).test(_elmVal);
							if(!regExpflag){
								$(this).poshytip('update',msg || '<font color=\'#a94442\'>输入的数据格式不正确</font>');
								$(this).poshytip('show');
								flag = false;
							}
						}
						break;
					//data-vali="[{type:'selectsRequired',ids:[{id:'citySel'},{id:'districtSel'}],msg:'所属区域必填'}]"
					case 'selectsRequired':
						var idsArrStr = _valiObjArr[idx]["ids"];
						if(idsArrStr && /^\[.*\]$/.test(idsArrStr)){
							var idsArr = eval(idsArrStr);
							for(var idx in idsArr){
								var temp = idsArr[idx];
								if(!$("#"+idsArr[idx]["id"]).val()){
									$(this).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
									$(this).poshytip('show');
									flag=false;
									break;
								}
							}
						}
						break;
					case 'identical':
						// 校验密码，确认密码是否相同，以及类似的表单中的两个输入域（input=text）中的值是否相等
						var elemId = _valiObjArr[idx]["field_id"];
						var otherInputVal = $("#" + elemId).val();
						if(_elmVal != otherInputVal) {
							//显示tips
							$(this).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
							$(this).poshytip('show');
							flag = false;
						}
						break;
				}
			}
			if(!flag){
				break;
			}
		}
	}
}

//submit之前对所有表单进行验证
function beforeSubmit(_scopeSelector,autoScroll) {
	var flag=true;
	var errOjbArr = [];
	// data-vali="[{type:'required',msg:'手机号必填'},{type:'regExp',regExpString:'^\[.*\]$',msg:'手机号格式不正确'},{type:'maxLength', max:20, msg:'手机号格式不正确'}]"
	//$.each($("[data-vali]"),function(i, n) {
	$.each($("#"+_scopeSelector).find("[data-vali]"),function(i, n) {
		//清除可能已有的提示信息
		$(n).poshytip('hide');
		var _valiObjStr = $(n).attr("data-vali");
		var _elmVal = $(n).val();
		if(_valiObjStr && /^\[.*\]$/.test(_valiObjStr)){
			var _valiObjArr = eval(_valiObjStr);
			for(var idx in _valiObjArr){
				var type = _valiObjArr[idx]["type"]; //校验类型
				var msg = _valiObjArr[idx]["msg"];//提示消息
				if(msg){
					msg = '<font color=\'#a94442\'>' + msg + '</font>'
				}
				if(type){
					switch (type) {
						case 'required':
							if(!_elmVal.trim()) {
								//显示tips
								$(n).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
								$(n).poshytip('show');
								errOjbArr.push(n);
								flag=false;
							}
							break;
						case 'maxLength':
							var max = _valiObjArr[idx]["max"];
							if(!isNaN(max) && parseInt(max) > 0 && _elmVal && _elmVal.length > parseInt(max)){
								//显示tips
								$(n).poshytip('update',msg || '<font color=\'#a94442\'>最多输入'+parseInt(max)+'个字符</font>');
								$(n).poshytip('show');
								errOjbArr.push(n);
								flag=false;
							}
							break;
						case 'minLength':
							var min = _valiObjArr[idx]["min"];
							if(!isNaN(min) && parseInt(min) > 0 && _elmVal && _elmVal.length < parseInt(min)){
								//显示tips
								$(n).poshytip('update',msg || '<font color=\'#a94442\'>最少输入'+parseInt(max)+'个字符</font>');
								$(n).poshytip('show');
								errOjbArr.push(n);
								flag=false;
							}
							break;
						case 'remote':
							var dfd   = new $.Deferred();
							var that = $(n);
							var _url = _valiObjArr[idx]["url"];
							var _elmName = _valiObjArr[idx]["attrName"] || $(this).attr("name") || "data";
							var dataObj = {};
							dataObj[_elmName] = _elmVal;
							// 额外向后台传递用户指定的一个或多个值
							var _fieldIds = _valiObjArr[idx]["fieldIds"];
							var _fieldIdsArr = null;
							if(_fieldIds && /^\[.*\]$/.test(_fieldIds)){
								_fieldIdsArr = eval(_fieldIds);
								if(Object.prototype.toString.call(_fieldIdsArr) === '[object Array]'){
									for(var k in _fieldIdsArr){
										dataObj[_fieldIdsArr[k]] = $("#"+_fieldIdsArr[k]).val();
									}
								}
							}
							function runCallback() {
								var xhr = $.ajax({
									type: "GET",
									async:false,
									url: _url,
									dataType: 'json',
									data: dataObj
								});
								xhr.then(function(response) {
									response.valid = response.valid === true || response.valid === 'true';
									if(response.valid){
										that.poshytip('update',msg || '<font color=\'#a94442\'>服务器端验证未通过</font>');
										that.poshytip('show');
										flag=false;
									}
								});
								dfd.fail(function() {
									xhr.abort();
								});
							}
							var validateResult = runCallback();
							if ('object' === typeof validateResult && validateResult.resolve) {
								validateResult.done(function($f, v, response) {});
							}
							break;
						case 'regExp':
							var regExpString = _valiObjArr[idx]["regExpString"];
							if(regExpString){
								// 注：由于使用了new RegExp 方式创建正则，所以正则表达式中含有“\” 时，需要转义成“\\”
								var regExpflag = new RegExp(regExpString).test(_elmVal);
								if(!regExpflag){
									$(n).poshytip('update',msg || '<font color=\'#a94442\'>输入的数据格式不正确</font>');
									$(n).poshytip('show');
									errOjbArr.push(n);
									flag=false;
								}
							}
							break;
						//data-vali="[{type:'selectsRequired',ids:[{id:'citySel'},{id:'districtSel'}],msg:'所属区域必填'}]"
						case 'selectsRequired':
							var idsArrStr = _valiObjArr[idx]["ids"];
							if(idsArrStr && /^\[.*\]$/.test(idsArrStr)){
								var idsArr = eval(idsArrStr);
								for(var idx in idsArr){
									var temp = idsArr[idx];
									if(!$("#"+idsArr[idx]["id"]).val()){
										$(n).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
										$(n).poshytip('show');
										errOjbArr.push(n);
										flag=false;
										break;
									}
								}
							}
							break;
						case 'identical':
							// 校验密码，确认密码是否相同，以及类似的表单中的两个输入域（input=text）中的值是否相等
							var elemId = _valiObjArr[idx]["field_id"];
							var otherInputVal = $("#" + elemId).val();
							if(_elmVal != otherInputVal) {
								//显示tips
								$(n).poshytip('update',msg || '<font color=\'#a94442\'>该项必填</font>');
								$(n).poshytip('show');
								flag = false;
							}
							break;
					}
				}
				if(!flag){
					break;
				}
			}
		}
	});
	// errOjbArr 记录所有校验未通过的项，并将滚动条滚动到第一个校验不通过的项位置
	if(errOjbArr.length > 0 && !flag && autoScroll){
		$("html,body").animate({scrollTop:$(errOjbArr[0]).offset().top});
	}
	errOjbArr = null;
	return flag;
}