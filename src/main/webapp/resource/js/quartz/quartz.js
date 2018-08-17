var conf = {
    colNames: [
        '任务名称', '任务分组', '任务状态', '是否按顺序执行', '任务执行顺序', '任务创建时间', '任务更新时间', '任务描述', '任务执行时调用的任务类', 'cron表达式', '操作'
    ],
    colModel: [
        {
            name: 'jobName',
            width: 100,
            title: true
        }, {
            name: 'jobGroup',
            width: 80,
            title: true
        }, {
            name: 'jobStatus',
            decode:'JOB_STATUS',
            codeList:codeList,
            width: 50,
            title: true
        }, {
            name: 'isOrderBy',
            decode:'isTrue',
            codeList:codeList,
            width: 50,
            title: true
        }, {
            name: 'orderNo',
            width: 80,
            title: true
        }, {
            name: 'createTime',
            date: "yyyy-MM-dd",
            width: 80,
            title: true
        }, {
            name: 'updateTime',
            date: "yyyy-MM-dd",
            width: 80,
            title: true
        }, {
            name: 'description',
            width: 150,
            title: true
        }, {
            name: 'beanClass',
            width: 200,
            title: true
        }, {
            name: 'cronExpression',
            width: 100,
            title: true
        }, {
            name: 'id',
            fomatter: formatter,
            width: 50
        }
    ]
};

$(document).ready(function () {
    queryScheduleJob();
    //回车键搜索
    $(".form-inline").keyup(function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            $("#searchBtn").trigger("click");
        }
    });
    initFormValidator();
    $("#jobTable").on("click", "a", function () {
        var jobList = way.get("jobList");
        way.set("updateForm", jobList[$(this).parents("tr").index()])
    })
});

function formatter(value) {
    return '<a onclick="showUpdateDiv(\'' + value + '\')" title="编辑"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;' +
        '<a onclick="runJobNow(\'' + value + '\')" title="立即执行"><i class="fa fa-caret-square-o-right fa-lg"></i></a>';
}

function showUpdateDiv() {
    $('#addCodeListDiv').modal({
        backdrop: "static",
        show: true
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [校验方法]
 * @author:     linlong
 * @update:     2016年5月16日 下午3:25:12
 */
function doValidate() {
    $('#updateForm').bootstrapValidator('validate');
}

function initFormValidator() {
    $("#updateForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            jobName: {
                validators: {
                    notEmpty: {
                        message: '请填写任务名称'
                    },
                    stringLength: {
                        max: 50,
                        message: '最多输入50个字符'
                    }
                }
            },
            jobGroup: {
                validators: {
                    notEmpty: {
                        message: '请填写任务组名称'
                    },
                    stringLength: {
                        max: 50,
                        message: '最多输入50个字符'
                    }
                }
            },
            jobStatus: {
                validators: {
                    notEmpty: {
                        message: '请选择任务状态'
                    },
                    stringLength: {
                        max: 50,
                        message: '最多输入50个字符'
                    }
                }
            },
            orderNo: {
                validators: {
                    stringLength: {
                        max: 2,
                        message: '最多输入2个字符'
                    },
                    regexp: {
                        regexp: /^[1-9]\d*/,
                        message: '只能输入数字'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: '请填写任务描述信息'
                    },
                    stringLength: {
                        max: 100,
                        message: '最多输入100个字符'
                    }
                }
            },
            cronExpression: {
                validators: {
                    notEmpty: {
                        message: '请填写cron表达式，设定任务执行时间'
                    },
                    stringLength: {
                        max: 100,
                        message: '最多输入100个字符'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e, data) {
        updateScheduleJob();
        $("#updateForm").bootstrapValidator('resetForm', true);
    });
}

/**
 * Created on   2016年5月16日
 * Discription: [更新任务]
 * @param id
 * @author:     tianci
 * @update:     2016年5月16日 下午3:28:23
 */
function updateScheduleJob() {
    var conditionData = way.get("updateForm");
    conditionData.lastProcessTime=new Date();
    conditionData.nextProcessTime=new Date();
    conditionData.updateTime=new Date();
    conditionData.createTime=new Date();
    $.ajax({
        url: basePath + '/QuartzController/method_updateScheduleJob.html',
        data: conditionData,
        dataType: 'json',
        type: 'post',
        success: function (data) {
            if (data > 0) {
                showMessage("修改成功！");
                queryScheduleJob();
                $('#addCodeListDiv').modal('hide');
            }
        },
        error: function (xhr, status) {
            ajaxErrorHandler(xhr);
        }
    });
}


/**
 * Created on   2016年5月16日
 * Discription: [清空查询条件输入域]
 * @author:     tianci
 * @update:     2016年5月16日 下午3:29:12
 */
function clearConditionInput() {
    $("#searchForm")[0].reset();
    ;
    // $("#realName").val("");
}


/**
 * Created on   2016年5月16日
 * Discription: [查询]
 * @author:     tianci
 * @param page
 * @update:     2016年5月16日 下午3:29:23
 */
function queryScheduleJob(pageNo) {
    var conditionData = {jobName: $("#jobName").val(), jobGroup: $("#jobGroup").val(), pageNo: pageNo};
    $.ajax({
        url: basePath + '/QuartzController/method_getScheduleJob.html',
        data: conditionData,
        dataType: 'json',
        type: 'post',
        success: function (data) {
            way.set("jobList", data.jobList);
            var quartzStart = data.quartzStart;
            var jobList = data.jobList;
            if (quartzStart == "on") {
                $("#quartzStatus").text("定时任务状态：开启");
            } else {
                $("#quartzStatus").text("定时任务状态：关闭");
            }
            if (data.jobList) {
                grid("jobTable", conf, jobList);
                createPageBar(data.page, "queryScheduleJob");
            }
        },
        error: function (xhr, status) {
            ajaxErrorHandler(xhr);
        }
    });
}

function runJobNow(id) {
    $.ajax({
        url: basePath + '/QuartzController/method_runJobNow.html',
        data: {id: id},
        dataType: 'json',
        type: 'post',
        success: function (data) {
            if (data == 1) {
                showMessage("运行成功！");
            } else if (data == 0) {
                showMessage("运行失败！");
            } else if (data==-1) {
                showMessage("定时任务队列中无此任务（需先启动定时任务，并将此定时任务添加到执行队列中）");
            }
        },
        error: function (xhr, status) {
            ajaxErrorHandler(xhr);
        }
    });
}
