<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<script src="http://223.202.15.20/js/jquery.js"></script>
 <link rel="stylesheet" href="http://223.202.15.20/js/jquery-ui.css" />
  <script src="http://223.202.15.20/js/jquery-ui.js"></script>
<script>
    function jump(offset){
        var checked = 'no';
        if ($("#reply").is(':checked'))
            checked = 'yes';
        var adv = 'no';
        if ($("#adv-div").css('display') == 'block')
            adv = 'yes';
        window.location = './q?type=${type}&q=' + encodeURI($("#q0").val()) + '&author=' + encodeURI($("#author").val()) + '&reply=' + checked  + '&adv=' + adv + '&offset=' + offset; 
    }

$(document).ready(function(){
    if ("${adv}" == "yes") {
        $("#adv-div").show();

        if ("${reply}" == "yes")
            $("#reply").prop('checked', true);
        else
            $("#reply").prop('checked', false);
        $('#author').prop('value', "${author}");
    } else
        $("#adv-div").hide();


    $("#submit-btn0").click(function(){
        jump(0);
    });

    $("#top20 li").click(function(e){
        $("#author").val($(this).text());
    });


    $("#submit-btn1").click(function(){
        window.location = './q?type=${type}&q=' + encodeURI($("#q1").val()); 
    });

   $("#adv-toggle").click(function(){
        $("#adv-div").toggle();
    });

    $('#q0,#author').keypress(function (e) {
      if (e.which == 13) {
        e.preventDefault();
        jump(0);
      }
    });

    $( "#author" ).autocomplete({
   source: function( request, response ) {
    $.ajax({
     type       : 'POST',
     url        : 'auto',
     data       : {
      q: $("#author").val() 
     },
     contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
     dataType   : 'json',
     success    : function( result ) {
      response( $.map( result, function( eachElement ) {
       return {
        label : eachElement,
        value : eachElement
       }
      }));
     },
     error      : function(obj, err) {
     }
    });
   },
   minLength: 1,
   select: function( event, ui ) {
   },
   open: function() {
   },
   close: function() {
   }
  });
});

