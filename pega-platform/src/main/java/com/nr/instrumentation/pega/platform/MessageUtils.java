package com.nr.instrumentation.pega.platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.pega.pegarules.pub.context.PRPulse;
import com.pega.platform.messaging.messagebus.Message;
import com.pega.platform.messaging.messagebus.MessageHandler;
import com.pega.platform.messaging.messagebus.internal.Channel;
import com.pega.platform.remoteexecution.ResponseMessage;
import com.pega.platform.remoteexecution.internal.RequestMessage;
import com.pega.platform.remoteexecution.internal.RequestMessageImpl;

public class MessageUtils {
	
		private static HashMap<NRMessageHandler<?>, MessageHandler<?>> delegates = new HashMap<NRMessageHandler<?>, MessageHandler<?>>();
		
		protected enum TypeOfDestination {
			QUEUE,
			TOPIC,
			UNKNOWN
		}
		
		private static TypeOfDestination getDestinationType(Channel<?> channel) {
			String classname = channel.getClass().getSimpleName();
			if(classname.toLowerCase().contains("queue")) {
				return TypeOfDestination.QUEUE;
			} else if(classname.toLowerCase().contains("topic")) {
				return TypeOfDestination.TOPIC;
			}
			return TypeOfDestination.UNKNOWN;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static MessageHandler<?> getWrapper(MessageHandler<?> msgHandler, Channel<?> channel) {
			if(!(msgHandler instanceof NRMessageHandler)) {
				NRMessageHandler<?> wrapper = new NRMessageHandler(msgHandler,getDestinationType(channel), channel.getName());
				if(!delegates.containsKey(wrapper)) {
					delegates.put(wrapper, msgHandler);
				}
				return wrapper;
			}
			return msgHandler;
		}
		
		public static void removeDelegate(MessageHandler<?> handler) {
			if(handler instanceof NRMessageHandler) {
				delegates.remove(handler);
			} else {
				if(delegates.containsValue(handler)) {
					Set<Entry<NRMessageHandler<?>, MessageHandler<?>>> entries = delegates.entrySet();
					for(Entry<NRMessageHandler<?>, MessageHandler<?>> entry : entries) {
						if(entry.equals(handler)) {
							delegates.remove(entry.getKey());
						}
					}
				}
			}
		}
		
		public static MessageProduceParameters getProduceParameters(Channel<?> channel, Object message) {
			TypeOfDestination type = getDestinationType(channel);
			MessageProduceParameters params = null;
			if(type == TypeOfDestination.QUEUE) {
				params = MessageProduceParameters.library("Pega-MessageBus").destinationType(DestinationType.NAMED_QUEUE).destinationName(channel.getName()).outboundHeaders(null).build();
			} else if(type == TypeOfDestination.TOPIC) {
				params = MessageProduceParameters.library("Pega-MessageBus").destinationType(DestinationType.NAMED_TOPIC).destinationName(channel.getName()).outboundHeaders(null).build();
			}
			return params;
		}
		
		private static void recordRequestMessage(Map<String, Object> attributes, RequestMessage<?,?> message) {
			if(message != null) {
				Utils.addAttribute(attributes, "RequestMessage-JobClass", message.getJobClass());
				Utils.addAttribute(attributes, "RequestMessage-RequestingNode", message.getRequestingNode());
				Utils.addAttribute(attributes, "RequestMessage-UniqueJobID", message.getUniqueJobID());
			}
		}
		
		private static void recordPulseMessage(Map<String, Object> attributes, PRPulse<?> message) {
			if(message != null) {
				Utils.addAttribute(attributes, "PRPulse-OriginatingNode", message.getOriginatingNode());
			}
		}
		
		private static void recordMessage(Map<String,Object> attributes, Message<?> message) {
			if(message != null) {
				Utils.addAttribute(attributes, "Message-MessageId", message.getMessageId());
			}
		}
		
		private static void recordResponseMessage(Map<String,Object> attributes, ResponseMessage<?> message) {
			if(message != null) {
				Utils.addAttribute(attributes, "ResponseMessage-ExecutingNode", message.getExecutingNode());
				Utils.addAttribute(attributes, "ResponseMessage-CauseOfFailure", message.getCauseOfFailure());
				Utils.addAttribute(attributes, "ResponseMessage-ExecutionID", message.getExecutionID());
				Utils.addAttribute(attributes, "ResponseMessage-UniqueJobID", message.getUniqueJobID());
			}
		}
		
		public static void processInboundMessage(Map<String, Object> attributes, Object message) {
			if(message instanceof RequestMessage) {
				RequestMessage<?,?> requestMsg = (RequestMessage<?,?>)message;
				recordRequestMessage(attributes, requestMsg);
			} else if(message instanceof PRPulse) {
				PRPulse<?> pulseMsg = (PRPulse<?>)message;
				recordPulseMessage(attributes, pulseMsg);
			} else if(message instanceof Message) {
				Message<?> msg = (Message<?>)message;
				recordMessage(attributes,msg);
			} else if(message instanceof ResponseMessage) {
				ResponseMessage<?> responseMsg = (ResponseMessage<?>)message;
				recordResponseMessage(attributes, responseMsg);
			}
			
		}
		
		public static void processOutboundMessage(Map<String, Object> attributes, Object message) {
			if(message instanceof RequestMessageImpl) {
				RequestMessageImpl<?,?> requestMsg = (RequestMessageImpl<?,?>)message;
				recordRequestMessage(attributes, requestMsg);
			} else if(message instanceof PRPulse) {
				PRPulse<?> pulseMsg = (PRPulse<?>)message;
				recordPulseMessage(attributes, pulseMsg);
			} else if(message instanceof Message) {
				Message<?> msg = (Message<?>)message;
				recordMessage(attributes,msg);
			} else if(message instanceof ResponseMessage) {
				ResponseMessage<?> responseMsg = (ResponseMessage<?>)message;
				recordResponseMessage(attributes, responseMsg);
			}
			
		}

}
