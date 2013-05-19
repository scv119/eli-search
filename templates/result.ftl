<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf8">
    <title>Search Result</title>
</head>

result ids:
<br>
<#list ret as item>
    ${item["title"]} <br>
    ${item["content"]}<br>
     ${item["url"]}
</#list>
<br>


</br>
</body>
</html>