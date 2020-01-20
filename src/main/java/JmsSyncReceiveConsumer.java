import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

 class JmsSyncReceiveConsumer implements SubscribeMXBean {

     private void jCons() throws JMSException {
         Connection connection;
         ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                 "tcp://localhost:61615");
         connection = connectionFactory.createConnection();
         Session session = connection.createSession(false,
                 Session.AUTO_ACKNOWLEDGE);
         try {
             Queue queue = session.createQueue("customerQueue");

             // Потребитель
             MessageConsumer consumer = session.createConsumer(queue);
             connection.start();
             while (true) {
                 TextMessage textMsg = (TextMessage) consumer.receive();
                 System.out.println(textMsg);
                 System.out.println("Received: " + textMsg.getText());
                 if (textMsg.getText().equals("END")) {
                     break;
                 }
             }
         } finally {
             if (session != null) {
                 session.close();
             }
             connection.close();
         }

     }
     @Override
     public void QueueConsumer1() throws JMSException {
         jCons();
     }

     @Override
     public void QueueConsumer2() throws JMSException {
         jCons();
     }




 }