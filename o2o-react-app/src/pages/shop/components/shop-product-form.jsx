import React from "react";
import { List, Button, InputItem, ImagePicker, Picker, WhiteSpace, WingBlank, Flex, Toast } from "antd-mobile";
import { createForm } from "rc-form";
import { reqProductCategoryList, reqAddOrModifyProduct, reqProduct } from "../../../api/shopAPI";
import { Redirect } from "react-router-dom";
import { IMGSERVERURL } from "../../../common/Constant";

const Item = List.Item;

// 通过自定义 moneyKeyboardWrapProps 修复虚拟键盘滚动穿透问题
const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent);
let moneyKeyboardWrapProps;
if (isIPhone) {
    moneyKeyboardWrapProps = {
        onTouchStart: e => e.preventDefault(),
    };
}


class ShopProductForm extends React.PureComponent {
    constructor(props) {
        super(props);
        //获取shopId 
        const shopId = props.match.params.sid;
        const productId = props.match.params.pid;
        this.state = {
            shopId,
            productId,
            productCategorys: [],
            isAddImg: true
        }
    }

    async componentDidMount() {
        Toast.loading('正在加载...', 0);
        const { shopId, productId } = this.state;
        if (shopId) {
            const response = await reqProductCategoryList(shopId);
            if (response.success) {
                let categoryArray = [];
                response.data.map(function (item, index) {
                    categoryArray.push({ value: item.productCategoryId, label: item.productCategoryName })
                    return index;
                })
                this.setState({
                    productCategorys: categoryArray
                });
            }
            //获取当前要编辑的商品信息
            if (productId) {
                const response = await reqProduct(productId);
                if (response.success) {
                    const form = this.props.form;
                    const data = response.data;
                    let imgs = [];
                    data.product.productImgs.map((item, index) => {
                        imgs.push({ url: IMGSERVERURL + item.imgAddr, id: item.productImgId })
                        return index;
                    });
                    form.setFieldsValue({
                        'productName': data.product.productName,
                        'productDesc': data.product.productDesc,
                        'normalPrice': data.product.normalPrice,
                        'promotionPrice': data.product.promotionPrice,
                        'priority': data.product.priority,
                        'productCategoryId': [data.product.productCategoryId],
                        'productImgs': imgs
                    })
                }
            }
        }
        Toast.hide();
    }


    back = () => {
        this.props.history.goBack();
    }

    onSubmitData = () => {
        const { shopId, productId } = this.state;
        const { form, history } = this.props;
        const { productName, productDesc, normalPrice, promotionPrice, productCategoryId, priority, productImgs } = form.getFieldsValue();
        form.validateFields(async (error) => {
            if (!error) {
                const productInfo = {
                    productId,
                    shopId,
                    productName,
                    productDesc,
                    normalPrice,
                    promotionPrice,
                    productCategoryId: productCategoryId[0],
                    priority
                }
                let formData = new FormData();
                formData.append("productInfo", JSON.stringify(productInfo));
                productImgs.map(function (item, index) {
                    formData.append("productImgs", item.file);
                    return index;
                })
                const requestType = !productId ? "add" : "modify";
                //send request
                Toast.loading("submit...", 0)
                const response = await reqAddOrModifyProduct(requestType, formData);
                Toast.hide();
                if (response.success) {
                    Toast.success("操作成功", 2);
                    setTimeout(() => {
                        history.goBack();
                    }, 2000)
                } else {
                    Toast.fail("fail," + response.data.stateInfo)
                }
            }
        })
    }

    onImagePickerChange = (files, type, index) => {
        //在这里要返回接收到的files，否则在form中获取不到值
        let bool = true;
        if (files.length >= 6) {
            bool = false;
        }
        this.setState({
            isAddImg: bool
        })
        return files;
    }

