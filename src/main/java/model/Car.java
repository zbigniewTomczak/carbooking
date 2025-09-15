package model;

import java.util.Objects;

public class Car {
    private final int id;
    private final CarType carType;

    public Car(int id, CarType carType) {
        this.id = id;
        this.carType = carType;
    }

    public int getId() {
        return id;
    }

    public CarType getCarType() {
        return carType;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Car car = (Car) o;
        return getId() == car.getId() &&
                       Objects.equals(getCarType(), car.getCarType());
    }

    @Override public int hashCode() {
        return Objects.hash(getId(), getCarType());
    }
}
