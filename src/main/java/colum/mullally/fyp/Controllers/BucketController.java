package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.Service.AmazonClient;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.pdfForm;
import org.apache.pdfbox.pdmodel.common.COSArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    public String uploadFile(@RequestPart(value = "file") MultipartFile file , Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.addPdf(new pdfForm(file.getOriginalFilename()));
        user.getPdf().get(user.getPdf().size()-1).setUrl(this.amazonClient.uploadFile(file));
        COSArrayList fields = amazonClient.getLista();
        for (int x =0;x<fields.size();x++) {
            PDField temp=(PDField)fields.get(x);
            user.getPdf().get(user.getPdf().size()-1).addAttributes(temp.getPartialName());
        }
        userRepository.save(user);
        return "Succesfully uploaded";
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl,Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
    @GetMapping("/download/{pdf}")
    public File saveAndDownLoad(@PathVariable("pdf") String pdfName, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdfName);
        pdfForm form= user.getPdf().get(index);
        return amazonClient.getFileFromS3Bucket(form);
    }
}
