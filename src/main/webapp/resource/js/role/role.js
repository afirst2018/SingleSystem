var conf = {
	colNames : [
		'角色名称', '角色描述', '对应权限', '操作'
	],
	colModel : [
		{
			name : 'roleName',
			width : 100,
			title : true
		},{
			name : 'roleDesc',
			width : 200,
			title : true
		},
		{
			name : 'id',
			width : 100,
            fomatter : privilegeBtnFormatter
		}, {
			name : 'id',
			fomatter : formatter,
			width : 120
		}
	]
};
$(document).ready(function() {
    // 注册添加/修改角色窗口关闭事件
	$('#addRole').on('hide.bs.modal', function() {
        $("#idTxt").val("");
        $("#roleName").val("");
        $("#roleDesc").val("");
		$("#roleForm").bootstrapValidator('resetForm', true);
	});
	initFormValidator();
    queryRole();
    //回车键搜索
	$(".form-inline").keyup(function (e) {
		var curKey = e.which; 
        if (curKey == 13){  
             $("#searchBtn").trigger("click");  
         }  
    });
});

function formatter(value) {
	return  '<a onclick="showUpdRoleDiv(' + value + ',this)" title="编辑"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;' +
            '<a onclick="delRole(' + value + ',this)" title="删除"><i class="fa fa-trash-o fa-lg"></i></a>';
}

function privilegeBtnFormatter(value,data) {
    return '<a class="badge badge-primary" href="javascript:getPrivilegeByRoleId(\''+data.id+'\');">设置权限</a>';
}

/**
 * Created on   2016年5月16日
 * Discription: [查询角色]
 * @author:     linlong
 * @param page
 * @update:     2016年5月16日 下午3:29:23
 */
