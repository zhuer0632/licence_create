<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign path="${request.getContextPath()}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试web下java读取硬件信息</title>
<link href="${path}/resource/common.css"  rel="stylesheet" type="text/css" />
<script src="${path}/resource/jquery-1.7.2.min.js"></script>
<script src="${path}/resource/common.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
			  var result=getJson("","${path}/lic_ctrl/sys_read.do");
			  if(result==undefined)
			  {
			  			alert("服务器端错误");
			  			return;
			  }
			  else
			  {
			  		var  msg="";
			  		msg=msg+"硬盘："+result['HDID']+"<br>"
			  		msg=msg+"CPU："+result['CPUID']+"<br>"
			  		msg=msg+"网卡："+result['MACID']+"<br>"
			  		$("#div_msg").html(msg);
			  }
	});
</script>
<style type="text/css">
	div
	{
		margin: 35px;
		border: 1px solid red;
		padding:5px;
	}
</style>
</head>
<body>
		<div style="margin: 20px;"><a href="${path}">首页</a></div>
		<div id="div_msg">
							
		</div>					
		
</body>
</html>