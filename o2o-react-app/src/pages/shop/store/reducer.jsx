import { GETSHOPLIST } from "./actionType";

const defaultState = {
    shopData: {}
}

export default (state = defaultState, action) => {
    switch (action.type) {
        case GETSHOPLIST:
            return state.shopData = action.data;
        default:
            return state;
    }
}