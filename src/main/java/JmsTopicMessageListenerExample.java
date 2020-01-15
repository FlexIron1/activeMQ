import java.net.URI;
import java.net.URISyntaxException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import Listener.ConsumerMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;

public class JmsTopicMessageListenerExample {
    final static Logger logger = Logger.getLogger(JmsTopicMessageListenerExample.class);

    public static void main(String[] args) throws URISyntaxException, Exception {


        BrokerService broker = BrokerFactory.createBroker(new URI(
                "broker:(tcp://localhost:61615)"));
        broker.start();
        Connection clientConnection = null;
        try {
            // Производитель
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61615");
            clientConnection = connectionFactory.createConnection();
            clientConnection.setClientID("TopicTest");
            Session session = clientConnection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);//автоматическое подверждение получения сообщения потребителем.
            Topic topic = session.createTemporaryTopic();

            // Consumer1 подписывается на customerTopic
            MessageConsumer consumer1 = session.createConsumer(topic);
            consumer1.setMessageListener(new ConsumerMessageListener("Самат"));

            // Consumer2 подписывается на  customerTopic
            MessageConsumer consumer2 = session.createConsumer(topic);
            consumer2.setMessageListener(new ConsumerMessageListener("Нияз"));

            clientConnection.start();

            // Публикация
            String payload = "Consumers";
            Message msg = session.createTextMessage(payload);
            MessageProducer producer = session.createProducer(topic);
            System.out.println("Sending text '" + payload + "'");
            producer.send(msg);

            Thread.sleep(3000);
            session.close();

        } catch (Exception ex) {
            logger.error("Ошибка", ex);

        } finally {
            if (clientConnection != null) {
                clientConnection.close();
            }
            broker.stop();
        }

    }
}