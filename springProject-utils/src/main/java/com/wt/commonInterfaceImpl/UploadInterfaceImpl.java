package com.wt.commonInterfaceImpl;

import com.wt.commonInterface.UploadInterface;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class UploadInterfaceImpl implements UploadInterface {

    @Override
    public Map<String, Object> uploadFile(InputStream inputStream, String uploadDir) {
        return null;
    }
}
