import lombok.Data;

/**
 * @decs: 用户实体
 * @program: xbot-onlinecs-service
 * @author: zhangxianwen
 * @create: 2021/2/7 11:34
 **/
@Data
public class User {
    /** 用户ID 实体唯一标识 */
    private Long userId;
    /** 所属公司ID */
    private Long companyId;
    /** 姓名 */
    private String name;
    /** 年龄 */
    private Integer age;
    /** 地址 */
    private String address;

    public Long companyId() {
        return companyId;
    }

    public String name() {
        return name;
    }

    /** 传入的名字是不是本实体的名字 */
    public boolean verifyName(String name) {
        if (this.name == null) {
            return name == null;
        }
        return this.name.equals(name);
    }

    // 其他方法
}
