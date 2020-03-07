$(function () {
    initData();
    const registerUrl = "/superadmin/shop/registershop";
    const editUrl = "/superadmin/shop/modifyshop";
    var shopId = getQueryValue("id");
    //判断queryString中是否有id参数，有的话就说明是编辑shop
    if (shopId != null) {
        //获取要修改的shop数据
        getShopInfo(shopId);
    }

    $("#btn-register").click(function () {

        var shopInfo = {};
        shopInfo.shopId = shopId;
        shopInfo.shopName = $("#shopName").val();
        shopInfo.shopAddr = $("#shopAddr").val();
        shopInfo.area = {
            areaId: $("#area").find("option").not(function () {
                return !this.selected;
            }).data("id")
        }
        shopInfo.shopCategory = {
            shopCategoryId: $("#category").find("option").not(function () {
                return !this.selected;
            }).data("id")
        }
        shopInfo.phone = $("#phone").val();
        shopInfo.shopDesc = $("#shopDesc").val();
        var shopImg = $("#shopImg")[0].files[0];
        var formData = new FormData();
        formData.append("shopInfo", JSON.stringify(shopInfo));
        formData.append("verifyCodeActual", $("#verifyCodeActual").val())
        formData.append("shopImg", shopImg);
        $.ajax({
            url: shopId == null ? registerUrl : editUrl,
            type: 'post',
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功！")
                } else {
                    $.toast(data.errorMsg);
                }
            }, fail: function (error) {
                console.log(error)
            },complete:function () {
                $("#verifyImg").click();
            }
        })
    })
})

function getShopInfo(id) {
    $.getJSON('/superadmin/shop/getshop?id=' + id, function (data) {
        if (data.success) {
            console.log(data.data.data);
            $("#shopName").val(data.data.shopName);
            $("#shopAddr").val(data.data.shopAddr);
            $("#phone").val(data.data.phone);
            $("#shopDesc").val(data.data.shopDesc);
            $("#area option[data-id='"+data.data.areaId +"']").attr("selected","selected");
            $("#category option[data-id='"+data.data.shopCategoryId +"']").attr("selected","selected");
            $("#category").attr("disabled","disabled");
        } else {
            $.toast(data.errorMsg);
        }
    })
}

function initData() {
    const initDataUrl = "/superadmin/shop/operationshopinitdata";
    $.getJSON(initDataUrl, function (data) {
        if (data.success) {
            var categoryHtml = "";
            var areaHtml = "";
            data.shopCategoryList.map(function (item, index) {
                categoryHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
            });
            data.areaList.map(function (item, index) {
                areaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
            })
            $("#category").html(categoryHtml);
            $("#area").html(areaHtml);
        } else {
            alert(data.errorMsg);
        }
    });
}

function changeVerifyCode(that) {
    $(that).attr("src", "/Kaptcha?time=" + new Date().getTime())
}

function getQueryValue(queryName) {
    var reg = new RegExp("(^|&)" + queryName + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    } else {
        return null;
    }
}