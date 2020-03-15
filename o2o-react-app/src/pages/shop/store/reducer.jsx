import { GET_SHOP_LIST, GET_PRODUCT_CATEGORY_LIST, GET_SHOP_OPERATION_INIT_DATE } from "./actionType";
import { fromJS } from "immutable";

const defaultState = fromJS({
    shopList: [],
    shopOperationInitData: {
        shopCategoryList: [],
        areaList: []
    },
    productCategoryList: []
})

export default (state = defaultState, action) => {

    switch (action.type) {
        case GET_SHOP_LIST:
            //set state时，也需要使用fromJS包装传递过来的数据
            return state.set("shopList", fromJS(action.data));
        case GET_PRODUCT_CATEGORY_LIST:
            return state.set("productCategoryList", fromJS(action.data));
        case GET_SHOP_OPERATION_INIT_DATE:
            return state.setIn(["shopOperationInitData", "shopCategoryList"], fromJS(action.shopCategoryList))
                .setIn(["shopOperationInitData", "areaList"], fromJS(action.areaList));
        default:
            return state;
    }
}