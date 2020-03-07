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

## `prompt`

* 在离开本页面时，发出提示信息

```js
<Prompt
  when={formIsHalfFilledOut}
  message="Are you sure you want to leave?"
/>
```
