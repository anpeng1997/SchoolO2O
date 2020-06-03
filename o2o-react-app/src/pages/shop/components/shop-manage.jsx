import React from "react";
import {Button, WhiteSpace, WingBlank} from "antd-mobile";
import {Redirect} from "react-router-dom";

export default class ShopManage extends React.Component {

    render() {
        const {history, match} = this.props;
        const shopId = match.params.id;
        if (!shopId) {
            return <Redirect to="/shop/shoplist"/>;
        }
        return <React.Fragment>
            <WhiteSpace size="xl"/>
            <WingBlank size="lg">
                <Button type="primary" size="large" onClick={() => {
                    history.push(`/shop/shopoperation/${shopId}`)
                }}>店铺信息管理
                </Button>
            </WingBlank>
            <WhiteSpace size="xl"/>
            <WingBlank size="lg">
                <Button type="primary" size="large" onClick={() => {
                    history.push(`/shop/productcategory/${shopId}`)
                }}>商品类别管理</Button>
            </WingBlank>
            <WhiteSpace size="xl"/>
            <WingBlank size="lg">
                <Button type="primary" size="large" onClick={
                    () => {
                        history.push(`/shop/product/${shopId}`)
                    }
                }>商品管理</Button>
            </WingBlank>
            <WhiteSpace size="xl"/>
            <WingBlank size="lg">
                <Button type="primary" size="large" onClick={
                    () => {
                        history.push(`/shop/shopcategory`)
                    }
                }>添加商店类别</Button>
            </WingBlank>
        </React.Fragment>
    }
}