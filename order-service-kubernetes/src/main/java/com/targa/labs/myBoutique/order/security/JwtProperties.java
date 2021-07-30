package com.targa.labs.myBoutique.order.security;

public class JwtProperties {
    public static final String SECRET = "152piqbDgq";
    public static final int EXPIRATION_TIME = 1_800_000; // 30 min
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
