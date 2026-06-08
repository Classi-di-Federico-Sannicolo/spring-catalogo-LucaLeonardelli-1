package com.lucaleonardelli.catalogo_vini.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class APIResponse<T> {
    
    private String status; 
    private T data;        
    private String message; 

    // risposte di successo
    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>("success", data, null);
    }

    // fallimenti di validazione
    public static <T> APIResponse<T> fail(T errors) {
        return new APIResponse<>("fail", errors, null);
    }

    // errori di sistema
    public static <T> APIResponse<T> error(String message) {
        return new APIResponse<>("error", null, message);
    }
}