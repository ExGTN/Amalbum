package com.mugenunagi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.datastructure.datamodel.dao.SampleMapper;

@Service
public class SampleService {
	@Autowired
	private SampleMapper sampleMapper;
	
	public String bark(){
		sampleMapper.insertDummy();
		return applicationProperties.getString("barkName"); //$NON-NLS-1$
	}
}
