package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.dto.StoryDTO;
import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.StoryConverterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StoryConverterUtilTest {

    @Autowired
    private StoryConverterUtil storyConverterUtil;

    private Story story;
    private StoryDTO storyDTO;

    @BeforeEach
    public void setup(){
        User user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        story = new Story(1, "Story Previous Tittle", "Story Previous Desciption", user);
        storyDTO =  new StoryDTO(1, "Story Tittle", "Story Desciption", "bill@gmail.com");
    }

    @Test
    @DisplayName("Test StoryEntityToStoryDTO")
    void shouldReturnStoryDTO() {
        StoryDTO returnedStoryDTO = storyConverterUtil.entityToDTO(story);

        assertEquals(returnedStoryDTO, storyDTO, "Stories should be equal");
    }

    @Test
    @DisplayName("Test IterableStoryDTO")
    void shouldReturnStoryDTOList() {
        List<StoryDTO> returnedStories = storyConverterUtil.iterableStoryDTO(Arrays.asList(story, story));

        assertEquals(returnedStories, Arrays.asList(storyDTO, storyDTO), "Story Lists should be equal");
    }
}
