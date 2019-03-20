package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.Service.AmazonClient;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.pdfForm;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.COSArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
@RunWith(SpringJUnit4ClassRunner.class)
public class BucketControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "build/generated-snippets");


    private MockMvc mockMvc;
    @InjectMocks
    private BucketController bucketController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AmazonClient amazonClient;

    @Before
    public void setUp() throws Exception {
        RestDocumentationResultHandler document = document("Storage",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.standaloneSetup(bucketController).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(document).build();
    }


    @Test
    public void uploadFile() throws Exception {
        User temp = new User("user");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        File pdf = new File("src\\test\\java\\colum\\mullally\\Resources\\OoPdfFormExample.pdf");
        byte[] content = Files.readAllBytes(pdf.toPath());
        MockMultipartFile mockMultipartFile= new MockMultipartFile("file",pdf.getName(), String.valueOf(MediaType.APPLICATION_PDF),content);
        Mockito.when(amazonClient.uploadFile(mockMultipartFile)).thenReturn("s3.eu-west-1.amazonaws.com/colum-mullally-fyp/1553009203695-OoPdfFormExample.pdf");
        PDDocument doc = PDDocument.load(pdf);
        PDDocumentCatalog pdCatalog = doc.getDocumentCatalog();
        PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
        COSArrayList lista = (COSArrayList)pdAcroForm.getFields();
        Mockito.when(amazonClient.getLista()).thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/storage/uploadFile")
                .file(mockMultipartFile).principal(mockPrincipal)
                .header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Successfully uploaded")))
                .andDo(document("Upload file"));
    }

   @Test
    public void deleteFile() throws Exception {
       User temp = new User("user");
       Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
       Principal mockPrincipal = Mockito.mock(Principal.class);
       Mockito.when(mockPrincipal.getName()).thenReturn("user");
       File pdf = new File("src\\test\\java\\colum\\mullally\\Resources\\OoPdfFormExample.pdf");
       byte[] content = Files.readAllBytes(pdf.toPath());
       Mockito.when(amazonClient.deleteFileFromS3Bucket("s3.eu-west-1.amazonaws.com/colum-mullally-fyp/1553009203695-OoPdfFormExample.pdf")).thenReturn("Successfully deleted");
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/storage/deleteFile")
                .param("url","s3.eu-west-1.amazonaws.com/colum-mullally-fyp/1553009203695-OoPdfFormExample.pdf")
                .principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Successfully deleted")))
                .andDo(document("Delete file"));
    }
    @Test
    public void saveAndDownLoad() throws Exception {
        User temp = new User("user");
        temp.addPdf(new pdfForm("OoPdfFormExample"));
        temp.getPdf().get(0).addAttributes("name");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        File pdf = new File("src\\test\\java\\colum\\mullally\\Resources\\OoPdfFormExample.pdf");
        Mockito.when(amazonClient.getFileFromS3Bucket(temp.getPdf().get(0))).thenReturn(pdf);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/storage/view/{pdf}","OoPdfFormExample").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("Download file"));
    }

    @Test
    public void saveAndView() throws Exception {
        User temp = new User("user");
        temp.addPdf(new pdfForm("OoPdfFormExample"));
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        File pdf = new File("src\\test\\java\\colum\\mullally\\Resources\\OoPdfFormExample.pdf");
        Mockito.when(amazonClient.getFileFromS3Bucket(temp.getPdf().get(0))).thenReturn(pdf);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/storage/view/{pdf}","OoPdfFormExample").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("View file"));
    }
}