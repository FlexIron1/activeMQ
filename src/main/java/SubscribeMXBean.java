import javax.jms.JMSException;

public interface SubscribeMXBean {

    void QueueConsumer1() throws JMSException;

    void QueueConsumer2() throws JMSException;

}
