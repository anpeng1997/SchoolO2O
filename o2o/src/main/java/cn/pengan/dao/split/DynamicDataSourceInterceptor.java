package cn.pengan.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * mybatis的拦截器，用来拦截sql语句，判断是用master库还是slave库
 */
//添加签名
//mybatis会将增删改的操作封装在update中，所以只需要添加update及query两个方法即可
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 拦截方法，遇到什么情况去拦截
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sourceType = null;
        Object[] args = invocation.getArgs();
        //获取statement中执行的sql语句
        MappedStatement ms = (MappedStatement) args[0];
        //判断是否使用了事务
        boolean isTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        if (isTransaction) {
            //如果使用了事务，那么说明是增删改的操作，所用就是用master库
            sourceType = DynamicDataSourceHolder.MASTER_DATASOURCE;
        } else {
            //在事务中有select操作
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                //selectKey 为自增id查询主键（SELECT LAST_INSERT_ID()）方法，则使用master库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    sourceType = DynamicDataSourceHolder.MASTER_DATASOURCE;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
                    //转换成小写，并将所有的制表符、换行符、空格替换掉
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        sourceType = DynamicDataSourceHolder.MASTER_DATASOURCE;
                    } else {
                        sourceType = DynamicDataSourceHolder.SLAVE_DATASOURCE;
                    }
                }
            } else {
                sourceType = DynamicDataSourceHolder.MASTER_DATASOURCE;
            }
        }
        logger.debug("设置方法【{}】use 【{}】 sqlCommanType 【{}】", ms.getId(), sourceType, ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDBType(sourceType);
        return invocation.proceed();
    }

    /**
     * 返回封装好的对象,对象
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        //如果是Executor就返回包装后的结构，否则就返回本体
        //在Mybatis中Executor是用来执行一切增删改查的操作
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
