import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
 
public class JmsProducerQueueClient {
    public static void main(String[] args) throws  Exception {
        Connection connection = null;
        try {
            // Producer
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61615");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("customerQueue");
            MessageProducer producer = session.createProducer(queue);
            String task = "Task";
            for (int i = 0; i < 10; i++) {
                String payload = task + i;
                Message msg = session.createTextMessage(payload);
                System.out.println("Sending text '" + payload + "'");
                producer.send(msg);
            }
            producer.send(session.createTextMessage("END"));
            Thread.sleep(Long.MAX_VALUE);
            session.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}