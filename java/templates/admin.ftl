<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf8">
    <title>Search Demo</title>
</head>
<body>
qps:${qps}<br>


索引状态:
正在重建?${rebuildIndex}
<br>
当前索引版本号${currentVersion}
重建索引版本号${futureVersion}
<br>




重建索引
<form action="./admin" method="post">
    <input type="hidden" name="reload" value="true"/>
    <input type="submit">
</form><br>

commit索引
<form action="./admin" method="post">
    <input type="hidden" name="commit" value="true"/>
    <input type="submit">
</form><br>

</body>
</html>