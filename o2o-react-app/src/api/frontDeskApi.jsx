import { axiosRequest } from "./myAxios";


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