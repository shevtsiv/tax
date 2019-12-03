package me.shevtsiv.tax.converter;

import me.shevtsiv.tax.proto.dto.Car;
import me.shevtsiv.tax.proto.dto.House;
import me.shevtsiv.tax.proto.dto.Land;
import me.shevtsiv.tax.proto.dto.Property;
import picocli.CommandLine;

public class PropertyParameterConverter implements CommandLine.ITypeConverter<Property> {
    @Override
    public Property convert(String value) throws Exception {
        // Remove parentheses
        String obj = value.substring(1, value.length() - 1);
        String[] objectElements = obj.split(";");
        String className = objectElements[0];
        double price = Double.parseDouble(objectElements[1]);
        switch (className) {
            case "Car": {
                Car.Type carType = Car.Type.valueOf(objectElements[2]);
                return new Car(price, carType);
            }
            case "House": {
                int roomsAmount = Integer.parseInt(objectElements[2]);
                return new House(price, roomsAmount);
            }
            case "Land": {
                double area = Double.parseDouble(objectElements[2]);
                return new Land(price, area);
            }
            default:
                throw new Exception("Unknown domain object class name was supplied!");
        }
    }
}
