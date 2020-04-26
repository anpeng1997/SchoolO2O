import { axiosRequest } from './myAxios';

export const reqLogin = (data) => axiosRequest("/api/login",data,"POST");