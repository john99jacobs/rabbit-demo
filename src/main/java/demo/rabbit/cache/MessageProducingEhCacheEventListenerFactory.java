package demo.rabbit.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * A factory for creating {@link MessageProducingEhCacheEventListener}s. Used to enable 
 * listener configuration in ehcache.xml, not for direct usage.
 * @author jjacobs@credera.com
 */
public class MessageProducingEhCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(final Properties properties) {
		return new MessageProducingEhCacheEventListener();
	}

}
