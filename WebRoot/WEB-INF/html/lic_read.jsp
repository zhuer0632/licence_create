<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign path="${request.getContextPath()}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证licence文件内容</title>
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
			
			createSwf_();
		
	});
	function createSwf_()
	{
			var fileId="${fileId}";
	        createSwf(fileId, "bealic_upload", "上传[bea.lic]", $("#fileList_bealic_upload"), "${path}", 100);
	}
	
	function read_bealic()
	{
			var  result=getJson("","${path}/lic_ctrl/read_bealic.do");
			if(result==undefined)
			{
				alert("服务器端错误");
				return;	
			}
			if(result['diverr']!=undefined)
			{
				alert(result['diverr']);	
			}
			else
			{
					var out=result['info'];
					var  msg=""
					msg=msg+"是否是正式版："+out['official_version']+"<br>";
					msg=msg+"过期时间："+out['endtime_str']+"<br>";
					msg=msg+"分店标示："+out['customer_code']+"<br>";
					msg=msg+"网卡："+out['macid']+"<br>";
					msg=msg+"CPU："+out['cpuid']+"<br>";
					msg=msg+"硬盘："+out['hdid']+"<br>";
					$("#id_msg").html(msg);
			}
	}
	
</script>
<style type="text/css">
	.d
	{
		margin: 35px;
		border: 1px solid red;
		padding:5px;
	}
</style>
</head>
<body>
				
		 		<div class="d">
		 			<a href="${path}">首页</a>			
		 		</div>
		 		
		 		<div class="d">
		 					<table  class="table_form">
	 								<tr>
	 									<td  class="tableformtitxt">上传bea.lic</td>
	 									<td>
	 												 <div id="outdiv_bealic_upload" class="divUpload">
													</div>
	 									</td>
	 									<td>
								 			<div id="fileList_bealic_upload" class="divFileList">
											</div>
											<div id="progress_bealic_upload"></div>
	 									</td>
	 								</tr>
									<tr>
										<td  class="tableformtitxt">读取的文件信息</td>
										<td colspan=2">
												<div id="id_msg"></div>
										</td>
									</tr>
									
									<tr>
										<td colspan="3">
												<input  type="button"  value="查看信息"    onclick="javascript:read_bealic();" />
										</td>
									</tr>
		 					</table>
		 		</div>
		
</body>
</html>