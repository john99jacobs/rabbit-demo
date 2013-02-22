package demo.rabbit.message;


/**
 * Processes incoming cache-related messages.
 * @author jjacobs@credera.com
 * @version 11/9/12
 */
public interface CacheMessageListener {
	
    public void onMessage(CacheEvictionMessage message);
    
}
