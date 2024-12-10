package com.cerv1no.ecommerce.dto;

import lombok.Data;

@Data
public class EmailConfirmationRequest {

    private String email;
    private String confirmationCode;
}
