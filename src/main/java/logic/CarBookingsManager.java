package logic;

import java.util.Date;
import java.util.List;

import model.Car;
import model.CarBooking;
import model.CarType;
import utils.DateUtils;

public class CarBookingsManager {

    public static CarBooking bookACar(CarType carType, Date from, int days) {
        CarBooking carBooking = nextAvailableCar(carType, from, days);
        try {
            CarBookingsRepo.book(carBooking);
        } catch (CarNotBookedException e) {
            System.out.println("Car "+carType+" is not available for specified dates");
            return null;
        }
        return carBooking;
    }

    static CarBooking nextAvailableCar(CarType carType, Date from, int days) {
        List<Integer> carIds = CarManager.carIdsForCarType(carType);
        if (carIds == null || carIds.isEmpty()) {
            throw new UnavailableCarTypeException("Car of type " + carType + " is not available");
        }
        Date to = DateUtils.addDays(from, days);
        for (Integer carId: carIds) {
            Car car = new Car(carId, carType);
            CarBooking carBooking = new CarBooking(car, from, to);
            List<CarBooking> allBookings = CarBookingsRepo.getAllBookings();
            if (!carBooking.overlaps(allBookings)) {
                return carBooking;
            }
        }
        return null;
    }
}
