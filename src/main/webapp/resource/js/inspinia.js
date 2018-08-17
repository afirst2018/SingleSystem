/**
 * Created on   2016年5月16日
 * Discription: [页面初始化方法]
 * @author:     linlong
 * @update:     2016年5月16日 下午1:25:10
 */
$(document).ready(function() {
	// showWelcomeTip();
	hideMenuLessThan769();
	// MetsiMenu
	$('#side-menu').metisMenu();
	// Close menu in canvas mode
	$('.close-canvas-menu').click(function() {
		//$("body").toggleClass("mini-navbar");
		//SmoothlyMenu();
	});
	// Minimalize menu
	$('.navbar-minimalize').click(function() {
		//$("body").toggleClass("mini-navbar");
		//SmoothlyMenu();
	});
	// Fix Bootstrap backdrop issu with animation.css
	$('.modal').appendTo("body");
	fix_height();
	// Fixed Sidebar
	$(window).bind("load", function() {
		if ($("body").hasClass('fixed-sidebar')) {
			$('.sidebar-collapse').slimScroll({
				height : '100%',
				railOpacity : 0.9
			});
		}
	});
	$(document).bind("load resize scroll", function() {
		$('#page-wrapper').css("min-height", $(window).height() + "px");
	});
	// 窗口宽度变化事件
	$(window).bind("resize", function() {
		$('#page-wrapper').css("min-height", $(window).height() + "px");
		hideMenuLessThan769();
	});

	/*sidebar start add by tianci*/
	// Open close right sidebar
	$('.right-sidebar-toggle').click(function(){
		$('#right-sidebar').toggleClass('sidebar-open');
	});
	// Initialize slimscroll for right sidebar
	$('.sidebar-container').slimScroll({
		height: '100%',
		railOpacity: 0.4,
		wheelStep: 10
	});
	// Full height of sidebar
	function fix_height() {
		var heightWithoutNavbar = $("body > #wrapper").height() - 61;
		$(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");

		var navbarHeigh = $('nav.navbar-default').height();
		var wrapperHeigh = $('#page-wrapper').height();

		if(navbarHeigh > wrapperHeigh){
			$('#page-wrapper').css("min-height", navbarHeigh + "px");
		}

		if(navbarHeigh < wrapperHeigh){
			$('#page-wrapper').css("min-height", $(window).height()  + "px");
		}

	}
	fix_height();

	// Fixed Sidebar
	$(window).bind("load", function () {
		if ($("body").hasClass('fixed-sidebar')) {
			$('.sidebar-collapse').slimScroll({
				height: '100%',
				railOpacity: 0.9
			});
		}
	})

	// Move right sidebar top after scroll
	$(window).scroll(function(){
		if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav') ) {
			$('#right-sidebar').addClass('sidebar-top');
		} else {
			$('#right-sidebar').removeClass('sidebar-top');
		}
	});

	/*sidebar end add by tianci*/
});

/**
 * Created on   2016年5月16日
 * Discription: [window窗口宽度小于769px时，隐藏左侧菜单栏]
 * @author:     linlong
 * @update:     2016年5月16日 下午1:25:10
 */
function hideMenuLessThan769() {
	if ($(this).width() < 769) {
		$('body').addClass('body-small')
	} else {
		$('body').removeClass('body-small')
	}
}

/**
 * Created on   2016年5月16日
 * Discription: [登录提示语]
 * @author:     linlong
 * @update:     2016年5月16日 下午1:26:35
 */
function showWelcomeTip() {
	toastr.options = {
	    closeButton : true,
	    progressBar : true,
	    showMethod : 'slideDown',
	    timeOut : 1500
	};
	toastr.success('欢迎访问初九数据科技后台管理系统');
}

/**
 * Created on   2016年5月16日
 * Discription: [Full height of sidebar]
 * @author:     linlong
 * @update:     2016年5月16日 下午1:27:00
 */
function fix_height() {
	var heightWithoutNavbar = $("body > #wrapper").height() - 61;
	$(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
	var navbarHeigh = $('nav.navbar-default').height();
	var wrapperHeigh = $('#page-wrapper').height();
	$('#page-wrapper').css("min-height", $(window).height() + "px");
}

function animationHover(element, animation){
	element = $(element);
	element.hover(
		function() {
			element.addClass('animated ' + animation);
		},
		function(){
			//wait for animation to finish before removing classes
			window.setTimeout( function(){
				element.removeClass('animated ' + animation);
			}, 2000);
		});
}

/**
 * Created on   2016年5月16日
 * Discription: [左侧菜单导航 收缩展开控制]
 * @author:     linlong
 * @update:     2016年5月16日 下午1:27:48
 */
/*
function SmoothlyMenu() {
	if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
		// Hide menu in order to smoothly turn on when maximize menu
		$('#side-menu').hide();
		// For smoothly turn on menu
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 100);
	} else if ($('body').hasClass('fixed-sidebar')) {
		$('#side-menu').hide();
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 300);
	} else {
		// Remove all inline style from jquery fadeIn function to reset menu state
		$('#side-menu').removeAttr('style');
	}
}
*/
