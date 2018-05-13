package com.wt.commonInterface;

import java.io.InputStream;
import java.util.Map;

public interface UploadInterface {
    public Map<String,Object> uploadFile(InputStream inputStream,String uploadDir);
}
