<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign path="${request.getContextPath()}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成lic文件</title>
<link href="${path}/resource/common.css"  rel="stylesheet" type="text/css" />
<link href="${path}/resource/upload.css" rel="stylesheet" type="text/css" />
<script src="${path}/resource/jquery-1.7.2.min.js"></script>
<script src="${path}/resource/swfupload.js"></script>
<script src="${path}/resource/swfupload.queue.js"></script>
<script src="${path}/resource/fileprogress.js"></script>
<script src="${path}/resource/handlers.js"></script>
<script src="${path}/resource/hz_upload.js"></script>
<script src="${path}/resource/datepicker/WdatePicker.js"></script>
<script src="${path}/resource/common.js"></script>
<script src="${path}/resource/common_check.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		createSwfFun();
	
	  $("input[name='official_version']:radio:checked").click(show_hide_tr());
	  $("input[name='official_version']").change(show_hide_tr);
	   
	  $("#lic_url").text("");
	  
	});
	function createSwfFun()
	{
		var fieldId = "${fileId}";
	    createSwf(fieldId, "sysinfo", "上传[SysInfo.properties]", $("#fileList_sysinfo"), "${path}", 150);
	}
	
	function show_hide_tr()
	{
		var  c=$("input[name='official_version']:radio:checked");
		if($(c).val()=="true")
		{	
			 //如果是正式版
			 $(".table_form").find("tr:first").hide();
			 $(".table_form").find("tr:eq(4)").show();
		}
		else
		{		
			$(".table_form").find("tr:first").show();
			$(".table_form").find("tr:eq(4)").hide();
		}
	}
	
	function submit()
	{
		    if(!check())
		    {
					return ;
		    }
		    
		    var  endtime_str=$("input[name='endtime_str']").val();
		    var official_version=$("input[name='official_version']:radio:checked").val();
		    var  mycode=$("input[name='mycode']").val();
		    var  customer_code=$("input[name='customer_code']").val();
			var args=new Object();
			args['endtime_str']=endtime_str;
			args['official_version']=official_version;
			args['mycode']=mycode;
			args['customer_code']=customer_code;
			
		    var result=getJson(args,"${path}/lic_ctrl/create.do");
		    if(result==undefined)
		    {
			    alert("服务器错误");
			    return;
	    	 }
	    	if(result['diverr']!=undefined)
	    	{
	    			setMsg("input","",result['diverr']); 
					return ;
	    	} 
	    	else
	    	{
	    			var  downurl=result['downurl'];
	    			$("#lic_url").css("display","block");
	    			$("#lic_url").attr("href",downurl);
	    			$("#lic_url").text("下载地址[bea.lic]["+new Date().getTime()+"]");
	    	}
	}
	
	function check()
	{
		  if(isBlank($("input[name='mycode']").val()))
		  {
			  setMsg("input","mycode","操作密码不能为空"); 
		      return false;
		  }
		  return true;
	}
	
	
</script>
</head>
<body>

		<div style="margin: 20px;"><a href="${path}">首页</a></div>
		<div class="msgbox">
			<span class="errmsg"></span>
		</div>
		
		<div style=" border: 1px red  solid; margin:10px;padding:10px;">
		 <table  class="table_form">
		 	<tr>
		 		<td class="tableformtitxt">到期时间[试用版]</td>
		 		<td colspan="2">
		 				<input name="endtime_str"  type="text"  readonly="readonly"  value="${endtime_str}" onClick="WdatePicker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
		 		</td>
		 	</tr>
		   <tr>
		 		<td class="tableformtitxt">是否是正式版</td>
		 		<td colspan="2">
		 				是<input type="radio"  name="official_version"    value="true">  
		 				否<input type="radio"  name="official_version"  checked value="false">
		 		</td>
		 	</tr>
		 	
		 	<tr>
		 		<td class="tableformtitxt">操作密码</td>
		 		<td><input name="mycode"  type="text"></td>
		 		<td>?询:qq584022269</td>
		 	</tr>
		 	
			<tr>
		 		<td class="tableformtitxt">分店编码</td>
		 		<td><input type="text" name="customer_code"/></td>
		 		<td>无特殊意义，可以为空</td>
		 	</tr>
			
   			<tr>
		 		<td class="tableformtitxt">硬件信息 </td>
		 		<td>
		 			 <!-- 上传硬件信息   字段名是: lic  -->
			        <div id="outdiv_sysinfo" class="divUpload">
					</div>
		 		</td>
		 		<td>
		 			<div id="fileList_sysinfo" class="divFileList">
					</div>
					<div id="progress_sysinfo"></div>
		 		</td>
		 	</tr>
		 	 
		 	 <tr>
		 	 	<td>
		 	 		<input  type="button"  value="生成lic文件"  onclick="javascript:submit();" />
		 	 	</td>
				<td colspan="2"  >
						<a href="" id="lic_url"   style="display: none;">
						</a>
				</td>
				
		 	 </tr>
		</table>
		</div>
		
		
</body>
</html>