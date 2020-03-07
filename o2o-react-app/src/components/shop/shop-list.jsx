import React from 'react';
import { List, Toast } from 'antd-mobile';
import { reqShopList } from "../../api";

const Item = List.Item;


class ShopList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            shopData: null
        }
    }

    async componentDidMount() {
        const that = this;
        Toast.loading("正在加载....", 0);
        const data = await reqShopList();
        Toast.hide();
        if (data.success) {
            that.setState({
                shopData: data
            })
        } else {
            Toast.fail("请求数据错误，请打开控制台查看详细数据", 3)
        }
    }


    shopManage(shopId) {
        this.props.history.push({
            pathname: "/shop/shopManage/" + shopId
        })
    }

    getShopStatus(code) {
        if (code === -1) {
            return "审核失败";
        } else if (code === 0) {
            return "审核中"
        } else {
            return "审核通过"
        }
    }

    render() {
        return (<React.Fragment>
            <List renderHeader={() => this.state.shopData == null ? "" :
                '欢迎您：' + this.state.shopData.currentUser.name} className="my-list">
                {
                    this.state.shopData == null ? "" : this.state.shopData.data.shopList.map((item, index) => {
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

export default ShopList;