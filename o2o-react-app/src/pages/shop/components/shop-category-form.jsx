import React from "react";
import { List, InputItem, TextareaItem, Picker, ImagePicker, WingBlank, Flex, Button, WhiteSpace, Toast } from "antd-mobile";
import { createForm } from "rc-form";
import { reqShopOperationInitData } from "../../../api/shopAPI";
import { reqAddOrModifyShopCategory } from "../../../api/shopAPI";

const Item = List.Item;

class ShopCategoryForm extends React.Component {

    constructor(props) {
        super(props);
        let shopCategoryId = !!props.match.params.id ? props.match.params.id : null;
        this.state = {
            isAddImg: true,
            shopCategoryId,
            shopCategoryList: []
        }
    }

    async componentDidMount() {
        let shopCategoryArray = [];
        const response = await reqShopOperationInitData()
        if (response.success) {
            response.data.shopCategoryList.map(function (item, index) {
                shopCategoryArray.push({ value: item.shopCategoryId, label: item.shopCategoryName });
                return index;
            });
            this.setState({ shopCategoryList: shopCategoryArray })
        } else {
            console.error(response);
            Toast.fail("列表数据加载失败", 2);
        }
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
    imgValidator = (rule, value, callback) => {
        //根据Id是否为null来判断是修改还是注册
        if (!this.state.shopCategoryId) {
            //注册
            if (!value || value.length <= 0) {
                callback('店铺类别的图片是必须的！')
            }
        }
        callback();
    }
    bac
    k = () => {
        this.props.history.goBack();
    }

    onSubmitData = () => {
        const { history, form } = this.props;
        this.props.form.validateFields(async (error) => {
            const { shopCategoryName, shopCategoryDesc, shopCategoryImg, priority, parentId } = form.getFieldsValue();

            //验证结果,当没有错误时error对象则为null
            const shopCategoryId = this.state.shopCategoryId;
            if (!error) {
                let categoryInfo = {};
                categoryInfo.shopCategoryId = shopCategoryId;
                categoryInfo.shopCategoryName = shopCategoryName;
                categoryInfo.shopCategoryDesc = shopCategoryDesc;
                categoryInfo.priority = priority;
                const parent = !!parentId ? parentId[0] : null;
                categoryInfo.parentId = parent;
                let formData = new FormData();
                formData.append("categoryInfo", JSON.stringify(categoryInfo));
                let currentFile = null;
                if (!!shopCategoryImg && shopCategoryImg.length > 0) {
                    currentFile = shopCategoryImg[0].file;
                }
                formData.append("imgFile", currentFile);
                const addOrModify = !!shopCategoryId ? 'modify' : 'add';
                const response = await reqAddOrModifyShopCategory(addOrModify, formData);
                if (response.success) {
                    Toast.success('操作成功！', 2);
                    setTimeout(() => {
                        history.goBack();
                    }, 2000)
                } else {
                    Toast.fail(response.erroInfo)
                }
            } else {
                console.error("vaildate Filed fail!!!");
            }
        })
    }


    render() {
        const { getFieldError, getFieldProps, getFieldValue } = this.props.form;
        return <React.Fragment>
            <List renderHeader={() => !this.state.shopCategoryId ? "添加店铺类别" : "修改店铺类别"} renderFooter={() =>
                getFieldError("shopCategoryName") ||
                getFieldError("shopCategoryDesc") ||
                getFieldError("priority") ||
                getFieldError("shopCategoryImg")
            }>
                <InputItem clear="true" maxLength="12"
                    {...getFieldProps('shopCategoryName', {
                        validateFirst: true,
                        rules: [
                            //申明式验证
                            { required: true, message: '店铺类别名称是必须的！' },
                            { whitespace: true, message: '店铺类别中不能含有空格！' },
                            { max: 12, message: '店铺类别名不能长于12位！' }
                        ]
                    })}
                    whitespace="true"
                    error={!!getFieldError('shopCategoryName')}
                    placeholder="输入类别名称">
                    类别名称
                </InputItem>
                <TextareaItem
                    title="类别描述"
                    placeholder="类别描述...."
                    data-seed="logId"
                    {...getFieldProps('shopCategoryDesc', {
                        rules: [
                            { required: true, message: '店铺类别描述是必须的！' }
                        ]
                    })}
                    error={!!getFieldError('shopCategoryDesc')}
                    rows={3}
                    count={100}
                />
                <Picker
                    {...getFieldProps('parentId', {
                        rules: []
                    })}
                    data={this.state.shopCategoryList}
                    cols={1}
                >
                    <Item arrow="horizontal">所属类别（可不选）：</Item>
                </Picker>
                <InputItem
                    error={!!getFieldError('priority')}
                    {...getFieldProps('priority', {
                        rules: [
                            { required: true, message: '店铺类别权重是必须的！' }
                        ]
                    })}
                    type="money"
                    clear
                    moneyKeyboardAlign="left"
                    disabledKeys={['.']}
                >
                    权重:
                </InputItem>
                <ImagePicker
                    files={getFieldValue('shopCategoryImg')}
                    error={!!getFieldError('shopCategoryImg')}
                    {...getFieldProps('shopCategoryImg', {
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

export default createForm()(ShopCategoryForm);
