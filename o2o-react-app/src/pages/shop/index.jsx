import React from "react";
import { NavBar, Icon } from "antd-mobile";
import { Route, Switch } from "react-router-dom";

import ShopManage from "./components/shop-manage";
import ShopList from "./components/shop-list";
import ShopForm from "./components/shop-form";
import ProductCategoryList from "./components/shop-product-category-list";
import ProductCategoryForm from "./components/shop-product-category-form";
import ProductList from "./components/shop-product-list";
import ProductForm from "./components/shop-product-form";

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
                <Route path="/shop/productcategory/:id" exact component={ProductCategoryList}></Route>
                <Route path="/shop/productcategoryoperation/:sid/:cid?" exact component={ProductCategoryForm}></Route>
                <Route path="/shop/product/:id" exact component={ProductList}></Route>
                <Route path="/shop/productoperation/:sid/:pid?" exact component={ProductForm}></Route>
                <Route component={ShopList}></Route>
            </Switch>
        </React.Fragment>
    }
}
export default ShopIndex;
