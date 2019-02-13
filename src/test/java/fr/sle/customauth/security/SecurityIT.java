package fr.sle.customauth.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author slemoine
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void isAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .header("Auth-Token", "abcd-efgh"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void accessDeniedWrongToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .header("Auth-Token", "xxxxx-xxxxx-xxxx-xxxx-xxxxxxxx"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void accessDeniedNoToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
