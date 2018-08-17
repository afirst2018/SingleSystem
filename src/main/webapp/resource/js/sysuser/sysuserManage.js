var conf = {
	colNames : [
		'用户名','真实姓名', '手机', '电子邮箱',  '创建时间','所属角色', '操作'
	],
	colModel : [
		{
			name : 'username',
			width : 100,
			title : true
		},{
			name : 'realName',
			width : 100,
			title : true
		},{
			name : 'mobile',
			width : 100,
			title : true
		},
		{
			name : 'email',
			width : 100,
		},{
			name : 'createOnStr',
			width : 100,
			title : true
		},{
			name : 'id',
			width : 80,
			fomatter : roleFormatter
		}, {
			name : 'id',
			fomatter : formatter,
			width : 120
		}
	]
};


$(document).ready(function() {
	// 注册新增/修改窗口 关闭事件
	$('#userDiv').on('hide.bs.modal', function() {
		$("*").poshytip('hide');
	});
	// 注册修改密码窗口 关闭事件
	$('#changePwd').on('hide.bs.modal', function () {
		$("#cpassword").val("");
		$("#crepassword").val("");
		$("#pwdForm").bootstrapValidator('resetForm', true);
	});
	// 注册停用账号窗口 关闭事件
	$('#disabledDiv').on('hide.bs.modal', function () {
		$("#reasonDesc").val("");
		$("#disabledForm").bootstrapValidator('resetForm', true);
		$("#userIdHdn").val("");
	});
	querySysUserList();
	initFormValidator();
});

