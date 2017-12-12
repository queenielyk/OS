package OS_SBP;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.Scanner;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;

public class Main {
	public static void main(String[] args) throws InterruptedException {

		Scanner input = new Scanner(System.in);

		System.out.println("Barbers: ");
		int iBarber = input.nextInt();

		System.out.println("FREE SEATS in Waiting Room: ");
		int WR = input.nextInt();

		System.out.println("Customers: ");
		int iCustomer = input.nextInt();

		if (WR > iBarber) {
			WaitingRoom waitingRoom = new WaitingRoom(WR);
			// SleepingBarber barberShop = new SleepingBarber(iBarber, WR, iCustomer);
			// barberShop.start();
			input.close();

			ExecutorService executorService = Executors.newFixedThreadPool(100);

			for (int i = 1; i <= iBarber; i++)
				executorService.submit(new Barber(waitingRoom, i));

			/*
			 * List<Customer> customers = Stream.generate(() -> new
			 * Customer(waitingRoom)).limit(iCustomer)
			 * .peek(executorService::submit).collect(toList());
			 */
			TimeUnit.SECONDS.sleep(2);

			List<Customer> customers = new ArrayList<>();
			for (int i = 0; i < iCustomer; i++) {
				Customer nCustomer = new Customer(waitingRoom);
				customers.add(nCustomer);
				customers.get(i).start();
				TimeUnit.SECONDS.sleep(1);
			}

			while (!customers.stream().allMatch(Customer::isShaved)) {
				TimeUnit.SECONDS.sleep(5);
			}

			System.out.println("All customers have been shaved");
			executorService.shutdownNow();
			executorService.awaitTermination(1, MINUTES);
		} else
			System.out.println("Incorrect input, FREE SEATS in Waiting Room must be more than Barbers.");
	}
}
