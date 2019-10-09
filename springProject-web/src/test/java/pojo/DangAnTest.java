package pojo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;


public class DangAnTest {
    private static String IP="17.16.28.45";

    public static void main(String[] args) throws IOException {
//        callArchive("建设用地报批","案卷档号","E090-YW0201-2003-0008");
        callArchiveFile("91909cad66a5a1e80166a6265a73001f");
//        getArchiveTree("建设用地报批","案卷档号","E090-YW0201-2003-0008");
//        getArchiveFile("91909cad66a5a1e80166a6265a73001f");
//        tiffToJPEGByImageIO("C:\\E090-YW0201-2003-0008-001.tif");

        //土地储备
//        callArchive("土地储备","案卷档号","1106-YW1401-2008-0001");
//        getArchiveFile("40288cc467398fe401673e3da26a052f");
    }

    private static void callArchive(String acname,String field,String value) throws IOException {
        String json = Request.Post("http://" + IP + ":8080/rams/systemAction!getArchiveData.action")
                .connectTimeout(2000)
                .socketTimeout(2000)
                .body(MultipartEntityBuilder.create()
                        .addTextBody("code", DigestUtils.md5Hex(acname + field + value + "md5key"), ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .addTextBody("acname", acname, ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .addTextBody("field", field, ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .addTextBody("value", value, ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .build())
                .execute()
                .returnContent()
                .asString(Consts.UTF_8);
        System.out.println(json);
    }

    /**
     * HttpHeaders，ContentType，MediaType
     * @param id
     * @throws IOException
     */
    private static void callArchiveFile(String id) throws IOException {
        String url="http://127.0.0.1:8080/file/download3";
//        String url="http://"+IP+":8080/rams/systemAction!getArchiveFile.action";
        Request.Post(url)
                .connectTimeout(2000)
                .socketTimeout(2000)
                .body(MultipartEntityBuilder.create()
                        .addTextBody("code", DigestUtils.md5Hex(id+"md5key"), ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .addTextBody("id", id, ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8))
                        .build())
                .execute()
                .handleResponse(httpResponse -> {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    HttpEntity entity = httpResponse.getEntity();
                    if(statusLine.getStatusCode()==200){
//                        ContentType contentType = ContentType.getOrDefault(entity);
                        Header header = httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0];
                        if(header.getValue().contains(ContentType.APPLICATION_JSON.getMimeType())){
                            System.out.println(EntityUtils.toString(httpResponse.getEntity()));//输出json字符串
                            return null;
                        }
                        Header headerContentDispostion = httpResponse.getHeaders(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION)[0];
                        String filename="";
                        if (headerContentDispostion!=null) {//从头中获取文件名字
                            filename = headerContentDispostion.getElements()[0].getParameterByName("filename").getValue();
                            filename = URLDecoder.decode(filename, "utf-8");
                        }
                        String filePath = "e:\\"+filename; // 文件路径
                        File file = new File(filePath);
                        FileOutputStream outputStream = new FileOutputStream(file);
                        InputStream inputStream = httpResponse.getEntity().getContent();
                        IOUtils.write(IOUtils.toByteArray(inputStream),outputStream);
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(outputStream);
                    }else{
                        System.out.println("code:"+statusLine.getStatusCode());
                    }
                    EntityUtils.consume(entity);
                    return null;
                });
    }

    public static void getArchiveTree(String acname,String field,String value){
        HttpClient httpClient1 = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://"+IP+":8080/rams/systemAction!getArchiveData.action");
        //post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addTextBody("code", DigestUtils.md5Hex(acname+field+value+"md5key"));
        mEntityBuilder.addPart("acname", new StringBody(acname, ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8)));
        mEntityBuilder.addPart("field", new StringBody(field,ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8)));
        mEntityBuilder.addPart("value", new StringBody(value,ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8)));
        post.setEntity(mEntityBuilder.build());

        try {
            HttpResponse httpResponse1 = httpClient1.execute(post);
            StatusLine statusLine = httpResponse1.getStatusLine();

            if (statusLine.getStatusCode() == 200) {  //httpResponse1.getAllHeaders()
                //Header[] heads=httpResponse1.getHeaders("Content-Disposition");
                //if(httpResponse1.getHeaders("Content-Type")[0].getValue().indexOf("application/json")>-1){//application/json类型为错误信息 或找不到文件
                System.out.println(EntityUtils.toString(httpResponse1.getEntity()));//输出json字符串
                //	post.releaseConnection();
                //	return;
                //}
            } else{
                //做其他处理...
                System.out.println("code:"+statusLine.getStatusCode());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
    }

    public static void getArchiveFile(String id){
        String url="http://127.0.0.1:8080/file/download3";
        HttpClient httpClient1 = HttpClients.createDefault();
//        "http://"+IP+":8080/rams/systemAction!getArchiveFile.action"
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addTextBody("code", DigestUtils.md5Hex(id+"md5key"));
        mEntityBuilder.addTextBody("id", id);
        post.setEntity(mEntityBuilder.build());
        try {
            HttpResponse httpResponse1 = httpClient1.execute(post);
            StatusLine statusLine = httpResponse1.getStatusLine();

            if (statusLine.getStatusCode() == 200) {  //httpResponse1.getAllHeaders()
                Header[] heads=httpResponse1.getHeaders("Content-Disposition");

                if(httpResponse1.getHeaders("Content-Type")[0].getValue().indexOf("application/json")>-1){//application/json类型为错误信息 或找不到文件
                    System.out.println(EntityUtils.toString(httpResponse1.getEntity()));//输出json字符串
                    post.releaseConnection();
                    return;
                }
                String filename="";
                if(heads.length>0){//从头中获取文件名字
                    filename=heads[0].getElements()[0].getParameterByName("filename").getValue();
                    filename= URLDecoder.decode(filename, "utf-8");
                }
                //httpResponse1.getHeaders()
                String filePath = "e:\\"+filename; // 文件路径
                File file = new File(filePath);
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = httpResponse1.getEntity()
                        .getContent();
                byte b[] = new byte[1024];
                int j = 0;
                while ((j = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, j);
                }
                outputStream.flush();
                outputStream.close();

            } else{
                //做其他处理...
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
    }

    private static void tiffToJPEGByImageIO(String tiff) {
        ImageInputStream input;
        try {
            input = ImageIO.createImageInputStream(new File(tiff));//以图片输入流形式读取到tif
            // Get the reader
            ImageReader reader = ImageIO.getImageReaders(input).next();//获得image阅读器，阅读对象为tif文件转换的流
            String path,tiffName;
            path = tiff.substring(0, tiff.lastIndexOf("."));
            tiffName = tiff.substring(tiff.lastIndexOf("\\"),tiff.lastIndexOf("."));
            try {
                reader.setInput(input);
                // Read page 2 of the TIFF file
                int count = reader.getNumImages(true);//tif文件页数
                //System.out.println(count);
                for(int i = 0; i < count; i++){
                    BufferedImage image = reader.read(i, null);//取得第i页
                    File f = new File(path,"\\"+tiffName+"_"+i+".jpg");
                    try {
                        if(!f.exists()){
                            f.getParentFile().mkdirs();
                            f.createNewFile();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIO.write(image, "JPEG", f);//保存图片
                }
            }
            finally {
                reader.dispose();
                input.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}