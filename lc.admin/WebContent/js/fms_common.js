//***************************************************************************
// JavaScript FMS 전역변수
//***************************************************************************
//탭 사용여부
var TAB_USE_YN = "N";

//화면 resize 이벤트 처리여부
var WINDOW_RESIZE_YN = "N";

//탭 index
var tab_counter = 2;

//탭 index와 id 를 저장하고 있는 맵
var tab_map = {};


//***************************************************************************
// Window JavaScript 관련 공통 함수
//***************************************************************************
/**
 * 팝업창 생성함수.
 * 
 * url : url
 * winName : winName
 * width : width
 * height : height
 * isScrollBar : isScrollBar
 * isResizable : isResizable
 */
//function Popup(url, winName, width, height, isScrollBar, isResizable){
function fnPopup(url, width, height){
	var winName = "";
	var isScrollBar = "no";
	var isResizable = "yes";
    var winl = (screen.width-width)/2;
    var wint = (screen.height-height)/2;
    var option = 'toolbar=0, location=0, status=1, menubar=0, scrollbars='+isScrollBar+', resizable='+isResizable+', top='+wint+', left='+winl+', width='+width+', height='+height;
    return window.open (url,winName,option);
}

/**
 * Modal 팝업창 생성함수.
 * 
 * url : url
 * width : width
 * height : height
 * args : arguments [option]
 */
function fnPopupModal(url, width, height, args){
	var rtn = window.showModalDialog( url, args, "dialogWidth:" + width + "px;dialogHeight:" + height + "px;help:no;scroll:no;status:no") ;
	if(typeof(rtn) == 'object' && rtn.result){
		return rtn.result;
	}else{
		return rtn;
	}
}

/**
 * Modal 팝업창에서 부모창에 데이터를 return하는 클래스.
 * 
 * 사용예 : 
 *    팝업창   var result = new ModalResult();
 *            result.set("name", "홍길동");
 *			  result.set("id", "tester"); 
 *            window.returnValue = result;
 *            
 *    부모창   var result = fnPopupModal(.....)
 *            alert(result.name);
 *            alert(result.id);
 */
var ModalResult = function(){
	this.result = {};
	
	this.set = function(id, value){
		this.result[id] = value;
	};
	
	this.get = function(id){
		return this.result[id];
	};
};




/**
 * Cookie 설정함수.
 * 
 * sName : cookie 명
 * sValue : cookie 값
 */
function fnSetCookie(sName, sValue)
{
	document.cookie = sName + "=" + escape(sValue);
}

/**
 * Cookie 조회함수.
 * 
 * sName : cookie 명
 */
function fnGetCookie(sName)
{
	// cookies are separated by semicolons
	var aCookie = document.cookie.split("; ");
	for (var i=0; i < aCookie.length; i++)
	{
		// a name/value pair (a crumb) is separated by an equal sign
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0]) 
			return unescape(aCrumb[1]);
	}
	return null;
}

/**
 * Yes/No Select Box 생성용 함수
 * 
 * id : select id(jquery형식)
 * title : 선택 title[option]
 */
function fnMakeYnSelect(id, title){
	var tr = "";
	if(title != undefined){
		tr = tr + "<option value=''>"+title+"</option>";
	}
	tr = tr + "<option value='Y'>Y</option>";
	tr = tr + "<option value='N'>N</option>";
	$(id+"").html(tr);	
}

/**
 * 업무대분류 SELECT BOX 생성 함수.(Sync용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectBiz(id, hicd, title, select_val){
	fnMakeSelect('commSelectService.getBiz.json', hicd, id, title, 'KEY', false, select_val);
}

/**
 * 업무대분류 SELECT BOX 생성 함수.(Async용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectBizAsync(id, hicd, title, select_val){
	fnMakeSelect('commSelectService.getBiz.json', hicd, id, title, 'KEY', true, select_val);
}

/**
 * 시스템 SELECT BOX 생성 함수.(Sync용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectSystem(id, hicd ,title, select_val){
	fnMakeSelect('commSelectService.getSystem.json', hicd, id, title, 'KEY', false, select_val);
}

/**
 * 시스템 SELECT BOX 생성 함수.(Async용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectSystemAsync(id, hicd ,title, select_val){
	fnMakeSelect('commSelectService.getSystem.json', hicd, id, title, 'KEY', true, select_val);
}

/**
 * 시스템 HOST BOX 생성 함수.(Sync용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectHost(id, hicd, title, select_val){
	fnMakeSelect('commSelectService.getHost.json', hicd, id, title, 'KEY', false, select_val);
}

/**
 * 시스템 HOST BOX 생성 함수.(Async용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값[option]
 * title : 선택 title[option]
 */
function fnMakeSelectHostAsync(id, hicd, title, select_val){
	fnMakeSelect('commSelectService.getHost.json', hicd, id, title, 'KEY', true, select_val);
}

/**
 * 공콩코드 SELECT BOX 생성 함수.(Sync용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectComm(id, hicd, title, select_val){
	if(hicd == undefined){
		alert('공통코드 호출시는 그룹코드를 입력해야 합니다.');
		return;
	}
	fnMakeSelect('commSelectService.getCommSubCd.json', hicd, id, title, 'CD', false, select_val);
}

/**
 * 공콩코드 SELECT BOX 생성 함수.(Async용)
 * 
 * id : select id(jquery형식)
 * hicd : 상위코드값
 * title : 선택 title[option]
 * select_val : 리스트 생성 후 default 선택 값[option]
 */
