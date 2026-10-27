package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class DemoController {

    private final Map<Integer, String> items = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    // 1. Simple hello
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Jenkins CI/CD pipeline!";
    }

    // 2. Hello with a name: /hello/Sarthak
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // 3. Query param: /greet?name=Sarthak
    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
        return "Welcome, " + name + "!";
    }

    // 4. Health check (useful for Jenkins/deployment checks)
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().toString());
        return status;
    }

    // 5. Get all items
    @GetMapping("/items")
    public Collection<String> getItems() {
        return items.values();
    }

    // 6. Get one item
    @GetMapping("/items/{id}")
    public ResponseEntity<String> getItem(@PathVariable int id) {
        String item = items.get(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        return ResponseEntity.ok(item);
    }

    // 7. Create item
    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody String name) {
        int id = counter.getAndIncrement();
        items.put(id, name);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("name", name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 8. Update item
    @PutMapping("/items/{id}")
    public ResponseEntity<String> updateItem(@PathVariable int id, @RequestBody String name) {
        if (!items.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        items.put(id, name);
        return ResponseEntity.ok("Item updated");
    }

    // 9. Delete item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        if (items.remove(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        return ResponseEntity.ok("Item deleted");
    }
}