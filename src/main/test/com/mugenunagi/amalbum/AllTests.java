package com.mugenunagi.amalbum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mugenunagi.amalbum.datastructure.ImageControllerTest;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntityTest;

@RunWith(Suite.class)
@SuiteClasses({
	  ContentsEntityTest.class
	, ImageControllerTest.class
	})
public class AllTests {
}