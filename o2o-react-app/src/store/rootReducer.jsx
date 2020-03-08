import { combineReducers } from "redux";
import shopReducer from "../pages/shop/store/reducer";

//将所有的reducer整合在一起
const rootReducer = combineReducers({
    shop: shopReducer
});

export default rootReducer;