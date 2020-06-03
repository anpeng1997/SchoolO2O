import React from "react";
import { Card, WingBlank, WhiteSpace, SearchBar, Button, Toast } from "antd-mobile";
import { reqShopDetail, reqProductList } from "../../../api/frontDeskApi";
import { IMGSERVERURL } from "../../../common/Constant";
import { DateTimeFormat } from "../../../common/utils";

class Index extends React.Component {


    constructor(props) {
        super(props);
        const { shopId } = props.match.params;
        this.state = {
            shopId,
            productCategoryList: [],
            productList: [],
            shopDetail: {},
            searchValue: "",
            filterCategoryIds: [-1]
        };
    }


    async componentDidMount() {
        const { shopId } = this.state;
        const detailResponse = await reqShopDetail(shopId);
        if (detailResponse.success) {
            const { productCategoryList, shop } = detailResponse.data;
            this.setState({
                productCategoryList,
                shopDetail: shop
            })
        }else{
            Toast.fail(detailResponse.errorInfo,2)
            console.error(detailResponse);
        }
        this.getDataByCondition();
    }

    async getDataByCondition() {
        const { shopId, searchValue, filterCategoryIds } = this.state;
        const productCategoryIds = filterCategoryIds.includes(-1) ? null : filterCategoryIds;
        const response = await reqProductList(shopId, searchValue, productCategoryIds);
        if (response.success) {
            this.setState({
                productList: response.data.productList
            })
        }else{
            Toast.fail(response.errorInfo,2)
            console.error(response);
        }
    }

    isSelectCondition = (conditionName) => {
        const { filterCategoryIds } = this.state;
        return filterCategoryIds.includes(conditionName) ? "primary" : "ghost";
    }

    searchBarChange = (value) => {
        this.setState({
            searchValue: value
        })
    }

    searchBarSubmit = async (value) => {
        await this.getDataByCondition();
    }

    searchBarCancel = (val) => {
        this.setState({
            searchValue: ""
        }, async () => {
            if (val !== "") {
                await this.getDataByCondition();
            }
        })
    }

    conditionBtnClick = (value) => {
        let newArray;
        const { filterCategoryIds } = this.state;
        const index = filterCategoryIds.indexOf(value);
        if (value === -1 && index >= 0) {
            return;
        } else if (value === -1 && index < 0) {
            newArray = [-1];
        } else {
            if (index >= 0) {//删除已有的
                filterCategoryIds.splice(index, 1);
                if (filterCategoryIds.length <= 0) {
                    newArray = [-1];
                } else {
                    newArray = filterCategoryIds
                }
            } else {//没有的就添加
                //判断 全部类别 是否存在，存在的话就先把它删除，在添加新的
                const allIndex = filterCategoryIds.indexOf(-1);
                if (allIndex >= 0) {
                    filterCategoryIds.splice(allIndex, 1);
                }
                newArray = filterCategoryIds.concat(value);
            }
        }
        this.setState({
            filterCategoryIds: newArray
        }, async () => {
            await this.getDataByCondition();
        })
    }

    //TODO : 完成了商店详情页，接下来就是商品详情页
    render() {
        const { shopDetail, productCategoryList, productList,searchValue } = this.state;
        return <div>
            <Card>
                <Card.Body>
                    {/* 数据还未加载时，应该是填充一张正在加载的图片 */}
                    <img src={shopDetail.shopImg ? `${IMGSERVERURL}${shopDetail.shopImg}` : "#"}
                        alt=""
                        style={{ width: "100%", minHeight: "300px" }}></img>
                </Card.Body>
                <Card.Footer content={shopDetail.shopDesc}>
                </Card.Footer>
            </Card>
            <WhiteSpace />
            <SearchBar value={searchValue} placeholder="输入商品关键字" maxLength={10}
                onChange={(val) => this.searchBarChange(val)}
                onSubmit={(val) => this.searchBarSubmit(val)}
                onCancel={(val) => this.searchBarCancel(val)}
            />
            <WingBlank>
                <WhiteSpace />
                <Button type={this.isSelectCondition(-1)} onClick={() => this.conditionBtnClick(-1)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>全部类别</Button>
                {
                    productCategoryList.map((item, index) => {
                        return <Button key={item.productCategoryId} type={this.isSelectCondition(item.productCategoryId)} onClick={() => this.conditionBtnClick(item.productCategoryId)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>{item.productCategoryName}</Button>
                    })
                }
                <WhiteSpace />
                {
                    productList.map((item, index) => {
                        return <Card key={index}>
                            <Card.Header
                                title={item.productName}
                            />
                            <Card.Body>
                                <img alt={item.productName}
                                    src={`${IMGSERVERURL}${item.imgAddr}`}
                                    style={{ float: "left", width: "120px", marginBottom: "15px" }} />
                                <div style={{ float: "left", marginLeft: "20px" }}>
                                    <div>{item.productDesc}</div>
                                    <div>原价：{item.normalPrice}</div>
                                    <div>现价：{item.promotionPrice}</div>
                                </div>
                            </Card.Body>
                            <Card.Footer content={"更新于：" + DateTimeFormat(item.lastEditTime, "YYYY-MM-DD HH:mm")} extra={<div>点击查看</div>} />
                        </Card>
                    })
                }

            </WingBlank>
        </div>
    }
}

export default Index;