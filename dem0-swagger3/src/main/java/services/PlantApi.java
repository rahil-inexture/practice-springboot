package services;

import com.spring.practice.demoswagger3.entity.Plant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PlantApi {
    @Operation(
            summary = "Fetch all plants",
            description = "fetches all plant entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<Plant>> getAll();


    @Operation(
            summary = "adds a plant",
            description = "Adds a plant to the list of plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a plnt"),
            @ApiResponse(responseCode = "409", description = "duplicate plant")
    })
    ResponseEntity<Void> addPlant(@RequestBody Plant plant);
}
