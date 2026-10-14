//package com.taskforge.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//
//public record RegisterRequest(
//    @NotBlank @Size(max = 100) String fullName,
//    @NotBlank @Email @Size(max = 150) String email,
//    @NotBlank @Size(min = 6, max = 100) String password
//) {}

package com.taskforge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @NotBlank
    @Size(max = 100)
    String fullName,

    @NotBlank
    @Email
    @Size(max = 150)
    String email,

    @NotBlank
    @Size(min = 6, max = 100)
    String password,

    @NotBlank
    String captchaId,

    @NotBlank
    String captchaAnswer

) {}