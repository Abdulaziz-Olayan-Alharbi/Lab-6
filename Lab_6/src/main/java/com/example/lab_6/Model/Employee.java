package com.example.lab_6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty
    @Size(min = 2, max = 10)
    private String id;
    @NotEmpty
    @Size(min = 8)
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;
    @Email
    private String email;
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^05.*")
    private String phone;
    @NotNull
    @Positive
    @Min(25)
    private int age;
    @NotEmpty
    @Pattern(regexp = "^(Superviser|Coordinator)$")
    private String position;
    private boolean onLeave = false;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate hireDate;
    @NotNull
    @PositiveOrZero
    private int annualLeave;

    public boolean getOnLeave (){
        return this.onLeave;
    }

}
