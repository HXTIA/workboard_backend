package run.hxtia.workbd.common.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import run.hxtia.workbd.pojo.po.User;

public class TokenListener implements CacheEventListener<Object, Object> {
    @Override
    public void onEvent(CacheEvent cacheEvent) {
        String token = (String) cacheEvent.getKey();

        switch (cacheEvent.getType()) {
            case CREATED: {
                User user = (User) cacheEvent.getNewValue();
                Caches.put(user.getId(), token);
                break;
            }
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
