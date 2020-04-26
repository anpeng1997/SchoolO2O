import React from "react";
import { InputItem, Button, Card, NavBar, WhiteSpace, Toast } from "antd-mobile";
import { createForm } from "rc-form";
import { reqLogin } from "../../api/loginAPI";

class Login extends React.Component {


    onSubmitData = () => {

        const { form, history } = this.props;
        const { userName, password } = form.getFieldsValue();
        form.validateFields(async (error) => {
            if (!error) {
                const response = await reqLogin({ userName, password });
                if (response.success) {
                    Toast.success("登录成功", 2);
                    setTimeout(() => {
                        history.push({
                            pathname: "/shop/shoplist"
                        })
                    }, 2000)
                } else {
                    Toast.fail(response.resultInfo, 2)
                }
            }
        })
    }

    render() {
        const { getFieldProps, getFieldError } = this.props.form;
        return <React.Fragment>
            <NavBar mode="light">
                Login
            </NavBar>
            <WhiteSpace size="lg"></WhiteSpace>
            <Card full>
                <Card.Body>
                    <InputItem placeholder="userName" {...getFieldProps('userName', {
                        rules: [
                            { required: true, message: '用户名是必须的！' }
                        ]
                    })}
                        error={!!getFieldError('userName')}
                    >用户名：</InputItem>

                    <InputItem type="password" placeholder="password"{...getFieldProps('password', {
                        rules: [
                            { required: true, message: '密码是必须的！' }
                        ]
                    })}
                        error={!!getFieldError('password')}
                    >密码：</InputItem>
                    <Button style={{ margin: '30px 80px' }} type="primary" size="small" onClick={this.onSubmitData}>登录</Button>
                </Card.Body>
                <Card.Footer content={getFieldError('userName') || getFieldError('password')} extra="pwoer by o2o">
                </Card.Footer>
            </Card>
        </React.Fragment>
    }
}
export default createForm()(Login);