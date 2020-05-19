import React from "react";
import HomeCss from "../../../css/home.module.css"

class CategoryItem extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        return <a href="#"><div className={HomeCss.categoryWrapper}>
            <img alt="" className={HomeCss.categoryImg} src="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg" ></img>
            <div className={HomeCss.descDiv}>
                奶茶
            </div>
        </div></a>
    }
}

export default CategoryItem;