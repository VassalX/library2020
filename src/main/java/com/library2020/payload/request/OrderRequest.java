package com.library2020.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class OrderRequest {

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expectedReturnDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date actualReturnDate;

    @NotNull
    private Long bookInstanceId;

    @NotNull
    private Long userId;

    private String status;
}
