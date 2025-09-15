package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CarType;

public class CarManager {
    private static Map<CarType, List<Integer>> carRepo = new HashMap<>();

//    static {
//        Map<CarType, List<Integer>> carRepo = new HashMap<>();
//
//        CarType sedan = new CarType("Sedan");
//        CarType suv = new CarType("SUV");
//        CarType van = new CarType("Van");
//
//        carRepo.put(sedan, List.of(1,2,3));
//        carRepo.put(suv,List.of(4));
//        carRepo.put(van,List.of(5,6));
//
//        CarManager.register(carRepo);
//
//    }

    public static void register(Map<CarType, List<Integer>> repo) {
        carRepo = repo;
    }

    public static List<Integer> carIdsForCarType(CarType carType) {
        return carRepo.get(carType);
    }
}
