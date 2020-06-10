package com.cly.mineworld.service.sendcloud.model;

import com.cly.mineworld.service.sendcloud.exception.ContentException;

public interface Content {
	public boolean useTemplate();

	public boolean validate() throws ContentException;
}