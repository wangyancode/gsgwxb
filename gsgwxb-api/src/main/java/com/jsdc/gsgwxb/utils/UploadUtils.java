package com.jsdc.gsgwxb.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadUtils {


	/**
	 * 上传文件 uuid
	 * 在指定位置创建临时文件,文件名称取uuid 缓存数据
	 * @param file
	 * @return
	 */
	public static String uploadUUIDFile(MultipartFile file,String FilePath) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String filePath = FilePath + "/" + UUID.randomUUID() + suffix;
		File targetFile = new File(filePath);
		if (!targetFile.getParentFile().exists()) {
			boolean mkdirs = targetFile.getParentFile().mkdirs();
			if (!mkdirs) {
				throw new RuntimeException("创建文件夹失败");
			}
		}
		file.transferTo(targetFile);
		return filePath;
	}
}
