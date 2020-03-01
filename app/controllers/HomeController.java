package controllers;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import actors.MyWebSocketActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import play.libs.Json;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private double[][] points;
    private byte[] binPoints;

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    private byte[] pointsToBytes(final double[][] points) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES * points.length * points[0].length);
        for (final double[] point : points) {
            byteBuffer.putDouble(point[0]);
            byteBuffer.putDouble(point[1]);
        }
        return byteBuffer.array();
    }

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer) {
        super();
        Random random = new Random();
        points = new double[1000000][2];
        for (int i = 0; i < points.length; ++i) {
            points[i][0] = random.nextDouble() * 0.08 - 122.123801;
            points[i][1] = random.nextDouble() * 0.08 + 37.893394;
        }
        binPoints = pointsToBytes(points);

        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result dataPoints(int count) {
        return ok(Json.toJson(new HashMap<String, Object>(){
            {
                put("latlons", Arrays.copyOfRange(points, 0, Math.min(count, points.length)));
            }
        }));
    }

    public Result binDataPoints(int count) {
        return ok(Arrays.copyOfRange(binPoints, 0, Math.min(count, points.length) * Double.BYTES * 2));
    }

    public WebSocket ws() {
        return WebSocket.Text.accept(
                request -> ActorFlow.actorRef(MyWebSocketActor::props, actorSystem, materializer)
        );
    }
}
