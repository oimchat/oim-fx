package com.onlyxiahui.oim.face;

import java.util.List;

import com.onlyxiahui.oim.face.bean.FaceCategory;
import com.onlyxiahui.oim.face.bean.FaceInfo;

/**
 * @author: XiaHui
 * @date: 2018-01-27 16:04:44
 */
public class HeadRepositoryTest {

	public static void main(String[] args) {
		FaceRepository fr = new FaceRepository();
		List<FaceCategory> list = fr.getAllFaceCategoryList();

		for (FaceCategory fc : list) {
			List<FaceInfo> faceInfoList = fc.getFaceInfoList();
			for (FaceInfo fi : faceInfoList) {
				System.out.println(fi.getRealPath());
			}
		}
	}
}
