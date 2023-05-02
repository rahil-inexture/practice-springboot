package controller;

import com.spring.practice.demoswagger3.entity.Plant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.PlantApi;
import services.PlantService;

import java.util.List;

@Tag(name = "Plant", description = "the Plant Api")
@RestController
@RequestMapping("/plant/all")
public class PlantController implements PlantApi {
    @Autowired
    private PlantService plantService;


    @Override
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Plant>> getAll() {
        var plants = plantService.getAll();
        return ResponseEntity.ok(plants);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> addPlant(Plant plant) {
        var result = plantService.addPlant(plant);
        if (result == -1)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
