package model;

import java.util.Objects;

public class CarType {
    private final String name;

    public CarType(String name) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("model.Car type cannot be null");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override public String toString() {
        return getName();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarType carType = (CarType) o;
        return getName().equals(carType.getName());
    }

    @Override public int hashCode() {
        return Objects.hash(getName());
    }
}
