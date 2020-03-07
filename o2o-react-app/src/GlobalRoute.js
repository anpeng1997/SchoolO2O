import React from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

import Shop from "./pages/shop";
import NotFound from "./pages/NotFound";


function GlobalRoute() {
    return (
        <Router>
            <Switch>
                <Route path="/" component={Shop}></Route>
                <Route path="/shop" component={Shop}></Route>
                <Route component={NotFound}></Route>
            </Switch>
        </Router>
    )
}

export default GlobalRoute;