import { reqShopList, reqProductCategoryList, reqShopOperationInitData } from "../../../api/shopAPI";
import { GET_SHOP_LIST, GET_PRODUCT_CATEGORY_LIST, GET_SHOP_OPERATION_INIT_DATE } from "./actionType";
import { Toast } from "antd-mobile";


const createShopListAction = (data) => {
    return {
        type: GET_SHOP_LIST,
        data
    }
}

const createProductCategoryListAction = (data) => {
    return {
        type: GET_PRODUCT_CATEGORY_LIST,
        data
    }
}

const createShopOperationInitDataAction = (shopCategoryList, areaList) => {
    return {
        type: GET_SHOP_OPERATION_INIT_DATE,
        shopCategoryList,
        areaList
    }
}

//在这个action中之所以能做复杂的逻辑，是因为我们配置了redux-thunk中间键
export const getShopListAction = () => {
    return async (dispatch) => {
        Toast.loading("正在加载....", 0);
        const response = await reqShopList();
        Toast.hide();
        dispatch(createShopListAction(response.data.shopList));
    }
}

export const getShopOperationInitDataAction = () => {
    return async (dispatch) => {
        let shopCategoryArray = [];
        let areaArray = [];
        const initData =await reqShopOperationInitData();
        Toast.hide();
        if (initData.success) {
            initData.shopCategoryList.map(function (item, index) {
                shopCategoryArray.push({ value: item.shopCategoryId, label: item.shopCategoryName });
                return index;
            });
            initData.areaList.map((item, index) => {
                areaArray.push({ value: item.areaId, label: item.areaName });
                return index;
            })
        } else {
            console.error(initData)
            Toast.fail("列表数据加载失败," + initData.errorMsg, 2);
        }
        dispatch(createShopOperationInitDataAction(shopCategoryArray, areaArray));
    }
}

export const getProductCategoryListAction = (shopId) => {
    return async (dispatch) => {
        Toast.loading('正在加载...', 0);
        const response = await reqProductCategoryList(shopId);
        Toast.hide();
        if (response.success) {
            dispatch(createProductCategoryListAction(response.data));
        } else {
            Toast.fail(response.errorMsg);
        }
    }
}