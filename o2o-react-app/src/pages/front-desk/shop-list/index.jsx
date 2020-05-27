import React from "react";
import { SearchBar, WhiteSpace, Card, Picker, WingBlank, List, Button } from "antd-mobile";
import { reqShopList, reqShopCategoryList, reqAreaList } from "../../../api/frontDeskApi";
import { IMGSERVERURL } from "../../../common/Constant";
import { DateTimeFormat } from "../../../common/utils";

class Index extends React.Component {


    constructor(props) {
        super(props);
        const parentId = props.match.params.parentId
        console.log("parent", parentId);
        this.state = {
            parentId,
            searchValue: "",
            categoryFilterCondition: [-1],
            selectAreaId: null,
            shopCategoryList: [],
            shopDate: { count: 0, shopList: [] },
            areas: []
        }
    }

    async componentDidMount() {
        const { parentId } = this.state;
        const shopResponse = await reqShopList(parentId);
        if (shopResponse.success) {
            const { count, shopList } = shopResponse.data;
            this.setState({
                shopDate: {
                    count,
                    shopList
                }
            });
        }
        const shopCategoryResponse = await reqShopCategoryList(parentId);
        if (shopCategoryResponse.success) {
            const { shopCategoryList } = shopCategoryResponse.data;
            this.setState({
                shopCategoryList
            })
        }
        const areaResponse = await reqAreaList();
        if (areaResponse.success) {
            let areas = [{ label: "全部区域", value: -1 }];
            areaResponse.data.rows.map((item, index) => {
                areas.push({ label: item.areaName, value: item.areaId });
                return index;
            })
            this.setState({
                areas
            })
        }
    }

    //TODO: 搜索关键字搜索框清空数据后，没有触发事件加载新的数据
    searchBarChange = (value) => {
        this.setState({
            searchValue: value
        })
    }

    searchBarSubmit = async (value) => {
        await this.byConditionGetShopData();
    }

    byConditionGetShopData = async () => {
        const { parentId, searchValue, categoryFilterCondition, selectAreaId } = this.state;
        let shopCategoryIds;
        if (categoryFilterCondition.includes(-1)) {
            shopCategoryIds = null;
        } else {
            shopCategoryIds = categoryFilterCondition;
        }
        let areaId = null;
        if (selectAreaId) {
            areaId = selectAreaId[0] === -1 ? null : selectAreaId[0]
        }
        const shopResponse = await reqShopList(parentId, searchValue, shopCategoryIds, areaId, null, null);
        if (shopResponse.success) {
            const { count, shopList } = shopResponse.data;
            this.setState({
                shopDate: {
                    count,
                    shopList
                }
            });
        }
    }

    conditionBtnClick = (value) => {
        let newArray;
        const { categoryFilterCondition } = this.state;
        const index = categoryFilterCondition.indexOf(value);
        if (value === -1 && index >= 0) {
            return;
        } else if (value === -1 && index < 0) {
            newArray = [-1];
        } else {
            if (index >= 0) {//删除已有的
                categoryFilterCondition.splice(index, 1);
                if (categoryFilterCondition.length <= 0) {
                    newArray = [-1];
                } else {
                    newArray = categoryFilterCondition
                }
            } else {//没有的就添加
                //判断 全部类别 是否存在，存在的话就先把它删除，在添加新的
                const allIndex = categoryFilterCondition.indexOf(-1);
                if (allIndex >= 0) {
                    categoryFilterCondition.splice(allIndex, 1);
                }
                newArray = categoryFilterCondition.concat(value);
            }
        }
        this.setState({
            categoryFilterCondition: newArray
        }, async () => {
            console.log(this.state.categoryFilterCondition)
            await this.byConditionGetShopData();
        })

    }

    isSelectCondition = (conditionName) => {
        const { categoryFilterCondition } = this.state;
        return categoryFilterCondition.includes(conditionName) ? "primary" : "ghost";
    }

    areaPickOnOk = (val) => {
        this.setState({ selectAreaId: val }, async () => {
            await this.byConditionGetShopData();
        })
    }

    render() {
        const { searchValue, areas, shopCategoryList, shopDate, selectAreaId } = this.state;
        return <React.Fragment>
            <SearchBar value={searchValue} placeholder="输入店铺关键字" maxLength={10}
                onChange={(val) => this.searchBarChange(val)}
                onSubmit={(val) => this.searchBarSubmit(val)}
            />
            <WingBlank size="sm">
                <Button type={this.isSelectCondition(-1)} onClick={() => this.conditionBtnClick(-1)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>全部类别</Button>
                {
                    shopCategoryList.map((item, index) => {
                        return <Button key={item.shopCategoryId} type={this.isSelectCondition(item.shopCategoryId)} onClick={() => this.conditionBtnClick(item.shopCategoryId)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>{item.shopCategoryName}</Button>
                    })
                }
                <Picker data={areas} cols={1} value={selectAreaId} onOk={(val) => this.areaPickOnOk(val)}>
                    <List.Item style={{ maxHeight: "30px", minHeight: "10px" }} arrow="horizontal">区域</List.Item>
                </Picker>
                <WhiteSpace />
                {
                    shopDate.shopList.map((item, index) => {
                        return <div key={index}>
                            <Card>
                                <Card.Header
                                    title={item.shopName}
                                />
                                {/* TODO: 前端的条件搜索 */}
                                <Card.Body>
                                    <img alt={item.shopName} src={`${IMGSERVERURL}/${item.shopImg}`} style={{ float: "left", width: "120px", height: "60px", marginBottom: "15px" }}></img>
                                    <div style={{ float: "left", marginLeft: "20px" }}>{item.shopDesc}</div>
                                </Card.Body>
                                <Card.Footer content={"更新于：" + DateTimeFormat(item.lastEditTime, "YYYY-MM-DD HH:mm")} extra={<div>点击查看</div>} />
                            </Card>
                            <WhiteSpace />
                        </div>
                    })
                }
            </WingBlank>
        </React.Fragment >
    }
}

export default Index;