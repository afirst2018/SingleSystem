$(function() {
	//$("#myModal").modal(); //初始化默认设置
	//$("#myModal").modal({keyboard:false}); //键值设置
	//$("#myModal").modal('show'); //初始化，立即调用show
	
	var uls = $('.sidebar-nav > ul > *').clone();
	uls.addClass('visible-xs');
	//$('#main-menu').append(uls.clone());
});

var gIndex = -1;
function addUser(){
	$('#updateUser').css("display","block");
	$("#updateUser").dialog({
        title: "添加用户"
        , 'class': "mydialog" 
        , onClose: function(){ $(this).dialog("close"); }
        , buttons:{
			"取消": function(){
				$(this).dialog("close");
			}
			,"保存": function(){
				var name = $("#username").val();
				if(name == "" || name == "请填写姓名"){
					$("#username").val("请填写姓名");
					return;
				}
				var phone = $("#userphone").val();
				if(phone == "" || name == "请填写电话"){
					$("#userphone").val("请填写电话");
					return;
				}
				var address = $("#useraddress").val();
				if(address == "" || address == "请填写地址"){
					$("#useraddress").val("请填写地址");
					return;
				}
				
				var num = parseInt($("#table1>tbody>tr:last>td:eq(0)").text()) + 1;
				var newRow = '<tr><td>' + num + '</td><td>' + name + '</td><td>' + phone + '</td><td>' + address + '</td><td><a href="#" onclick="updateUser(' + num + ')"><i class="fa fa-pencil"></i></a> <a href="#myModal" role="button" onclick="deleteUser(' + num + ')"><i class="fa fa-trash-o"></i></a></td></tr>';
				$("#table1 tr:last").after(newRow);
				
				$("#username").val("");
				$("#userphone").val("");
				$("#useraddress").val("");
				$(this).dialog("close");
			}
        }
	});
}

function updateUser(index){
	for(var i=0;i<$("#table1 tr").length;i++){
		var id = $("#table1 tr:gt(0):eq("+ i + ") td:eq(0)").text();
		if(id == index){
			gIndex = i;
			break;
		}
	}
	var username = $("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(1)").text();
	var userphone = $("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(2)").text();
	var useraddress = $("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(3)").text();
	$("#username").val(username);
	$("#userphone").val(userphone);
	$("#useraddress").val(useraddress);
	
	$('#updateUser').css("display","block");
	$("#updateUser").dialog({
        title: "修改信息"
        , 'class': "mydialog" 
        , onClose: function(){ $(this).dialog("close"); }
        , buttons:{
			"取消": function(){
				$(this).dialog("close");
			}
			,"确认": function(){
				var name = $("#username").val();
				if(name == "" || name == "请填写姓名"){
					$("#username").val("请填写姓名");
					return;
				}
				var phone = $("#userphone").val();
				if(phone == "" || name == "请填写电话"){
					$("userphone").val("请填写电话");
					return;
				}
				var address = $("#useraddress").val();
				if(address == "" || address == "请填写地址"){
					$("useraddress").val("请填写地址");
					return;
				}
				
				$("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(1)").text(name);
				$("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(2)").text(phone);
				$("#table1 tr:gt(0):eq("+ gIndex + ") td:eq(3)").text(address);
				
				$("#username").val("");
				$("#userphone").val("");
				$("#useraddress").val("");
				$(this).dialog("close");
			}
        }
	});
}

function deleteUser(index){
	for(var i=0;i<$("#table1 tr").length;i++){
		var id = $("#table1 tr:gt(0):eq("+ i + ") td:eq(0)").text();
		if(id == index){
			gIndex = i;
			break;
		}
	}
	$("#myDelete").modal('show');
}

function deleteConfirm(){
	$("#table1 tr:gt(0):eq(" + gIndex + ")").remove();
	$("#myDelete").modal('hide');
}