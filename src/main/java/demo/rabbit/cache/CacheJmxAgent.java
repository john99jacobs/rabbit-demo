package demo.rabbit.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;


/**
 * Exposes Ehcache cache statistics and configuration info via JMX. To use simply configure 
 * as a bean in your Spring application context and provide a reference to an {@link MBeanServer}.
 * 
 * @author jjacobs@credera.com
 *
 */
public class CacheJmxAgent {

    private MBeanServer mbeanServer;

    private ObjectName objectName;
    
	@PostConstruct
    public void start() throws Exception {
        CacheManager cacheMgr = CacheManager.getInstance();
        ManagementService.registerMBeans(cacheMgr, mbeanServer, true, true, true, true);
    }

    @PreDestroy
    public void stop() throws Exception {
        mbeanServer.unregisterMBean(objectName);

    }

	public void setMbeanServer(MBeanServer mbeanServer) {
		this.mbeanServer = mbeanServer;
	}
}
