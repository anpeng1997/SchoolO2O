import axios from "axios";
import { Toast } from 'antd-mobile';
import { createHashHistory } from 'history'; 


const history = createHashHistory();

//使用create创建出来的本身就是一个promise()
const myAxiosInstance = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 10000,
    withCredentials: true //允许携带跨域cookie
});

myAxiosInstance.interceptors.response.use((response) => {
    Toast.hide();
    return response;
 }, (error) => {
    Toast.hide();
    if (error.response.status === 401) {
        console.log(error.response.status)
        history.push("/login");
    }
    return Promise.reject(error);
})

export function axiosRequest(url, data = {}, method = 'GET', headers = {}) {

    return new Promise((resovle, reject) => {
        let promise;
        Toast.loading("Loading....", 0);
        switch (method) {
            case "GET":
                promise = myAxiosInstance.get(url, {
                    params: data,
                    headers: headers
                });
                break;
            case "DELETE":
                promise = myAxiosInstance.delete(url, data, {
                    headers: headers
                });
                break;
            case "PUT":
                promise = myAxiosInstance.put(url, data, {
                    headers: headers
                });
                break;
            default:
                promise = myAxiosInstance.post(url, data, {
                    headers: headers
                });
        }
       
        //成功了调用 resolve
        promise.then(response => {
            console.log(response);
            resovle(response.data);
        }).catch((error) => {
            //出错了，不调用reject
        });
    })
}

export function axiosRequestAll(array) {
    return new Promise((resovle, reject) => {
        axios.all(array).then(data => {
            resovle(data);
        }).catch(error => {
            Toast.hide()
            Toast.fail("请求出错，" + error.message)
        })
    })
}