# note

## `async` `await`

> 简化了`promise`对象的使用：不在以使用`then()`来指定成功/失败的回调函数，以同步编码的的方式实现了异步流程

### 使用方式

* 在返回`promise`的表达式左侧写`await`：不想要`promise`，想要`promise`异步执行的成功的value数据

* `await`所在函数定义的左侧写`async`

## react-router-dom

### `withRouter`

* 高阶组件，当当前组件没有被路由管理时，可以是用该组件将当前组件传入到路由中进行管理，这时就是可以使用到`this.props.history`

```js
export default withRouter(**);
```

### react非组件处理路由跳转

>react组件中的跳转有Redirect, Link, withRouter高阶组件包裹跳转等等,但是对于非组件中,如请求接口统一封装调用后的判断跳转, 这时需要创建history对象,调用对应方法跳转, 具体如下:

```npm
npm i history --save
```

```jsx
import { createHashHistory } from 'history'; // hash路由
// 或者
import { createBrowserHistory } from 'history'; // history路由
const history = createHashHistory();
history.push('/');
```

### `prompt`

* 在离开本页面时，发出提示信息

```js
<Prompt
  when={formIsHalfFilledOut}
  message="Are you sure you want to leave?"
/>
```

## redux&redux-react

> 用来集中管理当前应用的所有state

## redux-thunk

>中间键，让我们可以在action中执行异步操作（发起ajax请求等）。

```js
import thunk from "redux-thunk"
import { createStore, applyMiddleware } from 'redux';

//在创建store时，传递给applyMiddleware
const store = createStore(reducer, applyMiddleware(thunk));
```

### 使用

1. 使用createStore创建一个store,再使用Provider提供给整个应用使用

    ```js
    import React from 'react';
    import ReactDOM from 'react-dom';
    import App from './App';
    import 'antd-mobile/dist/antd-mobile.css';
    import { createStore, applyMiddleware } from 'redux';
    import { Provider } from 'react-redux';
    import reducer from "./pages/shop/store/reducer";
    import thunk from "redux-thunk"

    const store = createStore(reducer, applyMiddleware(thunk));

    ReactDOM.render(<Provider store={store}>
        <App />
    </Provider>,
        document.getElementById('root'));
    ```
  
2. 创建action，将action传递给reducer

    ```js
    import { reqShopList } from "../../../api";

    const createShopListAction = (data) => {
        return {
            type: 'GET_SHOP_LIST',
            data
        }
    }

    //在这个action中之所以能做复杂的逻辑，是因为我们配置了redux-thunk中间键
    export const getShopListAction = () => {
        return async (dispatch) => {
            const shopList = await reqShopList();
            dispatch(createShopListAction(shopList));
        }
    }
    ```

3. 创建reducer用来根据action来进行相对应的逻辑操作

    ```js
      const defaultState = {
        shopData: {}
        }

      export default (state = defaultState, action) => {
          switch (action.type) {
              case 'GET_SHOP_LIST':
                  return state.shopData = action.data;
              default:
                  return state;
          }
      }
    ```

4. 整合所有的reducer

    >将各个页面的所有reducer通过combineReducers来整合在一起，在后面的mapStatsToProps中通过点的方式指定要使用那个reducer

    ```js
    import { combineReducers } from "redux";
    import shopReducer from "../pages/shop/store/reducer";

    //将所有的reducer整合在一起
    const rootReducers = combineReducers({
        shop: shopReducer
    });

    export default rootReducers;
    ```

    ```js
    //在调用时指定要使用的时shopReducer
    const mapStateToProps = (state) => {
      return {
          shopData: state.shop
      }
    }
    ```

5. `mapStateToProps&mapDispatchToProps`

    >将store中数据映射在props中，还有映射dispatch action

    ```jsx
      import { bindActionCreators } from "redux";

      async componentDidMount() {
          Toast.loading("正在加载....", 0);
          //在mapDispatchToProps中使用了bindActionCreators,所以是调用props.actions
          //在action中，请求api时，函数是异步的
          await this.props.actions.getShopListAction();
          Toast.hide();
      }

      const mapStateToProps = (state) => {
      return {
          shopData: state.shop
        }
      }

      const mapDispatchToProps = (dispatch) => {
          //使用bindActionCreators绑定所有的actions
          return {
              actions: bindActionCreators(shopActions, dispatch)
          }
      }
    ```

6. 配置chrome Redux DevTools

    ```js
    //该js将创建store代码拆分出来，./store/index.jsx
    import { createStore, compose, applyMiddleware } from 'redux';
    import thunk from 'redux-thunk';
    import reducer from './reducer';

    const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

    const store = createStore(reducer,
        composeEnhancers(applyMiddleware(thunk)));

    export default store;
    ```

## `immutable.js`

> npm install immutable  
npm install redux-immutable

### 作用

>immutable对象是不可直接赋值的对象，它可以有效的避免错误赋值的问题  
常用API <https://www.cnblogs.com/chris-oil/p/8494337.html>

### 使用步骤

1. 在rootReducer中原本使用`import { combineReducers } from "redux";`，替换成`import { combineReducers } from "redux-immutable";`

2. 在reducer中使用fromJS对state进行包装

    ```jsx
    import { GETSHOPLIST, GETPRODUCTCATEGORY } from "./actionType";
    import { fromJS } from "immutable";

    const defaultState = fromJS({
        shopList: [],
        productCategoryList: []
    })

    export default (state = defaultState, action) => {
        switch (action.type) {
            case GETSHOPLIST:
                //set state时，也需要使用fromJS包装传递过来的数据
                return state.set("shopList", fromJS(action.data));
            case GETPRODUCTCATEGORY:
                return state.set("productCategoryList", fromJS(action.data))
            default:
                return state;
        }
    }
    ```

3. 在组件中的mapStateToProps中,使用`get()` or `getIn()`获得state

    ```jsx
    const mapStateToProps = (state) => {
    return {
        //getIn用来获得多层级的state
        productCategoryList: state.getIn(["shop","productCategoryList"])
        }
    }
    ```

4. 使用数据时，需要使用`toJS()`,将immutable对象转换成普通的js对象来使用

    ```jsx
    const shopList = this.props.shopList.toJS();
    ```

* `state.merge`设置多个属性

## react中的模块样式——module.css

### 说明

> create-react-app脚手架中内置了使用 CSS Modules 的配置,将css文件作为一个模块引入，这个模块中的css只作用于当前组件。不会影响当前组件的后代组件

### 使用

>将所有的 .css/.lee/.scss 等样式文件都修改成 .module.css/.module.less/.module.scss 等。即可使用 CSS Modules 的方式进行引入使用了。

 ```css
    .spaceCarousel {
    padding: 16px;
    background: #DEF1E5;
    overflow: hidden;
    }
 ```

 ```jsx
 import HomeCss from "../../css/home.module.css"

  <Carousel className={HomeCss.spaceCarousel}>
  </Carousel>
 ```
