package com.example.dto;

import com.example.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RequestDTO {
    private int requestId;
    private UserDTO user;
    private Book book;
    private Date requestDate;
    private String requestStatus;
}
