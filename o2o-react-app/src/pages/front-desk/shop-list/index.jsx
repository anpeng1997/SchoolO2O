import React from "react";
import { SearchBar, WhiteSpace, Card, Picker, WingBlank, List, Button } from "antd-mobile";

class Index extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            searchValue: "",
            filetrCondition: ["全部类别"],
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

    searchBarChange = (value) => {
        this.setState({
            searchValue: value
        })
    }

    conditionBtnClick = (value) => {
        let newArray;
        const { filetrCondition } = this.state;
        const index = filetrCondition.indexOf(value);
        if (value === "全部类别" && index >= 0) {
            return;
        } else if (value === "全部类别" && index < 0) {
            newArray = ["全部类别"];
        } else {
            if (index >= 0) {//删除已有的
                filetrCondition.splice(index, 1);
                if (filetrCondition.length <= 0) {
                    newArray = ["全部类别"];
                } else {
                    newArray = filetrCondition
                }

            } else {//没有的就添加
                //判断 全部类别 是否存在，存在的话就先把它删除，在添加新的
                const allIndex = filetrCondition.indexOf("全部类别");
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
        const { searchValue, areas } = this.state;
        return <React.Fragment>
            <SearchBar placeholder="Search" value={searchValue} placeholder="输入店铺关键字" onChange={this.searchBarChange} maxLength={10} />
            <WingBlank size="sm">
                <Button type={this.isSelectCondition("全部类别")} onClick={() => this.conditionBtnClick("全部类别")} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>全部类别</Button>
                <Button type={this.isSelectCondition("大排档")} onClick={() => this.conditionBtnClick("大排档")} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>大排档</Button>
                <Button type={this.isSelectCondition("奶茶")} onClick={() => this.conditionBtnClick("奶茶")} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>奶茶</Button>
                <Button type={this.isSelectCondition("烧烤")} onClick={() => this.conditionBtnClick("烧烤")} inline size="small" style={{ margin: '5px 10px 5px 0px' }}>烧烤</Button>
                <Picker data={areas} cols={1}>
                    <List.Item style={{ maxHeight: "30px", minHeight: "10px" }} arrow="horizontal">区域</List.Item>
                </Picker>
                <WhiteSpace />
                <Card>
                    <Card.Header
                        title="香飘飘奶茶店"
                    />
                    <Card.Body>
                        <div>This is content of `Card`</div>
                    </Card.Body>
                    <Card.Footer content="更新于2020-5-24" extra={<div>点击查看</div>} />
                </Card>
            </WingBlank>
        </React.Fragment>
    }
}

export default Index;