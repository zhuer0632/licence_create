//校验各种常规字段


//是否是空
function isBlank(v) {
    v = commonTrim(v);
    if (v.length == 0) {
        return true;
    }
    else {
        return false;
    }
}

//检查长度是否小于指定的值
function isLessthen(v,len) {
v = commonTrim(v);
if (v.length < len) 
{
	return true;
}
else {
return false;
}
}

//是否是数字
function isNum(v) {

	v = commonTrim(v);
    var reg = /^\d+$/;
    return reg.test(v);
} 


//邮箱
function isEmail(v) {
    v = commonTrim(v);
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    return reg.test(v);
}
//电话验证
function isPhone(v)
{
	return isTelephone(v) || isMobileNum(v);
}
//判断邮政编码
function isPostCode(v)
{ 	v = commonTrim(v);
	var reg = /^[1-9][0-9]{5}$/;
	 return reg.test(v);
}

//区号必填为3-4位的数字，区号之后用“-”与电话号码连接
//电话号码为7-8位的数字
//分机号码为3-4位的数字，非必填，但若填写则以“-”与电话号码相连接
function isTelephone(v) {
    v = commonTrim(v);
    var reg = /^\d{3,4}-\d{7,8}(-\d{3,4})?$/;
    return reg.test(v);
}
//整数判断
function isInteger(v){
	v=commonTrim(v);
	var reg= /^\d+$/;
	return reg.test(v);
}
//浮点数判断
function isDouble(v){
		v=commonTrim(v);
	    var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)|0)$/;
	    return reg.test(v);
}
//验证手机号
function isMobileNum(v) 
{
    v = commonTrim(v);
    var reg = /^0*(13|15|14|18)\d{9}$/;
    return reg.test(v);
}
//验证手机号[以13开头的]
function isMobileNumThirteen(v) 
{
    v = commonTrim(v);
    var reg = /^13\d{9}$/;
    return reg.test(v);
}
//验证手机号[不以18开头的]
function isMobileNumEighteen(v) 
{
    v = commonTrim(v);
    var reg = /^0*(13|15|14)\d{9}$/;
    return reg.test(v);
}
//验证手机号[以13开头的]
function isThirteenMobileNum(v)
{
	return isMobileNumThirteen(v) || isTelephone(v);
}
//验证手机号[不以18开头的]
function isNotEighteenMobileNum(v)
{
	return isMobileNumEighteen(v) || isTelephone(v);
}

//验证身份证号码，15位，18位，18位的时候最后一位可能是x(X)
function isIDNum(v) {
    v = commonTrim(v);
    var reg = /^(\d{14}|\d{17})(\d|[xX])$/;
    return reg.test(v);
}

//验证日期格式  yyyy-MM-dd 
//4位数字，横线，1或者2位数字，再横线，最后又是1或者2位数字。
function isDate(v) 
{
    v = commonTrim(v);
    var reg = /^\d{4}-\d{1,2}-\d{1,2}$/;
    return reg.test(v);
}

//验证小数格式
//必须有小数点，小数点欠至少有1位，小数点后最多有2位
function isFloat(v) 
{
    v = commonTrim(v);
    var reg = /^\d+\.\d{1,2}$/;
    return reg.test(v);
}
//验证传真格式是否正确
//校验普通电话、传真号码：可以“+”或数字开头，可含有“-” 和 “ ”
function isFax(v){
	v=commonTrim(v);
	//var reg=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	var reg = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/;
	return reg.test(v);
}

//返回中文字符的总长度，一个汉字长度算1。
function cLength(v) {
    var reg = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g;
    //匹配非中文的正则表达式 
    var temp = v.replace(reg, '');
    return temp.length;
}


//是否全部是中文
function isC(v) {
    //原始长度
    var length1 = v.length;
    //其中含有的中文长度
    var length2 = cLength(v);
    if (length1 == length2) {
        return true;
    }
    else {
        return false;
    }   
}
 

//-----以上是常用方法

//去除左右两边的空格
function commonTrim(v) {
    return v.replace(/(^\s*)|(\s*$)/g,"");  
}

//执行正则表达式验证
function regTest(regStr, Str) {
    
    var patt1 = new RegExp(regStr);
    var result = patt1.test(Str);
    
    return result;
}



