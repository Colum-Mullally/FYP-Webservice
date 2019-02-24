package colum.mullally.fyp.Service;

import colum.mullally.fyp.model.pdfForm;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.pdfbox.PDFBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.COSArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service
public class AmazonClient {

    private AmazonS3 s3Client;

    @Value("${s3.endpoint}")
    private String endpointUrl;
    @Value("${s3.bucket}")
    private String bucketName;
    @Value("${aws.access_key_id}")
    private String accessKey;
    @Value("${aws.secret_access_key}")
    private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3Client = AmazonS3ClientBuilder.standard().withRegion("eu-west-1").withCredentials(new AWSStaticCredentialsProvider(creds)).build();
    }
    private COSArrayList lista;
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }
    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            PDDocument doc = PDDocument.load(file);
            PDDocumentCatalog pdCatalog = doc.getDocumentCatalog();
            PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
            lista = (COSArrayList)pdAcroForm.getFields();
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        try {
            s3Client.deleteObject(bucketName , fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Successfully deleted";
    }

    public COSArrayList getLista() {
        return lista;
    }
    public File getFileFromS3Bucket(pdfForm form){
        String fileName = form.getUrl().substring(form.getUrl().lastIndexOf("/") + 1);
        try {
            InputStream in = s3Client.getObject(bucketName, fileName).getObjectContent();
            File temp = File.createTempFile(fileName, "");
            Files.copy(in, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            PDDocument doc = PDDocument.load(temp);
            PDDocumentCatalog pdCatalog = doc.getDocumentCatalog();
            PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
            for(int x=0; x<form.getAttributes().size();x++) {
                PDField field = pdAcroForm.getField(form.getAttributes().get(x).getName());
                if( field != null && form.getAttributes().get(x).getContent()!= null) {
                    field.setValue(form.getAttributes().get(x).getContent());
                }
            }
            doc.save(temp);
            doc.close();
            in.close();
            return temp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
   }

}
