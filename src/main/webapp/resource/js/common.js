$.messager.model = {
	ok:{ text: "<i class=\"fa fa-check\"> 确定", classed: 'btn btn-primary' },
	cancel: { text: "<i class=\"fa fa-times\"></i> 取消", classed: 'btn btn-white' }
};

$.fn.serializeObject = function () {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function () {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

var SEARCH_NON_MSG = '查不到任何匹配的数据';
function showMessage(message, id) {
	if(id) {
		$("#" + id).html(message);
	} else {
		$.messager.popup(message);
	}
}

// 统一 ajax erro函数 处理
function ajaxErrorHandler(xhr,msg){
	if(msg){
		showMessage(msg);
	}else{
		showMessage("系统内部发生错误，稍后请重试...");
	}
}

// session超时后，跳转到登录页
function jumpToLogin(){
	window.location = basePath;
}

function checkForm(formId) {
	var flag = true;
	$("#" + formId + " :input").each(function(i, n) {
		var validate = $(n).attr("pattern");
		var length = $(n).attr("maxLength");
		var required = $(n).attr("required");
		var value = $(n).val();
		if(required){
			if(!value){
				showMessage("请输入该信息");
				$(n).focus();
				flag = false;
				return false;
			}
		}
		if(validate && value != null && value != "") {
			var reg = new RegExp(validate);
			if(!reg.test(value)) {
				showMessage("数据格式不正确");
				$(n).focus();
				flag = false;
				return false;
			} 
		}
		if (length && value != null && value.length > length ) {
			showMessage("数据长度不得超过" + length + "个字符");
			$(n).focus();
			flag = false;
			return false;
		}
	});
	return flag;
}

function disableElement(id, time) {
	$("#" + id).attr("disabled", true);
	if(time) {
		setTimeout('enableElement("' + id + '")', time);
	}
}

function enableElement(id) {
	$("#" + id).attr("disabled", false);
}

function mergeData(res, dest, override) {
	var result = {};
	for (var key in dest) {
		result[key] = dest[key];
	}
	for (var key in res) {
		if (!override) {
			result[key] = res[key];
		} else {
			if (!result[key]) {
				result[key] = res[key];
			}
		}
	}
	return result;
}

function parseNull(value) {
	if (value) {
		return value;
	} else {
		return "";
	}
}

function HashMap() {
	var size = 0;
	var entry = new Object();

	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			size++;
		}
		entry[key] = value;
	}

	this.get = function(key) {
		return this.containsKey(key) ? entry[key] : null;
	}

	this.remove = function(key) {
		if (this.containsKey(key) && (delete entry[key])) {
			size--;
		}
	}

	this.containsKey = function(key) {
		return (key in entry);
	}

	this.containsValue = function(value) {
		for ( var prop in entry) {
			if (entry[prop] == value) {
				return true;
			}
		}
		return false;
	}

	this.values = function() {
		var values = new Array();
		for ( var prop in entry) {
			values.push(entry[prop]);
		}
		return values;
	}

	this.keys = function() {
		var keys = new Array();
		for ( var prop in entry) {
			keys.push(prop);
		}
		return keys;
	}

	this.size = function() {
		return size;
	}

	this.clear = function() {
		size = 0;
		entry = new Object();
	}
}

