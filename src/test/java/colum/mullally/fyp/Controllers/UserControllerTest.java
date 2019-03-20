package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.pdfForm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.security.Principal;

import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "build/generated-snippets");


    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        RestDocumentationResultHandler document = document("User",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.standaloneSetup(userController).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(document).build();
    }


    @Test
    public void details() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        User temp = new User("user");
        temp.addPdf(new pdfForm("OoPdfFormExample"));
        temp.getPdf().get(0).addAttributes("name");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/User/details").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes()))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("get userdata"));
    }

    @Test
    public void pdfDetails() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        User temp = new User("user");
        temp.addPdf(new pdfForm("OoPdfFormExample"));
        temp.getPdf().get(0).addAttributes("name");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/User/details/{pdf}","OoPdfFormExample").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes()))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("get pdfdata"));
    }

    @Test
    public void pdfDetails1() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");
        User temp = new User("user");
        temp.addPdf(new pdfForm("OoPdfFormExample"));
        temp.getPdf().get(0).addAttributes("name");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(temp);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/User//details/{pdf}/addcontent/{contentFieldName}","OoPdfFormExample","name").param("content", "colum").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes()))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("set text field"));
    }
}