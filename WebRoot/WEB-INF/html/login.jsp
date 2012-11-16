<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign path="${request.getContextPath()}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成lic文件</title>
<link href="${path}/resource/common.css"  rel="stylesheet" type="text/css" />
<script src="${path}/resource/jquery-1.7.2.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		 
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
			<div>
				<a href="${path}/l/lic_create.do">生成licence</a>
			</div>
			<div>
				<a href="${path}/l/lic_read.do">验证licence</a>
			</div>
			<div style="display: none;">
				<a href="${path}/l/sys_read.do">读取硬件信息</a>
				<br>
				<label>需要把\WEB-INF\lib\swt-extension-win32.dll,\WEB-INF\lib\swt-win32.dll拷贝到tomcat\bin下或者jdk\bin下</label>
			</div>
</body>
</html>