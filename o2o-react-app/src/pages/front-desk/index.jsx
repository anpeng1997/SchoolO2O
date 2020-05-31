import React from "react";
import { Route, Switch } from "react-router-dom";
import { NavBar, Icon } from "antd-mobile";
import Home from "./home";
import ShopList from "./shop-list"
import ShopDetail from "./shop-detail.jsx"

class Index extends React.Component {

    back = () => {
        this.props.history.goBack();
    }

    render() {
        return <React.Fragment>
            <NavBar
                mode="light"
                icon={<Icon type="left" />}
                onLeftClick={this.back}
                rightContent={[
                    <Icon key="1" type="ellipsis" />,
                ]}
            >O2O</NavBar>
            <Switch>
                <Route path="/frontdesk/home" exact component={Home}></Route>
                <Route path="/frontdesk/shops/:parentId" exact component={ShopList}></Route>
                <Route path="/frontdesk/shopdetail/:shopId" exact component={ShopDetail}></Route>
                <Route path="/" component={Home}></Route>
            </Switch>
        </React.Fragment>
    }
}

export default Index;