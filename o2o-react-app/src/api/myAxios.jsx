import axios from "axios";
import { Toast } from 'antd-mobile';

//使用create创建出来的本身就是一个promise()
const myAxiosInstance = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 5000,
    withCredentials: true //允许携带跨域cookie
});



export function axiosRequest(url, data = {}, method = 'GET', headers = {}) {

    return new Promise((resovle, reject) => {
        let promise;
        if (method === "GET") {
            promise = myAxiosInstance.get(url, {
                params: data,
                headers: headers
            });
        } else {
            promise = myAxiosInstance.post(url, data, {
                headers: headers
            });
        }
        //成功了调用 resolve
        promise.then(response => {
            resovle(response.data);
        }).catch((error) => {
            //失败了不调用reject,否则还是把错误返回给调用者了
            Toast.fail('请求出错，' + error.message);
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