function queryRole(page) {
    var conditionData = {};
    if (page) {
        // 说明：翻页时使用的查询条件是点击“查询按钮”后，记下的查询条件。而不使用用户临时输入的查询条件。
        conditionData.roleName = $("#roleNameConditionTxtHdn").val();
        conditionData = mergeData(page, conditionData);
    } else {
        conditionData.roleName = $("#roleNameConditionTxt").val();
    }
    $.ajax({
        url : basePath + '/role/method_queryRole.html',
        data : conditionData,
        dataType : 'json',
        type : 'post',
        success : function(data) {
            if (data && data.resultList) {
                console.log(data.resultList);
                grid("roleTable", conf, data.resultList);
                if (data.page) {
                    createPageBar(data.page);
                }
                $("#roleNameConditionTxtHdn").val(data.roleNameConditionTxtHdn);
            }
        },
        error : function(xhr,status){
            ajaxErrorHandler(xhr);
        }
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [分页条跳页]
 * @param n
 * @author:     linlong
 * @update:     2016年5月16日 下午3:29:23
 */
function page(n) {
    queryRole({
        pageNo : n
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [清空查询条件输入域]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:29:12
 */
function clearConditionInput() {
    $("#searchForm")[0].reset();
}

/**
 * Created on   2016年5月16日
 * Discription: [初始化表单校验器]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function initFormValidator() {
    $("#roleForm").bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            roleName: {
                validators: {
                    notEmpty: {
                        message: '请填写角色名'
                    },
                    stringLength: {
                        min: 3,
                        max: 16,
                        message: '角色名长度在3-16位'
                    },
                    remote: {
                        type: 'POST',
                        delay: 1000,
                        url: 'method_checkrolename.html',
                        message: '角色名已经存在'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z_]+$/,
                        message: '角色名仅能使用字母下划线'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e, data) {
        roleSave();
        $("#roleForm").bootstrapValidator('resetForm', true);
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [显示新增角色窗口]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function showAddRoleDiv() {
    $("#roleName").val("");
    $("#roleDesc").val("");
    $('#myModalLabel').html('新增角色');
    $("#roleName").removeAttr('disabled');
    $('#id_update').val(-1);
    $('#addRole').modal({
        backdrop : "static",
        show : true
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [校验方法]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function doValidate() {
    $('#roleForm').bootstrapValidator('validate');
}

/**
 * Created on   2016年5月16日
 * Discription: [新增/修改角色信息]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function roleSave() {
    var roleId = $("#idTxt").val();
    if (roleId) { // 修改
        $.ajax({
            url : basePath + '/role/method_updaterole.html',
            data : {
                id : roleId,
                roleDesc : $("#roleDesc").val()
            },
            dataType : 'json',
            type : 'post',
            success : function(data) {
                if(data) {
                    showMessage("修改成功");
                    queryRole();
                    $('#addRole').modal('hide');
                }else{
                    showMessage("修改失败");
                }
            },
            error : function(xhr,status){
                ajaxErrorHandler(xhr);
            }
        });
    } else { // 新增
        $.ajax({
            url : basePath + '/role/method_addrole.html',
            data : {
                roleName : $("#roleName").val(),
                roleDesc : $("#roleDesc").val()
            },
            dataType : 'json',
            type : 'post',
            success : function(data) {
                if(data.result){
                    showMessage("角色添加成功");
                    queryRole();
                    $('#addRole').modal('hide');
                }else{
                    if(data.msg){
                        showMessage(msg);
                    }
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
 * Discription: [显示修改角色窗口]
 * @author:     linlong
 * @param id
 * @update:     2016年5月16日 下午3:25:12
 */
function showUpdRoleDiv(id) {
    $('#myModalLabel').html('修改角色信息');
    if (id) {
        $.ajax({
            url : basePath + '/role/method_getRoleInfo.html',
            data : {
                id : id
            },
            dataType : 'json',
            type : 'post',
            success : function(data) {
                if(data){
                    $('#idTxt').val(id);
                    $("#roleName").val(data["roleName"]);
                    $("#roleDesc").val(data["roleDesc"]);
                    $("#roleName").attr('disabled','disabled');
                    $('#addRole').modal({backdrop:"static",show:true});
                }else{
                    showMessage("查询角色信息失败")
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
 * Discription: [删除]
 * @param id
 * @author:     linlong
 * @update:     2016年5月16日 下午3:28:23
 */
function delRole(id,obj) {
    $(obj).blur();
    $.messager.confirm("删除数据", "确定要删除吗？", function() {
        $.ajax({
            url : basePath + '/role/method_deleterole.html',
            data : {
                id : id
            },
            dataType : 'json',
            type : 'post',
            success : function(data) {
                if(data){
                    showMessage("删除角色成功！");
                    queryRole();
                }else{
                    showMessage("删除角色失败！");
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
 * Discription: [根据角色ID获取该角色拥有的所有权限]
 * @param id
 * @author:     linlong
 * @update:     2016年5月16日 下午3:28:23
 */
function getPrivilegeByRoleId(roleId) {
	$(":checkbox").prop("checked",false);
    $.ajax({
        url : basePath + '/role/method_getroleauth.html',
        data : {
            id : roleId
        },
        dataType : 'json',
        type : 'post',
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var privilege_id = data[i]['privilegeId'];
                // 该角色具有的权限，checkbox设定为选中状态
                $("[value="+privilege_id+"]:checkbox").prop("checked",true);
            }
            $('#saveButton').attr('onclick','javascript:saveAuth('+roleId+')');
            $('#setAuth').modal({backdrop:"static",show:true });
            $('.i-checks').iCheck({
				checkboxClass: 'icheckbox_square-green',
				radioClass: 'iradio_square-green'
			});
        },
        error : function(xhr,status){
            ajaxErrorHandler(xhr);
        }
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [保存权限]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function saveAuth(id) {
    var authArray = [];
    $(":checkbox").each(function(){
        if($(this).is(':checked')){
            var auth = $(this).val();
            authArray.push(auth);
        }
    });
    $.ajax({
        url : basePath + '/role/method_saveroleauth.html',
        data:{
            rid : id,
            auth : authArray
        },
        type:'post',
        cache:false,
        dataType:'json',
        success:function(data) {
            showMessage("角色权限设置成功!");
            $('#setAuth').modal('hide');
        },
        error : function(xhr,status){
            ajaxErrorHandler(xhr);
        }
    });
}