function fnMakeSelectCommAsync(id, hicd, title, select_val){
	if(hicd == undefined){
		alert('공통코드 호출시는 그룹코드를 입력해야 합니다.');
		return;
	}
	fnMakeSelect('commSelectService.getCommSubCd.json', hicd, id, title, 'CD', true, select_val);
}

/**
 * jqgrid내에 SELECT BOX 생성 함수 - 공통코드 전용.(sync)
 * 
 * hicd : 상위코드값
 */
function fnGetGridSelectComm(hicd){
	return fnGetGridSelect("commSelectService.getCommSubCd.json?hicd="+hicd, "CD", "CD_NM");
}

/**
 * jqgrid내에 SELECT BOX 생성 함수 - 시스템 HOST 전용.(sync)
 * 
 * hicd : 상위코드값
 */
function fnGetGridSelectHost(){
	return fnGetGridSelect("commSelectService.getHost.json", "KEY", "CD_NM");
}

/**
 * jqgrid내에 SELECT BOX 생성 함수 - 시스템 전용.(sync)
 */
function fnGetGridSelectSystem(){
	return fnGetGridSelect("commSelectService.getSystem.json", "KEY", "CD_NM");
}

/**
 * jqgrid내에 SELECT BOX 생성 함수 - 업무대분류 전용.(sync)
 */
function fnGetGridSelectBiz(hicd){
	return fnGetGridSelect("commSelectService.getBiz.json", "KEY", "CD_NM");
}

/**
 * jqgrid내에 SELECT BOX 생성 함수.(sync)
 * grid포맷에 맞는 String return.
 * 
 * url : 호출url
 * key_name : 키로 사용된 칼럼[option]
 * val_name : 값으로 사용된 칼럼[option]
 */
function fnGetGridSelect(url, key_name, val_name){
	var datas = '';
	var keynm = key_name;
	var name = val_name;
	if(key_name == undefined) keynm = 'KEY';
	if(val_name == undefined) name = 'CD_NM';

	var hicd = "";
	if(url.indexOf("?hicd=") != -1){
		hicd = '{"CD":"'+url.split("?hicd=")[1]+'"}';
	}
	$.ajax({ 
		type: "POST", 
		async: false,
		cache : false,
		dataType: "json",
		url: "${pageContext.request.contextPath}/"+url, 
		data: hicd,
		error: function(request, status, error){
			alert(request.responseText);
		}
	}).done(function( data ) { 
		$.each(data, function(key, val){
			datas = datas + val[keynm]+ ":" + val[name] + ";";
		});		
	});	
	if(datas.length >= 1) datas = datas.substring(0, datas.length-1);
	return ""+datas+"";
}

/**
 * SELECT BOX 생성 함수.
 * 
 * method : 호출 url명
 * hicd : 상위코드값
 * id : select id (jquery형식)
 * title : 선택 title ( null : [선택], "" : not display, text : text )
 * key_name : 키로 사용된 칼럼
 * sync : sync 여부(true,false)
 * select_val : 리스트 생성 후 default 선택 값
 */
function fnMakeSelect(url, hicd, id, title, key_name, sync, select_val){
	if(arguments.length < 7){
		alert("파라미터 갯수가 부족합니다. \n usage:fnMakeSelect(url, hicd, id, title, key_name, sync, select_val)");
		return;
	}
	var datas = "";
	var t = title;
	var keynm = key_name;
	if(hicd != undefined && hicd != null && hicd != 'null'){
		datas = '{"CD":"'+hicd+'"}';
	}
	if(title == undefined){
		t = "[선택]";
	}else if(title == ""){
		t = null;
	}
	if(key_name == undefined){
		keynm = "KEY";
	}
	//$.post("${pageContext.request.contextPath}/commSelectService."+method+".json",
	//		datas, 
	//		function(data, transportStatus) {
	//			if (transportStatus == 'success') {
	//				var tr = "<option value=''>"+t+"</option>";
	//				$.each(data, function(key, val){
	//					tr = tr + "<option value='"+val[keynm]+"'>"+val['CD_NM']+"</option>";
	//				});
	//				$(id+"").html(tr);
	//			} else {
	//				alert(transportStatus);
	//			}
	//		}, 
	//		"json"
	//);	
	
	$.ajax({ 
		type: "POST", 
		async: sync,
		cache : false,
		dataType: "json",
		url: "${pageContext.request.contextPath}/"+url, 
		data: datas,
		error: function(request, status, error){
			alert(request.responseText);
		}
	}).done(function( data ) {
		var tr = "";
		if(t != null){
			tr = "<option value=''>"+t+"</option>";
		}
		$.each(data, function(key, val){
			if(key_name == 'MIX'){
				tr = tr + "<option value='"+val['KEY']+'|'+val['CD']+"'>"+val['CD_NM']+"</option>";
			}else{
				tr = tr + "<option value='"+val[keynm]+"'>"+val['CD_NM']+"</option>";
			}
		});	
		$(id+"").html(tr);
		
		if(select_val != undefined){
			var selId = "select"+id+" option";
			$(selId).each(function() {
				if (select_val == $(this).val()) {
					$(this).attr("selected", "selected");
				}
			});			
		}
	});		
	
	return false;	
}

/**
 *  Ajax 형식의 Form Submit 함수
 *  
 *  url : 호출 URL
 *  form_nm : form id ( '#' 붙히지 말아야함. )
 *  callback_fn : callback 함수명
 */	
/*
function fnSubmitAjax(url, form_id, callback_fn){
	$.post( url,
			JSON.stringify(form2js(form_id, null, false)),
			function(data, transportStatus) {
				if(transportStatus == 'success'){
					if(callback_fn != undefined){
						callback_fn;
					}
				}else{
					alert(transportStatus);
				}
			}, 
			"json"
	);		
};
*/

