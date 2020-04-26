import { reqShopList, reqProductCategoryList, reqShopOperationInitData, reqProductList } from "../../../api/shopAPI";
import { GET_SHOP_LIST, GET_PRODUCT_CATEGORY_LIST, GET_SHOP_OPERATION_INIT_DATE, GET_PRODUCT_LIST } from "./actionType";
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

const CreateProductListAction = (data) => {
    return {
        type: GET_PRODUCT_LIST,
        data
    }
}

//在action中之所以能做复杂的逻辑，是因为我们配置了redux-thunk中间键
export const getShopListAction = () => {
    return async (dispatch) => {
        const response = await reqShopList();
        dispatch(createShopListAction(response.data.shopList));
    }
}

export const getShopOperationInitDataAction = () => {
    return async (dispatch) => {
        let shopCategoryArray = [];
        let areaArray = [];
        const initData = await reqShopOperationInitData();
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
            Toast.fail("列表数据加载失败," + initData.errorMsg, 2);
        }
        dispatch(createShopOperationInitDataAction(shopCategoryArray, areaArray));
    }
}

export const getProductCategoryListAction = (shopId) => {
    return async (dispatch) => {
        const response = await reqProductCategoryList(shopId);
        if (response.success) {
            dispatch(createProductCategoryListAction(response.data));
        } else {
            Toast.fail(response.errorMsg);
        }
    }
}

export const getProductListAction = (shopId, pageIndex = null, pageSize = null) => {
    return async (dispatch) => {
        const response = await reqProductList(shopId, pageIndex, pageSize)
        if (response.success) {
            dispatch(CreateProductListAction(response.data.productList))
        }else{
            console.warn("reqProductList is false",response)
        }
    }
}