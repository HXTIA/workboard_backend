package run.hxtia.workbd.common.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import run.hxtia.workbd.pojo.po.User;

/**
 * Token缓存监听器
 */
public class TokenListener implements CacheEventListener<Object, Object> {
    /**
     * 监听事件
     * @param cacheEvent ：缓存事件对象
     */
    @Override
    public void onEvent(CacheEvent cacheEvent) {
        String token = (String) cacheEvent.getKey();

        switch (cacheEvent.getType()) {
            // 添加事件
            case CREATED: {
                if (((String) cacheEvent.getKey()).startsWith("wx:")) return;
                User user = (User) cacheEvent.getNewValue();
                Caches.put(user.getId(), token);
                break;
            }
            // 过期和删除事件
            case EXPIRED:
            case REMOVED: {
                User user = (User) cacheEvent.getOldValue();
                Caches.remove(user.getId());
                break;
            }
            default:
                break;
        }
    }
}
