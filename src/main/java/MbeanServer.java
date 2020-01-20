import javax.management.*;


import java.lang.management.ManagementFactory;

public class MbeanServer {
    public static void main(String[] args) throws MalformedObjectNameException,
            NotCompliantMBeanException, InstanceAlreadyExistsException,
            MBeanRegistrationException, InterruptedException {

        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName(":type=JmsSyncReceiveClientExample");
        JmsSyncReceiveConsumer jsrce = new JmsSyncReceiveConsumer();
        mbeanServer.registerMBean(jsrce, objectName);
        System.out.println("Ожиданиееееее");
        Thread.sleep(Long.MAX_VALUE);
    }
}