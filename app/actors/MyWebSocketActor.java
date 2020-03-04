package actors;

import akka.actor.*;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class MyWebSocketActor extends AbstractActor {
    private final ActorRef out;
    private static final byte[] binPoints;

    static {
        Random random = new Random();
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES * 2 * 5000000);
        for (int i = 0; i < 2 * 5000000; i += 2) {
            byteBuffer.putDouble(random.nextDouble() * 0.08 - 122.123801);
            byteBuffer.putDouble(random.nextDouble() * 0.08 + 37.893394);
        }
        binPoints = byteBuffer.array();
    }

    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }

    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }

    private ByteString getBinPointsByCountMsg(ByteString msg) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.toArray());
        int count = byteBuffer.getInt();
        byte[] points = Arrays.copyOfRange(binPoints, 0, Math.min(count, 2 * 5000000) * Double.BYTES * 2);
        return new ByteStringBuilder().putBytes(points).result();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ByteString.class, message -> out.tell(getBinPointsByCountMsg(message), self()))
                .build();
    }
}
