package web.serviсe;

import org.springframework.stereotype.Service;
import web.model.Car;

import java.util.List;

@Service
public class CarServiceImp implements CarService {
    List<Car> cars = List.of(new Car("lada2107", 2003, false),
            new Car("tesla", 2020, true),
            new Car("TinyChair", 2034, true),
            new Car("TurboTaz", 2023, true),
            new Car("OpelAstra", 2004, true));

    @Override
    public List<Car> getAllCars(int count) {
        if (count >= 5) {
            return cars;

        } else if (count < 0) {
            throw new IllegalArgumentException("Ошибка count <1");
        } else {
            return cars.stream().limit(count).toList();
        }


    }
}
