// 在列表中选中的权限的主键id
var checkedPrivilegeId;
// 记录修改前的选中的权限对应的所有菜单id
var oldSelectedMenuIds;
// 记录修改后的选中的权限对应的所有菜单id
var newSelectedMenuIds;
// 记录树节点的“展开/折叠”状态
var expandStatus = false;
var setting = {
	view : {
		dblClickExpand : false,
		showLine : true,
		selectedMulti : false
	},
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parentId",
			rootPId : "0"
		},
		key : {
			name : "menuName"
		}
	}
};

var conf = {
	select : function(data){
		return '<span style="display: inline;margin-left: -20px;" class="radio i-checks">'+
				'	<label>'+
				'		<input type="radio" name="priId" value = '+data.id+' id="radio'+data.id+'" >'+
				'	</label>'+
				'</span>';
	},
	colNames : [
		'权限名称', '描述信息','操作'
	],
	colModel : [
		{
			name : 'privilegeName',
			width : '20%',
			fomatter : formatterPrivName,
			title : true
		},{
			name : 'privilegeDesc',
			width : '30%',
			title : true
		}, {
			name : 'id',
			fomatter : formatter,
			width : '20%'
		}
	]
};

$(document).ready(function() {
	// 设置新增/修改权限信息弹出窗口的位置
	$('#addPrivilegeListDiv').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : 200
		});
	});
	// 注册新增/修改窗口 关闭事件
	$('#addPrivilegeListDiv').on('hide.bs.modal', function() {
		clearInput();
		// 销毁表单校验器
		$("#addForm").bootstrapValidator('resetForm', true);
	});
	//回车键搜索
	$(".form-inline").keyup(function (e) {  
		var curKey = e.which; 
		if (curKey == 13){  
			$("#searchBtn").trigger("click");  
	}
	});
	queryPrivilegeList();
	initMenuTree(true);
	showLevelOneTree();
	initFormValidator();
});

function formatter(value) {
	return  '<a onclick="showUpdPrivilegeListDiv(' + value + ')" title="编辑"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;' +
    '<a onclick="delPrivilegeList(' + value + ',this)" title="删除"><i class="fa fa-trash-o fa-lg"></i></a>';
}

function formatterPrivName(value,data){
	return '<span id="tableTdPriName'+data.id+'">'+value+'</span>';
}

/**
 * Created on   2015年7月8日
 * Discription: [初始化表单校验器]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:37:03
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
			priName : {
				validators : {
					notEmpty : {
						message : '请填写权限名称'
					},
					stringLength : {
						max : 20,
						message : '最多输入20个字符'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
		privilegeListSave();
		$("#addForm").bootstrapValidator('resetForm', true);
	});
}
/**
 * Created on   2015年7月8日
 * Discription: [校验]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:37:14
 */
function doValidate() {
	$('#addForm').bootstrapValidator('validate');
}
/**
 * Created on   2015年7月8日
 * Discription: [初始化Tree]
 * @param needExpand
 * @author:     suliang
 * @update:     2015年7月8日 下午1:37:39
 */
