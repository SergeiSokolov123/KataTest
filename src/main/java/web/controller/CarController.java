package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Car;
import web.serviсe.CarService;
import web.serviсe.CarServiceImp;

import java.util.List;


@Controller
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("/cars")
    public String cars(@RequestParam(required = false, defaultValue = "5") int count, Model model) {
        List<Car> cars = carService.getAllCars(count);
        model.addAttribute("cars", cars);

        return "cars";
    }
}
