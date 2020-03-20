import React from "react";
import { connect } from "react-redux";
import { List, Button, Modal, Toast, WingBlank,WhiteSpace } from 'antd-mobile';
import { getProductCategoryListAction } from "../store/actionCreators";
import { reqDelectProductCategory } from "../../../api/shopAPI";

const Item = List.Item;
const Brief = Item.Brief;
const alert = Modal.alert;

class ShopProductCategoryList extends React.PureComponent {

    componentDidMount() {
        const { getProductCategoryAction, match } = this.props;
        getProductCategoryAction(match.params.id);
    }

    render() {
        const productCategorys = this.props.productCategoryList.toJS();
        return (<React.Fragment>
            <List renderHeader={() => "欢迎你"} className="my-list" >
                {
                    productCategorys.map((item, index) => {
                        return <Item key={index}
                            extra={<Button type="warning" size="small" onClick={() =>
                                alert('Delete', 'confirm delete?', [
                                    { text: 'Cancel', onPress: () => console.log('cancel') },
                                    {
                                        text: 'Ok',
                                        onPress: () =>
                                            new Promise(async (resolve) => {
                                                const response = await reqDelectProductCategory(item.productCategoryId)
                                                if (response.success) {
                                                    Toast.info("操作成功", 2);
                                                } else {
                                                    Toast.fail(response.errorMsg, 2)
                                                }
                                                setTimeout(resolve, 2000);
                                            }),
                                    },
                                ])} >删除</Button>}>
                            {item.productCategoryName}
                            <Brief> 优先级：{item.priority}</Brief>
                        </Item>
                    })
                }
            </List>
            <WhiteSpace size="lg"></WhiteSpace>
            <WingBlank size="lg">
                <Button type="primary" onClick={() => {
                    this.props.history.push("/shop/productcategoryoperation");
                }}> 添加类别 </Button>
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