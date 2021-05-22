package yong.booternetes.cafe.points;

import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points/{username}")
@RequiredArgsConstructor
public class PointsRestController {

    public final PointsService service;

    @GetMapping
    ResponseEntity<Map<String, Object>> read(@PathVariable String username) {
        var points = service.pointsFor(username);
        if (!points.isEmpty()) {
            return ResponseEntity.of(Optional.of(points));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    Map<String, Object> writes(@PathVariable String username, @RequestBody Map<String, Object> payload) {
        return service.addPoints(username, (int) payload.get("points"));
    }

}
