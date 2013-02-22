package demo.rabbit.util;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.concurrent.ThreadSafe;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Use this utility to retrieve a Spring {@link ApplicationContext ApplicationContext}
 * from a non-Spring managed class, among other things.
 * 
 * @author jjacobs@credera.com
 * @version 11/9/12
 */
@ThreadSafe
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

	// holds the application context, which is already inherently thread safe 
	private static ApplicationContext applicationContext;

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
		// this assignment may look dubious but it is safe as this method is only called by spring during
		// initialization; since we're using singleton scope it is guaranteed to be called exactly once
		applicationContext = checkNotNull(ctx, "spring attempted to set null application context");
	}

	/**
	 * Returns a reference to the application context.
	 * @return The application context, which will never be null.
	 */
	public static ApplicationContext getApplicationContext() {
		return checkNotNull(applicationContext, "missing application context");
	}
	
    /**
     * Returns a reference to a spring bean within the current application context.
     * @return The bean, which will never be null.
     */
	public static Object getBean(String beanName) {
	    return checkNotNull(getApplicationContext().getBean(beanName));
	}
	
    /**
     * Returns a reference to a spring bean within the current application context.
     * @return The bean, which will never be null.
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return checkNotNull(getApplicationContext().getBean(beanName, requiredType));
    }
}
