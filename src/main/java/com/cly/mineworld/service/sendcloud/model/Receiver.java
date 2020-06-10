package com.cly.mineworld.service.sendcloud.model;

import com.cly.mineworld.service.sendcloud.exception.ReceiverException;

public interface Receiver {
	public boolean useAddressList();
	
	public boolean validate() throws ReceiverException;
	
	public String toString();
}