/**
 *  form id 하위 데이터를 객체 형식으로 리턴하는 함수
 *  
 *  form_id : form id ( '#' 붙히지 말아야함. )
 */	
function fnGetFormData(form_id){
	return form2js(form_id, null, false);
}

/**
 *  필수 입력 여부 체크
 *  
 *  form_id : form id ( '#' 붙히지 말아야함. )
 */	
function fnCheckRequired(form_id){
	if(form_id != undefined){
		form_id = form_id.replace("#", "");
	}
	var id = "#" + form_id;
	var isContinue = true;
	$(id + " .must:input").each(function(){
		var type = $(this).attr("type");
		switch(type){
			case 'text':
				if($(this).val() == ""){
					alert("필수값을 입력하세요.");
					$(this).focus();
					isContinue = false;
					return false;
				}
				break;
		}
	});	
	
	if(!isContinue) return isContinue;
	
	$("#" + form_id + " select[class=must]").each(function(){
		if($(this).val() ==""){
			alert("필수값을 입력하세요.");
			$(this).focus();
			isContinue = false;
			return false;
		}
	});	

	return isContinue;
}

/**
 *  form id 하위 데이터를 ajax형식으로 서버로 전송하는 함수
 *  
 *  url : 호출 url
 *  form_id : form id ( '#' 붙히지 말아야함. )
 *  callback : callback 함수명[option]
 *  success_msg : 실행성공시 alert창에 띄울 메세지
 */	
function fnSubmitAjax(url, form_id, callback, success_msg) {

	if(!fnCheckRequired(form_id)) return;
	
	if ( jQuery.isFunction(form_id)) {
		callback = form_id;
		//data = {};
	}
	
	form_id = JSON.stringify(form2js(form_id, null, false));
	return fnServerConnector(url, form_id, callback, success_msg);
}

/**
 *  ajax 호출 함수, 파라미터를 직접 지정하여 호출함(json형식의 string이어야 함)
 *  
 *  url : 호출 url
 *  param : 전송 data 객체 또는 string
 *  callback : callback 함수명[option]
 */	
function fnServerConnector(url, param, callback, success_msg){
	if(param){
		if (typeof param != "string") {
			if($.contains(param, 'userdata')){
				param = JSON.stringify(param);
			}else{
				param = JSON.stringify(form2js(param, null, false));
			}
		}
	}

	var options = {
		url:  url,
		data: param,
		type: "POST",
		dataType: "json",
		timeout: 300000,
    	async: true,
    	cache : false,
    	//success: callback
    	success: function(data){
			callback(data);
			if(success_msg != undefined){
				alert(success_msg);
			}
		},
		error: function(request, status, error){
			alert(request.responseText);
		}
	};
	
	return jQuery.ajax(options);
}

//************************************************************************************
// 배치서버호출
var BatAdminDelegator = {
	init : {
		 START_BATCH_JOB_URL : "/jobs/launch/",
         REMOTE_START_BATCH_JOB_URL : "/jobs/remoteaunch/",
         START_BATCH_JOB_RUN_KEY_URL : "/jobs/jobrun/",
         STOP_BATCH_JOB_URL : "/jobs/executions/stop/",
         RESTART_BATCH_JOB_PREFIX_URL : "/jobs/",
         RESTART_BATCH_JOB_URL : "/executions/restart/",
         REMOTE_RESTART_BATCH_JOB_URL : "/executions/remoterestart/",
         ABANDON_BATCH_JOB_URL : "/jobs/executions/abandon/",
         batchServerUrl : "http://10.91.11.52:17201/smart.run.batch.admin.web"
         //batchServerUrl : "http://127.0.0.1:8089/smart.run.batch.admin.web"
	},
	
	//시작
	startBatchJob : function(batJobMap, batchServerUrl){
		batchServerUrl = batchServerUrl || this.init.batchServerUrl;
		var url = batchServerUrl + this.init.REMOTE_START_BATCH_JOB_URL + batJobMap.JOB_NM + "/" + batJobMap.BAT_JOB_RUN_KEY + "?practiceType=" + batJobMap.PRCT_TP;
		return this.BatchServerConnector.callService(url, batJobMap);
	},
	
	//  중지
	//	var batJobMap = {JOB_NM:$("#JOB_NM").val(), JOB_EXECUTION_ID:$("#JOB_EXECUTION_ID").val()};
	//	BatAdminDelegator.stopBatchJob(batJobMap);
	stopBatchJob : function(batJobMap, batchServerUrl){
		batchServerUrl = batchServerUrl || this.init.batchServerUrl;
		var url = batchServerUrl + this.init.STOP_BATCH_JOB_URL + batJobMap.JOB_NM + "/" + batJobMap.JOB_EXECUTION_ID;
		return this.BatchServerConnector.callService(url, batJobMap);
	},
	
	//재시작
	restartBatchJob : function(batJobMap, batchServerUrl){
		batchServerUrl = batchServerUrl || this.init.batchServerUrl;
		var url = batchServerUrl + this.init.RESTART_BATCH_JOB_PREFIX_URL + batJobMap.JOB_NM + "/" + this.init.REMOTE_RESTART_BATCH_JOB_URL + batJobMap.BAT_JOB_RUN_KEY;
		return this.BatchServerConnector.callService(url, batJobMap);
	},
	
	//파기
	abandonBatchJob : function(batJobMap, batchServerUrl){
		batchServerUrl = batchServerUrl || this.init.batchServerUrl;
		var url = batchServerUrl + this.init.ABANDON_BATCH_JOB_URL + batJobMap.JOB_NM + "/" + batJobMap.JOB_EXECUTION_ID;
		return this.BatchServerConnector.callService(url, batJobMap);
	},
	
	BatchServerConnector : {
		callService : function(urls, batJobMap){
			var param = '{"serviceUrl":"'+urls+'"}';
			var options = {
					url:  "batServCallService.callBatchJob.json",
					data: param,
					type: "POST",
					dataType: "json",
					timeout: 300000,
			    	async: true,
			    	cache : false,
			    	success: function(data){
						alert(data.resultMsg);
					},
					error: function(request, status, error){
						alert(request.responseText);
					}
				};
			return jQuery.ajax(options);		
		}
	}
};
//************************************************************************************

