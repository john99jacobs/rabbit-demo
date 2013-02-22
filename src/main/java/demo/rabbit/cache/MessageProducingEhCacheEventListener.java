package demo.rabbit.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import demo.rabbit.message.CacheMessageProducer;
import demo.rabbit.message.CacheMessageProducerImpl;
import demo.rabbit.util.ApplicationContextUtils;

/**
 * <p>An Ehcache cache listener that produces messages when removed, updated, expired and evicted
 * events occur on any cache entry. Does not send a message on put, because the intention is to remove
 * stale records from other's caches, not to keep caches 100% synchronized.
 * 
 * <p>Delegates the actual sending of messages to {@link CacheMessageProducerImpl}.
 * 
 * @author jjacobs@credera.com
 * @version 11/9/12
 */
public class MessageProducingEhCacheEventListener extends LoggingEhCacheEventListener {
    private static final Logger logger = Logger.getLogger(MessageProducingEhCacheEventListener.class);

    @Override
    public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
        super.notifyElementRemoved(cache, element);
        sendEvictionMessage(cache, element);
    }

    @Override
    public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
        super.notifyElementUpdated(cache, element);
        sendEvictionMessage(cache, element);
    }

    @Override
    public void notifyElementExpired(Ehcache cache, Element element) {
        super.notifyElementExpired(cache, element);
        sendEvictionMessage(cache, element);
    }

    @Override
    public void notifyElementEvicted(Ehcache cache, Element element) {
        super.notifyElementEvicted(cache, element);
        sendEvictionMessage(cache, element);
    }

    @Override
    public void notifyRemoveAll(Ehcache cache) {
        super.notifyRemoveAll(cache);
        if (CacheMessageListenerContext.isCacheEvictionNotification()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Suppressing remove all message for cacheName: [" + cache.getName() + "]");
            }
            return;
        }
        CacheMessageProducer producer = ApplicationContextUtils.getBean("cacheMessageProducer", CacheMessageProducer.class);
        producer.sendCacheEvictionMessage(cache.getName());
    }
    
    private void sendEvictionMessage(Ehcache cache, Element element) {
        if (CacheMessageListenerContext.isCacheEvictionNotification()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Suppressing cache eviction message for cacheName: [" + cache.getName() + "] key: [" + element.getKey() + "]");
            }
            return;
        }
        CacheMessageProducer producer = ApplicationContextUtils.getBean("cacheMessageProducer", CacheMessageProducer.class);
        producer.sendCacheEvictionMessage(cache.getName(), element.getKey());
    }
}
