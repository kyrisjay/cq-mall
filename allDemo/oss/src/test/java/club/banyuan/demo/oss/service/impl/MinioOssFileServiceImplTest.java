package club.banyuan.demo.oss.service.impl;


import club.banyuan.demo.oss.service.OssFileService;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinioOssFileServiceImplTest {

    @Autowired
    private OssFileService ossFileService;
    private final String OBJECT_NAME = "unit_test/unit_test.jpg";

    @Test
    public void uploadTest() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("httpClient/test.jpg");


        String url = ossFileService.save(OBJECT_NAME, inputStream);

        Assert.assertTrue(url.endsWith(OBJECT_NAME));
        Assert.assertTrue(ossFileService.isExist(OBJECT_NAME));
    }

    @Test
    public void deleteTest() throws IOException {
        ossFileService.delete("timg1.jpg");
        Assert.assertFalse(ossFileService.isExist(OBJECT_NAME));
    }

}