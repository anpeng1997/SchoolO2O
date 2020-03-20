import React from "react";
import { createForm } from "rc-form";
import { List, InputItem } from "antd-mobile";

class ShopProductCategoryForm extends React.Component {

    constructor(props) {
        super(props);
        let categoryId = !!props.match.params.id ? props.match.params.id : null;
        this.state = {
            categoryId
        };
    }

    render() {
        const { getFieldProps } = this.props.form;
        return <React.Fragment>
            <List renderHeader={() => !this.state.categoryId ? "添加商品类别" : "修改商品类别信息"}>
                <InputItem clear="true"
                    {...getFieldProps("productCategoryName", {
                        validateFirst: true,
                        defaultValue: 100,
                        rules: [
                            //申明式验证
                            { required: true, message: '商品类别名称是必须的！' },
                            { whitespace: true, message: '商品类别名称中不能含有空格！' },
                            { max: 12, message: '商品类别名不能长于12位！' },
                            //自定义验证器
                        ]
                    })} ></InputItem>
            </List>
        </React.Fragment>
    }
}

export default createForm()(ShopProductCategoryForm);