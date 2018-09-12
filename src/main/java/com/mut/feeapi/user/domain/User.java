package com.mut.feeapi.user.domain;

import com.mut.feeapi.security.domain.Authority;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Persistence model that represents a user.
 *
 * @author cassiomolin
 */

public @Data class User implements Serializable {

    private int USERID;

    private String USERNAME;

    private String PASSWORD;

    private String FIRSTNAME;

    private String LASTNAME;

    private String EMAIL;

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
