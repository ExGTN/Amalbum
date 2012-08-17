package com.mugenunagi.amalbum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mugenunagi.amalbum.albumstructure.PhotoRegistratorTest;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTOTest;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTOTest;
import com.mugenunagi.amalbum.datastructure.ImageControllerTest;
import com.mugenunagi.amalbum.datastructure.entity.BackupMasterEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.BackupRuleEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.ContentsRoleEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.LoginUserEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.MaterialRoleEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.RoleMasterEntityTest;
import com.mugenunagi.amalbum.datastructure.entity.UserRoleEntityTest;
import com.mugenunagi.amalbum.exception.AmalbumExceptionTest;
import com.mugenunagi.amalbum.exception.handler.AmalbumExceptionManagerTest;
import com.mugenunagi.amalbum.exception.handler.ExceptionHandlerSelectorTest;

@RunWith(Suite.class)
@SuiteClasses({
	// Constants
	  ConstantsTest.class
	// Test about Entity.
	, ContentsEntityTest.class
	, ContentsGroupEntityTest.class
	, MaterialEntityTest.class
	, BackupMasterEntityTest.class
	, BackupRuleEntityTest.class
	, ContentsRoleEntityTest.class
	, LoginUserEntityTest.class
	, MaterialRoleEntityTest.class
	, RoleMasterEntityTest.class
	, UserRoleEntityTest.class
	// Album DTO
	, AlbumPageDTOTest.class
	, PhotoDTOTest.class
	// Test about controller
	, ImageControllerTest.class
	// Exception
	, ExceptionHandlerSelectorTest.class
	, AmalbumExceptionTest.class
	, AmalbumExceptionManagerTest.class
	// Photo related classes
	, PhotoRegistratorTest.class
	})
public class AllTests {
}