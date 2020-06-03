import {axiosRequest} from "./myAxios";


export const reqHomeInitData = () => axiosRequest("/api/frontdesk/home/initdata");

export const reqShopCategoryList = (parentId) => axiosRequest(`/api/frontdesk/shop/shopcategorys/${parentId}`);

export const reqShopList = (parentId, searchKey = null, shopCategoryIds = null, areaId = null, pageSize = null, pageIndex = null) => {
    let data = {};
    const url = `/api/frontdesk/shop/${parentId}`;
    if (searchKey) {
        data["searchKey"] = searchKey;
    }
    if (shopCategoryIds) {
        data["shopCategoryIds"] = shopCategoryIds;
    }
    if (areaId) {
        data["areaId"] = areaId;
    }
    if (pageSize) {
        data["pageSize"] = pageSize;
    }
    if (pageIndex) {
        data["pageIndex"] = pageIndex;
    }
    return axiosRequest(url, data);
}

export const reqShopDetail = (shopId) => axiosRequest(`/api/frontdesk/shop/shopdetail/${shopId}`);

export const reqProductList = (shopId, searchKey = null, productCategoryIds = null, pageSize = null, pageIndex = null) => {
    let data = {};
    const url = `/api/frontdesk/product/${shopId}`;
    if (searchKey) {
        data["searchKey"] = searchKey;
    }
    if (productCategoryIds) {
        data["productCategoryIds"] = productCategoryIds;
    }
    if (pageSize) {
        data["pageSize"] = pageSize;
    }
    if (pageIndex) {
        data["pageIndex"] = pageIndex;
    }
    return axiosRequest(url, data);
}
