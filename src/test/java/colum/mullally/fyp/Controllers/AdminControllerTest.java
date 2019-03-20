package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import org.junit.Assert;
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
import java.util.Arrays;
import java.util.List;

import static com.amazonaws.services.elasticbeanstalk.model.ConfigurationOptionValueType.List;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "build/generated-snippets");


    private MockMvc mockMvc;
    @InjectMocks
    private AdminController adminController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;


    @Before
    public void setUp() throws Exception {
        RestDocumentationResultHandler document = document("Admin",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(document).build();
    }


    @Test
    public void getAll() throws Exception {
        Mockito.when(userAuthenticationRepository.findByUsername("Admin")).thenReturn(new UserAuthentication("Admin","admin","ADMIN"));
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("Admin");
        java.util.List< User > users = Arrays.asList(new User("Admin"), new User("user2"), new User("user3"));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/allusers").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("Admin get all"));
    }

    @Test
    public void promote() throws Exception {
        Mockito.when(userAuthenticationRepository.findByUsername("Admin")).thenReturn(new UserAuthentication("Admin","admin","ADMIN"));
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("Admin");
        Mockito.when(userAuthenticationRepository.findByUsername("user")).thenReturn(new UserAuthentication("user","user","USER"));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/promote").param("username","user").principal(mockPrincipal).header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:secret".getBytes()))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(document("Admin Promote"));
    }
}