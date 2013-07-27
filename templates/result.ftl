<!DOCTYPE html>
<html>
<head>
<script src="./js/jquery.js"></script>
<script>
$(document).ready(function(){
  $("#submit-btn0").click(function(){
    window.location = './q?type=${type}&q=' + $("#q0").val() ; 
  });
  $("#submit-btn1").click(function(){
    window.location = './q?type=${type}&q=' + $("#q1").val(); 
  });
});

</script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta name="keywords" content="自闭症儿童">
<title>以琳自闭症论坛-${query}-搜索结果</title>
<style type="text/css">
*{margin:0;padding:0;}
body{font-family: "微软雅黑";font-size: 15px}
input[type=text],
input[type=password],
input[type=text]:focus,
input[type=password]:focus,
textarea:focus {
  color: #373737;
  outline:none;
}
a{text-decoration: none}
a:focus{
  outline:none;
}
.cf:before,.cf:after{content:"";display:table;}
.cf:after{clear:both;}
.cf{zoom:1;}
.fl{float:left;display:inline;}
.fr{float:right;display:inline;}
.dl{display: inline-block;*display: inline;*zoom:1;_display: inline;_zoom:1;}
.cutoff{overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}
.search-wrap{width:560px;_margin-top:-15px;text-align: center;}
.search-form{padding-left:25px;}
.search-input{position: relative;float: left;*display: inline;*zoom:1;border:1px solid rgb(88, 139, 194);background: url(img/search_icon.png) no-repeat 8px 11px;}
.search-input input{width:418px;border:0 none;padding:3px 0;padding:9px 0 2px 0px\0;*padding:8px 0 2px 0px;height:30px;height:25px\0;*height:24px;_height:24px;line-height: 25px;font: 16px arial;margin-left:28px;vertical-align: top;}
#search-form{width:560px;}
#search-form .submit-btn{float: left;*display: inline;*zoom:1;width:107px;height:38px;background: url(img/search_btn.png) no-repeat 0 0;margin:0 0 0 -2px;}
.search-wrap .shadow-x{
   position:absolute;
   top:0;
   left:1px;
   width:445px;
   height:0;
   border-top:1px solid #d0d0d0;
   border-bottom:1px solid #f0f0f0;
   overflow:hidden;
   z-index:1;
}
.search-wrap .shadow-y{
   position:absolute;
   top:0;
   left:0;
   width:0;
   height:36px;
   border-left:1px solid #d0d0d0;
   border-right:1px solid #f0f0f0;
   overflow:hidden;
}
.search-tab .choice{padding:0px 10px;margin-bottom: 0px;}
.search-tab .bg{width:30px;height:5px;background: url(img/triangle.png) no-repeat 10px 0px;text-align: center;margin-top:5px; }
.search-tab .word{font-size: 15px;font-family: "微软雅黑";text-decoration: none;color:#535353;}
.search-tab .active{color:#448DD4;}
.search-count{margin:10px 0;}
.search-count p{color:rgb(126, 126, 126);font-size: 15px}
.item{margin:20px 0;}
.item a{color:rgb(34, 71, 190);}
.item img.avatar{width:55px;height:55px;margin-right:10px }
.item .search-title{color:rgb(30, 75, 218);}
.item .search-red{color:#ff6633;}
.item a.link-green{color:#33cc00;font-size: 13px}
.user-type{color:rgb(111, 111, 111);font-size: 13px}
.search-art{width:34%;height:45px;font-size: 13px;color:#535353;overflow: hidden;}
.search-detail span{margin:0 5px 0 0px;color:#535353;font-size: 14px}
.page{margin:10px 0;}
.page a{padding:5px;color:#3399cc;}
.page a.active{color:#ff9900;}
.page .next{color:rgb(30, 75, 218);text-decoration: underline;}
.search-relative a{padding:3px 10px;color:rgb(34, 71, 190);}
.search-relative span{color:#535353;}
.pos{position: relative;width:750px;}
.pos .tip{position: absolute;right:20px;top:10px;*top:23px;_top:23px;}
.pos .tip a{padding:0 10px;color:rgb(30, 75, 218);;}
.wrap{margin-left:30px;margin-top:20px;}
.hide{visibility:hidden;}
</style>
</head>
<body>
<div class="wrap">
<div class="search-tab">
	<div class="choice dl">
    <a href="./q?q=${query}&type=0" class="word dl<#if type==0> active </#if>">全部</a>
    <p class="bg<#if type!=0> hide </#if>"></p>
  </div>
  <div class="choice dl">
    <a href="./q?q=${query}&type=1" class="word dl<#if type==1> active </#if>">版块</a>
    <p class="bg<#if type!=1> hide </#if>"></p>
  </div>
  <div class="choice dl">
    <a href="./q?q=${query}&type=2" class="word dl<#if type==2> active </#if>">用户</a>
    <p class="bg<#if type!=2> hide </#if>"></p>
  </div>
</div>

<div class="search-wrap cf">
	<form id="search-form" action="./q">
		<div class="search-input">
		    <div class="shadow-x"></div>
		    <div class="shadow-y"></div>
		    <input type="text" name="q" id="q0" value="${query}"/>
		    <input type="hidden" name="type"  value="${type}"/>
		</div>
		<a class="submit-btn" id="submit-btn0"></a>
	</form>
</div>
<div class="search-count">
    <p>搜索到${total}个结果</p>
</div>
<div class="search-result">
<#list ret as item>
	<#if item["avatar"]??>
		<div class="item cf">
		      <img src="${item["avatar"]}" alt="" class="fl avatar"/>
		      <div class="fl">
			<p><a href="${item["url"]}" class="search-red">${item["title"]}</a></p>
			<p><a href="${item["url"]}" class="link-green">${item["url"]}</a></p>
			<p class="user-type">以琳用户</p>
		      </div>
		</div>	
	<#else>
	    <div class="item cf">
	      <a href="${item["url"]}" class="search-title">${item["title"]}</a>
	      <p class=""><a href="${item["url"]}" class="link-green">${item["url"]}</a></p>
	      <p class="search-detail">
		 <span>2013-07-25 22:11:56</span>
		 <span>主题 148</span>
		 <span>发帖人 <a href="#">夏小桃</a></span>
		 <span>总量 166</span>
		 <span><a href="#">-帖子</a></span>
	      </p>
	      <p class="search-art">${item["content"]}</p>
	    </div>
	</#if>
</#list>


<#if (total > 0)>
    <div class="page">
    <#if (offset >= 20)>
        <a href="./q?type=${type}&q=${query}&offset=${(offset-20)?c}">上一页</a>
    <#else>
        上一页
    </#if>

    <#assign now = offset/20+1>
    <#assign max = ((total+19)/20)?int>
    <#list -5..5 as i>
        <#if ((i+now) > 0 && (i + now) <= max)>
        
        <a href="./q?type=${type}&q=${query}&offset=${((i + now -1)*20)?c}" class="<#if i == 0>active<#else></#if>">${i+now}</a>	
        </#if>
    </#list>


    <#if (offset + 20 <= total)>
        <a href="./q?type=${type}&q=${query}&offset=${(offset+20)?c}">下一页</a>
    <#else>
        下一页
    </#if>
    </div>

    <div class="search-relative">
<!--
      <span>相关搜索</span><a href="#">夏小桃</a><a href="#">夏小桃</a><a href="#">夏小桃</a>
-->
    </div>
    <div class="pos">
      <div class="search-wrap cf" style="margin-top:15px;">
        <form id="search-form" action="./q">
          <div class="search-input">
              <div class="shadow-x"></div>
              <div class="shadow-y"></div>
              <input name="q" id="q1" type="text" value="${query}" />
	      <input type="hidden" name="type"  value="${type}"/>
          </div>
          <a class="submit-btn" id="submit-btn1"></a>
        </form>
      </div>
      <div class="tip"><!-- <a href="">高级搜索</a> --> <a href="http://www.sojump.com/jq/2615772.aspx">反馈意见</a></div>
  </div>
</#if>
</div>
</div>
</body>
</html>

