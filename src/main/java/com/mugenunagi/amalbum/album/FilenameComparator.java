package com.mugenunagi.amalbum.album;

import java.io.File;
import java.util.Comparator;

/**
 * ファイル名のコンパレータ
 * @author GTN
 *
 */
public class FilenameComparator implements Comparator<File> {
	public int compare(File file1, File file2) {
		String fileName1 = file1.getName();
		String fileName2 = file2.getName();
		return fileName1.compareTo(fileName2);
	}
}