/**
 *  탭방식으로 화면을 띄울때 contentArea만 남기고 나머지 부분은 hide 시키는 함수
 */
function fnTabSetting(){
	if(TAB_USE_YN === "Y"){
		try{
		
			var h = location.href;
			
			$("iframe[id^=ifr]", parent.document).each(function(){
				if(h.indexOf($(this).attr("src")) != -1){
					$("#topMenuArea").hide();
					$("#leftMenuArea").hide();
					$("#sliding").hide();
					$("#footerArea").hide();
					$("#contentArea").css("padding-left","0px");

					var id = "#" + $(this).attr("id");
					$(id, parent.document).height(document.body.offsetHeight);
					return;
				}
			});
			//$(id, parent.document).height(document.body.offsetHeight);
		}catch(e){alert(e)}
	}
}

/**
 *  window resize 이벤트 발생시 화면 refresh 시키는 함수
 */
function fnWinResizing(){
	if(WINDOW_RESIZE_YN === "Y"){
		//화면 resize 처리
		$(window).resize(function() { 
			if($(window).width() > 980){
				var leftMargin = $("#leftMenuArea").width();
				if(TAB_USE_YN === "Y"){
					leftMargin = 0;
				}
				$(".bodyArea").width($(window).width());		
				$("#contentArea").width($(window).width() - leftMargin - 44);		
				$("table.ui-jqgrid-btable").each(function(){
					var id = "#" + $(this).attr("id");
					$(id).jqGrid('setGridWidth', $(window).width() - leftMargin - 44);
				});
			}		  
		});		
	}
}


/**
 *  탭 생성 함수
 *  
 *  urls: 탭의 iframe으로 불러올 url
 *  title : 탭 제목
 */
function fnAddTab(urls, title){
	var st = "#tabs-" + tab_counter;
	var ifr = "ifr"+tab_counter;
	
	var ret = null;
	$(".ui-tabs span").each(function(){
		var text = $(this).text();
		if(text == title){
			ret = text;
			return;
		}
	})
	
	//if($(st).html() != null ) {
	if(ret != null) {
		//$("#contentArea").tabs('select',st);
		$("#contentArea").tabs('select', tab_map["'"+ret+"'"]);
	}else{
		$("#contentArea").tabs("add", st, title);
		tab_counter++;
		
		var fs = '<iframe src="'+urls+'" id="'+ifr+'" style="width:100%; height:auto; margin:0px" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" vspace="0" hspace="0"/>';
		$(st,"#contentArea").append(fs);
		
		tab_map["'"+title+"'"] = st;
	}
}

/**
 *  탭 레이아웃을 초기화하는 함수
 */
function fnTabInit(){
	tab_map = {};
	var insert_html = '		<ul> '+
	  				  '			<li><a href="#tabs-1">Main</a> </li> '+
	  				  '		</ul>';

	$(insert_html).insertBefore("#contentArea > #tabs-1");

	var maintab = jQuery('#contentArea').tabs({
					//tabTemplate: '<li><a href="<%="#"%>{href}"><%="#"%>{label}</a><span class="ui-icon ui-icon-close">Remove Tab</span></li>',
					add: function(e, ui) {
						$(ui.tab).parents('li:first')
							     .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="Close Tab"></span>')
							     .find('span.ui-tabs-close')
							     .click(function() {
							    	 maintab.tabs('remove', $('li', maintab).index($(this).parents('li:first')[0]));
							     });
						maintab.tabs('select', '#' + ui.panel.id);
					}
				});
}

/**
 * ajax 통신이 일어나면 로딩중 이미지가 보여주고 끝나면 사라지게 함
 * 
 */
$(this).ajaxStart(function() {
	defaultLoading();
}).ajaxStop(function() {
	defaultUnloading(); 
}).ajaxError(function() {
	alert("시스템 오류가 발생하였습니다.");
});

/**
 * 로딩중 이미지를 화면 중앙에 보여주고 배경은 반투명하게 변경함
 * 
 */
function loading() {
	jQuery.blockUI({ 
		message: "<img src='" + ROOT_URL + "/image/wbiz/common/loading.gif' />", 
		css: { 
			border: '1px solid #000000', 
			backgroundColor: '#FFF',  
			top: ((($.browser.msie) ? $(document).height() : $(window).height()) - 181) /2 + 'px',
			left: ((($.browser.msie) ? $(document).width() : $(window).width()) - 181) /2 + 'px',
			width: '181px'
		},
		overlayCSS:  {  
	        backgroundColor:'#000',  
	        opacity:        '0.4'  
	    }
	}); 
}

