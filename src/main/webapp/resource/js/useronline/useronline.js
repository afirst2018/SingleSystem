var conf = {
	colNames : [
		'用户名', '真实姓名',  '操作'
	],
	colModel : [
		{
			name : 'username',
			width : 100,
			title : true
		},{
			name : 'realName',
			width : 80,
			title : true
		}, {
			name : 'username',
			fomatter : formatter,
			width : 120
		}
	]
};

$(document).ready(function() {

	queryUserOnline();
    //回车键搜索
	$(".form-inline").keyup(function (e) {  
		var curKey = e.which; 
        if (curKey == 13){  
             $("#searchBtn").trigger("click");  
         }  
    });

	setInterval(queryUserOnline(), 1000);
});

function formatter(value) {
	return '<a onclick="kickOff(\''+value+'\')" title="踢掉"><i class="fa fa-hand-o-down fa-lg"></i></a>';
}



/**
 * Created on   2016年5月16日
 * Discription: [踢掉]
 * @param id
 * @author:     linlong
 * @update:     2016年5月16日 下午3:28:23
 */
function kickOff(username) {
	$.messager.confirm("强制下线", "确定要踢掉该管理员吗？", function() {
		$.ajax({
			url : basePath + '/UserOnlineController/method_kickOff.html',
			data : {
				username : username
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data) {
					showMessage(data);
					queryUserOnline();
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	});
}


/**
 * Created on   2016年5月16日
 * Discription: [清空查询条件输入域]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:29:12
 */
function clearConditionInput() {
	$("#username").val("");
	// $("#realName").val("");
}


/**
 * Created on   2016年5月16日
 * Discription: [查询userOnlineList]
 * @author:     linlong
 * @param page
 * @update:     2016年5月16日 下午3:29:23
 */
function queryUserOnline() {
	var conditionData = {username:$("#username").val()};
	$.ajax({
		url : basePath + '/UserOnlineController/method_getUserOnline.html',
		data : conditionData,
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data) {
				//createTableList(data.resultList);
				grid("userOnlineTable", conf, data);

			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}
