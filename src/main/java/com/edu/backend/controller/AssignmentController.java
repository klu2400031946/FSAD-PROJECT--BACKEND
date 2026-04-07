package com.edu.backend.controller;

import com.edu.backend.model.Assignment;
import com.edu.backend.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable String id) {
        return assignmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment newAssignment) {
        return assignmentRepository.save(newAssignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable String id, @RequestBody Assignment updatedDetails) {
        return assignmentRepository.findById(id).map(assignment -> {
            assignment.setTitle(updatedDetails.getTitle());
            assignment.setDescription(updatedDetails.getDescription());
            assignment.setDeadline(updatedDetails.getDeadline());
            assignment.setFileUrl(updatedDetails.getFileUrl());
            assignmentRepository.save(assignment);
            return ResponseEntity.ok(assignment);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable String id) {
        if (!assignmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        assignmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
