package demo.rabbit.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;


/**
 * A factory for creating {@link LoggingEhCacheEventListener}s. Used to enable 
 * listener configuration in ehcache.xml, not for direct usage.
 * @author jjacobs@credera.com
 */
public class LoggingEhCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(final Properties properties) {
		return new LoggingEhCacheEventListener();
	}

}