//创建分页条
function createPageBar(data,funName,pageDiv){
	var page = "";
	if(pageDiv){
		page = pageDiv;
	}else{
		page = "pageDiv";
	}
	var fun = "";
	if(funName){
		fun = funName;
	}else{
		fun = "page";
	}
	myFunName=fun;
	if(data){
		var totalCount;//总记录数
		if(data.totalCount && !isNaN(data.totalCount)){
			totalCount = parseInt(data.totalCount);
		}else{
			totalCount = 0;
		}
		var pageSize;
		if(data.pageSize && !isNaN(data.pageSize)){
			pageSize = data.pageSize;
		}else{
			pageSize = 1;
		}
		var totalPage=0;//总页数
		if(totalCount%pageSize == 0){
			totalPage = totalCount/pageSize;
		}else{
			totalPage = Math.ceil(totalCount/pageSize);
		}
		var curPage;//当前页
		if(!isNaN(data.pageNo)){
			curPage = parseInt(data.pageNo);
		}else{
			curPage = 1;
		}
		var pageBar = "<ul id='fenye' class='pagination'>";
		if(totalPage>1){
			//pageBar = pageBar + "<li><a href='javascript:"+fun+"(0);'>首页</a></li>";
			if(curPage-1<=0){
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: not-allowed;'>上一页</a></li>";
			}else if(curPage <= totalPage){
				pageBar = pageBar + "<li><a href='javascript:"+fun+"("+(curPage-1)+");'>上一页</a></li>";
			}
			var start=curPage-4;
			var end=7;

			if(start<=0 || totalPage <= end){
				start = 0;
			}
			if(curPage-start == 4){
				//if(curPage != 4){
				if(totalPage - curPage < end && totalPage > end){
					//start=start-1;
					start = totalPage-end;
				}
				end=end+start;
				if(end>=totalPage){
					end=totalPage;
				}
			}
			if(end>=totalPage){
				end=totalPage;
			}
			for (var i = start+1; i <=end; i++) {
				if(i== curPage){
					//pageBar = pageBar + "<li><a style='background-color:#80CBEB;' href='javascript:"+fun+"("+i+");'>"+i+"</a></li>";
					pageBar = pageBar + "<li class='active'><a 'javascript:void(0);'>"+i+"</a></li>";
				}else{
					pageBar = pageBar + "<li><a href='javascript:"+fun+"("+i+");'>"+i+"</a></li>";
				}
			}
			if(curPage > totalPage-1){
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: not-allowed;'>下一页</a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;'>共&nbsp;"+totalCount+"&nbsp;条记录&nbsp; "+curPage+"/"+totalPage+"页 </a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;padding: 0;'><input id='"+fun+"gotoPage' class='input-xlarge form-control' placeholder='跳转页码' type='number' min='1' max="+totalPage+" style='height: 26px;width: 100px;padding: 0px 10px;'></a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' onclick='checkGotoPage(\""+fun+"\","+totalPage+")'>跳转</a></li>";
			}else{
				pageBar = pageBar + "<li><a href='javascript:"+fun+"("+(curPage+1)+");'>下一页</a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;'>共&nbsp;"+totalCount+"&nbsp;条记录&nbsp; "+curPage+"/"+totalPage+"页 </a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;padding: 0;'><input id='"+fun+"gotoPage' class='input-xlarge form-control' placeholder='跳转页码' type='number' min='1' max="+totalPage+" style='height: 26px;width: 100px;padding: 0px 10px;'></a></li>";
				pageBar = pageBar + "<li><a href='javascript:void(0);' onclick='checkGotoPage(\""+fun+"\","+totalPage+")'>跳转</a></li>";
			}
			if(curPage >= totalPage-1){
				pageBar = pageBar + "";
			}else{
				//pageBar = pageBar + "<li><a href='javascript:"+fun+"("+(totalPage-1)+");'>末页</a></li>";
			}
			pageBar = pageBar + "</ul>";
			pageBar = pageBar + "<input type=hidden id='curPage' name='curPage' value='0'>";
		}else if(totalPage == 1){
			pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: not-allowed;'>上一页</a></li>"
				+ "<li class='active'><a href='javascript:void(0);'>1</a></li>"
				+ "<li><a href='javascript:void(0);' style='cursor: not-allowed;'>下一页</a></li>";
			pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;'>共&nbsp;"+totalCount+"&nbsp;条记录&nbsp; "+curPage+"/"+totalPage+"页 </a></li>";
			pageBar = pageBar + "<li><a href='javascript:void(0);' style='cursor: default;padding: 0;'><input id='"+fun+"gotoPage' class='input-xlarge form-control' placeholder='跳转页码' type='number' min='1' max="+totalPage+" style='height: 26px;width: 100px;padding: 0px 10px;'></a></li>";
			pageBar = pageBar + "<li><a href='javascript:void(0);' onclick='checkGotoPage(\""+fun+"\","+totalPage+")'>跳转</a></li>";
			pageBar = pageBar + "</ul>";
		}

		$("#" + page ).html(pageBar);
		$("#" + page ).find("input").keyup(function(e){
			var curKey = e.which;
			var inpVal = $(this).val();
			var isNo= /^[0-9]*[1-9][0-9]*$/;//非负整数
			if(!isNo.test( inpVal )){
				showMessage('请输入正确页码！');
				return ;
			}
			if (curKey == 13){
				$(this).parents("li").next().find("a").trigger("click");
			}
		});

	}
}

function checkGotoPage(fun,totalPage) {
	var pageNo=$("#" + fun +"gotoPage").val();
	var isNo= /^[0-9]*[1-9][0-9]*$/;//非负整数
	if(!isNo.test( pageNo )){
		showMessage('请输入正确页码');
		return ;
	}
	if(pageNo!=undefined){
		if(pageNo>totalPage){
			showMessage("您输入的页码大于总页码！");
			return;
		}else{
			eval(fun+"("+pageNo+")");
		}
	}
}

