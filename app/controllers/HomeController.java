package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import play.mvc.*;
import play.libs.Json;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private double[][] points = new double[1000000][2];

    public HomeController() {
        super();
        Random random = new Random();
        for (int i = 0; i < points.length; ++i) {
            points[i][0] = random.nextDouble() - 0.8 + 51.505;
            points[i][1] = random.nextDouble() - 0.8 - 0.09;
        }
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }


    public Result dataPoints(int count) {
        return ok(Json.toJson(new HashMap<String, Object>(){
            {
                put("latlons", Arrays.copyOfRange(points, 0, count));  // TODO: possible to avoid copy?
            }
        }));
    }
}
