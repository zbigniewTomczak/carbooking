package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CarBooking;
import model.CarType;

public class CarBookingsManagerTest {

    @BeforeEach
    void prepareCars() {
        Map<CarType, List<Integer>> carRepo = new HashMap<>();

        CarType sedan = new CarType("Sedan");
        CarType suv = new CarType("SUV");
        CarType van = new CarType("Van");

        carRepo.put(sedan, List.of(1,2,3));
        carRepo.put(suv,List.of(4));
        carRepo.put(van,List.of(5,6));

        CarManager.register(carRepo);
    }

    private Date getDate() {
        LocalDateTime local = LocalDateTime.of(2025, 9, 10, 10,0);
        return Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test()
    public void nextAvailableCar_unknownCarType_ThrowsException() {
        //given
        CarType sedan = new CarType("Unknown Type");
        Date date  = getDate();
        //when
        Exception exception = assertThrows(UnavailableCarTypeException.class,
                () -> CarBookingsManager.nextAvailableCar(sedan, date, 1));
        //then
        assertEquals("Car of type Unknown Type is not available", exception.getMessage());
    }

    @Test()
    public void nextAvailableCar_sedan_returnSedan() {
        //given
        CarType sedan = new CarType("Sedan");
        Date date  = getDate();
        //when
        CarBooking carBooking = CarBookingsManager.nextAvailableCar(sedan, date, 1);
        //then
        assertEquals(sedan, carBooking.getCar().getCarType());
    }

    @Test()
    public void nextAvailableCar_secondBooking_returnSecondId() {
        //given
        CarType sedan = new CarType("Sedan");
        Date date  = getDate();
        CarBookingsManager.bookACar(sedan, date, 1);
        //when
        CarBooking carBooking = CarBookingsManager.nextAvailableCar(sedan, date, 1);
        //then
        assertEquals(2, carBooking.getCar().getId());
    }

    @Test()
    public void nextAvailableCar_secondBookingSUV_returnNull() {
        //given
        CarType sedan = new CarType("SUV");
        Date date  = getDate();
        CarBookingsManager.bookACar(sedan, date, 1);
        //when
        CarBooking carBooking = CarBookingsManager.nextAvailableCar(sedan, date, 1);
        //then
        assertEquals(null, carBooking);
    }

}