package web.model;

public class Car {
String model;
int year;
boolean turbo;

    public Car(String model, int year, boolean turbo) {
        this.model = model;
        this.year = year;
        this.turbo = turbo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }
}
