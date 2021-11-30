/**
 * @decs: 贫血模型
 * @program: xbot-onlinecs-service
 * @author: zhangxianwen
 * @create: 2021/2/7 11:34
 **/
public class UserAnemia {
    /** 姓名 */
    private String name;
    /** 年龄 */
    private Integer age;
    /** 地址 */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
