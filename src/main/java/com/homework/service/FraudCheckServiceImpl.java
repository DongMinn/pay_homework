package com.homework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.dao.UserLogDao;
import com.homework.dto.ResponseDto;
import com.homework.dto.log.LogBase;
import com.homework.rule.RuleEngine;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

	@Autowired
	private RuleEngine ruleEngine;
	
	@Autowired
	private UserLogDao userLogDao;
	
	@Override
	public ResponseDto isFraud(long user_id) { 
		
		
		HashMap<String, Boolean> checkedFraud = ruleEngine.checkFraud(userLogDao.findById(user_id));
		ResponseDto responseDto = new ResponseDto(user_id); 
		responseDto.setIs_fraud(true);
		
		if(checkedFraud == null){
			return responseDto;
		}
		
		Iterator<String> keys = checkedFraud.keySet().iterator();
		
		ArrayList<String> fraudRules = new ArrayList<String>();
		while(keys.hasNext()){
			String key = keys.next();
			if(checkedFraud.get(key)==false){
				fraudRules.add(key);
			}
		} 
		
		
		if(fraudRules.size() > 0){
			responseDto.setRule(String.join(",", fraudRules));
			responseDto.setIs_fraud(false);
		}else{
			responseDto.setRule("");
		}
		
		return responseDto;
	}

}