function defaultLoading() {
	jQuery.blockUI({ 
		message: "", 
		css: { 
			backgroundColor: '#333',  
			top: ((($.browser.msie) ? $(document).height() : $(window).height()) - 181) /2 + 'px',
			left: ((($.browser.msie) ? $(document).width() : $(window).width()) - 181) /2 + 'px',
			width: '181px'
		},
		overlayCSS:  {  
	        backgroundColor:'#FFF',  
	        opacity:        '0'  
	    }
	}); 
}

/**
 * 로딩이 끝나면 배경을 원래대로 로딩된 이미지를 사라지게 함
 * 
 */
function unloading() {
	jQuery.unblockUI({ 
		fadeOut: 300, 
		css: { 
        cursor:         'default' 
    }		
	});
}

function defaultUnloading() {
	jQuery.unblockUI({ 
		fadeOut: 0, 
		css: { 
        cursor:         'default' 
    }		
	});
}

/*
 * ESC 나 backspace 클릭에 따른 이동 방지 
*/
document.onkeydown = checkKey;
function checkKey() {
	//alert(window.event.keyCode);
	
	if(window.event.keyCode == 27 ) { //ESC
		window.event.returnValue = false;
		return false; 
	}
	else if(window.event.keyCode == 8 ) { //backspace
		if ( !window.event.srcElement.isTextEdit) {
			window.event.returnValue = false;
			return false;
		}
		else if (window.event.srcElement.readOnly || window.event.srcElement.disabled ) {
			window.event.returnValue = false;
			return false;
		}
	}
	else if(window.event.keyCode == 13333 ) { //Enter
		window.event.returnValue = false;
		return false;
	}
	
	event.returnValue = true;
}

//***************************************************************************
// jQuery 관련 공통함수
//***************************************************************************
(function($){
	/**
	 *  서브메뉴 호출 함수
	 *  
	 *  id : 대분류 구분 id
	 */	
	$.fnJgoMenu = function(id){
		$("#leftMenu1").css("display", "none");
		if(id == '1'){
			$("#leftMenu1").css("display", "block");
			$("#leftMenu2").css("display", "none");
			$("#leftMenu3").css("display", "none");
			$("#leftMenu4").css("display", "none");
		}else if(id == '2'){
			$("#leftMenu1").css("display", "none");
			$("#leftMenu2").css("display", "block");
			$("#leftMenu3").css("display", "none");
			$("#leftMenu4").css("display", "none");				
		}else if(id == '3'){
			$("#leftMenu1").css("display", "none");
			$("#leftMenu2").css("display", "none");
			$("#leftMenu3").css("display", "block");
			$("#leftMenu4").css("display", "none");			
		}else if(id == '4'){
			$("#leftMenu1").css("display", "none");
			$("#leftMenu2").css("display", "none");
			$("#leftMenu3").css("display", "none");
			$("#leftMenu4").css("display", "block");				
		}

		$("#leftMenu5").css("display", "none");	
		if(id == '5'){
			$("#leftMenu1").css("display", "none");
			$("#leftMenu2").css("display", "none");
			$("#leftMenu3").css("display", "none");
			$("#leftMenu4").css("display", "none");				
			$("#leftMenu5").css("display", "block");				
		}
	};	
	

})(jQuery);




//***************************************************************************
//jQuery - jqgrid 관련 공통함수
//***************************************************************************
// 그리드 마지막 호출 정보
var lastsel;

// 삭제row 정보
var delarr = [];

// 추가row index
var addidx = 0;

var errorMsg = '';

// 초기 grid 생성시 지정했던 url
var init_default_url = {};

// jqgrid 기본 설정
$.extend($.jgrid.defaults, { 
	 autowidth:true,
	 multiselect: true,
	 serializeGridData: function (postData) { return JSON.stringify(postData); },
	 serializeRowData:  function (postData) { return JSON.stringify(postData); },
	 serializeDelData:  function (postData) { return JSON.stringify(postData); },
	 serializeCellData:  function (postData) { return JSON.stringify(postData); },
	 datatype: 'json',
	 mtype: 'POST',
	 rownumbers: true,
	 viewrecords: true,
	 gridview: true,
	 loadui:'block',
	 cellEdit:true,
	 editurl:"clientArray",
	 cellsubmit:"clientArray",
	 rowNum:10,
	 rowList:[10,20,30],
	 ajaxGridOptions:{timeout: 300000} ,
	 emptyrecords: "조회된 데이터가 없습니다.",
	 loadtext: "조회 중 입니다...",
	 treeGridModel : 'adjacency', //tree 관련설정
	 ExpandColClick : false, //tree 관련설정
	 beforeRequest : function(){
	  		var data = jQuery(this).jqGrid('getGridParam','postData');
	  		if(data == null || data.userdata == null){
	 	  		return false;
	  		}else if(data == undefined || data.userdata == undefined){
	 	  		return false;
	  		}
	 	  		return true;
	 },		 
	 jsonReader : {
	      root:"rows",
	      page: "page", 
	      total: "total", 
	      records: "records",
	      userdata: "userdata",
	      repeatitems: false,
	      id: "id" 
	 },
	treeReader : {
		   level_field: "LVL",
		   parent_id_field: "PARENT",
		   leaf_field: "ISLEAF",
		   expanded_field: "expanded"
		},
	 treeIcons : {
			plus:'ui-icon-folder-collapsed',
			minus:'ui-icon-folder-open',
			leaf:'ui-icon-document'			
	 },
     loadError:function(xhr, status, error){
	     //alert(xhr.responseText);
		 errorMsg = error;
	     alert(error);
	 },
	 loadComplete:function(data){
		 //if(data != null && data.resultMsg != null){
		//	 alert(data.resultMsg);
		 //}
	 },
	 gridComplete:function(){
		 init_default_url[$(this).attr("id")] = $(this).jqGrid('getGridParam', 'url');
	 },
    //afterEditCell: function () {
	//	 var e = jQuery.Event("keydown");                             
	//	 e.keyCode = $.ui.keyCode.ENTER;                             
	//	 var edit = $(".edit-cell > *");                             
	//	 edit.blur(function () {                                 
	//		 edit.trigger(e);                             
	//		});                         
	//},	 
     afterSaveCell:function(rowid, cellname, value, iRow, iCol){
		//check box toggle 기능 막음.
		var val = $(this).jqGrid('getCell',rowid, 1);
  		var cid = $(val).attr("id");
  		if($("[id="+cid+"]").is(":checked")){
			  ;
		}else{
			$(this).jqGrid('setSelection', rowid, false);
		}
     }
     //onSelectRow: function(id){ //cellEdit:false일때 row edit하기위해 사용.
	 //	  if(id && id!==lastsel){
	 //		  $(this).jqGrid('restoreRow',lastsel);
	 //		  lastsel=id;
	 //	  }
	 //	  $(this).jqGrid('editRow', id, {keys:true, url:'clientArray'});
	 //}	
  });

