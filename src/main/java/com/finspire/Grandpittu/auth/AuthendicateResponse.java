package com.finspire.Grandpittu.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthendicateResponse {
    private String token;
}
