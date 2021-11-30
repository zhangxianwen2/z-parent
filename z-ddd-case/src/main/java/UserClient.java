import javax.annotation.Resource;

/**
 * @decs: 客户端
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 14:36
 **/
public class UserClient {

    @Resource
    private UserDomainService userDomainService;

    public boolean verifyName(Long userId, String name) {
        return userDomainService.verifyName(userId,name);
    }
}
