package OS_SBP;

public class Barber implements Runnable {
	private final int ID;
	private final WaitingRoom waitingRoom;
	private int flag = 0;

	public Barber(WaitingRoom waitingRoom, int value) {
		this.waitingRoom = waitingRoom;
		ID = value;
	}

	@Override
	public void run() {
		try {
			while (true){
				System.out.println("Barber " + ID + " is sleeping/just finished a shave.");
				Customer customer = waitingRoom.nextCustomer();
				if (flag == 0) {
					System.out.println("Barber " + ID + " was waken by customer " + customer);
					flag = 1;
				} else
					System.out.println("Barber " + ID + " call and shave customer " + customer);
				customer.callAndShave();
				System.out.println("Barber " + ID + " finish cutting for customer " + customer);
				if(waitingRoom.getWaitingCustomers()==0)
					System.out.println("There are no Customers waiting in WR. Barber go to sleep");
				}
	
		} catch (InterruptedException e) {
			System.out.println("Barber " + ID + " has finished his job");
		}
	}
}
