import React from "react";
import HomeCss from "../../../../css/home.module.css"
import {IMGSERVERURL} from "../../../../common/Constant";
import {Link} from "react-router-dom";

class CategoryItem extends React.Component {

    render() {
        const {shopCategory} = this.props;
        return <Link to={"/frontdesk/shops/" + shopCategory.shopCategoryId}>
            <div className={HomeCss.categoryWrapper}>
                <img alt="" className={HomeCss.categoryImg}
                     src={`${IMGSERVERURL}${shopCategory.shopCategoryImg}`}></img>
                <div className={HomeCss.descDiv}>
                    <div className={HomeCss.categoryNameDiv}>{shopCategory.shopCategoryName}</div>
                    <div className={HomeCss.categoryDescDiv}>{shopCategory.shopCategoryDesc}</div>
                </div>
            </div>
        </Link>
    }
}

export default CategoryItem;