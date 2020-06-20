# SchoolO2O
校园商铺(javaweb_ssm)

## 结构

### `o2o-react-app`

> 前端：react，antdesign-mobile

### `o2o`

> 后端：SSM

### `o2o-springboot`

> springboot,mybatis,redis



## start

1. `o2o-react-app`

    1. 在`o2o-react-app\src\api`中配置后端url

        ```
        baseURL: "http://localhost:8080", //后端url及端口
        ```

    2. 在根目录运行npm命令

        ```powershell
        npm install

        npm start
        ```
    
    3. 访问：http://localhost:3000/

2. `o2o-springboot`

    1. 创建表，运行`myo2odb.sql`中的语句

    2. 配置application.yml中连接字符串等，在启动springboot应用