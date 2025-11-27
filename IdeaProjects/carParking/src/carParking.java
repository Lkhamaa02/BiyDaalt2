
import java.io.*;
        import java.util.Stack;

class Car {
    String plate;

    Car(String plate) {
        this.plate = plate;
    }
}

public class carParking {

    static final int MAX_SIZE = 10;
    static Stack<Car> garage = new Stack<>();
    static Stack<Car> temp = new Stack<>();

    public static void main(String[] args) {
        input();
    }

    static void input() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("cars.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                process(line.trim());
            }

            br.close();

        } catch (Exception e) {
            System.out.println("File read error: " + e.getMessage());
        }
    }

    static void process(String line) {

        if (line.length() < 2) return;

        char type = line.charAt(0);
        String plate = line.substring(1); // Машины дугаар
        plate = plate.replaceAll("\\s+", "");

        if (type == 'A') {
            arrival(plate);
        } else if (type == 'D') {
            departure(plate);
        }
    }

    static void arrival(String plate) {
        output("Arrival " + plate + " -> ");

        if (garage.size() >= MAX_SIZE) {
            output("Garage full, this car cannot enter.\n");
            return;
        }

        garage.push(new Car(plate));
        output("There is room.\n");
    }

    static void departure(String plate) {
        output("Departure " + plate + " -> ");

        if (garage.isEmpty()) {
            output("This car not in the garage.\n");
            return;
        }

        int moved = 0;
        boolean found = false;

        while (!garage.isEmpty()) {
            Car c = garage.pop();
            if (c.plate.equals(plate)) {
                found = true;
                break;
            }
            temp.push(c);
            moved++;
        }

        if (!found) {

            while (!temp.isEmpty()) garage.push(temp.pop());
            output("This car not in the garage.\n");
            return;
        }

        while (!temp.isEmpty()) {
            garage.push(temp.pop());
        }

        output(moved + " cars moved out.\n");
    }

    static void output(String msg) {
        System.out.print(msg);
    }
}