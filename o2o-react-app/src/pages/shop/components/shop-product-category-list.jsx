import React from "react";
import { connect } from "react-redux";
import { List, Button, Modal, Toast } from 'antd-mobile';
import { getProductCategoryListAction } from "../store/actionCreators";

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
                                alert('Delete', 'Are you sure???', [
                                    { text: 'Cancel', onPress: () => console.log('cancel') },
                                    {
                                        text: 'Ok',
                                        onPress: () =>
                                            new Promise((resolve) => {
                                                Toast.info('onPress Promise', 1);
                                                setTimeout(resolve, 1000);
                                            }),
                                    },
                                ])} >删除</Button>}>
                            {item.productCategoryName}
                            <Brief> 优先级：{item.priority}</Brief>
                        </Item>
                    })
                }
            </List>
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