//jqgrid checkbox formatter default 값 수정.
$.extend($.jgrid, { 
	formatter : {
		checkbox : {disabled:false}
	}
	
});

//jqgrid checkbox 확장 - multiselect 인 경우 사용함.
jQuery.extend($.fn.fmatter , {
    checkbox2 : function(cval, opts) {
		//alert(JSON.stringify(opts))
		//alert(opts.gid)
		var op = $.extend({},opts.checkbox), ds;
		if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({},op,opts.colModel.formatoptions);
		}
		if(op.disabled===true) {ds = "disabled=\"disabled\"";} else {ds="";}
		if($.fmatter.isEmpty(cval) || $.fmatter.isUndefined(cval) ) {cval = $.fn.fmatter.defaultFormat(cval,op);}
		cval=cval+"";cval=cval.toLowerCase();
		var bchk = cval.search(/(false|0|no|off|n)/i)<0 ? " checked='checked' " : "";
		return "<input type=\"checkbox\" " + bchk  + " value=\""+ cval+"\" offval=\"no\" "+ds+ "  onclick=checkCheck('"+opts.rowId+"','"+opts.gid+"')  />";
}
});

jQuery.extend($.fn.fmatter.checkbox2 , {
    unformat : function(cellval, options, o) {
		var cbv = (options.colModel.editoptions) ? options.colModel.editoptions.value.split(":") : ["Yes","No"];
		var ret = $('input',o).is(":checked") ? cbv[0] : cbv[1];	
	    return ret;
	}
});


//jqgrid checkbox2 에서 사용하는 함수.
function checkCheck(rowid, gid){
	var id = "#"+gid;
	var val = $(id).jqGrid('getCell',rowid, 1);
	//alert(val);
		var cid = $(val).attr("id");
		if($("[id="+cid+"]").is(":checked")){
		  ;
		}else{
			$(id).jqGrid('setSelection', rowid, false);
		}			
}

