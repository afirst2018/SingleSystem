var conf = {
		colNames : ['公告内容', '修改日期', '是否显示', '顺序号', '操作'],
		colModel : [ {
			name : 'noticeText',
			title : true,
			width : 300
		}, {
			name : 'modifyDate',
			date : "yyyy-MM-dd HH:mm:ss",
			width : 100
		}, {
			name : 'needShow',
			width : 80
		}, {
			name : 'orderNum',
			width : 80
		}, {
			name : 'id',
			fomatter : formatter,
			width : 100
		}  ]
	};
var gridPageInfo = {};
var conditionData = {};
function formatter(value) {
	return "<a onclick='updateNotice(" + value + ")' title='编辑'>"
		+ "<i class='fa fa-pencil fa-lg'></i></a>&nbsp;&nbsp;&nbsp;"
		+ "<a onclick='Isdelete(" + value + ",this)' title='删除'>"
		+ "<i class='fa fa-trash-o fa-lg'></i></a></td></tr>"
}
$(function(){
    // 初始化日期控件
	$('#data_5 .input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        todayBtn: 'linked', 	
		language: 'zh-CN'
    }).on('changeDate', function(ev){
    	var startdate = $("#startdate").val();
    	var enddate = $("#enddate").val();
    	if(startdate){
    		$('#enddate').datepicker('setStartDate', startdate);
    	}
		if(enddate){
			$('#startdate').datepicker('setEndDate', enddate);
		}
	});
	initFormValidator();
	$('#noticeDiv').on('hide.bs.modal', function () {
		$( "#addForm" ).bootstrapValidator('resetForm', true);
	});
	doQuery();
    //回车键搜索
	$("#form1").keyup(function (e) {  
		var curKey = e.which; 
        if (curKey == 13){  
             $("#searchBtn").trigger("click");  
         }  
    });
});

function addNotice() { 
	way.clear();
	$("#myModalLabel").text("新增公告信息");
    $("#noticeDiv").modal({
	    backdrop : "static",
	    show : true
	});
    way.set("notice",{needShow:"1"});
}

function updateNotice(id) {
	way.clear();
	$.ajax({
		url : basePath + '/noticeManage/method_selectNoticeById.html',
		data : {id:id},
		dataType : 'json',
		type : 'post',
		success : function(data) {
			way.set("notice",data[0]);
		},
		error : function(xhr,status) {
			ajaxErrorHandler(xhr);
		}
	});
	$("#myModalLabel").text("修改公告信息");
    $("#noticeDiv").modal({
	    backdrop : "static",
	    show : true
	});
}

function selectNotice() {
	var startdate=$("#startdate").val();
	var enddate=$("#enddate").val();
	var noticeText=way.get("searchNotice.noticeCon");
    conditionData={
    	modifyDateStart : startdate,
    	modifyDateEnd : enddate,
    	noticeText : noticeText
    }
    doQuery();
}

function doQuery(page) {
	$.ajax({
        url : basePath + "/noticeManage/method_selectNotice.html",
        data : mergeData({
            pageNo : page
        }, conditionData),
        type : 'post',
        cache : false,
        success : function(result) {
        	var resultList = result['resultList'];
            gridPageInfo['pageNo'] = result['pageNo'];
            gridPageInfo['pageSize'] = result['pageSize'];
            gridPageInfo['totalCount'] = result['totalCount'];
            gridPageInfo['totalPage'] = result['totalPage'];
            if (resultList && resultList.length > 0) {
            	grid("notice", conf, resultList);
                createPageBar(gridPageInfo, "doQuery");
            } else {
            	$("#notice").empty();
            	$("#pageDiv").empty();
            }
        },
        error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}
    });
}

function clearForm() {
	$("#form1")[0].reset();
	$('#enddate').datepicker('setStartDate', null);
	$('#startdate').datepicker('setEndDate', null);
	way.set("searchNotice.noticeCon","");
}

function doValidate() {
	$('#addForm').bootstrapValidator('validate');
}

function initFormValidator() {
	$("#addForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			noticeText : {
				validators : {
					notEmpty : {
						message : '请填写公告内容'
					},
					stringLength : {
						max : 500,
						message : '最多输入500个字符'
					}
				}
			},
			needShow : {
				validators : {
					notEmpty : {
						message : '请选择是否显示'
					}
				}
			},
			orderNum : {
				validators : {
					notEmpty : {
						message : '请填写排序序号'
					},
					stringLength : {
						max : 3,
						message : '最多输入3位大于0的整数'
					},
					regexp : {
						regexp : /^([1-9]\d*)$/,
						message : '最多输入3位大于0的整数'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
		save();
	});
}

function save() {
	var data = way.get("notice");
	var method, tips;
	if($("#idTxt").val()){
		method = "method_updateNotice.html";
		tips = "修改";
	} else {
		method = "method_addNotice.html";
		tips = "新增";
	}
	$.ajax({
        url : basePath + "/noticeManage/" + method,
        data : {
        	id : data.id,
        	noticeText : data.noticeText,
        	needShow : data.needShow,
        	orderNum : data.orderNum
        },
        type : 'post',
        dataType : 'json',
        cache : false,
        success : function(result) {
            if (result == 1) {
            	showMessage(tips + "成功");
            	$('#noticeDiv').modal('hide');
            	$("#addForm").bootstrapValidator('resetForm', true);
            	doQuery();
            } else {
            	showMessage(tips + "失败");
            }
        },
        error : function(xhr,status){
			ajaxErrorHandler(xhr);
		}

    });
}

function Isdelete(id,obj){
	$(obj).blur();
	$.messager.confirm("删除数据", "确定要删除吗？", function() {
		$.ajax({
			url : basePath + '/noticeManage/method_deleteNotice.html',
			data : {id:id},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (data==1) {				
					showMessage("删除成功");
					doQuery();
				} else {
					showMessage("删除失败");
				}
			},
			error : function(xhr,status) {
				ajaxErrorHandler(xhr);
			}
		});
		
	});
	
}