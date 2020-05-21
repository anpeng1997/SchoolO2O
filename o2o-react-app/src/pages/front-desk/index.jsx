import React from "react";
import { Route, Switch } from "react-router-dom";
import { NavBar, Icon } from "antd-mobile";
import Home from "./home";

class Index extends React.Component {

    render() {
        return <React.Fragment>
            <NavBar
                mode="light"
                icon={<Icon type="left" />}
                onLeftClick={() => console.log('onLeftClick')}
                rightContent={[
                    <Icon key="1" type="ellipsis" />,
                ]}
            >O2O</NavBar>
            <Switch>
                <Route path="/frontdesk/home" exact component={Home}></Route>
            </Switch>
        </React.Fragment>
    }
}

export default Index;