import React from "react";
import { createForm } from "rc-form";
import { connect } from "react-redux";
import { getShopOperationInitDataAction } from "../store/actionCreators";
import { reqRegisterOrModifyShop, reqShopInfo } from "../../../api/shopAPI"
import {
    List,
    InputItem,
    ImagePicker,
    Flex,
    Button,
    Picker,
    TextareaItem,
    WhiteSpace,
    WingBlank
} from "antd-mobile";
import { Toast } from "antd-mobile";

const Item = List.Item;

class ShopForm extends React.Component {

    constructor(props) {
        super(props);
        let shopId = !!props.match.params.id ? props.match.params.id : null;
        this.state = {
            shopId: shopId,
            isAddImg: true,
            verifyCodeUrl: "http://localhost:8080/Kaptcha"
        }
    }

    async componentDidMount() {
        Toast.loading('正在加载...', 0);
        //获得初始数据
        const shopId = this.props.match.params.id;
        await this.props.getInitData(shopId);
        if (!!this.state.shopId) {
            const shopData = await reqShopInfo(shopId)
            Toast.hide();
            const form = this.props.form;
            if (shopData.success) {
                form.setFieldsValue({
                    'shopName': shopData.data.shopName,
                    'shopDesc': shopData.data.shopDesc,
                    'shopAddr': shopData.data.shopAddr,
                    'phone': shopData.data.phone,
                    'areaId': [shopData.data.areaId],
                    'shopCategoryId': [shopData.data.shopCategoryId]
                })
            } else {
                Toast.hide();
                console.error(shopData);
                Toast.fail("数据加载失败!" + shopData.errorMsg, 3);
            }
        }
    }

    onErrorClick = () => {
        if (this.state.hasError) {
            Toast.info('请输入11位的手机号');
        }
    }

    isSelectable = () => {
        const img = this.props.form.getFieldValue('shopImg')
        if (!img) {
            return true;
        }
        if (img.length <= 0) {
            return true;
        }
        return false;
    }

    back = () => {
        this.props.history.goBack();
    }

    onImagePickerChange = (files, type, index) => {
        //在这里要返回接收到的files，否则在form中获取不到值
        if (files.length <= 0) {
            this.setState({
                isAddImg: true
            })
        } else {
            this.setState({
                isAddImg: false
            })
        }
        return files;
    }

    onSubmitData = () => {
        const { history, form } = this.props;
        const { shopName, shopCategoryId, areaId, shopAddr, phone, shopImg, shopDesc, verifyCodeActual } = form.getFieldsValue();
        let shopId = this.state.shopId;
        this.props.form.validateFields(async (error) => {
            //验证结果
            if (!error) {
                let shopInfo = {};
                shopInfo.shopId = shopId;
                shopInfo.shopName = shopName;
                shopInfo.shopAddr = shopAddr;
                shopInfo.area = { areaId: areaId[0] };
                shopInfo.shopCategory = { shopCategoryId: shopCategoryId[0] };
                shopInfo.phone = phone;
                shopInfo.shopDesc = shopDesc;
                let formData = new FormData();
                formData.append("shopInfo", JSON.stringify(shopInfo));
                formData.append("verifyCodeActual", verifyCodeActual)
                let currentFile = null;
                if (!!shopImg && shopImg.length > 0) {
                    currentFile = shopImg[0].file;
                }
                formData.append("shopImg", currentFile);
                const registerOrModify = !!shopId ? 'modify' : 'register';
                const data = await reqRegisterOrModifyShop(registerOrModify, formData, { 'Content-Type': 'multipart/form-data' })
                if (data.success) {
                    Toast.success('操作成功！', 2);
                    setTimeout(() => {
                        history.goBack();
                    }, 2000)
                } else {
                    Toast.fail(data.errorMsg)
                }
            } else {
                console.error("vaildate Filed fail!!!");
            }
        })
    }


    /**
     * shopName自定义验证器
     * 
     */
    shopNameValidator = (rule, value, callback) => {
        if (value && value.length >= 3) {
            callback(); //调用无参的callback则代表验证通过
        } else {
            callback("店铺名称不能小于三个字！");  //给callback传递参数则代表验证失败
        }
    }

    phoneValidator = (rule, value, callback) => {
        //必须先判断value值是否有值,否则该处报错的话不会打印在控制台上，form.validateFields也不会执行，因为后续没有callback执行了
        if (!!value) {
            if (value.replace(/\s/g, '').length < 11) {
                callback("号码必须为11位");
            } else {
                callback();
            }
        } else {
            callback("号码为必填的！");
        }
    }

    imgValidator = (rule, value, callback) => {
        //根据shopId是否为null来判断是修改还是注册
        if (!this.state.shopId) {
            //注册
            if (!value) {
                callback('店铺的图片是必须的')
            }
            if (value.length <= 0) {
                callback('店铺的图片是必须的')
            }
        }
        callback();
    }

