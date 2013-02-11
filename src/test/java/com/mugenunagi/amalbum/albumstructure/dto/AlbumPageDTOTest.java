package com.mugenunagi.amalbum.albumstructure.dto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;

public class AlbumPageDTOTest {

	@Test
	public void testFields() {
		AlbumPageDTO dto = new AlbumPageDTO();
		
		ContentsGroupEntity albumPageInfo	= new ContentsGroupEntity();
		albumPageInfo.setName( "TestContentsGroupEntity" );
		
		List<PhotoDTO> photoDTOList			= new ArrayList<PhotoDTO>();
		photoDTOList.add( new PhotoDTO() );
		photoDTOList.add( new PhotoDTO() );
		photoDTOList.add( new PhotoDTO() );
		
		// set
		dto.setAlbumPageInfo( albumPageInfo );
		dto.setPhotoDTOList( photoDTOList );

		// get
		ContentsGroupEntity pageInfo = dto.getAlbumPageInfo();
		List<PhotoDTO> list = dto.getPhotoDTOList();
		
		// assert
		assertEquals( "AlbumPageDTOTest", "TestContentsGroupEntity", pageInfo.getName() );
		assertEquals( "AlbumPageDTOTest", 3, list.size() );
	}

}