function dateformat(date, fmt) {
	var o = {
		"M+" : date.getMonth() + 1, // 月份
		"d+" : date.getDate(), // 日
		"H+" : date.getHours(), // 小时
		"m+" : date.getMinutes(), // 分
		"s+" : date.getSeconds(), // 秒
		"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
		"S" : date.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function formatDate(d,dFormat) {
	if (!d)
		return '';
	var day = new Date(1 * d);
	if(!dFormat){
		dFormat = "yyyy-MM-dd HH:mm:ss";
	}
	return dateformat(day, dFormat);
}

//grid初始化方法
function grid(id, conf, data) {
	var select = conf.select;
	var thConf = conf.colNames;
	var tdConf = conf.colModel;
	var thStr = "<thead><tr>";
	if(select) {
		thStr += "<th width='50px' style='text-align:center;'></th>";
	}
	thStr += "<th width='50px' style='text-align:center;'>序号</th>";

	for(var i=0;i<thConf.length;i++) {
		var width = tdConf[i]['width'] ? " width='" + tdConf[i]['width'] + "'" : "";
		thStr += ("<th" + width + " style='text-align:center;'>" + thConf[i] + "</th>");
	}
	thStr += "</tr></thead>";
	var tdStr = "<tbody>";
	for(var i=0;i<data.length;i++) {
		tdStr += "<tr>";
		if(select) {
			tdStr += "<td align='center'>" + select(data[i]) + "</td>";
		}
		tdStr += "<td align='center'>" + (i + 1) + "</td>";
		for(var j=0;j<tdConf.length;j++){
			//处理undefined问题
			if(data[i][tdConf[j]['name']] === undefined||data[i][tdConf[j]['name']]==null){
				data[i][tdConf[j]['name']] = "";
			}
			var tdData = "";
			if(tdConf[j]['fomatter']) {
				//tdData = tdConf[j]['fomatter'](data[i][tdConf[j]['name']]);
				tdData = tdConf[j]['fomatter'](data[i][tdConf[j]['name']],data[i]); // update by suliang 2015-08-24
			} else if(tdConf[j]['decode']) {
				var cl = tdConf[j]['codeList'][tdConf[j]['decode']];
				if(cl) {
					if(cl[data[i][tdConf[j]['name']]]===undefined||cl[data[i][tdConf[j]['name']]]==null){
						cl[data[i][tdConf[j]['name']]] = "";
					}
					tdData = cl[data[i][tdConf[j]['name']]];
				} else {
					tdData = data[i][tdConf[j]['name']];
				}
			} else if(tdConf[j]['date']) {
				tdData = formatDate(data[i][tdConf[j]['name']],tdConf[j]['date']);
			} else if(tdConf[j]['number']) {// update by lihang 2015-08-26
				tdData = formatNumber(data[i][tdConf[j]['name']]);
			} else {
				tdData = data[i][tdConf[j]['name']];
			}
			var align = tdConf[j]['align'] ? " align='" + tdConf[j]['align'] + "'" : " align='center'";
			var title = tdConf[j]['title'] ? " title='" + tdData + "'" : "";
			var tdClass = tdConf[j]['class'] ? " class='" + tdConf[j]['class'] + "'" : "";//update by tianci 给td加class属性
			var aLable = tdConf[j]['aLable'] ? "<a class='badge badge-primary'>"+tdData+"</a>" : "";//updaet by tianci 为文字添加a标签背景色绿色
			if(aLable){
				tdStr += ("<td" + align + title + tdClass + ">" + aLable + "</td>");
			}else{
				tdStr += ("<td" + align + title + tdClass + ">" + tdData + "</td>");
			}
		}
		tdStr += "</tr>";
	}
	tdStr += "</tbody>";
	$("#" + id).empty().append(thStr + tdStr);
}

// 初始化下拉列表
function buildSelect(selectId, codeList, kindValue, isDefault, defaultName) {
	$("#" + selectId).empty();
	var defaultHtml = "";
	if (isDefault) {
		defaultHtml += '<option value="" selected="selected">' + (defaultName ? defaultName : "") + '</option>';
	}
	var code = codeList[kindValue];
	for (var obj in code) {
		defaultHtml += ' <option value=' + obj + '>' + code[obj] + '</option>';
	}
	$("#" + selectId).append(defaultHtml);
}