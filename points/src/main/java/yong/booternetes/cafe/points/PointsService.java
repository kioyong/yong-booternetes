package yong.booternetes.cafe.points;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class PointsService {

    private final Map<String, Integer> db = new ConcurrentHashMap<>();


    Map<String, Object> addPoints(String username, int points) {
        int existing = (int) pointsFor(username).get("points");
        db.put(username, existing + points);
        return pointsFor(username);
    }

    Map<String, Object> pointsFor(String u) {
        return this.points(u, this.db.getOrDefault(u, 0));
    }

    private Map<String, Object> points(String u, int i) {
        return Map.of("username", u, "points", i);
    }

}
