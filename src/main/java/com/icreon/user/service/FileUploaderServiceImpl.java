package com.icreon.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.icreon.user.model.User;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;

/**
 * @author swapnil.mane
 *
 */
@Service
public class FileUploaderServiceImpl implements IFileUploaderService {
	
	
	public List<User> userExcelReaderService() {
		return null;
	}
		
	@Override
	public void uploadFile(MultipartFile file) {
		
		try {
            Path copyLocation = Paths
                .get(StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                + ". Please try again!");
        }
		
    }
        
}


