package club.banyuan.mgt.controller;

import club.banyuan.mgt.service.OssFileService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private OssFileService ossFileService;


    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String objectName = simpleDateFormat.format(new Date()) + "/" + filename;

        try {
            return ossFileService.save(objectName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }


    @RequestMapping(value = "/image/delete", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("objectName") String objectName) {
        try {
            ossFileService.delete(objectName);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}