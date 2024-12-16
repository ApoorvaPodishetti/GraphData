package accolite.GraphData;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(String id, String name, String parentId) {
        Node node = new Node(id, name, parentId);
        nodes.put(id, node);
    }

    public Node addNode(String name, String parentId) {
        Node node = new Node(name, parentId);
        nodes.put(node.getId(), node);
        return node;
    }

    public void addRelationship(String parentId, String childId) {
        if (nodes.containsKey(parentId) && nodes.containsKey(childId)) {
            nodes.get(childId).setParentId(parentId);
        }
    }

    public Map<String, Object> getRelationshipDetails(String parentId, String childId) {
        if (!nodes.containsKey(parentId) || !nodes.containsKey(childId)) {
            throw new RuntimeException("Data doesn't exist for the given ID.");
        }

        Map<String, Object> relationshipDetails = new HashMap<>();
        Node parentNode = nodes.get(parentId);
        Node childNode = nodes.get(childId);
        if (childNode.getParentId().equals(parentId)) {
            relationshipDetails.put("parent", parentNode);
            relationshipDetails.put("child", childNode);
        }
        return relationshipDetails;
    }

    public Node getNode(String id) {
        Node node = nodes.get(id);
        if (node == null) {
            throw new RuntimeException("Data doesn't exist for the given ID.");
        }
        return node;
    }

    public Node findNodeByName(String name) {
        for (Node node : nodes.values()) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        throw new RuntimeException("Data doesn't exist for the given name.");
    }

    public List<Node> getPath(String start, String end) {
        Node startNode = findNode(start);
        Node endNode = findNode(end);
        if (startNode == null || endNode == null) {
            throw new RuntimeException("Data doesn't exist for the given ID.");
        }
        List<Node> path = new ArrayList<>();
        if (findPath(startNode.getId(), endNode.getId(), path)) {
            return path;
        }
        return Collections.emptyList();
    }

    private boolean findPath(String current, String end, List<Node> path) {
        path.add(nodes.get(current));
        if (current.equals(end)) return true;

        for (Node node : nodes.values()) {
            if (node.getParentId() != null && node.getParentId().equals(current)) {
                if (findPath(node.getId(), end, path)) return true;
            }
        }

        path.remove(nodes.get(current));
        return false;
    }

    public int getDepth(String idOrName) {
        Node node = findNode(idOrName);
        if (node == null) {
            throw new RuntimeException("Data doesn't exist for the given ID or name.");
        }
        String nodeId = node.getId();
        int depth = 0;
        while (nodes.get(nodeId).getParentId() != null) {
            nodeId = nodes.get(nodeId).getParentId();
            depth++;
        }
        return depth;
    }

    private Node findNode(String idOrName) {
        Node node = nodes.get(idOrName);
        if (node == null) {
            node = findNodeByName(idOrName);
        }
        if (node == null) {
            throw new RuntimeException("Data doesn't exist for the given ID or name.");
        }
        return node;
    }

}
