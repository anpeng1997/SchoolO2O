import React from 'react';
import { List, Button } from 'antd-mobile';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as shopActions from "../store/actionCreators";

const Item = List.Item;


class ShopList extends React.Component {

    componentDidMount() {
        //在mapDispatchToProps中使用了bindActionCreators,所以是调用props.actions
        this.props.actions.getShopListAction();
    }


    shopManage(shopId) {
        this.props.history.push({
            pathname: "/shop/shopManage/" + shopId
        })
    }

    render() {
        const shopList = this.props.shopList.toJS();
        const { history } = this.props;
        return (<React.Fragment>
            <List renderHeader={() => "欢迎你"} className="my-list">
                {
                    shopList.map((item, index) => {
                        return <Item key={index}
                            extra={item.advice}
                            arrow="horizontal"
                            onClick={this.shopManage.bind(this, item.shopId)}>
                            {item.shopName}
                        </Item>
                    })
                }
                <Item>
                    <Button style={{ marginTop: "50px" }} type="primary" size="large" onClick={
                        () => {
                            history.push(`/shop/shopoperation`)
                        }
                    }>注册商店</Button>
                </Item>
            </List>

        </React.Fragment>)
    }

}

const mapStateToProps = (state) => {
    return {
        shopList: state.getIn(["shopReducer", "shopList"])
    }
}

const mapDispatchToProps = (dispatch) => {

    return {
        //原始方式:获取一个action后再dispatch
        // getShopList(){
        //     dispatch(getShopListAction())
        // }
        //使用bindActionCreators绑定所有的actions
        actions: bindActionCreators(shopActions, dispatch)
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ShopList);