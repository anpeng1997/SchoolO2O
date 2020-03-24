import React from "react";
import { connect } from "react-redux";
import { List, Button, Modal, Toast, WingBlank, WhiteSpace, Flex } from 'antd-mobile';
import { getProductCategoryListAction } from "../store/actionCreators";
import { reqDelectProductCategory } from "../../../api/shopAPI";

const Item = List.Item;
const Brief = Item.Brief;
const alert = Modal.alert;

class ShopProductCategoryList extends React.PureComponent {

    constructor(props) {
        super(props);
        const shopId = props.match.params.id;
        this.state = {
            shopId
        }
    }

    componentDidMount() {
        this.getCategoryList();
    }

    getCategoryList = () => {
        const { getProductCategoryAction } = this.props;
        getProductCategoryAction(this.state.shopId);
    }

    render() {
        const productCategorys = this.props.productCategoryList.toJS();
        const { shopId } = this.state;
        const { history } = this.props;
        return (<React.Fragment>
            <List renderHeader={() => "欢迎你"} className="my-list" >
                {
                    productCategorys.map((item, index) => {
                        return <Item key={index}
                            extra={<Flex>
                                <Flex.Item>
                                    <Button type="warning" size="small" onClick={() =>
                                        alert('Delete', 'confirm delete?', [
                                            { text: 'Cancel', onPress: () => console.log('cancel') },
                                            {
                                                text: 'Ok',
                                                onPress: () =>
                                                    new Promise(async (resolve) => {
                                                        const response = await reqDelectProductCategory(item.productCategoryId)
                                                        resolve();
                                                        Toast.info(response.resultInfo, 2);
                                                        setTimeout(this.getCategoryList, 2000);
                                                    }),
                                            },
                                        ])} >删除</Button>
                                </Flex.Item>
                                <Flex.Item>
                                    <Button type="primary" size="small" onClick={() => history.push(`/shop/productcategoryoperation/${shopId}/${item.productCategoryId}`)} >编辑</Button>
                                </Flex.Item>
                            </Flex>} >
                            {item.productCategoryName}
                            <Brief> 优先级：{item.priority}</Brief>
                        </Item>
                    })
                }
            </List>
            <WhiteSpace size="lg"></WhiteSpace>
            <WingBlank size="lg">
                <Button type="primary" onClick={() => this.props.history.push(`/shop/productcategoryoperation/${shopId}`)}> 添加类别 </Button>
            </WingBlank>
        </React.Fragment>)
    }
}

const mapStateToProps = (state) => {
    return {
        productCategoryList: state.getIn(["shopReducer", "productCategoryList"])
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getProductCategoryAction(shopId) {
            dispatch(getProductCategoryListAction(shopId))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ShopProductCategoryList);