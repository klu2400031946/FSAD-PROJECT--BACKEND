package com.edu.backend.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/code-execute")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CodeExecutionController {

    @PostMapping
    public ResponseEntity<?> executeCode(@RequestBody CodeRequest request) {
        String lang = request.getLanguage();
        String code = request.getCode();
        
        Map<String, Object> response = new HashMap<>();
        
        // Very basic simulation for Python/Java/C print/println/printf statements
        StringBuilder simulatedOutput = new StringBuilder();
        
        if ("python".equals(lang)) {
            Pattern p = Pattern.compile("print\\([\"'](.+?)[\"']\\)");
            Matcher m = p.matcher(code);
            while (m.find()) {
                simulatedOutput.append(m.group(1)).append("\n");
            }
            if (simulatedOutput.length() == 0) {
                simulatedOutput.append("Executed successfully (Python simulation)\n");
            }
        } else if ("java".equals(lang)) {
            Pattern p = Pattern.compile("System\\.out\\.println\\([\"'](.+?)[\"']\\)");
            Matcher m = p.matcher(code);
            while (m.find()) {
                simulatedOutput.append(m.group(1)).append("\n");
            }
            if (simulatedOutput.length() == 0) {
                simulatedOutput.append("Executed successfully (Java simulation)\n");
            }
        } else if ("c".equals(lang)) {
            Pattern p = Pattern.compile("printf\\([\"'](.+?)[\"']\\)");
            Matcher m = p.matcher(code);
            while (m.find()) {
                simulatedOutput.append(m.group(1)).append("\n");
            }
            if (simulatedOutput.length() == 0) {
                simulatedOutput.append("Executed successfully (C simulation)\n");
            }
        } else {
            response.put("error", "Language " + lang + " is not supported.");
            return ResponseEntity.badRequest().body(response);
        }
        
        response.put("output", simulatedOutput.toString());
        response.put("warnings", null);
        
        return ResponseEntity.ok(response);
    }
}

@Data
class CodeRequest {
    private String language;
    private String code;
}
