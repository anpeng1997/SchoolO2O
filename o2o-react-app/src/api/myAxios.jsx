import axios from "axios";
import { Toast } from 'antd-mobile';
import { createHashHistory } from 'history';


const history = createHashHistory();

//使用create创建出来的本身就是一个promise()
const myAxiosInstance = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 10000,

    /**
     * 【允许携带跨域cookie】
     * 一方面要服务器同意，指定Access-Control-Allow-Credentials字段，另一方面，开发者必须在AJAX请求中打开withCredentials属性
     * 否则，即使服务器同意发送Cookie，浏览器也不会发送。或者，服务器要求设置Cookie，浏览器也不会处理。
     * 需要注意的是，如果要发送Cookie，Access-Control-Allow-Origin就不能设为星号，
     * 必须指定明确的、与请求网页一致的域名。
     * 同时，Cookie依然遵循同源政策，只有用服务器域名设置的Cookie才会上传，其他域名的Cookie并不会上传，
     * 且（跨源）原网页代码中的document.cookie也无法读取服务器域名下的Cookie。
     * 
     * https://www.ruanyifeng.com/blog/2016/04/cors.html
     */
    withCredentials: true
});

myAxiosInstance.interceptors.request.use(function (config) {
    Toast.loading("Loading....", 0);
    return config;
}, function (error) {
    //请求错误
})

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
            //出错了，不调用reject
        })
    })
}