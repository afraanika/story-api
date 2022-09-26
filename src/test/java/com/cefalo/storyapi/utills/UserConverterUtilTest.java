package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.UserConverterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserConverterUtilTest {

    @Autowired
    private UserConverterUtil userConverterUtil;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    public void setup(){
        user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        userDTO =  new UserDTO(1, "Billy", "01236547893", "bill@gmail.com");
    }

    @Test
    @DisplayName("Test UserEntityToUserDTO")
    void shouldReturnUserDTO() {
        UserDTO returnedUserDTO = userConverterUtil.entityToDTO(user);

        assertEquals(returnedUserDTO, userDTO, "Users should be equal");
    }

    @Test
    @DisplayName("Test IterableUserDTO")
    void shouldReturnUserDTOList() {
        List<UserDTO> returnedUsers = userConverterUtil.iterableUserDTO(Arrays.asList(user, user));

        assertEquals(returnedUsers, Arrays.asList(userDTO, userDTO), "User Lists should be equal");
    }
}
