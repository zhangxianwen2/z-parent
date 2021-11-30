import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @decs: 货币值对象
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 13:39
 **/
public class MonetaryValue {
    /** 数量 */
    private BigDecimal amount;
    /** 货币单位 */
    private CurrencyUnit unit;

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyUnit getUnit() {
        return unit;
    }

    public MonetaryValue(BigDecimal amount, CurrencyUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    /** 货币值对象自带拼接方法，返回一个金额+单位的字符串 */
    public String joint() {
        return this.amount + this.unit.getDesc();
    }
}
