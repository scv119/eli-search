    <%
    Function Base64Encode(psText)
         dim oXml, oStream, oNode
         Set oXml =Server.CreateObject("MSXML2.DOMDocument")
             Set oStream =Server.CreateObject("ADODB.Stream")
                 Set oNode =oXml.CreateElement("tmpNode")
                     oNode.dataType ="bin.base64"
                     oStream.Charset ="gb2312"
                     oStream.Type =2
                     If oStream.state =0 Then oStream.Open()
                     oStream.WriteText(psText)
                     oStream.Position =0
                     oStream.Type =1
                     oNode.nodeTypedValue =oStream.Read(-1)
                     oStream.Close()
                     Base64Encode =oNode.Text
                 Set oNode =Nothing
             Set oStream =Nothing
         Set oXml =Nothing
    End Function


        Dim conn
        Dim rs
        Dim sql
        Dim rtype
        Dim para1
        Dim para2
        Dim idx0
        Dim idx1

        set conn=Server.CreateObject("ADODB.Connection")
        conn.Provider="Microsoft.Jet.OLEDB.4.0"
        conn.Open Server.MapPath("DATA/yl3504503.mdb")
        set rs = Server.CreateObject("ADODB.recordset")
        set rtype = Request.QueryString("type")
        set para1 = Request.QueryString("since")
        set para2 = Request.QueryString("limit")
        if NOT Request.QueryString("token")="ksdf3pikj13asdf" then
            Response.End
        end if
        if (NOT IsNumeric(para1)) OR (NOT IsNumeric(para2)) then
            Response.End
        end if
        if rtype = "announce" then
            sql="select ID, ParentID, TopicSortID, BoardId, RootId, Title, Content, ndatetime,Hits,UserID from LeadBBS_Announce where ID in ( select top "&para2&" ID from LeadBBS_Announce where ndatetime > "&para1&" order by ndatetime asc) order by ndatetime asc"
        elseif rtype = "user" then
            sql="select ID, UserName, ApplyTime from LeadBBS_User where ID in ( select top "&para2&" ID from LeadBBS_User where ApplyTime > "&para1&" order by ApplyTime asc) order by ApplyTime asc"
        else
            Response.End
        end if
        rs.Open  sql, conn

        Response.Write("[")
        idx0 = 0
        do until rs.EOF
          if idx0 > 0 then
                Response.Write(",")
          end if
          idx0 = idx0 + 1
          Response.Write("{")
          idx1 = 0
          for each x in rs.Fields
            if idx1 > 0 then
                Response.Write(",")
            end if
            idx1 = idx1 + 1
            Response.Write(""""&x.name&"""")
            Response.Write(" : ")
            if VarType(x.value) = 8 then
                Response.Write(""""&Base64Encode(x.value)&"""")
            else
                Response.Write(""""&x.value&"""")
            end if
          next
          Response.Write("}")
          rs.MoveNext
        loop
        Response.Write("]")

        rs.close
        conn.close
    %>
