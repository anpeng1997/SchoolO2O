import React from "react";
import {Carousel, Card} from "antd-mobile";
import {reqHomeInitData} from "../../../api/frontDeskApi";
import CategoryItem from "./components/category-item";
import {IMGSERVERURL} from "../../../common/Constant";

//将css文件作为一个模块引入，这个模块中的css只作用于当前组件。不会影响当前组件的后代组件
//(create-react-app 中内置了使用 CSS Modules 的配置，当前方式就是使用 create-react-app 内置的用法)
import HomeCss from "../../../css/home.module.css"


class Index extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            headLines: [],
            categorys: [],
            imgHeight: 200,
        }
    }


    async componentDidMount() {
        // simulate img loading
        const initData = await reqHomeInitData();
        console.log(initData)
        if (initData.success) {
            this.setState({
                categorys: initData.data.shopCategoryList,
                headLines: initData.data.headLineList
            })
        }
    }

    render() {
        return <React.Fragment>
            <Carousel className={HomeCss.spaceCarousel}
                      autoplay={true}
                      autoplayInterval={3000}
                      infinite
                      cellSpacing={10}
                      slideWidth={0.8}
                      afterChange={index => this.setState({slideIndex: index})}
            >
                {this.state.headLines.map((val, index) => (
                    <a
                        key={val.lineImg}
                        href="#"
                        style={{display: 'inline-block', width: '100%', height: this.state.imgHeight}}
                    >
                        <img
                            src={`${IMGSERVERURL}/${val.lineImg}`}
                            alt=""
                            style={{width: '100%', verticalAlign: 'top'}}
                            onLoad={() => {
                                // fire window resize event to change height
                                window.dispatchEvent(new Event('resize'));
                                this.setState({imgHeight: 'auto'});
                            }}
                        />
                    </a>
                ))
                }
            </Carousel>
            <Card full>
                <Card.Header title="全部商品"
                             thumb="/imgs/shop.png"
                             extra={<span></span>}>
                </Card.Header>
                <Card.Body>
                    {
                        this.state.categorys.map((item, index) =>
                            <CategoryItem shopCategory={item} key={index}></CategoryItem>
                        )
                    }
                </Card.Body>
            </Card>
        </React.Fragment>
    }
}

export default Index;