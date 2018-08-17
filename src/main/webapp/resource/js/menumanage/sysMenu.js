var zTree;
var demoIframe;
var setting = {
    view : {
        dblClickExpand : false,
        showLine : true,
        selectedMulti : false
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
    },
    callback : {
	    onClick : function(event, treeId, treeNode) {
		    $("#form1")[0].reset();
		    // $("#form1").formValidation('resetForm', true);
		    $("#form1").bootstrapValidator('resetForm', true);
		    $("#id").val('');
		    $("#parentId").val('');
		    if (treeNode.id == 1) {
			    $("#id").val(treeNode.id);
			    return;
		    }
		    $("#parentId").val(treeNode.parentId);
		    $("#menuName").val(treeNode.menuName);
		    $("#menuUrl").val(treeNode.menuUrl);
		    $("#image").val(treeNode.image);
		    $("#orderNum").val(treeNode.orderNum);
		    //$("input:radio[name=isleaf][value='" + treeNode.isleaf + "']").prop("checked", true);
		    $("input:radio[name=isleaf][value='" + treeNode.isleaf + "']").iCheck('check');
		    if (treeNode.isleaf == 1) {
			    $("#menuUrl").attr("disabled", false);
		    } else {
			    $("#menuUrl").attr("disabled", true);
		    }
		    $("#id").val(treeNode.id);
	    }
    }
};
/**
 * Created on   2015年7月8日
 * Discription: [查询菜单]
 * @param needExpand
 * @param nodeId
 * @author:     linlong
 * @update:     2015年7月8日 下午1:26:35
 */
