/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 14:11
 **/
public enum CurrencyUnit {
    //
    CENT(1, "分"),
    DIME(2, "角"),
    DOLLAR(3, "元"),
    ;

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    CurrencyUnit(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
