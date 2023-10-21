package com.flipkart.fdsg.planning.ip.core.config;

import lombok.Data;

@Data
public class HttpConfig {
    // These are in milli-seconds.
    private Integer connectTimeout;
    private Integer readTimeout;
}
