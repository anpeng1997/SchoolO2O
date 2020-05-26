import React from "react";
import { SearchBar, WhiteSpace, Card, Picker, WingBlank, List, Button } from "antd-mobile";
import { reqShopList } from "../../../api/frontDeskApi";

class Index extends React.Component {


    constructor(props) {
        super(props);
        const parentId = props.match.params.parentId
        console.log("parent", parentId);
        this.state = {
            searchValue: "",
            parentId,
            filetrCondition: [-1],
            shopCategoryList: [],
            shopList: [],
            areas: [
                {
                    label: '春',
                    value: '春',
                },
                {
                    label: '夏',
                    value: '夏',
                },
            ]
        }
    }

    async componentDidMount() {
        const { parentId } = this.state;
        const response = await reqShopList(parentId);
        if (response.success) {
            const { shopCategoryList, shopList } = response.data;
            this.setState({
                shopCategoryList,
                shopList
            });
        }
        console.log(response)
    }

    searchBarChange = (value) => {
        this.setState({
            searchValue: value
        })
    }

    conditionBtnClick = (value) => {
        let newArray;
        const { filetrCondition } = this.state;
        const index = filetrCondition.indexOf(value);
        if (value === -1 && index >= 0) {
            return;
        } else if (value === -1 && index < 0) {
            newArray = [-1];
        } else {
            if (index >= 0) {//删除已有的
                filetrCondition.splice(index, 1);
                if (filetrCondition.length <= 0) {
                    newArray = [-1];
                } else {
                    newArray = filetrCondition
                }

            } else {//没有的就添加
                //判断 全部类别 是否存在，存在的话就先把它删除，在添加新的
                const allIndex = filetrCondition.indexOf(-1);
                if (allIndex >= 0) {
                    filetrCondition.splice(allIndex, 1);
                }
                newArray = filetrCondition.concat(value);
            }
        }
        this.setState({
            filetrCondition: newArray
        }, () => {
            console.log(this.state.filetrCondition)
        })

    }

    isSelectCondition = (conditionName) => {
        const { filetrCondition } = this.state;
        return filetrCondition.includes(conditionName) ? "primary" : "ghost";
    }

    render() {
        const { searchValue, areas, shopCategoryList, shopList } = this.state;
        return <React.Fragment>
            <SearchBar placeholder="Search" value={searchValue} placeholder="输入店铺关键字" onChange={this.searchBarChange} maxLength={10} />
            <WingBlank size="sm">
                <Button type={this.isSelectCondition(-1)} onClick={() => this.conditionBtnClick(-1)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>全部类别</Button>
                {
                    shopCategoryList.map((item, index) => {
                        return <Button key={item.shopCategoryId} type={this.isSelectCondition(item.shopCategoryId)} onClick={() => this.conditionBtnClick(item.shopCategoryId)} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>{item.shopCategoryName}</Button>
                    })
                }
                <Picker data={areas} cols={1}>
                    <List.Item style={{ maxHeight: "30px", minHeight: "10px" }} arrow="horizontal">区域</List.Item>
                </Picker>
                <WhiteSpace />
                {
                    shopList.map((item, index) => {
                        return <Card key={index}>
                            <Card.Header
                                title={item.shopName}
                            />

                            //TODO: 前端的条件搜索
                            <Card.Body>
                                <div>{item.shopDesc}</div>
                            </Card.Body>
                            <Card.Footer content={"更新于：" + item.lastEditTime} extra={<div>点击查看</div>} />
                        </Card>
                    })
                }

            </WingBlank>
        </React.Fragment>
    }
}

export default Index;