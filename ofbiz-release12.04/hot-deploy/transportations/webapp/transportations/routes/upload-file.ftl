<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <!--<meta http-equiv="Content-Type" content="multipart/form-data; charset=ISO-8859-1">-->

        <title>Insert title here</title>
        <script TYPE="TEXT/JAVASCRIPT" language=""JAVASCRIPT">
            function uploadFile()
            {
                //alert("Before calling upload.jsp");
                window.location='<@ofbizUrl>testing_service1</@ofbizUrl>'

            }

            function logout1()
            {
                //alert("Logout1");
                alert("<@ofbizUrl>logout1</@ofbizUrl>");
                window.location='<@ofbizUrl>logout1</@ofbizUrl>'
            }
        </script>
        </head>
        <!-- <form action="<@ofbizUrl>testing_service1</@ofbizUrl>" enctype="multipart/form-data" name="app_details_frm"> -->
        <body bgcolor="cyan">
            <form enctype="multipart/form-data" action="<@ofbizUrl>uploadAttachFile</@ofbizUrl>" METHOD=POST>
                <center style="height: 299px;">
                    <table border="0" style="height: 177px; width: 788px">
                        <tr style="height: 115px; ">
                            <td style="width: 103px; ">
                            <td style="width: 440px; "><h1>APPLICATION DETAILS</h1>
                            <td style="width: 55px; ">
                        </tr>
                        <tr>
                            <td style="width: 125px; ">Application name : </br></td>
                            <td>
                                <input name="app_name_txt" id="txt_1" value=" " />
                            </td>
                            <td></br></br></td>
                        </tr>
                        <tr>
                            <td style="width: 125px; ">Excell sheet &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>
                            <td>
                                <input type="file" name="filename"/>
                            </td>
                            <td></br></br></td>
                        </tr>
                        <tr>
                            <td>
                            <td></br>
                            <td>
                        </tr>
                        <tr>
                            <td>
                               <input type="button" name="logout1_cmd" value="LOGOUT" onclick="logout1()"/>
                              <!-- <input type="submit" name="logout_cmd" value="logout"/>-->
                            </td>
                            <td>
                                <input type="submit" name="upload_cmd" value="UPLOAD" />
                                <!-- <input type="button" name="upload1_cmd" value="Upload" onclick="uploadFile()"/> -->
                            </td>
                        </tr>
                    </table>
                </center>
            </form>
        </body>
</html>