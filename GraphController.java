package accolite.GraphData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @PostMapping("/nodes")
    public ResponseEntity<?> addNode(@RequestBody NodeRequest nodeRequest) {
        try {
            return ResponseEntity.ok(graphService.addNode(nodeRequest.getName(), nodeRequest.getParentId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> getNode(@PathVariable String id) {
        try {
            return ResponseEntity.ok(graphService.getNode(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/path")
    public ResponseEntity<?> getPath(@RequestParam String start, @RequestParam String end) {
        try {
            return ResponseEntity.ok(graphService.getPath(start, end));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/depth")
    public ResponseEntity<?> getDepth(@RequestParam String idOrName) {
        try {
            return ResponseEntity.ok(graphService.getDepth(idOrName));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/relationships")
    public ResponseEntity<?> addRelationship(@RequestBody RelationshipRequest relationshipRequest) {
        try {
            graphService.addRelationship(relationshipRequest.getParentId(), relationshipRequest.getChildId());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/relationships")
    public ResponseEntity<?> getRelationshipDetails(@RequestParam String parentId, @RequestParam String childId) {
        try {
            return ResponseEntity.ok(graphService.getRelationshipDetails(parentId, childId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
