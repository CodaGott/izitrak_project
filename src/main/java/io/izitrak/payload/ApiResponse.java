package io.izitrak.payload;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String successMessage;
}
