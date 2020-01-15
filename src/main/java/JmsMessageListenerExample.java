import Listener.ConsumerMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import javax.jms.*;
import java.net.URI;

public class JmsMessageListenerExample {
    final static Logger logger = Logger.getLogger(JmsMessageListenerExample.class);

    public static void main(String[] args) throws Exception {
        BrokerService bs = BrokerFactory.createBroker(new URI("broker:(tcp://localhost:61616)"));
        bs.start();
        Connection connection = null;
        try {
            //Производитель
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("customerQueue");
            String payload = "Important Task";
            Message message = session.createTextMessage(payload);
            MessageProducer messageProducer = session.createProducer(queue);
            System.out.println("Sending text " + payload);
            messageProducer.send(message);
            //Потребитель
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new ConsumerMessageListener("Самат"));
            connection.start();
            Thread.sleep(2000);
            session.close();
        } catch (Exception ex) {
            logger.error("Ошибка", ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        bs.stop();
    }
}
