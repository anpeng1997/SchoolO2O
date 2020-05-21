import React from "react";
import HomeCss from "../../../../css/home.module.css"
import { IMGSERVERURL } from "../../../../common/Constant";

class CategoryItem extends React.Component {

    render() {
        const { shopCategory } = this.props;
        return <a href="#" style={{ display: "block" }}>
            <div className={HomeCss.categoryWrapper}>
                <img alt="" className={HomeCss.categoryImg} src={`${IMGSERVERURL}${shopCategory.shopCategoryImg}`} ></img>
                <div className={HomeCss.descDiv}>
                    <div className={HomeCss.categoryNameDiv}>{shopCategory.shopCategoryName}</div>
                    <div className={HomeCss.categoryDescDiv}>{shopCategory.shopCategoryDesc}</div>
                </div>
            </div></a>
    }
}

export default CategoryItem;