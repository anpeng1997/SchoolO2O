import { reqShopList } from "../../../api";
import { GETSHOPLIST } from "./actionType";


const createShopListAction = (data) => {
    return {
        type: GETSHOPLIST,
        data
    }
}

//在这个action中之所以能做复杂的逻辑，是因为我们配置了redux-thunk中间键
export const getShopListAction = () => {
    return async (dispatch) => {
        const shopList = await reqShopList();
        dispatch(createShopListAction(shopList));
    }
}