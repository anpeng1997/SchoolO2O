import React from 'react';
import { List, Toast } from 'antd-mobile';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as shopActions from "../store/actionCreators";

const Item = List.Item;


class ShopList extends React.Component {

    async componentDidMount() {
        Toast.loading("正在加载....", 0);
        //在mapDispatchToProps中使用了bindActionCreators,所以是调用props.actions
        await this.props.actions.getShopListAction();
        Toast.hide();
    }


    shopManage(shopId) {
        this.props.history.push({
            pathname: "/shop/shopManage/" + shopId
        })
    }

    getShopStatus = code => {
        if (code === -1) {
            return "审核失败";
        } else if (code === 0) {
            return "审核中"
        } else {
            return "审核通过"
        }
    }

    render() {
        const { shopData } = this.props;
        console.log(shopData);
        return (<React.Fragment>
            <List renderHeader={() => !shopData.currentUser ? "" :
                '欢迎您：' + shopData.currentUser.name} className="my-list" >
                {
                    !shopData.currentUser ? "" : shopData.data.shopList.map((item, index) => {
                        return <Item key={index}
                            extra={this.getShopStatus(item.enableStatus)}
                            arrow="horizontal"
                            onClick={this.shopManage.bind(this, item.shopId)}>
                            {item.shopName}
                        </Item>
                    })
                }
            </List>
        </React.Fragment>)
    }

}

const mapStateToProps = (state) => {
    return {
        shopData: state.shop
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