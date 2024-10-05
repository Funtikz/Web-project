package org.example.magazinexampleproject.controllers;

import lombok.RequiredArgsConstructor;
import org.example.magazinexampleproject.dto.ClothingDto;
import org.example.magazinexampleproject.service.ClothingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClothingController {
    private ClothingService clothingService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ClothingDto>> findAll(){
        return new ResponseEntity<>(clothingService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ClothingDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(clothingService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ClothingDto> createClothing(@Validated @RequestBody ClothingDto clothingDto){
        return new ResponseEntity<>(clothingService.save(clothingDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClothing(@PathVariable("id") Long id){
        clothingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/increase-quantity/{id}/{quantity}")
    public ResponseEntity<ClothingDto> increaseQuantity(@PathVariable("quantity") int quantity,
                                             @PathVariable("id") Long id){
        return new ResponseEntity<>(clothingService.increaseQuantity(id,quantity), HttpStatus.OK);
    }
}
