//js初始化
$(function(){
    //初始化 存储周期 Table
    var oTableOne = new InitTableData();
    oTableOne.Init();
});

// 在bootstrap中显示数据  用前端分页
var InitTableData = function () {
    var oTableInit = new Object();
    oTableInit.Init = function (){
        $('#tableDataSource').bootstrapTable({
            method:'get',                                         //请求方式（*）
            url:"/StandardComparision/findAllDataSource",    //请求后台的URL（*）
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            // queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5,10],        //可供选择的每页的行数（*）
            clickToSelect: true,                //是否启用点击选中行
            striped:true,
            uniqueId: "dataId",                     //每一行的唯一标识，一般为主键列
            // sortable: true,                     //是否启用排序
            columns:[{checkbox: true, visible: true},                  //是否显示复选框
                {field: 'dataId', title: '数据库ID', align: 'center' ,visible : false},
                {field: 'dataName', title: '数据库类型', align: 'center'},
                {field: 'userName', title: '用户名', align: 'center'},
                {field: 'passWord', title: '密码', align: 'center'},
                {field: 'jdbcUrl', title: '数据库URL', align: 'center'},
                {field: 'fromUser', title: '来源User', align: 'center'},
                {field: 'toUser', title: '导入User' , align: 'center'},
                {
                    formatter: function (value, row, index) {
                        return [
                            "<a href='javascript:modifyData("+'"'+ row.dataId +'"'+")'>" +
                            '<i class="glyphicon glyphicon-pencil"></i>编辑' +
                            '</a>'
                        ].join('');
                    },
                    title: '操作'
                }]
        });
    };

    return oTableInit;
};

// 点击新增按钮后，弹出新增数据的模太框
function addNewDataSource() {
    document.getElementById("DataSourceForm").reset();
    $("#editModal").modal("show");
}

//按取消建后清除输入框里面的内容
function resetAddModal() {
    document.getElementById("DataSourceForm").reset();
    document.getElementById("waitImage").style.display="none";
    document.getElementById("failureImage").style.display="none";
    document.getElementById("successImage").style.display="none";
    $("#editModal").modal("hide");
    $("#tableDataSource").bootstrapTable("refresh");
}

//点击确认后的的功能
function editData() {
    debugger;
    var paramList = $("#DataSourceForm").serializeArray();
    var dataId = paramList[0].value;
    var url = null;
    var flag = null;
    if(dataId.trim() == ""){
        flag="增加数据库连接信息";
        url="/StandardComparision/addDataSource"
    }else{
        flag = "编辑数据库连接信息";
        url="/StandardComparision/editDataSource"
    }
    $.ajax({
        url: url,
        data:paramList,
        dataType: "json",
        traditional: true,//属性在这里设置
        method: "post",
        success: function (data) {
            if(data.state=="success") {
                bootbox.alert({
                    size: "small",
                    message: flag+"成功",
                    callback: function () {
                    }
                });
                resetAddModal();
            }else{
                bootbox.alert({
                        size: "small",
                        message: flag+"失败",
                        callback: function(){ /* your callback code */ }
                    }
                );
            }
        }
    });
}

//点击编辑按钮后
function editDataSource(){
   debugger;
    var rows = $("#tableDataSource").bootstrapTable("getSelections");
    var len = rows.length;
    if(len == 1){
        var row = rows[0];
        document.getElementById('dataId').value=row['dataId'];
        document.getElementById('dataName').value=row['dataName'];
        document.getElementById('userName').value=row['userName'];
        document.getElementById('passWord').value=row['passWord'];
        document.getElementById('jdbcUrl').value=row['jdbcUrl'];
        document.getElementById('fromUser').value=row['fromUser'];
        document.getElementById('toUser').value=row['toUser'];
        $("#editModal").modal("show");
    }else{
        bootbox.alert({
            size: "small",
            message: "请选择一行数据",
            callback: function(){ /* your callback code */ }
        })
    }
}

//表格里面的编辑按钮
function modifyData(dataId){
    var row = $("#tableDataSource").bootstrapTable('getRowByUniqueId', dataId);
    document.getElementById('dataId').value=row['dataId'];
    document.getElementById('dataName').value=row['dataName'];
    document.getElementById('userName').value=row['userName'];
    document.getElementById('passWord').value=row['passWord'];
    document.getElementById('jdbcUrl').value=row['jdbcUrl'];
    document.getElementById('fromUser').value=row['fromUser'];
    document.getElementById('toUser').value=row['toUser'];
    $("#editModal").modal("show");
}

//删除功能
function delDataSource() {
    debugger;
    var rows = $("#tableDataSource").bootstrapTable("getSelections");
    var len = rows.length;
    var delDataId = [];
    for (var i = 0; i < len; i++) {
        delDataId.push(rows[i].dataId);
    }
    var messageLen = "已选择 "+len +" 行数据，请确认是否删除？";
    if (len == 0) {
        bootbox.alert({
            size: "small",
            message: "未指定待操作对象",
            callback: function(){ /* your callback code */ }
        })
    } else {
        bootbox.confirm({
            size: "small",
            backdrop:true,
            message: messageLen,
            callback: function(result){
                if(result){
                    $.ajax({
                        url: "/StandardComparision/deleteDataSource",
                        dataType: "json",
                        traditional: true,//属性在这里设置
                        method: "post",
                        data: {
                            "delDataId": delDataId
                        },
                        success: function (data) {
                            if (data.state == "success") {
                                bootbox.alert({
                                    size: "small",
                                    message: "删除成功",
                                    callback: function () { /* your callback code */
                                    }
                                });
                                resetAddModal();
                            }else{
                                bootbox.alert({
                                    size: "small",
                                    message: "删除失败",
                                    callback: function(){ /* your callback code */ }
                                });
                                resetAddModal();
                            }
                        }
                    });
                }
            }
        })
    }
}

//测试数据源是否能连接的按钮 新增和修改页面的测试功能
function testConn(){
    document.getElementById("waitImage").style.display="block";
    var paramList = $("#DataSourceForm").serializeArray();
    $.ajax({
        url: "/StandardComparision/testConnAddEdit",
        data:paramList,
        dataType: "json",
        traditional: true,//属性在这里设置
        method: "post",
        success: function (data) {
            if(data.state=="success") {
                //修改 连接数据源成功的结果
                document.getElementById("waitImage").style.display="none";
                document.getElementById("failureImage").style.display="none";
                document.getElementById("successImage").style.display="block";
            }else{
                document.getElementById("successImage").style.display="none";
                document.getElementById("waitImage").style.display="none";
                document.getElementById("failureImage").style.display="block";
            }
        }
    })
}