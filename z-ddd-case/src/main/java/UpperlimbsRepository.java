/**
 * @decs: 上肢聚合持久化前端，在领域内编写
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/2/7 16:53
 **/
public interface UpperlimbsRepository {

    /** 单个保存 */
    boolean save(Upperlimbs upperlimbs);

    /** 多个保存 */
    boolean saveAll(List<Upperlimbs> upperlimbsList);

    /** 根据唯一标识查询 */
    Upperlimbs upperlimbsById(UpperlimbsId id);

    /** 根据特定条件查询 */
    List<Upperlimbs> upperlimbsByCondition(String name, ...);

    /** 查询满足条件的实例数 */
    int countByCondition(String name, ...);

}
