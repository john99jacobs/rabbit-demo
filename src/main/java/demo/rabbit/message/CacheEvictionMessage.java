package demo.rabbit.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CacheEvictionMessage implements Serializable {

	private final String cacheName;
	private final Serializable key;
	private final boolean evictAll;
	private final String serverName;
	private final String appName;
	
    public CacheEvictionMessage(String cacheName, Serializable key, String serverName, String appName) {
        this.cacheName = cacheName;
        this.key = key;
        this.evictAll = false;
        this.serverName = serverName;
        this.appName = appName;
    }

    public CacheEvictionMessage(String cacheName, String serverName, String appName) {
        this.cacheName = cacheName;
        this.key = null;
        evictAll = true;
        this.serverName = serverName;
        this.appName = appName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public Serializable getKey() {
        return key;
    }

    public boolean isEvictAll() {
        return evictAll;
    }

    public String getAppName() {
        return appName;
    }

    public String getServerName() {
        return serverName;
    }

}