package com.icreon.user.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author swapnil.mane
 *
 */
public interface IFileUploaderService {
	
	public void uploadFile(MultipartFile file);
}
