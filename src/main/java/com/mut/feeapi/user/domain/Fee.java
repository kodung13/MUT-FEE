package com.mut.feeapi.user.domain;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;


public @Data class Fee implements Serializable {

    private BigInteger PROD_ID;
    private String PROD_NAME;
    private String PROD_FEE;



}
