package com.library2020.payload.request;

import com.library2020.model.Author;
import com.library2020.model.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class BookRequest {

    @NotNull
    @NotBlank
    private String publishingHouse;

    @NotNull
    @Min(0)
    private Integer publishingYear;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @Min(1)
    private Long numberOfPages;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Long numberOfInstances;

    private String category;

    private Set<String> authors;
}
