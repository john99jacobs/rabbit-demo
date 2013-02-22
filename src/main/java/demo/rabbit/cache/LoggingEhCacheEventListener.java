package demo.rabbit.cache;

import static com.google.common.base.Preconditions.checkNotNull;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import org.apache.log4j.Logger;

/**
 * An Ehcache cache listener that logs events.
 * @author jjacobs@credera.com
 */
public class LoggingEhCacheEventListener implements CacheEventListener {
    private static final Logger LOG = Logger.getLogger(LoggingEhCacheEventListener.class);

    /**
     * Constructor.
     */
    public LoggingEhCacheEventListener() {
        if (LOG.isInfoEnabled()) {
            LOG.info("cache listener registered:[" + this + "]");
        }
    }

    @Override
    public void notifyElementRemoved(final Ehcache cache, final Element element) throws CacheException {
        checkArguments(cache, element);
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("element removed for cache:[" + cache.getName() + "], element:[" + element + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void notifyElementPut(final Ehcache cache, final Element element) throws CacheException {
        checkArguments(cache, element);
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("element put for cache:[" + cache.getName() + "], element:[" + element + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void notifyElementUpdated(final Ehcache cache, final Element element) throws CacheException {
        checkArguments(cache, element);
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("element updated for cache:[" + cache.getName() + "], element:[" + element + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void notifyElementExpired(final Ehcache cache, final Element element) {
        checkArguments(cache, element);
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("element expired for cache:[" + cache.getName() + "], element:[" + element + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void notifyElementEvicted(final Ehcache cache, final Element element) {
        checkArguments(cache, element);
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("element evicted for cache:[" + cache.getName() + "], element:[" + element + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void notifyRemoveAll(final Ehcache cache) {
        checkNotNull(cache, "cache event listener called with null cache!");
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("remove all for cache:[" + cache.getName() + "]");
            }
        } catch (final Exception e) {
            logListenerException(e);
        }
    }

    @Override
    public void dispose() {
        // nothing to do
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Logs an exception that occurred while attempting to log an event. Basically just guarantees
     * that it does not throw.
     * @param e The exception
     */
    private void logListenerException(final Exception e) {
        try {
            LOG.error("unable to log event for cache", e);
        } catch (final Exception e1) {
            // nothing to do
        }
    }

    private void checkArguments(Ehcache cache, Element element) {
        checkNotNull(cache, "cache event listener called with null cache!");
        checkNotNull(element, "cache event listener called with null element!");
    }
}
