package teams.xianlin.com.teamshit.Utils;

import java.util.ArrayList;
import java.util.List;

import teams.xianlin.com.teamshit.Bean.UserBean;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ListUtils {
    public static ListUtils getInstance() {
        return createListUtils.listUtils;
    }

    public static class createListUtils {
        private final static ListUtils listUtils = new ListUtils();
    }

    /**
     * 根据对象里面的获取对象里面的头像然后在组成一个头像
     *
     * @param userBeanList
     * @return
     */
    public List<String> getListStrForListObject(List<UserBean> userBeanList) {
        List lists = null;
        if (userBeanList != null && userBeanList.size() > 0) {
            lists = new ArrayList();
            for (UserBean userBean : userBeanList
                    ) {
                lists.add(userBean.getPortraitUri());
            }
            return lists;
        }
        return lists;
    }
}
