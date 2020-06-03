import {axiosRequest, axiosRequestAll} from './myAxios';
import {CT_FORMDATA} from "./httpHeaders";

export const reqShopList = () => axiosRequest("/api/shop/paginationshop");

//SHOP FORM

export const reqShopInfo = (shopId) => axiosRequest("/api/shop/" + shopId);

export const reqShopOperationInitData = () => axiosRequest("/api/shop/operationinitdata");

/**
 *
 * @param {String} type 请求是 register or modify
 * @param {Object} formData
 * @param {Array} headers
 */
export const reqRegisterOrModifyShop = (type, formData) => {
    let url = "/api/shop/register";
    if (type === 'modify') {
        url = "/api/shop/modify";
    }
    return axiosRequest(url, formData, 'POST', CT_FORMDATA);
}

/**
 * 并发请求对象(返回的数组数据和传入的数组参数请求一一对应)
 * @param {Array} arr 请求的pormise对象数组
 */
export const reqAll = (arr) => axiosRequestAll(arr);


export const reqProductCategoryList = (shopId) => axiosRequest("/api/productcategory/list/" + shopId);

export const reqProductCategory = (categoryId) => axiosRequest("/api/productcategory/" + categoryId);

export const reqDelectProductCategory = (categoryId) => axiosRequest("/api/productcategory/" + categoryId, {}, "DELETE");

export const reqAddProductCategory = (data) => axiosRequest("/api/productcategory/add", data, "POST");

export const reqModifyProductCategory = (data) => axiosRequest("/api/productcategory/edit", data, "PUT");

export const reqAddOrModifyProduct = (type, formData) => {
    let url = "/api/product";
    if (type === "modify") {
        url = "/api/product/edit";
    }
    return axiosRequest(url, formData, "POST", CT_FORMDATA);
};

export const reqProductList = (shopId, pageIndex, pageSize) => {
    let url = `/api/product/paginationproduct?shopid=${shopId}`
    if (pageIndex) {
        url = +`&pageindex${pageIndex}`
    }
    if (pageSize) {
        url = +`&pagesize${pageSize}`
    }
    return axiosRequest(url);
}

export const reqProduct = (productId) => axiosRequest(`/api/product/${productId}`);

export const reqChangeStatus = (productId) => axiosRequest(`/api/product/status/${productId}`, {}, "PUT")

export const reqDeleteProduct = (productId) => axiosRequest(`/api/product/${productId}`, {}, "DELETE")

export const reqAddOrModifyShopCategory = (type, data) => {
    let url = "/api/shopcategory";
    if (type === "modify") {
        //TODO: 修改api
        url = ""
    }
    return axiosRequest(url, data, "POST");
};