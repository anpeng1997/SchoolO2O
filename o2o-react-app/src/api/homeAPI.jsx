import { axiosRequest } from "./myAxios";

export const reqHeadLine = (size) => axiosRequest(`/api/headline/${size}`);

export const reqShopCategory = () => axiosRequest("/api/shopcategory");