function querySysUserList(page) {
	var conditionData = {};
	if (page) {
		// 说明：翻页时使用的查询条件是点击“查询按钮”后，记下的查询条件。而不使用用户临时输入的查询条件。
		conditionData.realName = $("#realNameTxtHdn").val();
		conditionData.mobile = $("#mobileTxtHdn").val();
		conditionData.username = $("#usernameTxtHdn").val();
		conditionData.enabledStr = $("#enabledTxtHdn").val();
		conditionData = mergeData(page, conditionData);
	} else {
		conditionData.realName = $("#realNameTxt").val();
		conditionData.mobile = $("#mobileTxt").val();
		conditionData.username = $("#usernameTxt").val();
		conditionData.enabledStr = $("#enabledSel").val();
	}
	$.ajax({
		url : basePath + '/sysuser/method_querySysUserList.html',
		data : conditionData,
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data && data.resultList) {
				grid("userListTable", conf, data.resultList);
				if (data.page) {
					createPageBar(data.page);
				}
				$("#realNameTxtHdn").val(data.realNameTxtHdn);
				$("#mobileTxtHdn").val(data.mobileTxtHdn);
				$("#usernameTxtHdn").val(data.usernameTxtHdn);
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}

function formatter(value,data) {
	var rtnStr = "";
	var iconStr = "";
	var btnStr = "";
	if(data.enabledStr == "是"){
		iconStr = "fa fa-toggle-on fa-lg";
		btnStr = '<a onclick="showStopUsingDiv(' + value + ',this)" title="启用/停用"><i class="'+iconStr+'"></i></a>';
	}else{
		iconStr = "fa fa-toggle-off fa-lg";
		btnStr = '<a onclick="startUsing(' + value + ',this)" title="启用/停用"><i class="'+iconStr+'"></i></a>';
	}
	rtnStr = '<a onclick="showUpdUserDiv(' + value + ',this)" title="编辑"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;' +
			 '<a onclick="showUpdPwdDiv(' + value + ',this)" title="修改密码"><i class="fa fa-key fa-lg"></i></a>&nbsp;&nbsp;'+ 
			 '<a onclick="delUserRoleById(' + value + ',this)" title="删除"><i class="fa fa-trash-o fa-lg"></i></a>&nbsp;&nbsp;' + btnStr;
	return rtnStr;
}

function roleFormatter(value){
	return '<a class="badge badge-primary" href="javascript:setRole('+value+');">设置角色</a>';
}

function showUpdUserDiv(id,obj) {
	$(obj).blur();
	if (id) {
		$.ajax({
			url : basePath + '/sysuser/method_getSysUserById.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data) {
					$('#myModalLabel').text("修改用户信息");
					$("#userForm")[0].reset();
					$("#userPk").val(data.id);
					$("#username").attr("disabled",true);
					$("#username").val(data.username);
					$("#username").attr("data-vali","");
					$('.i-checks').iCheck({
						checkboxClass: 'icheckbox_square-green',
						radioClass: 'iradio_square-green'
					});
					if(data.sex == '1'){
						$('#maleRadio').iCheck('check');
					}else{
						$('#femaleRadio').iCheck('check');
					}
					$("#realName").val(data.realName);
					$("#mobile").val(data.mobile);
					$("#email").val(data.email);
					$("#qq").val(data.qq);
					// 隐藏密码，确认密码输入域，并取消验证
					$("#pwdDiv").hide();
					$("#repwdDiv").hide();
					$("#pwd").attr("data-vali","");
					$("#repwd").attr("data-vali","");
					$.doRegistValidator("formDiv");
					$('#userDiv').modal({
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
	}else{
		showMessage("无该账号的详细信息");
	}
}

function triggerIsEnabledSelBlur(){
	$("#isEnabledSelValiInput").trigger("change");
}

function saveUser() {
	if($.doValidate("formDiv")){
		var userPkVal = $("#userPk").val();
		if(userPkVal){ // 修改
			$.ajax({
				url : basePath + '/sysuser/method_updateUser.html',
				data :collectUserData(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					if (data == "1") {
						showMessage("修改成功");
						$("#userForm")[0].reset();
						$('#userDiv').modal("hide");
						querySysUserList();
					} else {
						showMessage("修改失败");
					}
				},
				error : function(xhr,status){
					ajaxErrorHandler(xhr);
				}
			});
		}else{ // 新增
			$.ajax({
				url : basePath + '/sysuser/method_addUser.html',
				data :collectUserData(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					if (data == "1") {
						showMessage("新增成功");
						$("#userForm")[0].reset();
						$('#userDiv').modal("hide");
						querySysUserList();
					} else {
						showMessage("新增失败");
					}
				},
				error : function(xhr,status){
					ajaxErrorHandler(xhr);
				}
			});
		}
	}
}

function collectUserData() {
	var formData = {};
	formData["realName"] = $("#realName").val();
	formData["sex"] = $('input[name="sexType"]:checked ').val();
	formData["id"] = $("#userPk").val();
	formData["mobile"] = $("#mobile").val();
	formData["email"] = $("#email").val();
	formData["qq"] = $("#qq").val();
	// 新增时，使用下面两个值，修改时，无视
	formData["password"] = $("#pwd").val();
	formData["username"] = $("#username").val();
	return formData;
}

function showStopUsingDiv(userId,obj) {
	$(obj).blur();
    $("#userIdHdn").val(userId);
	$('#disabledDiv').modal({
		backdrop : "static",
		show : true
	});
}

// 初始化表单验证
function initFormValidator() {
	$("#disabledForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			reasonDesc : {
				validators : {
					notEmpty : {
						message : '停用原因必填'
					},
					stringLength : {
						max : 200,
						message : '最多输入200个字'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
        var userId = $("#userIdHdn").val();
        if(userId){
            stopUsing(userId,$("#reasonDesc").val());
        }else{
            showMessage("系统异常，无法获取账号信息，请稍后再试。")
        }
	});
}

// 验证表单，并执行退回操作
function validateStopReason() {
	$('#disabledForm').bootstrapValidator('validate');
}

function stopUsing(p_id,p_reason) {
	$.ajax({
		url : basePath + '/sysuser/method_updateDisabledAccount.html',
		data : {
			id : p_id,
			reason:p_reason
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data == "1") {
				showMessage("操作成功");
				$("#disabledForm").bootstrapValidator('resetForm', true);
				$('#disabledDiv').modal("hide");
				querySysUserList();
			} else {
				showMessage("操作失败");
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}

function startUsing(id,obj) {
	$(obj).blur();
	$.messager.confirm("消息提示", "确定要恢复启用该账号吗？", function() {
		$.ajax({
			url : basePath + '/sysuser/method_updateEnabledAccount.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data == "1") {
					showMessage("操作成功");
					querySysUserList();
				}else{
					showMessage("操作失败");
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	});

}

function clearConditionInput() {
	$("#conditionInput")[0].reset();
}

//
function showUpdPwdDiv(userId,obj) {
	$(obj).blur();
	$('#changePwd').modal({
		backdrop : "static",
		show : true
	});
	initPwdValidator(userId);
}

// 初始化表单验证
function initPwdValidator(userId) {
	$("#pwdForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			cpassword : {
				validators : {
					notEmpty : {
						message : '请填写新密码'
					},
					stringLength : {
						max : 20,
						message : '最多输入20个字符'
					}
				}
			},
			crepassword : {
				validators : {
					notEmpty : {
						message : '请填写确认密码'
					},
					stringLength : {
						max : 20,
						message : '最多输入20个字符'
					},
					identical:{
						field:'cpassword',
						message : '两次输入的密码不一致'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
		changePwdByManager(userId);
	});
}

function validatePwd() {
	$('#pwdForm').bootstrapValidator('validate');
}

function changePwdByManager(userId) {
	$.ajax({
		url : basePath + '/sysuser/method_changePwdByManager.html',
		data : {
			userId : userId,
			npwd:$("#cpassword").val()
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {
			if (data == "1") {
				showMessage("修改密码成功");
			}else{
				showMessage("修改密码失败");
			}
			$("#pwdForm").bootstrapValidator('resetForm', true);
			$('#changePwd').modal("hide");
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
}

function showAddUserDiv() {
	$('#myModalLabel').text("新增用户信息");
	$("#userForm")[0].reset();
	$("#userPk").val("");
	$("#username").attr("disabled",false);
	$("#username").val("");
	$('.i-checks').iCheck({
		checkboxClass: 'icheckbox_square-green',
		radioClass: 'iradio_square-green'
	});
	$('#maleRadio').iCheck('check');
	$("#realName").val("");
	$("#mobile").val("");
	$("#email").val("");
	$("#qq").val("");
	$("#pwd").val("");
	$("#repwd").val("");
	$("#pwdDiv").show();
	$("#repwdDiv").show();
	$("#pwd").attr("data-vali","[{type:'required',msg:'密码必填'},{type:'maxLength',max:20}]");
	$("#repwd").attr("data-vali","[{type:'required',msg:'确认密码必填'},{type:'maxLength',max:20},{type:'identical',field_id:'pwd',msg:'两次输入的密码不一致'}]");
	var url = basePath + "/sysuser/method_checkUsername.html";
	$("#username").attr("data-vali","[{type:'required',msg:'用户名必填'},{type:'maxLength',max:20},{type:'remote',url:'"+url+"',msg:'该用户名已存在'}]");
	$.doRegistValidator("formDiv");
	$('#userDiv').modal({
		backdrop : "static",
		show : true
	});
}

function setRole(id){
	if(!id){
		return;
	}
	$.ajax({
		url:basePath + '/sysuser/method_getuserrole.html',
		data:{
			id : id
		},
		type:'post',
		cache:false,
		dataType:'json',
		success:function(data) {
			$(":checkbox").each(function(){
				$(this).attr("checked",false);
			});
			for(var i = 0;i<data.length;i++)
			{
				var roleid = data[i]['roleId'];
				$("[value="+roleid+"]:checkbox").prop("checked",true);
			}
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	});
	$('#saveButton').attr('onclick','javascript:saveRole('+id+')');
	$('#setRole').modal({backdrop:"static",show:true });
}


function saveRole(uid){
	var roleArray = new Array();
	$(":checkbox").each(function(){
		if($(this).is(':checked')){
			var role = $(this).val();
			roleArray.push(role);
		}
	});
	$.ajax({
		url:basePath + '/sysuser/method_saveuserrole.html',
		data:{
			uid : uid,
			role : roleArray
		},
		type:'post',
		cache:false,
		dataType:'json',
		success:function(data) {
			showMessage('用户角色设置成功!');
			$('#setRole').modal('hide');
		},
		error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
	})
}

function enableChangeHandler(obj) {
	$("#enabledTxtHdn").val(obj.value);
}

function page(n) {
	querySysUserList({
		pageNo : n
	});
}

/**
 * Created on   2016年10月26日
 * Discription: [删除用户]
 * @param id
 * @author:     suliang
 * @update:     2016年10月26日 下午1:39:50
 */
function delUserRoleById(id,obj) {
	$(obj).blur();
	$.messager.confirm("删除用户", "确定要删除这个用户吗？", function() {
		$.ajax({
			url : basePath + '/sysuser/method_deluserrolebyid.html',
			data : {
				id : id
			},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data && data.message) {
					showMessage(data.message);
					querySysUserList();
				}
			},
			error : function(xhr,status){
				ajaxErrorHandler(xhr);
			}
		});
	});
}