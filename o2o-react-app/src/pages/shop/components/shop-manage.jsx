import React from "react";
import { Button, WhiteSpace, WingBlank } from "antd-mobile";
import { Redirect } from "react-router-dom";

export default class ShopManage extends React.Component {

    componentDidMount() {
        
    }

    render() {
        const { history, match } = this.props;
        if(!match.params.id){
            return  <Redirect to="/shop/shoplist" />;
        }
        return <React.Fragment>
            <WhiteSpace size="xl" />
            <WingBlank size="lg">
                <Button type="primary" size="large" onClick={() => {
                    history.push("/shop/shopoperation/" + match.params.id)
                }} >店铺信息管理
                </Button>
            </WingBlank>
            <WhiteSpace size="xl" />
            <WingBlank size="lg">
                <Button type="primary" size="large" >店铺管理</Button>
            </WingBlank>
            <WhiteSpace size="xl" />
            <WingBlank size="lg">
                <Button type="primary" size="large" >商品类别管理</Button>
            </WingBlank>
        </React.Fragment>
    }
}