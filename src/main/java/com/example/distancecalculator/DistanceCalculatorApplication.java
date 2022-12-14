package com.example.distancecalculator;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Main application calculating the distance between two Stations in km.
 * Data containing has been provided from <a href="https://data.deutschebahn.com/dataset/data-haltestellen.html#">Deutsche Bahn </a>
 *
 * @author Marcel Glab
 * @version 1.0
 * @since 2022-12-10
 */
@SpringBootApplication
@RestController
public class DistanceCalculatorApplication {
    public static final int EARTH_RADIUS_IN_KM = 6371;
    /**
     * @link stations is where each Station is storaged in
     */
    public static Map<String, Station> stations;

    /**
     * initialize spring application, prepare the data from the CSV
     */
    public static void main(String[] args) {

        SpringApplication.run(DistanceCalculatorApplication.class, args);
        CSVReader csvReader = new CSVReader();
        stations = csvReader.readStationsFromCSV("src/D_Bahnhof_2020_alle.csv");
    }

    /**
     * the annotation is used to map web requests to calculate the distance between
     *
     * @param startDS100       - String - The abbreviation of the departure station
     * @param destinationDS100 - String - The abbreviation of the destination station
     * @return JSONObject containing - {
     * "from": "String",
     * "to": "String",
     * "distance": int,
     * "unit": "km"
     * }
     */
    @RequestMapping(path = "/api/v1/distance/{startDS100}/{destinationDS100}")
    public JSONObject createTripInfoJSON(@PathVariable String startDS100, @PathVariable String destinationDS100) {

        // Look up the depart and arrive stations using their DS100 codes
        Station depart = stations.get(startDS100);
        Station arrive = stations.get(destinationDS100);
        // Create a JSON object to store the trip information
        JSONObject tripInfo = new JSONObject();
        // Add the depart and arrive station names to the JSON object
        tripInfo.put("from", depart.getName());
        tripInfo.put("to", arrive.getName());
        // Add the distance between the depart and arrive stations to the JSON object
        tripInfo.put("distance", calculateDistanceBetweenCoordinates(depart, arrive));
        // Add the unit of distance (e.g. km) to the JSON object
        tripInfo.put("unit: ", "km");
        // Return the JSON object
        return tripInfo;
    }

    /**
     * Calculates the distance between two Station using the haversine formula:
     * The haversine formula determines the great-circle distance between two points on a sphere given their longitudes and latitudes.
     * Important in navigation, it is a special case of a more general formula in spherical trigonometry, the law of haversines, that
     * relates the sides and angles of spherical triangles. -> <a href="https://en.wikipedia.org/wiki/Haversine_formula">harvesine formula</a>
     *
     * @param start       - departure Station to get latitude/longitude
     * @param destination - arrival Station to get latitude/longitude
     * @return - result of the calculation as int - unit: km
     */
    public int calculateDistanceBetweenCoordinates(Station start, Station destination) {
        //Check if both stations are not null
        if (start != null && destination != null) {
            // Check if the start or destination station are null or if their latitude or longitude are null
            if (start.getLatitude() == null || start.getLongitude() == null || destination.getLatitude() == null || destination.getLongitude() == null) {
                return 0;
            }
            // Calculate the difference in latitude and longitude between the start and destination stations
            double deltaLatitude = Math.toRadians(destination.getLatitude() - start.getLatitude());
            double deltaLongitude = Math.toRadians(destination.getLongitude() - start.getLongitude());

            // Convert the latitude of the start and destination stations to radians
            double latitudeStart = Math.toRadians(start.getLatitude());
            double latitudeDestination = Math.toRadians(destination.getLatitude());

            // Calculate the distance between the two stations using the Haversine formula
            double tmp = Math.pow(Math.sin(deltaLatitude / 2), 2) + Math.pow(Math.sin(deltaLongitude / 2), 2) * Math.cos(latitudeStart) * Math.cos(latitudeDestination);
            double tmp2 = Math.atan2(Math.sqrt(tmp), Math.sqrt(1 - tmp));

            return (int) Math.round(EARTH_RADIUS_IN_KM * tmp2);
        } else {
            //If either start or destination was null return 0
            return 0;
        }
    }


}