    imgValidator = (rule, value, callback) => {
        //根据shopId是否为null来判断是修改还是注册
        if (!this.state.productId) {
            //注册
            if (!value) {
                callback('商品图片是必须的！')
            }
            if (value.length <= 0) {
                callback('商品图片是必须的！')
            }
        }
        if (value.length > 6) {
            callback("最多只能上传6张图片！")
        }
        callback();
    }


    render() {
        const { getFieldProps, getFieldError, getFieldValue } = this.props.form;
        if (!this.state.shopId) {
            return <Redirect to="/shop/shoplist" />;
        }
        return <React.Fragment>
            <List renderHeader={() => !this.state.productId ? '添加商品' : '修改商品'}
                renderFooter={() => getFieldError('productName') ||
                    getFieldError('productDesc') || getFieldError('normalPrice') ||
                    getFieldError('promotionPrice') || getFieldError('productCategoryId') ||
                    getFieldError('priority') || getFieldError('productImgs')}
            >
                <InputItem {...getFieldProps('productName', {
                    rules: [
                        { required: true, message: '商品名称是必须的！' },
                        { max: 10, message: '商品名称长度不能大于10' }
                    ]
                })}
                    error={!!getFieldError('productName')}
                >
                    商品名称：
                </InputItem>
                <InputItem {...getFieldProps('productDesc', {
                    rules: [
                    ]
                })}
                    error={!!getFieldError('productDesc')}
                >
                    商品描述：
                </InputItem>
                <InputItem
                    {...getFieldProps('normalPrice', {
                        rules: [
                            { required: true, message: '商品原价是必须的！' }
                        ]
                    })}
                    error={!!getFieldError('normalPrice')}
                    type="money"
                    clear
                    moneyKeyboardAlign="left"
                    moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                >原价：</InputItem>
                <InputItem
                    {...getFieldProps('promotionPrice', {
                        rules: [
                            { required: true, message: '商品促销价是必须的！' }
                        ]
                    })}
                    type="money"
                    clear
                    error={!!getFieldError('promotionPrice')}
                    moneyKeyboardAlign="left"
                    moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                >促销价:</InputItem>
                <Picker
                    {...getFieldProps('productCategoryId', {
                        rules: [
                            { required: true, message: '商品类别是必须的！' }
                        ]
                    })}
                    data={this.state.productCategorys}
                    cols={1}
                    error={!!getFieldError('productCategoryId')}>
                    <Item arrow="horizontal">类别：</Item>
                </Picker>
                <InputItem
                    error={!!getFieldError('priority')}
                    {...getFieldProps('priority', {
                        rules: [
                            { required: true, message: '商品类别权重是必须的！' }
                        ]
                    })}
                    type="money"
                    clear
                    moneyKeyboardAlign="left"
                    disabledKeys={['.']}
                    moneyKeyboardWrapProps={moneyKeyboardWrapProps}>
                    权重:
                </InputItem>
                <Item>商品详情图：</Item>
                <ImagePicker
                    files={getFieldValue('productImgs')}
                    error={!!getFieldError('productImgs')}
                    {...getFieldProps('productImgs', {
                        //设置value类型
                        valuePropName: 'fileList',
                        //处理表单获取值的事件
                        getValueFromEvent: this.onImagePickerChange,
                        rules: [
                            { validator: this.imgValidator }
                        ]
                    })}
                    selectable={this.state.isAddImg}
                    multiple={true}
                    accept="image/jpeg,image/jpg,image/png"
                >
                </ImagePicker>
                <WhiteSpace size="lg"></WhiteSpace>
                <WingBlank size="lg">
                    <Flex>
                        <Flex.Item><Button size="large" onClick={this.back} >取消</Button></Flex.Item>
                        <Flex.Item><Button type="primary" size="large" onClick={this.onSubmitData} >确认</Button></Flex.Item>
                    </Flex>
                </WingBlank>
                <WhiteSpace size="lg"></WhiteSpace>
            </List>
        </React.Fragment>
    }
}

export default createForm()(ShopProductForm);