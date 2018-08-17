var conf = {
	colNames : [
		'类型名称', '类型值', '数据字典名称', '数据字典值', '排序序号','备注', '操作'
	],
	colModel : [
		{
			name : 'kindName',
			width : 100,
			title : true
		},{
			name : 'kindValue',
			width : 80,
			title : true
		},{
			name : 'codeName',
			width : 100,
			title : true
		},
		{
			name : 'codeValue',
			width : 100,
		},{
			name : 'orderNum',
			width : 100,
			title : true
		},{
			name : 'remark',
			width : 100,
			title : true
		}, {
			name : 'id',
			fomatter : formatter,
			width : 120
		}
	]
};

$(document).ready(function() {
	// 设置新增/修改codelist弹出窗口的位置
	$('#addCodeListDiv').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : 150
		});
	});
	// 注册新增/修改窗口 关闭事件
	$('#addCodeListDiv').on('hide.bs.modal', function() {
		clearInput();
		$("#addForm").bootstrapValidator('resetForm', true);
	});
	initFormValidator();
	queryCodeList();
    //回车键搜索
	$(".form-inline").keyup(function (e) {  
		var curKey = e.which; 
        if (curKey == 13){  
             $("#searchBtn").trigger("click");  
         }  
    });
});

function formatter(value) {
	return '<a onclick="showUpdCodeListDiv(' + value + ')" title="编辑"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;' +
	'<a onclick="delCodeList(' + value + ',this)" title="删除"><i class="fa fa-trash-o fa-lg"></i></a>';
}

/**
 * Created on   2016年5月16日
 * Discription: [初始化表单校验器]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function initFormValidator() {
	$("#addForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			kindName : {
				validators : {
					notEmpty : {
						message : '请填写类型名称'
					},
					stringLength : {
						max : 50,
						message : '最多输入50个字符'
					}
				}
			},
			kindValue : {
				validators : {
					notEmpty : {
						message : '请填写类型值'
					},
					stringLength : {
						max : 20,
						message : '最多输入20个字符'
					}
				}
			},
			codeName : {
				validators : {
					notEmpty : {
						message : '请填写数据字典名称'
					},
					stringLength : {
						max : 50,
						message : '最多输入50个字符'
					}
				}
			},
			codeValue : {
				validators : {
					notEmpty : {
						message : '请填写数据字典值'
					},
					stringLength : {
						max : 1,
						message : '最多输入1个字符'
					},
					regexp : {
						regexp : /^[A-Za-z0-9]+$/,
						message : '只能输入英文字母或数字'
					}
				}
			},
			orderNum : {
				validators : {
					notEmpty : {
						message : '请填写排序序号'
					},
					stringLength : {
						max : 20,
						message : '最多输入20个字符'
					},
					regexp : {
						regexp : /^([1-9]\d*)$/,
						message : '最多输入3位大于0的整数'
					}
				}
			},
			remark : {
				validators : {
					stringLength : {
						max : 100,
						message : '最多输入100个字符'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
		codeListSave();
		$("#addForm").bootstrapValidator('resetForm', true);
	});
}
/**
 * Created on   2016年5月16日
 * Discription: [显示新增codelist窗口]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function showAddCodeListDiv() {
	$('#myModalLabel').text("添加数据字典信息");
	$('#addCodeListDiv').modal({
		backdrop : "static",
		show : true
	});
	clearInput();
}
/**
 * Created on   2016年5月16日
 * Discription: [显示修改codelist窗口]
 * @author:     linlong
 * @param id
 * @update:     2016年5月16日 下午3:25:12
 */
