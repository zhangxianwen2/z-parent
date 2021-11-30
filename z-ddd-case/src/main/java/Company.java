/**
 * @decs: 公司实体
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 16:22
 **/
public class Company {
    /** 公司ID */
    private Long companyId;

    /** 公司类型 */
    private String companyType;

    /** 公司实体提供是够属于互联网公司的方法 */
    public boolean internetCompany() {
        return "互联网".equals(companyType);
    }
}
