package OS_SBP;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


public class WaitingRoom {
	private int qCapacity;

    private final ArrayBlockingQueue<Customer> waitingCustomers;

    public WaitingRoom(int capacity) {
    	//System.out.println("WR capacity is: "+capacity);
        waitingCustomers = new ArrayBlockingQueue<>(capacity);
        qCapacity = new Integer(capacity);
        System.out.println("WR capacity is: "+qCapacity);
    }

    public boolean takeASeat(Customer customer) throws InterruptedException {
//        System.out.println("There are "+waitingCustomers.size()+" customers are waiting in WR");
    	if(waitingCustomers.size() == qCapacity) {
        return false;
    	}
    	else {
    		System.out.println("A customer "+customer+" arrived WR. There are "+(qCapacity-waitingCustomers.size())+" seats left");
            waitingCustomers.put(customer);
    		return true;
    	}
    }

    public Customer nextCustomer() throws InterruptedException {
    	TimeUnit.SECONDS.sleep(2);
        return waitingCustomers.take();
    }
    
    public int getWaitingCustomers() throws InterruptedException {
    	TimeUnit.SECONDS.sleep(2);
        return waitingCustomers.size();
    }
}
