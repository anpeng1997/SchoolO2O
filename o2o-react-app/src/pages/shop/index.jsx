import React from "react";
import { NavBar, Icon } from "antd-mobile"
import { Route, Switch } from "react-router-dom";

import ShopManage from "./components/shop-manage"
import ShopList from "./components/shop-list"
import ShopForm from "./components/shop-form"

class ShopIndex extends React.Component {

    back = () => {
        this.props.history.goBack();
    }

    render() {
        return <React.Fragment>
            <NavBar
                mode="light"
                icon={<Icon type="left" />}
                onLeftClick={this.back}
            >我的店铺</NavBar>
            <Switch>
                <Route path="/shop/shoplist" exact component={ShopList}></Route>
                <Route path="/shop/shopmanage/:id?" exact component={ShopManage}></Route>
                <Route path="/shop/shopoperation/:id?" exact component={ShopForm}></Route>
                <Route component={ShopList}></Route>
            </Switch>
        </React.Fragment>
    }
}
export default ShopIndex;
