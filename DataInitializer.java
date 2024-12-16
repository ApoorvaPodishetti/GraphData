package accolite.GraphData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GraphService graphService;

    @Override
    public void run(String... args) throws Exception {
        graphService.addNode("1", "CEO", null);
        graphService.addNode("2", "CTO", "1");
        graphService.addNode("3", "CFO", "1");
        graphService.addNode("4", "COO", "1");
        graphService.addNode("5", "Engineering Manager", "2");
        graphService.addNode("6", "QA Manager", "2");
        graphService.addNode("7", "Software Engineer 1", "5");
        graphService.addNode("8", "Software Engineer 2", "5");
        graphService.addNode("9", "QA Engineer 1", "6");
        graphService.addNode("10", "QA Engineer 2", "6");
        graphService.addNode("11", "Accountant 1", "3");
        graphService.addNode("12", "Accountant 2", "3");
        graphService.addNode("13", "Operations Manager", "4");
        graphService.addNode("14", "Operations Specialist 1", "13");
        graphService.addNode("15", "Operations Specialist 2", "13");

        graphService.addRelationship("1", "2");
        graphService.addRelationship("1", "3");
        graphService.addRelationship("1", "4");
        graphService.addRelationship("2", "5");
        graphService.addRelationship("2", "6");
        graphService.addRelationship("5", "7");
        graphService.addRelationship("5", "8");
        graphService.addRelationship("6", "9");
        graphService.addRelationship("6", "10");
        graphService.addRelationship("3", "11");
        graphService.addRelationship("3", "12");
        graphService.addRelationship("4", "13");
        graphService.addRelationship("13", "14");
        graphService.addRelationship("13", "15");
    }
}
