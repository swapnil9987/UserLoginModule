package com.icreon.user.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.icreon.user.model.User;
import com.icreon.user.repository.UserRepository;
import com.icreon.user.service.IExcelDataService;
import com.icreon.user.service.IFileUploaderService;

/**
 * @author swapnil.mane
 *
 */
@Controller
public class ExcelDataController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IFileUploaderService fileService;
	
	@Autowired
	IExcelDataService excelservice;
	
	/**
	 * Endpoint used to show 
	 * upload excel data page
	 */
	@GetMapping("/uploadexceldata")
	public String showuploadExcelDataPage() {
		return "uploadexceldata";
		
	}
	
	/**
	 * Endpoint used to upload
	 * excel data
	 */
	@PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)  {

        fileService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message",
            "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
        return "redirect:/uploadexceldata";
    }
    
	/**
	 * Endpoint used to save
	 * excel data to database
	 */
    @GetMapping("/saveData")
    public String saveExcelData(HttpSession session) {
    	List<User> excelDataAsList = excelservice.getExcelDataAsList();
    	int noOfRecords = excelservice.saveExcelData(excelDataAsList);
    	session.setAttribute("message", noOfRecords + " records are uploaded successfully");
    	return "redirect:/homepage";
    }
	
}
