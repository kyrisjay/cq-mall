package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.service.OssFileService;

import io.minio.ErrorCode;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.ErrorResponseException;
import io.minio.policy.PolicyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioOssFileServiceImpl implements OssFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioOssFileServiceImpl.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;

    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @Value("${minio.accessKey}")
    private String ACCESS_KEY;

    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @Override
    public String save(String objectName, InputStream stream) throws IOException {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            if (!minioClient.bucketExists(BUCKET_NAME)) {
                LOGGER.debug("桶不存在创建桶，{}", BUCKET_NAME);
                minioClient.makeBucket(BUCKET_NAME);
                minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_WRITE);
            }

            minioClient.putObject(BUCKET_NAME, objectName, stream, null);
            LOGGER.debug("上传文件成功，{}", objectName);
            return ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
        } catch (Exception e) {
            LOGGER.error("上传文件失败", e);
            throw new IOException(e);
        }
    }

    @Override
    public void delete(String objectName) throws IOException {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject(BUCKET_NAME, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    public boolean isExist(String objectName) throws IOException {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            ObjectStat objectStat = minioClient.statObject(BUCKET_NAME, objectName);
            System.out.println(objectStat);
            return true;
        } catch (ErrorResponseException e) {
            // 用来判断对象是否存在，不存在则抛出此异常，并且code包含noSuchKey信息
            if (ErrorCode.NO_SUCH_KEY == e.errorResponse().errorCode()) {
                return false;
            } else {
                throw new IOException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
}