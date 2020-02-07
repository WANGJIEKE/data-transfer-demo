package controllers;

import java.util.HashMap;
import java.util.Random;

import play.mvc.*;
import play.libs.Json;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

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
        double[][] points = new double[count][2];
        Random random = new Random();
        for (int i = 0; i < points.length; ++i) {
            points[i][0] = random.nextDouble() - 0.5 + 51.505;
            points[i][1] = random.nextDouble() - 0.5 - 0.09;
        }

        return ok(Json.toJson(new HashMap<String, Object>(){
            {
                put("latlons", points);
            }
        }));
    }
}