function queryMenu(needExpand, nodeId) {
	$.ajax(basePath + "/menuManager/method_queryMenu.html", {
	    type : 'post',
	    cache : false,
	    async : false,
	    success : function(data) {
		    var t = $("#tree");
		    $.fn.zTree.init(t, setting, data);
		    zTree = $.fn.zTree.getZTreeObj("tree");
		    zTree.expandAll(needExpand);
		    showLevelOneTree()
		    if (nodeId) {
			    var node = zTree.getNodesByParam("id", nodeId, null);
			    if (node.length > 0) {
				    zTree.selectNode(node[0], true);
				    setting.callback.onClick(event, "tree", node[0]);
			    }
		    }
	    },
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
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

$(document).ready(function() {
	queryMenu(true);
	$("input[type='radio']").on('ifClicked', function (event) {
		var isleaf = this.value;
		if (isleaf == 1) {
			$("#menuUrlRequired").show();
			$("#menuUrl").attr("disabled", false);
		} else {
			$("#menuUrlRequired").hide();
			$("#menuUrl").attr("disabled", true);
			$("#menuUrl").val("");
		}
	});
	showLevelOneTree();
	$("#form1").bootstrapValidator({
	    message : 'This value is not valid',
	    feedbackIcons : {
	        valid : 'glyphicon glyphicon-ok',
	        invalid : 'glyphicon glyphicon-remove',
	        validating : 'glyphicon glyphicon-refresh'
	    },
	    fields : {
	        menuName : {
		        validators : {
		            notEmpty : {
			            message : '请填写菜单名称'
		            },
		            stringLength : {
		                max : 16,
		                message : '不能超过16个字符'
		            }
		        }
	        },
	        orderNum : {
		        validators : {
		            notEmpty : {
			            message : '请填写顺序号'
		            },
		            regexp : {
		                regexp : /^([1-9]\d*)$/,
		                message : '请输入正确的顺序号'
		            }
		        }
	        },
	        image : {
		        validators : {
			        stringLength : {
			            max : 330,
			            message : '不能超过330个字符'
			        }
		        }
	        },
	        menuUrl : {
		        validators : {
		            notEmpty : {
			            message : '请填写菜单地址'
		            },
		            stringLength : {
		                max : 330,
		                message : '不能超过330个字符'
		            },
		            regexp : {
		                regexp : /^[^\*]*$/,
		                message : '请输入正确的菜单地址（不能包含*号）'
		            }
		        }
	        }
	    }
	}).on('success.form.bv', function(e, data) {
		saveMenu();
	});
	
	$('.i-checks').iCheck({
		checkboxClass: 'icheckbox_square-green',
		radioClass: 'iradio_square-green'
	});
});
/**
 * Created on   2015年7月8日
 * Discription: [新增菜单]
 * @author:     linlong
 * @update:     2015年7月8日 下午1:27:18
 */
function addMenu() {
	var selectedName = zTree.getSelectedNodes();
	if (selectedName && selectedName.length > 0) {
		if (selectedName[0].isleaf == 1) {
			showMessage("该节点为叶子节点，不能新增子节点");
			return;
		}
		$("#form1")[0].reset();
		$("#parentId").val(selectedName[0].id);
		$("#id").val("");
		$.ajax(basePath + "/menuManager/method_insertMenu.html", {
		    data : {
			    parentId : $("#parentId").val()
		    },
		    type : 'post',
		    cache : false,
		    success : function(data) {
			    if (data > 1) {
				    showMessage("新增菜单成功");
				    $("#id").val(data);
				    queryMenu(true, data);
			    } else {
				    showMessage("新增菜单失败");
			    }
		    },
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	} else {
		showMessage("请选择节点");
	}
}
/**
 * Created on   2015年7月8日
 * Discription: [删除菜单]
 * @author:     linlong
 * @update:     2015年7月8日 下午1:27:29
 */
function deleteMenu() {
	var id = $("#id").val();
	if (!id) {
		showMessage("请选择要删除的节点");
		return;
	}
	if (id == 1) {
		showMessage("根节点不能被删除");
		return;
	}
	$.messager.confirm("删除菜单", "确定要删除该菜单吗？", function() {
		$.ajax(basePath + "/menuManager/method_deleteMenu.html", {
		    data : {
			    id : id
		    },
		    type : 'post',
		    cache : false,
		    success : function(data) {
			    if (data == 1) {
				    showMessage("删除成功");
				    $("#form1")[0].reset();
				    $("#id").val('');
				    $("#parentId").val('');
				    queryMenu(true);
			    } else if (data == 2) {
				    showMessage("数据被引用，无法删除");
			    } else {
				    showMessage("删除失败");
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
 * Discription: [表单校验]
 * @author:     linlong
 * @update:     2015年7月8日 下午1:27:52
 */
function checkForm() {
	if (!$("#parentId").val()) {
		showMessage("请选择菜单");
		return;
	}
	$("#form1").bootstrapValidator('validate');
}
/**
 * Created on   2015年7月8日
 * Discription: [保存菜单]
 * @author:     linlong
 * @update:     2015年7月8日 下午1:28:15
 */
function saveMenu() {
	var isleaf = $('input:radio[name=isleaf]:checked').val();
	if (!isleaf) {
		showMessage("请选择是否为叶子节点");
		return;
	}
	var value = $("#menuUrl").val();
	if (isleaf == 1) {
		if (!value) {
			showMessage("请输入url");
			$("#menuUrl").focus();
			return;
		}
	} else {
		if (value) {
			showMessage("非叶子节点的菜单url不能有值");
			$("#menuUrl").focus();
			return;
		}
	}
	var url = basePath + "/menuManager/method_updateMenuById.html";
	$.ajax(url, {
	    data : {
	        id : $("#id").val(),
	        parentId : $("#parentId").val().trim(),
	        menuName : $("#menuName").val().trim(),
	        menuUrl : $("#menuUrl").val().trim(),
	        image : $("#image").val().trim(),
	        orderNum : $("#orderNum").val().trim(),
	        isleaf : isleaf
	    },
	    type : 'post',
	    cache : false,
	    success : function(data) {
		    if (data == 1) {
			    showMessage("保存成功");
			    queryMenu(true);
		    } else if (data == 2) {
			    showMessage("包含子菜单的菜单不能修改为叶子节点");
		    } else {
			    showMessage("保存失败");
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