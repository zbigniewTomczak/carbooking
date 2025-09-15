package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import model.CarBooking;
import model.CarType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarBookingsConcurrencyTest {

    CarType sedan = new CarType("Sedan");

    @BeforeEach
    void prepareCars() {
        Map<CarType, List<Integer>> carRepo = new HashMap<>();

        carRepo.put(sedan, List.of(1,2,3,4,5,6));

        CarManager.register(carRepo);
    }

    private Date getDate() {
        LocalDateTime local = LocalDateTime.of(2025, 9, 10, 10,0);
        return Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void testSynchronizedIncrement() throws InterruptedException {

        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);

        Runnable task = () -> {

            try {//given
                CarType sedan = new CarType("Sedan");
                Date date = getDate();
                //when
                CarBooking carBooking = null;
                    //retry 10x
                    int i = 0;
                    while (carBooking == null && i < 10) {
                        carBooking = CarBookingsManager.bookACar(sedan, date, 1);
                        i++;
                    }
                //then
                if (carBooking != null) {
                    assertEquals(sedan, carBooking.getCar().getCarType());
                }
            } finally {
                latch.countDown();
            }
        };

        for (int i = 0; i < threadCount; i++) {
            new Thread(task).start();
        }

        latch.await(); // wait for all threads to finish

        assertTrue(CarManager.carIdsForCarType(sedan).size() >= CarBookingsRepo.getAllBookings().size(),
                "Booked cars exceed available cars. System in inconsistent state.");
    }
}