</script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta name="keywords" content="自闭症儿童">
<title>以琳自闭症论坛-${query}-搜索结果</title>
<style type="text/css">
*{margin:0;padding:0;}
li{list-style:none}
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
.search-wrap{width:760px;_margin-top:-15px;text-align: center;}
.search-form{padding-left:25px;}
.search-input{width: 300px;height: 36px;position: relative;float: left;*display: inline;*zoom:1;border:1px solid rgb(88, 139, 194);background: url(img/search_icon.png) no-repeat 8px 11px;}
.search-input input{width:270px;border:0 none;padding:3px 0;padding:9px 0 2px 0px\0;*padding:8px 0 2px 0px;height:30px;height:25px\0;*height:24px;_height:24px;line-height: 25px;font: 16px arial;margin-left:28px;vertical-align: top;}
.search-author{position: relative;width:190px;height:36px;margin-left:10px; margin-right:10px; float: left;*display: inline;*zoom:1;border:1px solid rgb(88, 139, 194);}
.search-author input {float:left;width:140px;border:0 none;padding:3px 0;padding:9px 0 2px 0px\0;*padding:8px 0 2px 0px;margin-left:10px;height:30px;height:25px\0;*height:24px;_height:24px;line-height: 25px;font: 16px arial;vertical-align: top;}
.advance-input {width:300px;height:30px;margin-top:5px;margin-right:20px;font:16px arial;}
.advance-check {width:300px;height:30px;margin-top:5px;font:16px arial;}
#adv-div{width:618px;border:1px solid rgb(204, 204, 204);margin-top:10px;line-height:25px;padding-left:8px;height:125px}
#top20{padding:0px; margin-left:5px; text-align:left; float:left; width: 525px;}
#top20 li{margin:0px;padding-left:5px;padding-right:5px;width:95px;float:left;overflow:hidden;text-decoration: underline;color:rgb(0,0,255);}
#search-form{width:760px;}
#search-form .submit-btn{float: left;*display: inline;*zoom:1;width:107px;height:38px;background: url(img/search_btn.png) no-repeat 0 0;margin:0 0 0 -2px;}
.more-btn{position:relative;top:10px;float: right;*display: inline;*zoom:1;width:17px;height:17px;background: url(img/more.png) no-repeat 0 0;margin-right:10px;}
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
.search-art{width:34%;height:42px;font-size: 13px;color:#535353;overflow: hidden;}
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
    <a href="javascript:window.location.href=encodeURI('./q?q=${query}&type=0')" class="word dl<#if type==0> active </#if>">全部</a>
    <p class="bg<#if type!=0> hide </#if>"></p>
  </div>
  <div class="choice dl">
    <a href="javascript:window.location.href=encodeURI('./q?q=${query}&type=1')" class="word dl<#if type==1> active </#if>">版块</a>
    <p class="bg<#if type!=1> hide </#if>"></p> </div> <div class="choice dl"> <a href="javascript:window.location.href=encodeURI('./q?q=${query}&type=2')" class="word dl<#if type==2> active </#if>">用户</a> <p class="bg<#if type!=2> hide </#if>"></p> </div> </div>

<div class="pos">
  <div class="search-wrap cf" >
    <form id="search-form" action="./q">
      <div class="search-input">
          <input type="text" name="q" id="q0" value="${query}" placeholder="查询词"/>
          <input type="hidden" name="type"  value="${type}"/>
      </div>
      <#if type==0>
      <div class="search-author">
          <input type="text" name="author" id="author" value="${author}" placeholder="发帖人"/>
          <a class="more-btn" id="adv-toggle"></a>
      </div>
      </#if>
      <a class="submit-btn" id="submit-btn0"></a>
    </form>
  </div>
  <#if type==0>
  <div class="tip"><a href="http://www.sojump.com/jq/2615772.aspx">反馈意见</a></div>
  </#if>
  <div id="adv-div">
        <div>
            <input type="checkbox" class="advnace-check" id="reply"/>
            包含回复
        </div>
        <span style="float:left;width:85px;">
            活跃发帖者:
        </span>
        <ul id="top20">
            <li>方静</li>
            <li>thankstoyilin</li>
            <li>binfeng2000</li>
            <li>秋爸爸</li>
            <li>燕原</li>
            <li>云鹤</li>
            <li>何子</li>
            <li>sxy</li>
            <li>pastryjing</li>
            <li>语晨313</li>
            <li>JJmum</li>
            <li>张雁</li>
            <li>daluobo</li>
            <li>kwenma2</li>
            <li>老虎娘</li>
            <li>清潭</li>
            <li>柴火</li>
            <li>玮玮</li>
            <li>zouwen</li>
            <li>IBelieve</li>
        </ul>
  </div>
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
	      <a href="${item["url"]}" target="_blank" class="search-title">${item["title"]}</a>
	      <p class=""><a href="${item["url"]}" target="_blank" class="link-green">${item["url"]}</a></p>
          <#if item["type"] == "discussion">
	      <p class="search-detail">
		 <span>${item["created"]}</span>
		 <span>发帖人 <a href="${item["author_url"]}">${item["author_name"]}</a></span>
		 <span>浏览量 ${item["hits"]}</span>
	      </p>
          </#if>
	      <p class="search-art">${item["content"]}</p>
	    </div>
	</#if>
</#list>


<#if (total > 0)>
    <div class="page">
    <#if (offset >= 20)>
        <a href=" javascript:jump(${(offset-20)?c})">上一页</a>
    <#else>
        上一页
    </#if>

    <#assign now = offset/20+1>
    <#assign max = ((total+19)/20)?int>
    <#list -5..5 as i>
        <#if ((i+now) > 0 && (i + now) <= max)>
        
        <a href="javascript:jump(${((i + now -1)*20)?c})" class="<#if i == 0>active<#else></#if>">${i+now}</a>	
        </#if>
    </#list>


    <#if (offset + 20 <= total)>
        <a href="javascript:jump(${(offset+20)?c})">下一页</a>
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
              <input name="q" id="q1" type="text" value="${query}" />
	      <input type="hidden" name="type"  value="${type}"/>
          </div>
          <a class="submit-btn" id="submit-btn1"></a>
        </form>
      </div>
  </div>
</#if>
</div>
</div>
</body>
</html>

