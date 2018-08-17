$(document).ready(function() {
    initPwdUpdVali();
    $(".dropdown-toggle").on("click",function () {
        $("#right-sidebar").removeClass();
    })
});
function initPwdUpdVali() {
    $("#pwdUpdForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields : {
            opwd : {
                validators : {
                    notEmpty : {
                        message : '请填写原密码'
                    },
                    stringLength : {
                        max : 20,
                        message : '最多输入20个字符'
                    },
                    remote: {
                        type: 'POST',
                        delay: 1000,
                        url: basePath +'/sysuser/method_checkPwd.html',
                        message: '原密码不正确'
                    }
                }
            },
            npwd : {
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
            qpwd : {
                validators : {
                    notEmpty : {
                        message : '请填写确认密码'
                    },
                    stringLength : {
                        max : 20,
                        message : '最多输入20个字符'
                    },
                    identical:{
                        field:'npwd',
                        message : '两次输入的密码不一致'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e, data) {
        doChangePwd();
        $("#pwdUpdForm").bootstrapValidator('resetForm', true);
    });
}

function doChangePwd() {
    $.ajax({
        url : basePath + '/sysuser/method_changePwdBySelf.html',
        data : {
            npwd : $("#npwdTxt").val()
        },
        dataType : 'json',
        type : 'post',
        success : function(data) {
            if (data && data.valid) {
                showMessage("修改成功");
                setTimeout('window.location.href= basePath + "/sys_logout.html"',1600);
            }else {
                showMessage("修改失败");
            }
        },
        error : function(xhr,status){
            ajaxErrorHandler(xhr);
        }
    });
}