    render() {
        const { shopCategoryList, areaList } = this.props;
        const { getFieldProps, getFieldError, getFieldValue } = this.props.form;
        return <React.Fragment>
            <List renderHeader={() => this.state.shopId == null ? '注册店铺' : '修改店铺信息'} renderFooter={() => getFieldError('shopName') || getFieldError('shopCategoryId')
                || getFieldError("areaId") || getFieldError('shopAddr') || getFieldError('phone') || getFieldError('shopImg') ||
                getFieldError('shopDesc') || getFieldError('verifyCodeActual')
            }>
                <InputItem clear="true" maxLength="12"
                    {...getFieldProps('shopName', {
                        validateFirst: true,
                        defaultValue: 100,
                        rules: [
                            //申明式验证
                            { required: true, message: '店铺名称是必须的！' },
                            { whitespace: true, message: '店铺名称中不能含有空格！' },
                            { max: 12, message: '店铺名不能长于12位！' },
                            //自定义验证器
                            { validator: this.shopNameValidator },
                        ]
                    })}
                    defaultValue={100}
                    whitespace="true"
                    error={!!getFieldError('shopName')}
                    placeholder="输入店铺名称">
                    店铺名称
                </InputItem>
                <Picker data={shopCategoryList.toJS()} cols={1}
                    onOk={(val) => { this.setState({ currentPickerShopCategoryId: val }) }}
                    error={!!getFieldError('shopCategoryId')}
                    {...getFieldProps('shopCategoryId', {
                        rules: [
                            { required: true, message: '必须选择一个店铺类别!' }
                        ]
                    })} className="forss">
                    <Item arrow="horizontal">店铺类别</Item>
                </Picker>
                <Picker data={areaList.toJS()} cols={1}
                    onOk={(val) => { this.setState({ currnetPickerAareaId: val }) }}
                    error={!!getFieldError('areaId')}
                    {...getFieldProps('areaId', {
                        rules: [
                            { required: true, message: '必须选择一个区域类别!' }
                        ]
                    })} className="forss">
                    <Item arrow="horizontal">所属地区</Item>
                </Picker>
                <InputItem clear="true" maxLength="40"
                    error={!!getFieldError('shopAddr')}
                    {...getFieldProps('shopAddr', {
                        rules: [
                            { required: true, message: '店铺详细地址是必须的' }
                        ]
                    })}
                    placeholder="输入店铺详细地址">
                    详细地址
                </InputItem>
                <InputItem
                    type="phone"
                    placeholder="输入电话号码"
                    error={!!getFieldError('phone')}
                    {...getFieldProps('phone', {
                        rules: [
                            { validator: this.phoneValidator },
                        ]
                    })}
                    onErrorClick={this.onErrorClick}
                >
                    联系号码
                </InputItem>
                <Item>店铺图片</Item>
                <ImagePicker
                    files={getFieldValue('shopImg')}
                    error={!!getFieldError('shopImg')}
                    {...getFieldProps('shopImg', {
                        //设置value类型
                        valuePropName: 'fileList',
                        //处理表单获取值的事件
                        getValueFromEvent: this.onImagePickerChange,
                        rules: [
                            { validator: this.imgValidator }
                        ]
                    })}
                    //只能选择一张图片
                    selectable={this.state.isAddImg} >
                </ImagePicker>
                <TextareaItem
                    title="店铺描述"
                    placeholder="介绍你的店铺...."
                    data-seed="logId"
                    {...getFieldProps('shopDesc', {
                        rules: [
                            { required: true, message: '店铺描述是必须的！' }
                        ]
                    })}
                    error={!!getFieldError('shopDesc')}
                    rows={3}
                    count={100}
                />
                <WhiteSpace size="md"></WhiteSpace>
                <InputItem
                    placeholder="输入验证码（点击图片更换）"
                    error={!!getFieldError('verifyCodeActual')}
                    {...getFieldProps('verifyCodeActual', {
                        rules: [
                            {
                                required: true, message: '验证码是必须的！'
                            }
                        ]
                    })}
                >
                    <img alt="验证码" src={this.state.verifyCodeUrl} onClick={() => { this.setState({ verifyCodeUrl: "http://localhost:8080/Kaptcha?time=" + new Date().getTime() }) }} style={{ height: 'auto', width: '100%' }}></img>
                </InputItem>
                <WhiteSpace size="lg"></WhiteSpace>
                <WingBlank size="lg">
                    <Flex>
                        <Flex.Item><Button size="large" onClick={this.back} >取消</Button></Flex.Item>
                        <Flex.Item><Button type="primary" size="large" onClick={this.onSubmitData} >确认</Button></Flex.Item>
                    </Flex>
                </WingBlank>
                <WhiteSpace size="lg"></WhiteSpace>
            </List>
        </React.Fragment >
    }
}

const mapStateToProps = (state) => {
    return {
        shopCategoryList: state.getIn(["shopReducer", "shopOperationInitData", "shopCategoryList"]),
        areaList: state.getIn(["shopReducer", "shopOperationInitData", "areaList"]),
        shopInfo: state.getIn(["shopReducer", "shopOperationInitData", "shopInfo"])
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getInitData() {
            dispatch(getShopOperationInitDataAction())
        }
    }
}

//createForm:高级函数，将一个组件给它，将返回一个新的组件
export default connect(mapStateToProps, mapDispatchToProps)(createForm()(ShopForm));