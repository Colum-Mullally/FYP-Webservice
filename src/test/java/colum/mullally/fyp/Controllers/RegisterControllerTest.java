package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "build/generated-snippets");


    private MockMvc mockMvc;
    @InjectMocks
    private RegisterController registerController;

    @Mock
    private  UserRepository userRepository;
    @Mock
    private  UserAuthenticationRepository userAuthenticationRepository;

    @Before
    public void setUp() throws Exception {
        RestDocumentationResultHandler document = document("Register",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(document).build();
    }

    @Test
    public void register() throws Exception {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/register")
                        .param("username","password")
                        .param("password","password"))
                .andExpect(MockMvcResultMatchers.status().isOk()
        ).andDo(document("Register"));
    }
}