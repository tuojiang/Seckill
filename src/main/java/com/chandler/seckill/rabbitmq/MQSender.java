package com.chandler.seckill.rabbitmq;

import com.chandler.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date: 18-12-4
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

	public void sendMiaoshaMessage(MiaoshaMessage mm) {
		String msg = RedisService.beanToString(mm);
		log.info("send message :"+msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
	}

//    public void send(Object message) {
//		String msg =  RedisService.beanToString(message);
//		log.info("send message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//	}
//
//	public void sendTopic(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send topic message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.queue1", msg+"1");
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
//	}
//
//	public void sendFanout(Object message){
//    	String msg = RedisService.beanToString(message);
//    	log.info("send fanout message :" + msg);
//    	amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg);
//	}
//
//	public void senHeader(Object message){
//    	String msg = RedisService.beanToString(message);
//    	log.info("send fanout message:"+msg);
//		MessageProperties properties = new MessageProperties();
//		properties.setHeader("header1","values1");
//		properties.setHeader("header2","values2");
//		Message obj = new Message(msg.getBytes(),properties);
//		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
//	}


}