(function($){
	
	/**
	 * Grid 조회함수
	 * 
	 * select_url_str : 호출url
	 * search_data : 검색어, 형식:{key1:value1, key2:value2, ...}
	 * form_id : 검색어 필수체크시 필요한 form id [option]
	 */
	$.fn.fnSelGrid = function(select_url_str, search_data, form_id){
		if(!fnCheckRequired(form_id)) return;
		
		delarr = [];
	    $(this).clearGridData();  // 이전 데이터 삭제
	    $(this).jqGrid('setGridParam',{postData: null});
		$(this).jqGrid('setGridParam',{
			url: select_url_str,
			postData: {
				userdata: search_data 
			}
		}).trigger("reloadGrid");
		return false;
	};
	
	/**
	 *  Grid 행추가함수
	 */	
	$.fn.fnAddGrid = function(init){
		var data = init;
		if(init == undefined || init == null){
			data = {};
		}
		var rowIdx = 'new' + addidx;
		$(this).jqGrid('addRow',{rowID:rowIdx,initdata:data, position:'last', addRowParams : {keys:true, url:'clientArray'}    });
		addidx++;
		
		//required 항목 css 처리
		var cm = $(this).jqGrid('getGridParam','colModel');
		for(var i =0, len=cm.length;i<len; i++){
			if(cm[i].editrules) {
				if(cm[i].editrules.required){
					//alert(cm[i].name);
					var id = "#" + rowIdx + "_" + cm[i].name;
					if($(id).attr("class") == "editable"){
						$(id).addClass("must");
					}
				}
			}
		}
		
		
		return rowIdx;
	};
	
	/**
	 *  Grid 행 삭제함수(Grid상에서만 삭제처리)
	 */		
	$.fn.fnClientDelGrid = function(){
		//var arr = jQuery(this).jqGrid('getGridParam','selarrrow');
		var arr_s = jQuery(this).jqGrid('getGridParam','selarrrow');
		for(var i=0;i<arr_s.length;i++){
			var del = $(this).jqGrid('delRowData',arr_s[i]);
			//var del = true;
			//$("#"+arr_s[i]).css("background", "#ff0000"); //no delete row.
			//$("#list").jqGrid('setRowData', rowid , {STAT:"<font color='red'>U</font>"});
			//grid.jqGrid('setColProp', 'state', { editoptions: { value: states} }); 
			//var rows= jQuery("#list").jqGrid('getRowData');
			if(del){
				var ret = $(this).jqGrid('getRowData', arr_s[i]);
				ret.id = arr_s[i];
				ret.oper = "delete";
				delarr[delarr.length] = ret; 
			}
		}
		return false;
	};
	
	/**
	 *  Grid 행 삭제함수(서버에 바로 반영)
	 *  
	 *  select_url_str : 호출url
	 */	
	$.fn.fnDelGrid = function(save_url_str, etc_data){
		if(etc_data == "undefined" || etc_data == undefined) etc_data = {};
		
		var arr_s = jQuery(this).jqGrid('getGridParam','selarrrow');
		
		//unfocusing 처리 + 신규row삭제
		if(!$(this).fnDelInputDeleteRowTag()) return false;
		
		if(arr_s.length == 0){
			alert("선택된 ROW가 없습니다.");
			return false;
		}
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
		$(this).jqGrid('setGridParam',{postData: null});
		
		var del_eid = [];
		
		for(var i=0;i<arr_s.length;i++){
			//$("#"+arr_s[i]).css("background", "#ff0000"); //no delete row.
			var ret = $(this).jqGrid('getRowData', arr_s[i]);
			if(new String(arr_s[i]).indexOf('new') == -1){
				ret.id = arr_s[i];
				ret.oper = "delete";
				delarr[delarr.length] = ret;
			}else{
				del_eid[del_eid.length] = arr_s[i];
			}
		}
		
		//신규row filtering
		for(var i=0;i<del_eid.length;i++){
			$(this).jqGrid('delRowData',del_eid[i]);			
		}
		
		if(delarr.length == 0){
			alert("선택된 ROW가 없습니다.");
			return false;
		}	
		
		$(this).jqGrid('setGridParam',{
			url:save_url_str,
			postData: {
				deldata:delarr,
				userdata: etc_data
			}
		}).trigger("reloadGrid");
		
		var this_id = $(this);
		setTimeout(function(){
			if(errorMsg == ""){
				var return_data = $(this_id).jqGrid('getGridParam', 'userData');
				if(return_data != null && return_data.resultMsg != null){
					if(return_data.resultMsg != ""){
						alert(return_data.resultMsg);
					}
				}else{		
					alert('삭제되었습니다.');
				}			
			}
		}, 300);		
		
		delarr = [];
		
		//url 이 계속 delete로 남아 있을 경우 페이징처리시 에러발생하는 경우를 막기위함.
		var init_url = init_default_url[$(this).attr("id")];
		if(init_url){
			$(this).jqGrid('setGridParam',{
				url:init_url
			});
		}
		
		return false;
	};
	
	/**
	 *  Grid 저장함수
	 *  
	 *  save_url_str : 호출url
	 *  etc_data : 기타 저장 data, 형식:{key1:value1, key2:value2, ...}
	 */		
	$.fn.fnSaveGrid = function(save_url_str, etc_data){
		if(etc_data == "undefined" || etc_data == undefined) etc_data = {};
		
		$(this).jqGrid('setGridParam',{postData: null}); 
		
		//unfocusing 처리
		if(!$(this).fnDelInputTag()) return false;
		
		var arr_s = $(this).jqGrid('getGridParam','selarrrow');
		var arr = [];
		for(var i=0;i<arr_s.length;i++){
			var idx = arr_s[i];
			var ret = $(this).jqGrid('getRowData', idx);
			if(idx.indexOf('new') != -1){
				ret.oper = "insert";
			}else{
				ret.oper = "update";
			}
			ret.id = idx;
			
			arr[arr.length] = ret;
		}
		for(var i=0;i<delarr.length;i++){
			arr[arr.length] = delarr[i];
		}
		
		if(arr.length == 0) {
			alert("선택된 ROW가 없습니다.");
			return false;
		}
		if(!confirm("저장하시겠습니까?")){
			return false;
		}		
		
		$(this).jqGrid('setGridParam',{
			url:save_url_str,
			postData: {
				savedata:arr,
				userdata: etc_data,
				deldata:delarr
			}
		}).trigger("reloadGrid");

		//$("#result_msg").html('저장되었습니다.');
		
		var this_id = $(this);
		setTimeout(function(){
			if(errorMsg == ""){
				var return_data = $(this_id).jqGrid('getGridParam', 'userData');
				if(return_data != null && return_data.resultMsg != null){
					if(return_data.resultMsg != ""){
						alert(return_data.resultMsg);
					}
				}else{		
					alert('저장되었습니다.');
				}			
			}
		}, 300);

		
		delarr = [];
		
		//url 이 계속 save로 남아 있을 경우 페이징처리시 에러발생하는 경우를 막기위함.
		var init_url = init_default_url[$(this).attr("id")];
		if(init_url){
			$(this).jqGrid('setGridParam',{
				url:init_url
			});
		}
		
		return false;
	};
	
	/**
	 *  Grid 저장시 input tag 삭제처리 + 신규row 삭제함수
	 *  
	 */	
	$.fn.fnDelInputDeleteRowTag = function(){
		try{
			//unfocusing (input tag 삭제위해)
			var eid = [];
			var eic = [];
			var editcell = $(this).jqGrid('getGridParam', 'savedRow');
			for(var i=0;i<editcell.length;i++){
				eid[eid.length] = editcell[i].id;
				eic[eic.length] = editcell[i].ic;
			}
			
			for(var i=0;i<eid.length;i++){
				if(new String(eid[i]).indexOf('new') == -1){
					$(this).jqGrid("saveCell", eid[i], eic[i]); //update row
				}else{
					$(this).jqGrid('delRowData',eid[i]);
				}
			}
			
		}catch(e){
			alert(e);
			return false;
		}
		return true;
	};		
	
	/**
	 *  Grid 저장시 input tag 삭제처리 함수
	 *  
	 */		
	$.fn.fnDelInputTag = function(){
		try{
			//unfocusing (input tag 삭제위해)
			var editcell = $(this).jqGrid('getGridParam', 'savedRow');
			var eid = [];
			var eic = [];
			for(var i=0;i<editcell.length;i++){
				eid[eid.length] = editcell[i].id;
				eic[eic.length] = editcell[i].ic;
			}
			
			for(var i=0;i<eid.length;i++){
				if(new String(eid[i]).indexOf('new') == -1){
					$(this).jqGrid("saveCell", eid[i], eic[i]); //update row
				}else{
					$(this).jqGrid('saveRow', eid[i], false, 'clientArray'); //add row
				}
				//validation alert창 생성여부 확인
				if($("div").is("#info_dialog")){
					return false;
				}
			}
		}catch(e){
			alert(e);
			//throw e;
			return false;
		}
		return true;
	};
	
	
	/**
	 *  Grid 저장함수 - 체크여부와 무관하게 모든 grid data 전송
	 *  
	 *  save_url_str : 호출url
	 *  etc_data : 기타 저장 data, 형식:{key1:value1, key2:value2, ...}
	 */		
	$.fn.fnSaveAllGrid = function(save_url_str, etc_data){
		if(etc_data == "undefined" || etc_data == undefined) etc_data = {};
		
		$(this).jqGrid('setGridParam',{postData: null}); 

		if(!$(this).fnDelInputTag()) return false;
		
		var arr_s = $(this).jqGrid('getDataIDs');
		var arr = [];
		for(var i=0;i<arr_s.length;i++){
			var idx = arr_s[i];
			var ret = $(this).jqGrid('getRowData', idx);
			if(idx.indexOf('new') != -1){
				ret.oper = "insert";
			}else{
				ret.oper = "update";
			}
			ret.id = idx;
			
			arr[arr.length] = ret;
		}
		for(var i=0;i<delarr.length;i++){
			arr[arr.length] = delarr[i];
		}
		
		if(arr.length == 0) {
			alert("선택된 ROW가 없습니다.");
			return false;
		}
		if(!confirm("저장하시겠습니까?")){
			return false;
		}		
		
		$(this).jqGrid('setGridParam',{
			url:save_url_str,
			postData: {
				savedata:arr,
				userdata: etc_data,
				deldata:delarr
			}
		}).trigger("reloadGrid");
		
			var this_id = $(this);
			setTimeout(function(){
				if(errorMsg == ""){
					var return_data = $(this_id).jqGrid('getGridParam', 'userData');
					if(return_data != null && return_data.resultMsg != null){
						if(return_data.resultMsg != ""){
							alert(return_data.resultMsg);
						}
					}else{		
						alert('저장되었습니다.');
					}			
				}
			}, 300);
		
		delarr = [];
		
		//url 이 계속 save로 남아 있을 경우 페이징처리시 에러발생하는 경우를 막기위함.
		var init_url = init_default_url[$(this).attr("id")];
		if(init_url){
			$(this).jqGrid('setGridParam',{
				url:init_url
			});
		}
		
		return false;
	};	
	
	/**
	 *  Grid 칼럼에서 더블클릭하여 팝업창을 띄운후 리턴받은 값을 그리드내 컬럼에 세팅할때 사용함. 
	 *  
	 *  rowId : rowid
     *  iRow : row index
	 *	iCol : column index
	 *	data : json 형식의 데이터. {A:'value1', B:'value2',...}
	 *
	 * [사용예]
  	 *	var data = {BAT_JOB_RUN_KEY:result.BAT_JOB_RUN_KEY,
	 *			    JOB_NM:result.JOB_NM,
	 *				BAT_JOB_NM:result.BAT_JOB_NM,
	 *				APSVR_ID:result.APSVR_ID};
	 *	$("#list").fnSetGridPopData(rowId, iRow, iCol, data);
	 *
	 *
	 */		
	$.fn.fnSetGridPopData = function(rowId, iRow, iCol, data){
		if(new String(rowId).indexOf('new') == -1){
			//컬럼수정
			$(this).jqGrid('setRowData',rowId, data);
			
			$(this).jqGrid("restoreRow", rowId);
			//$(this).jqGrid("saveCell", iRow, 1);
			
			//checkbox control
			var val = $(this).jqGrid('getCell',rowId, 1);
	  		var cid = $(val).attr("id");
	  		if($("[id="+cid+"]").is(":checked")){
				  ;
			}else{
				$(this).jqGrid('setSelection', rowId, false);
			}			
		}else{
			//row추가
			var id, t = this;
			$.each(data, function(name, value){
				id = "#"+rowId+"_"+name;
				if($(id).length > 0){
					$(id).val(value);
				}else{
					$(t).jqGrid('setCell', rowId, name, value);
				}
			});
			//var ind = $(this).jqGrid("getInd",rowId,true);
			//var e = $.Event("keydown", { keyCode: 13 }); //Enter
			//$(ind).trigger(e);
		}
	};
	
	//--
	$.fn.fnForceEnterGrid = function(rowIdx){
		alert('deprecated function');
	};
})(jQuery);



/* datepicker */
jQuery(function($){
	$.datepicker.setDefaults({
		showOn: 'both',
		buttonImageOnly: true,
		buttonImage: '/smartnew/fms/images/icoCal.gif',
		buttonText: 'Calendar',
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd" });
	
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});


