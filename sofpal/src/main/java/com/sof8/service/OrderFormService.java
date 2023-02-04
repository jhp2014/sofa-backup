package com.sof8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.OrderForm;
import com.sof8.mapper.OrderFormMapper;

@Service
public class OrderFormService{

	@Autowired
	OrderFormMapper mapper;
	
	public void registerorder(OrderForm v) throws Exception {
		mapper.insertorder(v);
	}
	
	public void registerdetailorder(OrderForm v) throws Exception {
		mapper.insertdetailorder(v);
	}
	
	public void registerreservation(OrderForm v) throws Exception {
		mapper.insertreservation(v);
	}
}