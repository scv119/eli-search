<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf8">
    <title>Search Result</title>
    <style>
        .hi {
            color: #c20a0a;
        }

        .search {
            color: rgb(51, 51, 51);
            display: block;
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            font-size: 33px;
            height: 66px;
            line-height: 18px;
            width: 940px;
        }

        .box {
            width:80%;
            font-size: 23px;
        }

        .btn {
            width:10%;
            font-size: 23px;
         }

        .title {
            font-size: 17px;
            border: 2px;
            margin: 5px;
        }

        .url {
            font-size: 12px;
            border: 0px;
            margin: 0px;
        }

        .content {
            font-size: 16px;
            border: 0px;
            margin: 0px;
        }
    </style>
</head>
<div class="search">
<form method="GET" action="/q" >
<input type="text" value="${query}" placeholder="搜索..." name="q" class="box">
<button type="submit" class="btn" >搜索</button>
</form>
</div>

<#if (total > 0)>
<div class="pg">
<#if (offset >= 20)>
<a href="./q?q=${query}&offset=${offset-20}">上一页</a>
<#else>
上一页
</#if>
${offset/20+1}/${page}
<#if (offset + 20 <= total)>
<a href="./q?q=${query}&offset=${offset+20}">下一页</a>
<#else>
下一页
</#if>
</div>
</#if>


<div>
<#list ret as item>


    <li class="title"><a href="${item["url"]}">${item["title"]}</a>
        <ul class="url"><a href="${item["url"]}">${item["url"]}</a></ul>
        <ul class="content">${item["content"]}</ul>
    </li>
</#list>
</div>

<#if (total > 0)>
<div class="pg">
<#if (offset >= 20)>
<a href="./q?q=${query}&offset=${offset-20}">上一页</a>
<#else>
上一页
</#if>
${offset/20+1}/${page}
<#if (offset + 20 <= total)>
<a href="./q?q=${query}&offset=${offset+20}">下一页</a>
<#else>
下一页
</#if>
</div>
</#if>

</br>
</body>
</html>