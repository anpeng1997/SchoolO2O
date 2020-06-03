import {reqShopList, reqProductCategoryList, reqShopOperationInitData, reqProductList} from "../../../api/shopAPI";
import {GET_SHOP_LIST, GET_PRODUCT_CATEGORY_LIST, GET_SHOP_OPERATION_INIT_DATE, GET_PRODUCT_LIST} from "./actionType";
import {Toast} from "antd-mobile";


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
        if (!response.success) {
            console.error(response);
            Toast.error("商店列表获取失败", 2);
        }
        dispatch(createShopListAction(response.data.shopList));
    }
}

export const getShopOperationInitDataAction = () => {
    return async (dispatch) => {
        let shopCategoryArray = [];
        let areaArray = [];
        const initData = await reqShopOperationInitData();
        if (initData.success) {
            initData.data.shopCategoryList.map(function (item, index) {
                shopCategoryArray.push({value: item.shopCategoryId, label: item.shopCategoryName});
                return index;
            });
            initData.data.areaList.map((item, index) => {
                areaArray.push({value: item.areaId, label: item.areaName});
                return index;
            });
            dispatch(createShopOperationInitDataAction(shopCategoryArray, areaArray));
        } else {
            console.error(initData);
            Toast.fail("列表数据加载失败", 2);
        }

    }
}

export const getProductCategoryListAction = (shopId) => {
    return async (dispatch) => {
        const response = await reqProductCategoryList(shopId);
        if (response.success) {
            dispatch(createProductCategoryListAction(response.data));
        } else {
            console.error(response);
            Toast.fail("商品类别列表加载失败", 2);
        }
    }
}

export const getProductListAction = (shopId, pageIndex = null, pageSize = null) => {
    return async (dispatch) => {
        const response = await reqProductList(shopId, pageIndex, pageSize)
        if (response.success) {
            dispatch(CreateProductListAction(response.data.productList))
        } else {
            console.error(response)
            Toast.fail("商品列表加载失败", 2);
        }
    }
}