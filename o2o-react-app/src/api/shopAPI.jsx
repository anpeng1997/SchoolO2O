import { axiosRequest, axiosRequestAll } from './myAxios';


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
export const reqRegisterOrModifyShop = (type, formData, headers) => {
    var url = "/api/shop/register";
    if (type === 'modify') {
        url = "/api/shop/modify";
    }
    return axiosRequest(url, formData, 'POST', headers);
}

/**
 * 并发请求对象(返回的数组数据和传入的数组参数请求一一对应)
 * @param {Array} arr 请求的pormise对象数组 
 */
export const reqAll = (arr) => axiosRequestAll(arr);


export const reqProductCategoryList = (shopId) => axiosRequest("/api/productcategory/getlist/" + shopId);

export const reqDelectProductCategory = (categoryId) => axiosRequest("/api/productcategory/delete/" + categoryId,{},"DELETE");