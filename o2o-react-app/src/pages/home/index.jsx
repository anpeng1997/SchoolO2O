import React from "react";
import { NavBar, Icon, Carousel } from "antd-mobile";
//将css文件作为一个模块引入，这个模块中的css只作用于当前组件。不会影响当前组件的后代组件
//(create-react-app 中内置了使用 CSS Modules 的配置，当前方式就是使用 create-react-app 内置的用法)
import HomeCss from "../../css/home.module.css"

class Index extends React.Component {

    state = {
        data: ['1', '2', '3'],
        imgHeight: 176,
    }
    componentDidMount() {
        // simulate img loading
        setTimeout(() => {
            this.setState({
                data: ['AiyWuByWklrrUDlFignR', 'TekJlZRVCjLFexlOCuWn', 'IJOtIlfsYdTyaDTRVrLI'],
            });
        }, 100);
    }

    render() {
        return <React.Fragment>
            <NavBar
                mode="light"
                icon={<Icon type="left" />}
                onLeftClick={() => console.log('onLeftClick')}
                rightContent={[
                    <Icon key="0" type="search" style={{ marginRight: '16px' }} />,
                    <Icon key="1" type="ellipsis" />,
                ]}
            >O2O</NavBar>
            <Carousel className={HomeCss.spaceCarousel}
                autoplay={true}
                autoplayInterval={3000}
                infinite
                cellSpacing={10}
                slideWidth={0.8}
                beforeChange={(from, to) => console.log(`slide from ${from} to ${to}`)}
                afterChange={index => console.log('slide to', index)}
            >
                {this.state.data.map(val => (
                    <a
                        key={val}
                        href="#"
                        style={{ display: 'inline-block', width: '100%', height: this.state.imgHeight }}
                    >
                        <img
                            src={`https://zos.alipayobjects.com/rmsportal/${val}.png`}
                            alt=""
                            style={{ width: '100%', verticalAlign: 'top' }}
                            onLoad={() => {
                                // fire window resize event to change height
                                window.dispatchEvent(new Event('resize'));
                                this.setState({ imgHeight: 'auto' });
                            }}
                        />
                    </a>
                ))}
            </Carousel>

        </React.Fragment>
    }
}

export default Index;