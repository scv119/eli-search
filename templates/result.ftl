<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf8">
    <title>Search Result</title>
    <style>
	   #center {
            margin-left: 120px;
            margin-right:254px;
            width:528px;
            padding-left: 8px;
            padding-right: 8px;
            font-family: Arial, sans-serif;
        }

        .hi {
            font-weight: bold;
        }

        .search {
            color: rgb(51, 51, 51);
            margin-left: 120px;
            margin-right:254px;
            display: block;
            font-size: 13px;
            height: 50px;
            line-height: 18px;
            width: 500px;
        }

        .box {
            width:80%;
            font-size: 16px;
        }

        .btn {
            width:10%;
            font-size: 16px;
         }

        .item {
            font-size: 16px;
            color: rgb(17, 85, 204);
            font-size: 16px;
            height: 70px;
            margin-bottom:23px;
            line-height: 19px;
        }

        .title {
            font-size: 16px;
            color: rgb(17, 85, 204);
            font-size: 16px;
            line-height: 19px;
            border: 0px;
            margin: 0px;
        }

        .url {
            font-size: 14px;
            color: rgb(0, 153, 51);
            height: auto;
            line-height:16px;
            padding-bottom: 2px;
            border: 0px;
            margin: 0px;
        }

        .content {
            color: rgb(68,68,68);
            font-size: 13px;
            height: auto;
            line-height:16px;
            border: 0px;
            margin: 0px;
        }


           .avatar {
                height: 60px;
                width:  60px;
                padding-right: 10px;
                float: left;
            }

        .pg {
            font-family: arial, sans-serif;
            height: 35px;
            font-size: 13px;
            width: 308px;
            margin-left: 110px;
            margin-right: 110px;
        }
    </style>
</head>
<div class="search">
<form method="GET" action="/q" >
<input type="text" value="${query}" placeholder="搜索..." name="q" class="box">
<button type="submit" class="btn" >搜索</button>
</form>
</div>


<div id="center">
<#list ret as item>
    <div class="item">
    <#if item["avatar"]??>
     <img class="avatar" src="${item["avatar"]}"/>
    </#if>
    <div class="title"><a href="${item["url"]}">${item["title"]?html}</a></div>
    <div class="url">${item["url"]}</div>
    <div class="content">${item["content"]?html}</div>
   </div>
</#list>

<#if (total > 0)>
<div class="pg">
<#if (offset >= 20)>
<a href="./q?q=${query}&offset=${offset-20}">上一页</a>
<#else>
上一页
</#if>
${offset/20+1}/${(total+19)/20}
<#if (offset + 20 <= total)>
<a href="./q?q=${query}&offset=${offset+20}">下一页</a>
<#else>
下一页
</#if>
</div>
</#if>

</div>

</br>
</body>
</html>
