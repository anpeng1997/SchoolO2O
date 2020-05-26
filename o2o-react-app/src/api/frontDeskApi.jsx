import { axiosRequest } from "./myAxios";


export const reqHomeInitData = () => axiosRequest("/api/frontdesk/home/initdata");

export const reqShopList = (parentId, searchKey = null) => {
    const url = `/api/frontdesk/shop/${parentId}`;
    if (searchKey) {
        return axiosRequest(url, { searchKey });
    }
    return axiosRequest(url);
}