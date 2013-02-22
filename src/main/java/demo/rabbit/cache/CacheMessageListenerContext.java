package demo.rabbit.cache;

/**
 * A class that holds information about the cache listeners for the current thread.
 * @author jjacobs@credera.com
 * @version 11/12/12
 *
 */
public class CacheMessageListenerContext {

    private static final ThreadLocal<Boolean> holder = new ThreadLocal<Boolean>();
    
    /**
     * Is the current thread of execution the result of a cache eviction notification message?
     * Use to prevent sending superfluous eviction messages to the message exchange.
     * @return boolean
     */
    public static boolean isCacheEvictionNotification() {
        return holder.get() == null ? false : holder.get();
    }
    
    /**
     * Sets the cache eviction notification indicator bound to the current thread.  
     * @param value Is the current thread the result of an Ehcache event listener notification?
     */
    public static void setEventListenerNotification(boolean value) {
        holder.set(value);
    }
    
    /**
     * Removes the event listener notification indicator from the current thread.
     */
    public static void clear() {
        holder.remove();
    }
}
