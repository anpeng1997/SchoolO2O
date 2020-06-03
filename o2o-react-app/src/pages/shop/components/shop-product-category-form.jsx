import React from "react";
import {createForm} from "rc-form";
import {List, InputItem, TextareaItem, Flex, WhiteSpace, WingBlank, Button, Toast} from "antd-mobile";
import {reqAddProductCategory, reqModifyProductCategory, reqProductCategory} from "../../../api/shopAPI";

// 通过自定义 moneyKeyboardWrapProps 修复虚拟键盘滚动穿透问题
const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent);
let moneyKeyboardWrapProps;
if (isIPhone) {
    moneyKeyboardWrapProps = {
        onTouchStart: e => e.preventDefault(),
    };
}

class ShopProductCategoryForm extends React.PureComponent {

    constructor(props) {
        super(props);
        const shopId = props.match.params.sid;
        const categoryId = props.match.params.cid ?? null;
        this.state = {
            categoryId,
            shopId
        };
    }

    async componentDidMount() {
        const categoryId = this.state.categoryId;
        if (categoryId) {
            const response = await reqProductCategory(categoryId);
            if (response.success) {
                this.props.form.setFieldsValue({
                    "productCategoryName": response.data.productCategoryName,
                    "priority": response.data.priority,
                    "productCategoryDesc": response.data.productCategoryDesc
                })
            }
        }
    }

    back = () => {
        this.props.history.goBack();
    }
    onSubmitData = () => {
        const {shopId, categoryId} = this.state;
        const form = this.props.form;
        form.validateFields(async (error) => {
            if (!error) {
                const formData = {
                    shopId: shopId,
                    productCategoryId: categoryId,
                    ...form.getFieldsValue()
                }
                let response;
                if (categoryId) {
                    //modify
                    response = await reqModifyProductCategory(formData)
                } else {
                    //add
                    response = await reqAddProductCategory(formData);
                }
                if (response.success) {
                    Toast.success('操作成功！', 2);
                    setTimeout(() => {
                        this.props.history.goBack();
                    }, 2000)
                } else {
                    Toast.fail(response.resultInfo, 2);
                }
            }
        })
    }

    render() {
        const {getFieldProps, getFieldError} = this.props.form;
        return <React.Fragment>
            <List
                renderHeader={() => !this.state.categoryId ? "添加商品类别" : "修改商品类别信息"}
                renderFooter={() => getFieldError('productCategoryName') || getFieldError('priority')}>
                <InputItem clear="true"
                           error={!!getFieldError('productCategoryName')}
                           {...getFieldProps("productCategoryName", {
                               validateFirst: true,
                               defaultValue: 100,
                               rules: [
                                   //申明式验证
                                   {required: true, message: '商品类别名称是必须的！'},
                                   {whitespace: true, message: '商品类别名称中不能含有空格！'},
                                   {max: 12, message: '商品类别名不能长于12位！'},
                               ]
                           })} >
                    类别名称:
                </InputItem>
                <InputItem
                    error={!!getFieldError('priority')}
                    {...getFieldProps('priority', {
                        rules: [
                            {required: true, message: '商品类别权重是必须的！'}
                        ]
                    })}
                    type="money"
                    clear
                    moneyKeyboardAlign="left"
                    disabledKeys={['.']}
                    moneyKeyboardWrapProps={moneyKeyboardWrapProps}>
                    类别权重:
                </InputItem>
                <TextareaItem {...getFieldProps('productCategoryDesc')} rows="3" title="类别描述:" count="100" clear="true">
                </TextareaItem>
                <WhiteSpace size="lg"></WhiteSpace>
                <WingBlank size="lg">
                    <Flex>
                        <Flex.Item><Button size="large" onClick={this.back}>取消</Button></Flex.Item>
                        <Flex.Item><Button type="primary" size="large"
                                           onClick={this.onSubmitData}>确认</Button></Flex.Item>
                    </Flex>
                </WingBlank>
                <WhiteSpace size="lg"></WhiteSpace>
            </List>
        </React.Fragment>
    }
}

export default createForm()(ShopProductCategoryForm);