package demo.rabbit.message;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Sends cache related messages to the RabbitMQ exchange where they are processed by
 * interested parties, usually another BLC application instance with it's own local cache. 
 * @author jjacobs@credera.com
 * @version 11/9/12
 *
 */
@Component("cacheMessageProducer")
public class CacheMessageProducerImpl implements CacheMessageProducer {
	private static final Logger LOG = Logger.getLogger(CacheMessageProducerImpl.class);
	private static final String routingKey = null; 

	@Resource(name="defaultAmqpTemplate")
	private AmqpTemplate defaultAmqpTemplate;

    @Value("${tomcat.server.name}")
    private String serverName;

    @Value("#{@appName}")
    private String appName;

    @Value("${messaging.rabbitmq.exchange.cache}")
    private String exchange;
    
    @Override
	public void sendCacheEvictionMessage(String cacheName, Serializable key) {
		checkNotNull(cacheName, "cacheName cannot be null");
		checkNotNull(key, "key cannot be null");
		if (LOG.isDebugEnabled()) {
		    LOG.debug("[" + appName + "] Sending cache eviction message for cacheName: [" + cacheName + "] key: [" + key + "]");
		}
		defaultAmqpTemplate.convertAndSend(exchange, routingKey, new CacheEvictionMessage(cacheName, key, serverName, appName), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                return message;
            }
        });
	}
    
    @Override
	public void sendCacheEvictionMessage(String cacheName) {
		checkNotNull(cacheName, "cacheName cannot be null");
		if (LOG.isDebugEnabled()) {
		    LOG.debug("[" + appName + "] Sending evict all message for cacheName: [" + cacheName + "]");
		}
		defaultAmqpTemplate.convertAndSend(exchange, routingKey, new CacheEvictionMessage(cacheName, serverName, appName), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                return message;
            }
        });
	}
}
