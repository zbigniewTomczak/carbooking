package logic;

import java.util.ArrayList;
import java.util.List;

import model.CarBooking;

public class CarBookingsRepo {
    private static List<CarBooking> bookings = new ArrayList<>();
    //private static Map<Date,List<Integer>> carIdsBookedByDay = new HashMap<>();

    public static synchronized void book(CarBooking carBooking) {
        if (carBooking != null && !carBooking.overlaps(getAllBookings())) {
            bookings.add(carBooking);
            System.out.println("Car " + carBooking.getCar().getId() + " booked!");
        } else {
            throw new CarNotBookedException();
        }
    }

    public static List<CarBooking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}
