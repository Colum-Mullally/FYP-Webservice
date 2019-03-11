//package colum.mullally.fyp;
//
//import colum.mullally.fyp.Controllers.RegisterController;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(RegisterController.class)
//public class RegisterControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Before
//    public void init() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
//
//    }
//
//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(post("/v1/register").param("username","users").param("password","password"))
//                .andExpect(status().isOk());
//    }
//}