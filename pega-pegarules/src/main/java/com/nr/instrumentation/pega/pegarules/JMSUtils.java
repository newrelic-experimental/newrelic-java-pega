package com.nr.instrumentation.pega.pegarules;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;

public class JMSUtils {

	private static final Pattern NORMALIZE = Pattern.compile("((?<=[\\W_]|^)([0-9a-fA-F\\.\\-]){4,}(?=[\\W_]|$))");
	private static final String CATEGORY = "Message";
	
    public static Message nameTransaction(Message msg) {
        if (msg != null) {
            try {
                Destination dest = msg.getJMSDestination();
                if (dest instanceof Queue) {
                    Queue queue = (Queue) dest;
                    if (queue instanceof TemporaryQueue) {
                        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,
                                false, CATEGORY, "JMS/Queue/Temp");
                    } else {
                        String queueName = normalizeName(queue.getQueueName());
                        NewRelic.getAgent().getLogger().log(Level.FINE,
                            "Normalizing queue name: {0}, {1}. ", queue.getQueueName(), queueName);

                        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,
                                false, CATEGORY, "JMS/Queue/Named", queueName);
                    }
                } else if (dest instanceof Topic) {
                    Topic topic = (Topic) dest;
                    if (topic instanceof TemporaryTopic) {
                        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,
                                false, CATEGORY, "JMS/Topic/Temp");
                    } else {
                        String topicName = normalizeName(topic.getTopicName());
                        NewRelic.getAgent().getLogger().log(Level.FINEST,
                            "Normalizing topic name: {0}, {1}. ",topic.getTopicName(), topicName);

                        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,
                                false, CATEGORY, "JMS/Topic/Named", topicName);
                    }
                } else {
                    NewRelic.getAgent().getLogger().log(Level.FINE,
                            "Error naming JMS transaction: Invalid Message Type.");
                }
            } catch (JMSException e) {
                NewRelic.getAgent().getLogger().log(Level.FINE, e, "Error naming JMS transaction");
            }
        } else {
            // Not a useful transaction.
            NewRelic.getAgent().getTransaction().ignore();
        }
        return msg;
    }

	
    public static void processConsume(Message message, TracedMethod tracer) {
        if (message == null) {
            NewRelic.getAgent().getLogger().log(Level.FINER, "JMS processConsume: message is null");
            return;
        }

        try {
            DestinationType destinationType = getDestinationType(message.getJMSDestination());
            String destinationName = getDestinationName(message.getJMSDestination());
            tracer.reportAsExternal(MessageConsumeParameters
                    .library("JMS")
                    .destinationType(destinationType)
                    .destinationName(destinationName)
                    .inboundHeaders(new InboundWrapper(message))
                    .build());
        } catch (JMSException exception) {
            NewRelic.getAgent().getLogger().log(Level.FINE, exception,
                    "Unable to record metrics for JMS message consume.");
        }
    }	
    
    private static String getDestinationName(Destination destination) throws JMSException {

        if (destination instanceof TemporaryQueue || destination instanceof TemporaryTopic) {
            return "Temp";
        }

        if (destination instanceof Queue) {
            Queue queue = (Queue) destination;
            String queueName = queue.getQueueName();
            return queueName;
        }

        if (destination instanceof Topic) {
            Topic topic = (Topic) destination;
            String topicName = topic.getTopicName();
            return topicName;
        }

        return "Unknown";
    }

    private static DestinationType getDestinationType(Destination destination) {
        if (destination instanceof TemporaryQueue) {
            return DestinationType.TEMP_QUEUE;
        } else if (destination instanceof TemporaryTopic) {
            return DestinationType.TEMP_TOPIC;
        } else if (destination instanceof Queue) {
            return DestinationType.NAMED_QUEUE;
        } else {
            return DestinationType.NAMED_TOPIC;
        }
    }

    protected static String normalizeName(String name) {
        Matcher matcher = NORMALIZE.matcher(name);
        return matcher.replaceAll("#");
    }
}
