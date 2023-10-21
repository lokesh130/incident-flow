package com.flipkart.fdsg.planning.ip.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IFServiceConfiguration {
    @NotNull
    private String url;
}
