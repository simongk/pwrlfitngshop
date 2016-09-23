package com.simongk.exceptions;

import lombok.Getter;
import lombok.Setter;

public class DuplicatedItemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private String msg;

	public DuplicatedItemException(String msg) {
		this.msg = msg;
	}
	
	
}
