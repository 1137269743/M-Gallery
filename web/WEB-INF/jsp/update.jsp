<%@page contentType="text/html; charset=utf-8"%>
<!-- 新增油画页面 -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增油画</title>
    <link rel="stylesheet" type="text/css" href="css/create.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/validation.js"></script>
    <script type="text/javascript">
        function checkSubmit() {
            let r1 = checkEmpty("#pname", '#errPname');
            let r2 = checkCategory("#category", '#errCategory');
            let r3 = checkPrice("#price", '#errPrice');
            let r4 = true;
            if ($("#isPreviewModified").val() == "1") {
                r4 = checkFile("#painting", '#errPainting');
            }
            let r5 = checkEmpty("#description", '#errDescription');
            if (r1 && r2 && r3 && r4 && r5) {
                return true;
            } else {
                return false;
            }
        }

        $(function () {
            $("#category").val(${painting.category});
        })

        function selectPreview() {
            checkFile('#painting', '#errPainting');
            $("#preview").hide();
            $("#isPreviewModified").val(1);
        }
    </script>
</head>
<body>
    <div class="container">
        <fieldset>
            <legend>新增油画</legend>
            <form action="management?method=update" method="post"
                autocomplete="off" enctype="multipart/form-data" onsubmit="return checkSubmit()">
                <ul class="ulform">
                    <li>
                        <span>油画名称</span>
                        <span id="errPname"></span>
                        <input id="pname" name="pname" value="${painting.pname}" onblur="checkEmpty('#pname', '#errPname')">
                    </li>
                    <li>
                        <span>油画类型</span>
                        <span id="errCategory"></span>
                        <select id="category" name="category" onchange="checkCategory('#category', '#errCategory')">
                            <option value="-1">请选择油画类型</option>
                            <option value="1">抽象主义</option>
                            <option value="2">现实主义</option>
                        </select>
                    </li>
                    <li>
                        <span>油画价格</span>
                        <span id="errPrice"></span>
                        <input id="price" name="price" value="${painting.price}" onblur="checkPrice('#price', '#errPrice')">
                    </li>
                    <li>
                        <span>作品预览</span>
                        <input type="hidden" id="isPreviewModified" name="isPreviewModified" value="0">
                        <span id="errPainting"></span>
                        <img id="preview" src="${painting.preview}" style="width: 361px;height: 240px;">
                        <input id="painting" name="painting" type="file" style="padding-left: 0;" accept="image/*" onchange="selectPreview()" />
                    </li>
                    <li>
                        <span>详细描述</span>
                        <span id="errDescription"></span>
                        <textarea id="description" name="description" onblur="checkEmpty('#description', '#errDescription')">${painting.description}</textarea>
                    </li>
                    <li style="text-align: center">
                        <input type="hidden" id="id" name="id" value="${painting.id}">
                        <button type="submit" class="btn-button">提交表单</button>
                    </li>
                </ul>
            </form>
        </fieldset>
    </div>
</body>
</html>