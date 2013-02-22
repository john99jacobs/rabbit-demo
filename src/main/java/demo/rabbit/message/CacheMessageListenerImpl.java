package demo.rabbit.message;

import static com.google.common.base.Preconditions.checkNotNull;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import demo.rabbit.cache.CacheMessageListenerContext;

/**
 * Processes incoming cache-related messages from the RabbitMQ exchange.
 * @author jjacobs@credera.com
 * @version 11/9/12
 */
@Component("cacheMessageListener")
public class CacheMessageListenerImpl implements CacheMessageListener{

    private static final Logger LOG = Logger.getLogger(CacheMessageListenerImpl.class);

    @Value("${tomcat.server.name}")
    private String serverName;

    @Value("#{@appName}")
    private String appName;

    @Override
    public void onMessage(CacheEvictionMessage message) {
        checkNotNull(message, "null message received");
        CacheMessageListenerContext.setEventListenerNotification(true);
        if (serverName.equals(message.getServerName()) && appName.equals(message.getAppName())) {
            if(LOG.isDebugEnabled()) {
                LOG.debug("[" + appName + "] Ignoring cache eviction message sent from [" + serverName + "] [" + appName + "] instance for key: [" + message.getKey() + "], cache: [" + message.getCacheName() + "]");
            }
        } else {
            if(LOG.isDebugEnabled()) {
                LOG.debug("[" + appName + "] Cache eviction message received from server: [" + message.getServerName() + "], application: [" + message.getAppName() + "]");
            }
            Ehcache cache = CacheManager.getInstance().getEhcache(message.getCacheName());
            if (cache == null) {
                LOG.warn("[" + appName + "] No Ehcache found for cacheName: [" + message.getCacheName() + "]");
                return;
            }
            if(message.isEvictAll()) {
                cache.removeAll();
                if(LOG.isDebugEnabled()) {
                    LOG.debug("[" + appName + "] All elements removed from cache: [" + message.getCacheName() + "]");
                }
            } else {
                boolean result = cache.removeQuiet(message.getKey());
                if (LOG.isDebugEnabled()) {
                    if (result) {
                        LOG.debug("[" + appName + "] Element with key: [" + message.getKey() + "] removed from cache: [" + message.getCacheName() + "]");
                    } else {
                        LOG.debug("[" + appName + "] Element with key: [" + message.getKey() + "] not found in cache: [" + message.getCacheName() + "]");
                    }
                }
            }
        }
        CacheMessageListenerContext.clear();
    }
}
