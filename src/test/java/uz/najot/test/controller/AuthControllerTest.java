package uz.najot.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uz.najot.test.config.MyMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description: TODO
 * @date: 02 April 2024 $
 * @time: 7:40 PM 33 $
 * @author: Qudratjon Komilov
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Autowired
    MockMvc api;

    @Test
    @MyMockUser(role = "ROLE_USER")
    void admin_check_permission_with_user() throws Exception {
        api.perform(get("/gratings/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void hello() throws Exception {
        api.perform(get("/gratings/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @MyMockUser(role = "ROLE_ADMIN")
    void admin_check_permission_with_admin() throws Exception {
        api.perform(get("/gratings/admin"))
                .andExpect(status().isOk());
    }
}