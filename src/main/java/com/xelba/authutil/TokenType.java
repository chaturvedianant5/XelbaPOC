package com.xelba.authutil;

public class TokenType {

    private String token;
    private Long expireAt;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the expireAt
     */
    public Long getExpireAt() {
        return expireAt;
    }

    /**
     * @param expireAt the expireAt to set
     */
    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    
}