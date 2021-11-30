/**
 * @decs: 用户信息的领域服务
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 16:33
 **/
public class UserDomainService {

    public boolean verifyName(Long userId, String name) {
        User user = userService.userById(userId);
        // String userName = user.getName();
        // return userName.equals(name);
        Company company = companyService.companyById(user.companyId());
        boolean isInternet = company.internetCompany();
        if (!isInternet) {
            return false;
        }
        return user.verifyName(name);
    }
}
