package topic;

import Listener.ConsumerMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;

import javax.jms.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class JmsTopicMessageListenerExample {
    private final static Logger logger = Logger.getLogger(JmsTopicMessageListenerExample.class);

    public static void main(String[] args) throws  Exception {


        BrokerService broker = BrokerFactory.createBroker(new URI(
                "broker:(tcp://localhost:61615)"));
        broker.start();
        Connection clientConnection = null;
        try {
            // Производитель
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61615");
            clientConnection = connectionFactory.createConnection();
            Session session = clientConnection.createSession(false,
                    Session.CLIENT_ACKNOWLEDGE);
            Topic topic = session.createTemporaryTopic();
            // Consumer1 подписывается на customerTopic
            MessageConsumer consumer1 = session.createConsumer(topic);
            consumer1.setMessageListener(new ConsumerMessageListener("Самат"));

            // Consumer2 подписывается на  customerTopic
            MessageConsumer consumer2 = session.createConsumer(topic);
            consumer2.setMessageListener(new ConsumerMessageListener("Нияз"));

            clientConnection.start();

            // Публикация
            Scanner scanner = new Scanner(System.in);
            String payload = scanner.nextLine();
            Message msg = session.createTextMessage(payload);
            MessageProducer producer = session.createProducer(topic);
            System.out.println("Отправка сообщения '" + payload + "'");
            producer.send(msg);

            Thread.sleep(3000);


        } catch (Exception ex) {
            logger.error("Ошибка", ex);

        } finally {
            if (clientConnection != null) {
                clientConnection.close();

            }
            broker.stop();
//            broker.start();
        }

    }
}