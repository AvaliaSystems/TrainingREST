package io.swagger.api;

import io.swagger.entities.FruitEntity;
import io.swagger.model.Fruit;

import io.swagger.annotations.*;

import io.swagger.repositories.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class FruitsApiController implements FruitsApi {

    @Autowired
    FruitRepository fruitRepository;

    public ResponseEntity<Object> createFruit(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Fruit fruit) {
        FruitEntity newFruitEntity = toFruitEntity(fruit);
        fruitRepository.save(newFruitEntity);
        Long id = newFruitEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFruitEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    public ResponseEntity<List<Fruit>> getFruits() {
        List<Fruit> fruits = new ArrayList<>();
        for (FruitEntity fruitEntity : fruitRepository.findAll()) {
            fruits.add(toFruit(fruitEntity));
        }
        /*
        Fruit staticFruit = new Fruit();
        staticFruit.setColour("red");
        staticFruit.setKind("banana");
        staticFruit.setSize("medium");
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(staticFruit);
        */
        return ResponseEntity.ok(fruits);
    }


    private FruitEntity toFruitEntity(Fruit fruit) {
        FruitEntity entity = new FruitEntity();
        entity.setColour(fruit.getColour());
        entity.setKind(fruit.getKind());
        entity.setSize(fruit.getSize());
        return entity;
    }

    private Fruit toFruit(FruitEntity entity) {
        Fruit fruit = new Fruit();
        fruit.setColour(entity.getColour());
        fruit.setKind(entity.getKind());
        fruit.setSize(entity.getSize());
        return fruit;
    }

}
