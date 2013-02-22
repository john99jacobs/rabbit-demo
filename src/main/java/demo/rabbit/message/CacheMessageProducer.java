package demo.rabbit.message;

import java.io.Serializable;

/**
 * Sends cache related messages to the RabbitMQ exchange where they are processed by
 * interested parties, usually another BLC application instance with it's own local cache. 
 * @author jjacobs@credera.com
 * @version 11/9/12
 *
 */
public interface CacheMessageProducer {
	
	/**
	 * Sends a <code>CacheEvictionMessage</code> to the message broker for the specified cacheName and key.
	 * @param cacheName Name of the cache containing the element to evict
	 * @param key The key for the element to evict
	 * @param serverName This server's name
	 * @param appName This application's name
	 */
	public void sendCacheEvictionMessage(String cacheName, Serializable key);

    /**
     * Sends a <code>CacheEvictionMessage</code> to the message broker for the specified cacheName. Indicates
     * that all entries in the specified cache should be cleared.
     * @param cacheName Name of the cache to clear
     * @param serverName This server's name
     * @param appName This application's name
     */
	public void sendCacheEvictionMessage(String cacheName);
}
