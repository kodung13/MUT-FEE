package com.mut.feeapi.user.domain;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;


public @Data class Fee implements Serializable {

    private BigInteger PRODID;

    private String PRODNAME;

    private String PRODFEE;



}
