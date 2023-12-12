package com.example.fran365.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MailDto {
    private String address;
    private String title;
    private String message;
}
