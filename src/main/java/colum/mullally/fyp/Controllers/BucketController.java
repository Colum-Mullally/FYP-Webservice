package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.Service.AmazonClient;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.pdfForm;
import org.apache.pdfbox.pdmodel.common.COSArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

@RestController
@RequestMapping("v1/storage/")
public class BucketController {

    private AmazonClient amazonClient;
    private UserRepository userRepository;

    @Autowired
    BucketController(AmazonClient amazonClient,UserRepository userRepository) {
        this.amazonClient = amazonClient;
        this.userRepository = userRepository;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file , Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.addPdf(new pdfForm(file.getOriginalFilename()));
        String fileURL= this.amazonClient.uploadFile(file);
        if(!(fileURL.equals(""))) {
            user.getPdf().get(user.getPdf().size() - 1).setUrl(fileURL);
            COSArrayList fields = amazonClient.getLista();
            for (int x = 0; x < fields.size(); x++) {
                PDField temp = (PDField) fields.get(x);
                user.getPdf().get(user.getPdf().size() - 1).addAttributes(temp.getPartialName());
            }
            userRepository.save(user);
            return new ResponseEntity<>("Successfully uploaded",HttpStatus.OK);
        }
        else
        return new ResponseEntity<>("Error occurred while uploading",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity deleteFile(@RequestParam(value = "url") String fileUrl,Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.deleteDoc("fileUrl");
        userRepository.save(user);
        String Content =this.amazonClient.deleteFileFromS3Bucket(fileUrl);
        return new ResponseEntity<>(Content,HttpStatus.OK);
    }
    @GetMapping(value="/download/{pdf}",produces = "application/pdf")
    public ResponseEntity saveAndDownLoad(@PathVariable("pdf") String pdfName, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdfName);
        if(index >= 0) {
            pdfForm form = user.getPdf().get(index);
            File file = amazonClient.getFileFromS3Bucket(form);
            try {
                byte[] content = Files.readAllBytes(file.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.add("content-disposition", "attachment;filename=" + pdfName);
                headers.setContentDispositionFormData(pdfName, pdfName);
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                ResponseEntity<byte[]> response = new ResponseEntity<>(content, headers, HttpStatus.OK);
                return response;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value="/view/{pdf}",produces = "application/pdf")
    public ResponseEntity saveAndView(@PathVariable("pdf") String pdfName, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdfName);
        if(index >= 0) {
            pdfForm form = user.getPdf().get(index);
            File file = amazonClient.getFileFromS3Bucket(form);
            try {
                byte[] content = Files.readAllBytes(file.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.add("content-disposition", "inline;filename=" + pdfName);
                headers.setContentDispositionFormData(pdfName, pdfName);
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                ResponseEntity<byte[]> response = new ResponseEntity<>(content, headers, HttpStatus.OK);
                return response;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