function initMenuTree(needExpand) {
	$.ajax(basePath + "/privilege/method_queryMenu.html", {
		type : 'post',
		cache : false,
		async : false,
		success : function(data) {
			$.fn.zTree.init($("#tree"), setting, data);
			$.fn.zTree.getZTreeObj("tree").expandAll(needExpand);
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}
/**
 * Created on   2015年7月8日
 * Discription: [获取所有全勾选的节点id]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:37:54
 */
function setCheckedNodeIds() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var nodes = treeObj.getCheckedNodes(true);
	newSelectedMenuIds = new HashMap();
	for ( var idx in nodes) {
		if (!nodes[idx].getCheckStatus().half) {// 全勾选
			if (!nodes[idx].isParent) { // 叶子节点
				newSelectedMenuIds.put(nodes[idx].id, nodes[idx].id);
			}
		}
	}
}
/**
 * Created on   2015年7月8日
 * Discription: [保存角色菜单]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:38:01
 */
function savePrivilegeMenuRela() {
	if (!$("#selectedPriName").text()) {
		showMessage("请先选择一个权限");
		return;
	}
	setCheckedNodeIds();
	var newSelectedMenuIdsArr = newSelectedMenuIds.values();
	var oldSelectedMenuIdsArr = oldSelectedMenuIds.values();
	var delMenuIds = [];
	var addMenuIds = [];
	if (oldSelectedMenuIdsArr && oldSelectedMenuIdsArr.length > 0) {
		// 获取新增的节点：
		for ( var k in newSelectedMenuIdsArr) {
			if (!oldSelectedMenuIds.containsKey(newSelectedMenuIdsArr[k])) {
				addMenuIds.push(newSelectedMenuIdsArr[k]);
			}
		}
		// 获取删除的节点：
		for ( var i in oldSelectedMenuIdsArr) {
			if (!newSelectedMenuIds.containsKey(oldSelectedMenuIdsArr[i])) {
				delMenuIds.push(oldSelectedMenuIdsArr[i]);
			}
		}
	} else {
		// 获取新增的节点：
		for ( var j in newSelectedMenuIdsArr) {
			addMenuIds.push(newSelectedMenuIdsArr[j]);
		}
	}
	// 保存权限菜单对应关系
	$.ajax({
		url : basePath + '/privilege/method_savePrivilegeMenuRela.html',
		data : {
			privilegeId : checkedPrivilegeId,
			addIds : addMenuIds.join(","),
			delIds : delMenuIds.join(",")
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data && data.message) {
				showMessage(data.message);
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}
/**
 * Created on   2015年7月8日
 * Discription: [选择权限]
 * @param id
 * @author:     suliang
 * @update:     2015年7月8日 下午1:38:22
 */
function selectPrivilege(id) {
	if (id) {
		checkedPrivilegeId = id;
		clearTreeCheck();
		$("#selectedPriName").text($("#tableTdPriName" + id).text());
		$.ajax({
			url : basePath + '/privilege/method_getMenuIds.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				var objList = data.menuIds;
				oldSelectedMenuIds = new HashMap();
				if (objList && objList.length > 0) {
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					var node;
					for ( var idx in objList) {
						// 为当前权限所能访问的菜单，设定选中状态
						node = treeObj.getNodeByParam("id", objList[idx].menuId + "", null);
						treeObj.checkNode(node, true, true);
						oldSelectedMenuIds.put(objList[idx].menuId, objList[idx].menuId);
					}
				}
			},
			error : function(xhr,status){
				oldSelectedMenuIds = new HashMap();
				ajaxErrorHandler(xhr);
			}
		});
	}
}
/**
 * Created on   2015年7月8日
 * Discription: [取消所有被勾选的节点]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:38:59
 */
function clearTreeCheck() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	treeObj.checkAllNodes(false);
}
/**
 * Created on   2015年7月8日
 * Discription: [显示新增权限窗口]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:39:10
 */
function showAddPrivilegeListDiv() {
	$('#myModalLabel').text("添加权限信息");
	$('#addPrivilegeListDiv').modal({
		backdrop : "static",
		show : true
	});
	clearInput();
}
/**
 * Created on   2015年7月8日
 * Discription: [显示修改权限窗口]
 * @param id
 * @author:     suliang
 * @update:     2015年7月8日 下午1:39:19
 */
function showUpdPrivilegeListDiv(id) {
	if (id) {
		$.ajax({
			url : basePath + '/privilege/method_queryPrivilegeListById.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.flag) {
					$('#myModalLabel').text("修改权限信息");
					$("#idTxt").val(data.entity.id);
					$("#priNameTxt").val(data.entity.privilegeName);
					$("#priDescTxt").val(data.entity.privilegeDesc);
					$('#addPrivilegeListDiv').modal({
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
 * Created on   2015年7月8日
 * Discription: [保存权限]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:39:28
 */
function privilegeListSave() {
	var privilegeId = $("#idTxt").val();
	if (privilegeId) { // 修改
		$.ajax({
			url : basePath + '/privilege/method_updPrivilegeList.html',
			data : {
				id : privilegeId,
				privilegeName : $("#priNameTxt").val(),
				privilegeDesc : $("#priDescTxt").val()
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.message) {
					showMessage(data.message);
					$('#addPrivilegeListDiv').modal('hide');
					queryPrivilegeList();
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
				url : basePath + '/privilege/method_addPrivilegeList.html',
				data : {
					privilegeName : $("#priNameTxt").val(),
					privilegeDesc : $("#priDescTxt").val()
				},
				dataType : 'json',
				type : 'post',
				success : function(data) {
					if (data && data.message) {
						showMessage(data.message);
						$('#addPrivilegeListDiv').modal('hide');
						queryPrivilegeList();
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
 * Created on   2015年7月8日
 * Discription: [校验待增加的记录是否已存在]
 * @returns {String}
 * @author:     suliang
 * @update:     2015年7月8日 下午1:39:42
 */
function checkExist() {
	var existFlag = "";
	$.ajax({
		url : basePath + '/privilege/method_checkExistOne.html',
		data : {
			privilegeName : $("#priNameTxt").val()
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
 * Created on   2015年7月8日
 * Discription: [删除权限]
 * @param id
 * @author:     suliang
 * @update:     2015年7月8日 下午1:39:50
 */
function delPrivilegeList(id,obj) {
	$(obj).blur();
	$.messager.confirm("删除权限", "确定要删除这个权限吗？所有与之对应关系（权限-菜单，角色-权限）也随之删除。", function() {
		$.ajax({
			url : basePath + '/privilege/method_delPrivilegeListById.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.message) {
					showMessage(data.message);
					queryPrivilegeList();
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	});
}
/**
 * Created on   2015年7月8日
 * Discription: [清空表单输入域]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:40:03
 */
function clearInput() {
	$("#idTxt").val("");
	$("#priNameTxt").val("");
	$("#priDescTxt").val("");
}
/**
 * Created on   2015年7月8日
 * Discription: [清空查询条件输入域]
 * @author:     suliang
 * @update:     2015年7月8日 下午1:40:13
 */
function clearConditionInput() {
	$("#priNameConditionTxt").val("");
	$("#priDescConditionTxt").val("");
}
/**
 * Created on   2015年7月8日
 * Discription: [ 分页条跳页]
 * @param n
 * @author:     suliang
 * @update:     2015年7月8日 下午1:40:22
 */
function page(n) {
	queryPrivilegeList({
		pageNo : n
	});
}
/**
 * Created on   2015年7月8日
 * Discription: [ 刷新列表]
 * @param list
 * @author:     suliang
 * @update:     2015年7月8日 下午1:40:30
 */
function createTableList(list) {
	$("#tBodyElement").empty();
	var index = 0;
	var tBodyHtml = "";
	for( var obj in list){
		index++;
		tBodyHtml = '<tr>' +
			'	<td><input type="radio" name="priId" onclick="selectPrivilege(' + list[obj].id + ');" id="radio' + list[obj].id + '"/></td>' +
			'	<td>' + index + '</td>' +
			'	<td><span id="tableTdPriName' + list[obj].id + '">' + list[obj].privilegeName + '</span></td>' +
			'	<td>' + list[obj].privilegeDesc + '</td>' +
			'	<td with="100px">' +
			'		<button class="btn btn-primary btn-circle" title="编辑" onclick="showUpdPrivilegeListDiv(' + list[obj].id + ')"><i class="fa fa-pencil"></i></button>&nbsp;' +
			'		<button class="btn btn-primary btn-circle" title="删除" onclick="delPrivilegeList(' + list[obj].id + ',this)"><i class="fa fa-trash-o"></i></button>' +
			'	</td>' +
			'</tr>';
		$("#tBodyElement").append(tBodyHtml);
	}
	if (checkedPrivilegeId) {
		$("#radio" + checkedPrivilegeId).iCheck('check');
	}
}
/**
 * Created on   2015年7月8日
 * Discription: [查询权限列表]
 * @param page
 * @author:     suliang
 * @update:     2015年7月8日 下午1:40:43
 */
function queryPrivilegeList(page) {
	var conditionData = {};
	if (page) {
		// 说明：翻页时使用的查询条件是点击“查询按钮”后，记下的查询条件。而不使用用户临时输入的查询条件。
		conditionData.privilegeName = $("#priNameTxtHdn").val();
		conditionData.privilegeDesc = $("#priDescTxtHdn").val();
		conditionData = mergeData(page, conditionData);
	} else {
		conditionData.privilegeName = $("#priNameConditionTxt").val();
		conditionData.privilegeDesc = $("#priDescConditionTxt").val();
	}
	$.ajax({
		url : basePath + '/privilege/method_queryPrivilegeList.html',
		data : conditionData,
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data && data.resultList) {
				//createTableList(data.resultList);
				grid("priviListTable", conf, data.resultList);
				$('.i-checks').iCheck({
					checkboxClass: 'icheckbox_square-green',
					radioClass: 'iradio_square-green'
				});
				$("input[type='radio']").on('ifClicked', function (event) {
					selectPrivilege(this.value);
				});
				if (data.resultList.length == 0) {
					//showMessage(SEARCH_NON_MSG);
				}
				if (data.page) {
					clearTreeCheck();
					createPageBar(data.page);
				}
				$("#priNameTxtHdn").val(data.priNameTxtHdn);
				$("#priDescTxtHdn").val(data.priDescTxtHdn);
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}

/**
 * Created on   2015年7月31日
 * Discription: [展开全部树节点]
 * @param event
 * @param treeId
 * @param treeNode
 * @author:     suliang
 * @update:     2015年7月31日 上午11:17:28
 */
function nodeExpendHandler(event, treeId, treeNode){
	$.fn.zTree.getZTreeObj("tree").expandAll(true);
}

/**
 * Created on   2015年7月31日
 * Discription: [折叠全部树节点]
 * @param event
 * @param treeId
 * @param treeNode
 * @author:     suliang
 * @update:     2015年7月31日 上午11:17:45
 */
function nodeCollapseHandler(event, treeId, treeNode){
	$.fn.zTree.getZTreeObj("tree").expandAll(false);
}

/**
 * Created on   2015年7月31日
 * Discription: [只展开一级菜单]
 * @author:     suliang
 * @update:     2015年7月31日 下午12:31:47
 */
function showLevelOneTree() {
	zTree = $.fn.zTree.getZTreeObj("tree");
	var node = zTree.getNodeByTId("tree_1");;
	zTree.expandNode(node,true,false,false);
}