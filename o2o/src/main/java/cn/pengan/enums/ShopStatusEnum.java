package cn.pengan.enums;

/*
 * shop状态的枚举值
 * */
public enum ShopStatusEnum {
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法商铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_SHOP_ID(-1002, "shopId为空"),
    NULL_SHOP_INFO(-1003, "商店信息为空");

    private int state;
    private String stateInfo;

    private ShopStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopStatusEnum statusOf(int index) {
        for (ShopStatusEnum value : values()) {
            if (value.getState() == index) {
                return value;
            }
        }
        return null;
    }
}
