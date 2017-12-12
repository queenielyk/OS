package OS_SBP;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//implements Runnable
public class Customer extends Thread{

	private static final AtomicInteger idGenerator = new AtomicInteger();

	private final int id;

	private final WaitingRoom waitingRoom;

	private final SynchronousQueue<Boolean> synchronousQueue;

	private volatile boolean shaved;

	public Customer(WaitingRoom waitingRoom) {
		this.id = idGenerator.incrementAndGet();
		this.waitingRoom = waitingRoom;
		this.synchronousQueue = new SynchronousQueue<>();
	}

	@Override
	public void run() {
		try {
			if(waitingRoom.takeASeat(this)) {
			System.out.println("Customer " + this + " arrived the WR and took a seat"); // wait to be called and shaved
			waitToBeCalledAndShaved();
			shaved = true;
			}
			else {
				System.out.println("There are no free seats. Customer " + this+ " has left the barbershop.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void callAndShave() throws InterruptedException {
		synchronousQueue.put(true);
			TimeUnit.SECONDS.sleep(3);
		System.out.println("Customer " + this + " finish cutting");
	}

	public void waitToBeCalledAndShaved() throws InterruptedException {
		synchronousQueue.take();
	}

	public boolean isShaved() {
		return shaved;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
} 