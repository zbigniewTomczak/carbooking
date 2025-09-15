package model;

import java.util.Date;

import java.util.Date;
import java.util.List;

public class CarBooking {
    private final Car car;
    private final Date from;
    private final Date to;

    public CarBooking(Car car, Date from, Date to) {
        this.car = car;
        this.from = new Date(from.getTime());
        this.to = new Date(to.getTime());
    }

    public Car getCar() {
        return car;
    }

    public Date getFrom() {
        return new Date(from.getTime());
    }

    public Date getTo() {
        return new Date(to.getTime());
    }

    public boolean overlaps(List<CarBooking> allBookings) {
        return allBookings.stream().anyMatch(b -> this.overlaps(b));
    }

    private boolean overlaps(CarBooking b) {
        return this.car.equals(b.car) && !this.to.before(b.from) && !this.from.after(b.to);
    }

}

