package com.example.distancecalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import java.util.List;

/**
 * Main application calculating the distance between two Stations in km.
 * Data containing has been provided from https://data.deutschebahn.com/dataset/data-haltestellen.html#
 * @author  Marcel Glab
 * @version 1.0
 * @since 2022-12-10
 */
@SpringBootApplication
@RestController
public class DistanceCalculatorApplication {
    /**
     * @link stations is where each Station is storaged in
     */
    public static List<Station> stations;

    /**
     *
     * initialize spring application, prepare the data from the CSV
     */
    public static void main(String[] args) {

        SpringApplication.run(DistanceCalculatorApplication.class, args);
        CSVReader csvReader = new CSVReader();
        stations = csvReader.readStationsFromCSV("src/D_Bahnhof_2020_alle.csv");
    }

    /**
     * the annotation is used to map web requests to calculate the distance between
     * @param startDS100 - String - The abbreviation of the departure station
     * @param destinationDS100 - String - The abbreviation of the destination station
     * @return JSONObject containing - {
     *  "from": "String",
     *  "to": "String",
     *  "distance": int,
     *  "unit": "km"
     * }
     */
    @RequestMapping(path = "/api/v1/distance/{startDS100}/{destinationDS100}")
    public JSONObject createTripInfoJSON(@PathVariable String startDS100, @PathVariable String destinationDS100) {
        Station depart = null;
        Station arrive = null;
        JSONObject tripInfo = new JSONObject();

        for (Station station : stations) {
            if (station.getDS100().equals(startDS100)){
                depart = station;
            }
            if (station.getDS100().equals(destinationDS100)){
                arrive = station;
            }
        }

        tripInfo.put("from", depart.getName());
        tripInfo.put("to", arrive.getName());
        tripInfo.put("unit: ", "km");
        tripInfo.put("distance", calculateDistanceBetweenCoordinates(depart, arrive));
        return tripInfo;
    }

    /** Calculates the distance between two Station using the haversine formula:
     * The haversine formula determines the great-circle distance between two points on a sphere given their longitudes and latitudes.
     * Important in navigation, it is a special case of a more general formula in spherical trigonometry, the law of haversines, that
     * relates the sides and angles of spherical triangles. -> <a href="https://en.wikipedia.org/wiki/Haversine_formula">harvesine formula</a>
     *
     * @param start - departure Station to get latitude/longitude
     * @param destination - arrival Station to get latitude/longitude
     * @return - result of the calculation as int - unit: km
     */
    public int calculateDistanceBetweenCoordinates(Station start, Station destination){
        int earthRadiusInKm = 6371;
        if (start.getLatitude() != Integer.MAX_VALUE && start.getLongitude() != Integer.MAX_VALUE && destination.getLatitude() != Integer.MAX_VALUE && destination.getLongitude() != Integer.MAX_VALUE) {
            double deltaLatitude = degreesToRadians(destination.getLatitude() - start.getLatitude());
            double deltaLongitude = degreesToRadians(destination.getLongitude() - start.getLongitude());

            double latitudeStart = degreesToRadians(start.getLatitude());
            double latitudeDestination = degreesToRadians(destination.getLatitude());
            double tmp = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) + Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2) * Math.cos(latitudeStart) * Math.cos(latitudeDestination);
            double tmp2 = Math.atan2(Math.sqrt(tmp), Math.sqrt(1 - tmp));
            return (int) Math.round(earthRadiusInKm * tmp2);
        }else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Function to convert degree values in radians
     * @param degrees - value to convert
     * @return returns converted value as double
     */
    public double degreesToRadians(double degrees){
        return degrees*(Math.PI/180D);
    }

}
