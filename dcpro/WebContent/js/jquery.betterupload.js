/**
 * 用于图片上传
 */
/*得到待上传的图片*/
	 function previewImage(fileObj,divPreviewId){
        var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
        var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();
        var browserVersion= window.navigator.userAgent.toUpperCase();
        if(allowExtention.indexOf(extention)>-1){ 
            if(fileObj.files){//兼容chrome、火狐7+、360浏览器5.5+等，应该也兼容ie10，HTML5实现预览
                if(window.FileReader){
                    var reader = new FileReader(); 
                    reader.onload = function(e){
                    $("#showlogo").css("display:none");
                    $("#"+divPreviewId).attr("src",e.target.result);
                    }  
                    reader.readAsDataURL(fileObj.files[0]);
                }else if(browserVersion.indexOf("SAFARI")>-1){
                    alert("不支持Safari浏览器6.0以下版本的图片预览!");
                }
            }else if (browserVersion.indexOf("MSIE")>-1){//ie、360低版本预览
                if(browserVersion.indexOf("MSIE 6")>-1){//ie6
                 	$("#"+divPreviewId).attr("src",fileObj.value);
                   // document.getElementById(divPreviewId).innerHTML="<img src='"+fileObj.value+"'>";
                }else{//ie[7-9]
                    fileObj.select();
                    if(browserVersion.indexOf("MSIE 9")>-1)
                        fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问
                    var newPreview =document.getElementById(divPreviewId+"New");
                    if(newPreview==null){
                        newPreview =document.createElement("div");
                        newPreview.setAttribute("id",divPreviewId+"New");
                        newPreview.style.width = "150px";
                        newPreview.style.height = "150px";
                        newPreview.style.border="solid 1px #d2e2e2";
                    }
                    alert(1);
                    newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                            
                    var tempDivPreview=document.getElementById(divPreviewId);
                    tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);
                    tempDivPreview.style.display="none";                    
                }
            }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox
                var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
                if(firefoxVersion<7){//firefox7以下版本
                    document.getElementById(divPreviewId).innerHTML="<img src='"+fileObj.files[0].getAsDataURL()+"'>";
                }else{//firefox7.0+ 
                    document.getElementById(divPreviewId).innerHTML="<img src='"+window.URL.createObjectURL(fileObj.files[0])+"'>";
                }
            }else{
            	document.getElementById(divPreviewId).innerHTML="<img src='"+fileObj.value+"'>";
                jcrop_api.setImage(fileObj.value);
            }         
        }else{
            alert("仅支持"+allowExtention+"为后缀名的文件!");
            fileObj.value="";//清空选中文件
            if(browserVersion.indexOf("MSIE")>-1){                        
                fileObj.select();
                document.selection.clear();
            }                
            fileObj.outerHTML=fileObj.outerHTML;
        }
        document.getElementById(divPreviewId).style.border="";
       // document.getElementById(divPreviewId+"Up").style.display="block";
       // document.getElementById(divPreviewId+"De").style.display="block";
        showFileInfo(fileObj.files[0],divPreviewId);
    }
    //获取图片的大小、名称予以显示，这里还可以显示图片的文件类型
    function showFileInfo(file,divPreviewId) {
        var fileName = file.name;
        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
        if (file) {
            var fileSize = 0;
            if (file.size > 1024 * 1024){
                fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
            }else{
                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
            }
           document.getElementById("fileName").innerHTML = file.name;
            //document.getElementById(divPreviewId+"Size").innerHTML = '图片大小: ' + fileSize;
            //document.getElementById(divPreviewId+"Name").style.display="block";
           // document.getElementById(divPreviewId+"Size").style.display="block";
        }
       // uploadFile(fileId);
    }
    /*
     function uploadFile(fileId) {
        document.getElementById("show"+fileId+"Up").style.display="none";
        document.getElementById("show"+fileId+"Me").style.display="block";
        var fd = new FormData();
        fd.append("file", document.getElementById("local"+fileId).files[0]);
        var xhr = new XMLHttpRequest();
        //上传中设置上传的百分比
        xhr.upload.addEventListener("progress", function(evt){
            if (evt.lengthComputable) {
                var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                document.getElementById("show"+fileId+"Me").innerHTML = '上传中'+percentComplete+"%";
            }else {
                document.getElementById("show"+fileId+"Me").innerHTML = '无法计算';
            }
        }, false);
        //请求完成后执行的操作
        xhr.addEventListener("load", function(evt){
            var message = evt.target.responseText,
                obj = eval("("+message+")");
            if(obj.status == 1){
                $("#"+fileId).val(obj.picName);
                document.getElementById("show"+fileId+"Me").innerHTML = "已上传";
                  alert("上传成功!");
            }else{
                alert(obj.message);
            }
            
        }, false);
        //请求error
        xhr.addEventListener("error", uploadFailed, false);
        //请求中断
        xhr.addEventListener("abort", uploadCanceled, false);
        //发送请求
        xhr.open("POST", "${ctx}/manage/file/File/uploadPic.htm");
        xhr.send(fd);
    }
 
    function uploadFailed(evt) {
        alert("上传出错.");
    }
 
    function uploadCanceled(evt) {
        alert("上传已由用户或浏览器取消删除连接.");
    }
    */
    
    /**
     * 上传表格
     */
    function previewEXCEL(fileObj,divPreviewId){
        var allowExtention=".xls";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
        var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();            
        var browserVersion= window.navigator.userAgent.toUpperCase();
        if(allowExtention.indexOf(extention)>-1){ 
        	$("#fileName").empty().append(fileObj.files[0].name);
        	$("#excelIcon").removeClass("hidden");
        	$("#uploadFile").attr("disabled",false);
        	
        }else{
            alert("仅支持"+allowExtention+"为后缀名的文件!");
            fileObj.value="";//清空选中文件
            if(browserVersion.indexOf("MSIE")>-1){                        
                fileObj.select();
                document.selection.clear();
            }                
            fileObj.outerHTML=fileObj.outerHTML;
        }
    }