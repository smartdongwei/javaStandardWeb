
//用于 iframe 的点击 js 当点击左侧按钮时，获取点击的id 切换到对应的页面
$(document).ready(function() {
    $("#homePage").on("click",function(){//
        $("#rightJpanel").attr("src","home.html");
    });
    $("#dataAssets").on("click",function(){//
        $("#rightJpanel").attr("src","tableDemo.html");
    });
    $("#datastreammonitor").on("click",function(){//
        $("#rightJpanel").attr("src","datagov/datastreammonitor.html");
    });
    $("#datasharemonitor").on("click",function(){//
        $("#rightJpanel").attr("src","datagov/datasharemonitor.html");
    });
    $("#memorycycle").on("click",function(){//
        $("#rightJpanel").attr("src","datagov/memorycycle.html");
    });
    $(".left_li_01").click(
        function(event){
            $(".left_li_01").removeClass("active");
            $(this).addClass("active");
        }
    );
});