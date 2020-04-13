import React from "react";
import { Button, List, WingBlank, Modal, WhiteSpace } from "antd-mobile";
import { connect } from "react-redux";
import { getProductListAction } from "../store/actionCreators";
import { reqChangeStatus } from "../../../api/shopAPI";
import ThumbImg from "./thumbImg";

const Item = List.Item;
const Brief = Item.Brief;
const alert = Modal.alert;

class ShopProductList extends React.PureComponent {

    constructor(props) {
        super(props);
        const shopId = props.match.params.id;
        this.state = {
            shopId
        }
    }

    componentDidMount() {
        this.getProductList();
    }

    getProductList = () => {
        this.props.getProductsAction(this.state.shopId)
    }

    changeStatus = async (productId) => {
        const response = await reqChangeStatus(productId);
        console.log(response);
    }

    render() {
        const shopId = this.state.shopId;
        const { history, match, productList } = this.props;
        const products = productList.toJS();
        return <React.Fragment>
            <List renderHeader={'商品列表'}>
                {
                    products.map((item, index) => {
                        return <Item key={index} thumb={<ThumbImg src={item.imgAddr} alt={item.productDesc}></ThumbImg>} align="top" multipleLine
                            extra={
                                <div>
                                    <Button type="warning" size="small" onClick={() => { }} >删除</Button>
                                    <WhiteSpace size="md"></WhiteSpace>
                                    <Button type="primary" size="small" onClick={() => history.push(`/shop/productoperation/${shopId}/${item.productId}`)} >编辑</Button>
                                    <WhiteSpace size="md"></WhiteSpace>
                                    <Button type="ghost" size="small" onClick={() => this.changeStatus(item.productId)} >{item.enableStatus === 0 ? "上架" : "下架"}</Button>
                                </div>
                            }
                        >
                            {item.productName}
                            <Brief>{item.enableStatus === 0 ? "未上架" : "已上架"}</Brief>
                            <Brief>促销价格：￥{item.promotionPrice}</Brief>
                            <Brief>原价价格：￥{item.normalPrice}</Brief>
                        </Item>
                    })
                }
            </List>
            <WhiteSpace size="md"></WhiteSpace>
            <WingBlank>
                <Button type="primary" onClick={() => {
                    history.push(`/shop/productoperation/${match.params.id}`);
                }} >添加商品</Button>
            </WingBlank>
        </React.Fragment>
    }
}

const mapStateToProps = (state) => {
    return {
        productList: state.getIn(["shopReducer", "productList"])
    }
}

const mapDispathcToProps = (dispatch) => {
    return {
        getProductsAction(shopId) {
            dispatch(getProductListAction(shopId))
        }
    }
}

export default connect(mapStateToProps, mapDispathcToProps)(ShopProductList);