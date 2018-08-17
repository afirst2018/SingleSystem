(function($) {
	// 保存原有的jquery ajax;
	var $_ajax = $.ajax;
	$.ajax = function(options) {
		var originalSuccess, mySuccess, success_context;
		if (options.success) {
			// save reference to original success callback
			originalSuccess = options.success;
			success_context = options.context ? options.context : $;
			// 自定义callback
			mySuccess = function(data) {
				if (data['sessionstatus'] == "timeout") {
						$('#sessionTimeoutDiv').modal({
							backdrop : "static",
							show : true
						});
						return;
				} else if (data['accessDeniedStatus'] == "no_permission") {
					$('#accessDeniedDiv').modal({
						backdrop : "static",
						show : true
					});
					return;
				} else if (data['expiredtatus'] == "expired") {
                    $('#expiredDiv').modal({
                        backdrop : "static",
                        show : true
                    });
                    return;
                } else if(data['errorStatus'] == "exception"){
					var errorMsg = data['errorMsg'];
					if(errorMsg){
						$("#expiredDiv_errorMsg").text(errorMsg);
					}else {
						$("#expiredDiv_errorMsg").text("系统内部发生错误，稍后请重试...");
					}
                	$('#errorDiv').modal({
                        backdrop : "static",
                        show : true
                    });
                    return;
                }else {
					// call original success callback
					// originalSuccess.apply(success_context, arguments);
					return originalSuccess.apply(success_context, arguments);
				}
			};
			// override success callback with custom implementation
			options.success = mySuccess;
		}
		// call original ajax function with modified arguments
		// $_ajax.apply($, arguments);
		return $_ajax.apply($, arguments);
	};
})(jQuery);