function showUpdCodeListDiv(id) {
	clearInput();
	if (id) {
		$.ajax({
			url : basePath + '/codeList/method_queryCodeListById.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.flag) {
					$('#myModalLabel').text("修改数据字典信息");
					$("#idTxt").val(data.entity.id);
					$("#kindNameTxt").val(data.entity.kindName);
					$("#kindValueTxt").val(data.entity.kindValue);
					$("#codeNameTxt").val(data.entity.codeName);
					$("#codeValueTxt").val(data.entity.codeValue);
					$("#orderNumTxt").val(data.entity.orderNum);
					$("#remarkTxt").val(data.entity.remark);
					$('#addCodeListDiv').modal({
						backdrop : "static",
						show : true
					});
				} else {
					showMessage("未查询到数据，无法修改。 ");
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	}
}
/**
 * Created on   2016年5月16日
 * Discription: [校验方法]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function doValidate() {
	$('#addForm').bootstrapValidator('validate');
}
/**
 * Created on   2016年5月16日
 * Discription: [保存codeList]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function codeListSave() {
	var codeListId = $("#idTxt").val();
	if (codeListId) { // 修改
		$.ajax({
			url : basePath + '/codeList/method_updCodeList.html',
			data : {
				id : codeListId,
				kindName : $("#kindNameTxt").val(),
				kindValue : $("#kindValueTxt").val(),
				codeName : $("#codeNameTxt").val(),
				codeValue : $("#codeValueTxt").val(),
				orderNum : $("#orderNumTxt").val(),
				remark : $("#remarkTxt").val()
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.message) {
					showMessage(data.message);
					$('#addCodeListDiv').modal('hide');
					queryCodeList();
					clearInput();
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	} else { // 新增
		if ("0" == checkExist()) {
			$.ajax({
				url : basePath + '/codeList/method_addCodeList.html',
				data : {
					kindName : $("#kindNameTxt").val(),
					kindValue : $("#kindValueTxt").val(),
					codeName : $("#codeNameTxt").val(),
					codeValue : $("#codeValueTxt").val(),
					orderNum : $("#orderNumTxt").val(),
					remark : $("#remarkTxt").val()
				},
				dataType : 'json',
				type : 'post',
				success : function(data) {
					if (data && data.message) {
						showMessage(data.message);
						$('#addCodeListDiv').modal('hide');
						queryCodeList();
						clearInput();
					}
				},
				error : function(xhr,status){
					ajaxErrorHandler(xhr);
				}
			});
		}
	}
}

/**
 * Created on   2016年5月16日
 * Discription: [校验待增加的记录是否已存在]
 * @returns {String}
 * @author:     linlong
 * @update:     2016年5月16日 下午3:26:52
 */
function checkExist() {
	var existFlag = "";
	$.ajax({
		url : basePath + '/codeList/method_checkExistOne.html',
		data : {
			kindName : $("#kindNameTxt").val(),
			kindValue : $("#kindValueTxt").val(),
			codeName : $("#codeNameTxt").val(),
			codeValue : $("#codeValueTxt").val()
		},
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(data) {
			if (data) {
				existFlag = data.flag;
				if ("1" == existFlag) {
					showMessage("该记录已存在，无法增加。");
				}
			}
		},
		error : function(xhr,status){
			existFlag = "1";
			ajaxErrorHandler(xhr);
		}
	});
	return existFlag;
}

/**
 * Created on   2016年5月16日
 * Discription: [删除]
 * @param id
 * @author:     linlong
 * @update:     2016年5月16日 下午3:28:23
 */
function delCodeList(id,obj) {
	$(obj).blur();
	$.messager.confirm("删除数据", "确定要删除吗？", function() {
		$.ajax({
			url : basePath + '/codeList/method_delCodeListById.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.message) {
					showMessage(data.message);
					queryCodeList();
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
 * Discription: [清空表单输入域]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:28:58
 */
function clearInput() {
	$("#idTxt").val("");
	$("#kindNameTxt").val("");
	$("#kindValueTxt").val("");
	$("#codeNameTxt").val("");
	$("#codeValueTxt").val("");
	$("#orderNumTxt").val("");
	$("#remarkTxt").val("");
}

/**
 * Created on   2016年5月16日
 * Discription: [清空查询条件输入域]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:29:12
 */
function clearConditionInput() {
	$("#knameConditionTxt").val("");
	$("#kvalueConditionTxt").val("");
	$("#cnameConditionTxt").val("");
	$("#cvalueConditionTxt").val("");
	$("#remarkConditionTxt").val("");
}

/**
 * Created on   2016年5月16日
 * Discription: [分页条跳页]
 * @param n
 * @author:     linlong
 * @update:     2016年5月16日 下午3:29:23
 */
function page(n) {
	queryCodeList({
		pageNo : n
	});
}
/**
 * Created on   2016年5月16日
 * Discription: [刷新列表]
 * @author:     linlong
 * @param list
 * @update:     2016年5月16日 下午3:29:23
 */
function createTableList(list) {
	$("#tBodyElement").empty();
	var index = 0;
	var tBodyHtml = "";
	for ( var obj in list) {
		index++;
		tBodyHtml = '<tr>' +
			'	<td>' + index + '</td>'+
			'	<td>' + list[obj].kindName + '</td>'+
			'	<td>' + list[obj].kindValue + '</td>'+
			'	<td>' + list[obj].codeName+'</td>'+
			'	<td>' + list[obj].codeValue + '</td>'+
			'	<td>' + list[obj].orderNum + '</td>'+
			'	<td>' + list[obj].remark + '</td>'+
			'	<td width="100px">' +
			'		<button onclick="showUpdCodeListDiv(' + list[obj].id + ')" class="btn btn-primary btn-circle"><i class="fa fa-pencil"></i></button>&nbsp;&nbsp;' +
			'		<button onclick="delCodeList(' + list[obj].id + ',this)" class="btn btn-primary btn-circle"><i class="fa fa-trash-o"></i></button>' +
			'	</td>' +
			'</tr>';
		$("#tBodyElement").append(tBodyHtml);
	}
}
/**
 * Created on   2016年5月16日
 * Discription: [查询codelist]
 * @author:     linlong
 * @param page
 * @update:     2016年5月16日 下午3:29:23
 */
function queryCodeList(page) {
	var conditionData = {};
	if (page) {
		// 说明：翻页时使用的查询条件是点击“查询按钮”后，记下的查询条件。而不使用用户临时输入的查询条件。
		conditionData.kindName = $("#knameConditionTxtHdn").val();
		conditionData.kindValue = $("#kvalueConditionTxtHdn").val();
		conditionData.codeName = $("#cnameConditionTxtHdn").val();
		conditionData.codeValue = $("#cvalueConditionTxtHdn").val();
		conditionData.remark = $("#remarkConditionTxtHdn").val();
		conditionData = mergeData(page, conditionData);
	} else {
		conditionData.kindName = $("#knameConditionTxt").val();
		conditionData.kindValue = $("#kvalueConditionTxt").val();
		conditionData.codeName = $("#cnameConditionTxt").val();
		conditionData.codeValue = $("#cvalueConditionTxt").val();
		conditionData.remark = $("#remarkConditionTxt").val();
	}
	$.ajax({
		url : basePath + '/codeList/method_queryCodeList.html',
		data : conditionData,
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data && data.resultList) {
				//createTableList(data.resultList);
				grid("codeListTable", conf, data.resultList);
				if (data.resultList.length == 0) {
					//showMessage(SEARCH_NON_MSG);
				}
				if (data.page) {
					createPageBar(data.page);
				}
				$("#knameConditionTxtHdn").val(data.knameConditionTxtHdn);
				$("#kvalueConditionTxtHdn").val(data.kvalueConditionTxtHdn);
				$("#cnameConditionTxtHdn").val(data.cnameConditionTxtHdn);
				$("#cvalueConditionTxtHdn").val(data.cvalueConditionTxtHdn);
				$("#remarkConditionTxtHdn").val(data.remarkConditionTxtHdn);
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}
