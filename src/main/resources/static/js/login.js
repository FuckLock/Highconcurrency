function login() {
    $("#loginForm").validate({
        submitHandler: function(){
            doLogin();
        }
    });
}

function doLogin(){
    g_showLoading();
    var password = $("#password").val();

    if (password.length != 0){
        var salt = g_passsword_salt;
        var str = "" + salt.charAt(0) + salt.charAt(2) + password + salt.charAt(5) + salt.charAt(4);
        password = md5(str);
    }

    var moblie = $("#mobile").val();

    $.ajax({
        url: "/login/do_login",
        type: "POST",
        data:{
            mobile: moblie,
            password: password
        },
        success:function(data){
            layer.closeAll();
            if(data.code == 0){
                layer.msg("成功");
                window.location.href="/goods/to_list";
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.closeAll();
        }
    });
}