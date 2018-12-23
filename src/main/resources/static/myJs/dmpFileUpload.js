//js初始化
$(function(){
    InitExcelFile()
});


function InitExcelFile() {

    $("#dmpFile").fileinput({
        uploadUrl: "/StandardComparision/uploadDmlFile",//上传的地址
        uploadAsync: true,              //异步上传
        language: "zh",                 //设置语言
        showCaption: true,              //是否显示标题
        showUpload: true,               //是否显示上传按钮
        showRemove: true,               //是否显示移除按钮
        // showPreview: true,             //是否显示预览按钮
        browseClass: "btn btn-primary", //按钮样式
        uploadLabel: "导入",           //设置上传按钮的汉字
        enctype: 'multipart/form-data',
        dropZoneEnabled: true,         //是否显示拖拽区域
        allowedFileExtensions: ["dmp"], //接收的文件后缀
        maxFileCount: 10,                        //最大上传文件数限制
        overwritelnitial:false,
        initialPreviewAsData:true,
        maxFilesNum:10,
        // maxFileSize:0,
        previewFileIcon: '<i class="glyphicon glyphicon-open-file"></i>',
        allowedPreviewTypes: null,
        minImageWidth: 20, //图片的最小宽度
        minImageHeight: 20,//图片的最小高度
        maxImageWidth: 50,//图片的最大宽度
        maxImageHeight: 50,//图片的最大高度
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        previewFileIconSettings: {
            'dmp': '<i class="glyphicon glyphicon-open-file"></i>'
        }
        // uploadExtraData: {
        //     service: service
        // }
    }).on("fileuploaded", function (event, data) {
        var response = data.response;
        var html;
        if (response.status == '200') {
            html = "<div  style='float:left;' ><p  style='text-align:left'>" + response.filename + "导入oracle数据库成功&emsp; </p></div>"
        } else {
            html = "<div  style='float:left;'><p style='text-align:left'>" + response.filename + "导入oracle数据库失败&emsp; </p></div>&emsp"
        }
        $(html).appendTo($('#response'));
    }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
        alert('文件上传失败！' + msg);
    }).on("filebatchselected", function(event, files) {
        // debugger;
       // 选择文件成功后，将结果显示页面去除
        var parent = document.getElementById("response");
        var aa = parent.children.length;
        for(i=0;i<aa;i++){
            parent.removeChild(parent.children[0])
        }
    });
}