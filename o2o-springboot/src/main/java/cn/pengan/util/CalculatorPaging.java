package cn.pengan.util;

public class CalculatorPaging {

    /**
     * 通过页索引及页大小计算offset值
     *
     * @param pageIndex 页索引
     * @param pageSize  页大小
     * @return offset 值
     */
    public static int calcRowIndex(int pageIndex, int pageSize) {
        return pageIndex <= 0 ? 0 : (pageIndex - 1) * pageSize;
    }
}
