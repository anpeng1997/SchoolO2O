//import { combineReducers } from "redux"
//这里我们使用的immutable的combineReducers,对reducers进行整合
import {combineReducers} from "redux-immutable";
import shopReducer from "../pages/shop/store/reducer";

//将所有的reducer整合在一起
const rootReducer = combineReducers({
    shopReducer
});

export default rootReducer;