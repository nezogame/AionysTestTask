package org.denys.hudymov.aionystesttask.cotroller.impl;

import java.io.File;
import java.nio.file.Files;
import org.denys.hudymov.aionystesttask.repository.NoteRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class NoteControllerImplIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private NoteRepository noteRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURI_whenMockMVC_thenResponseOK() throws Exception {
        mockMvc.perform(get("/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[3].id").value(4))
                .andExpect(jsonPath("$.[4].id").value(5));
    }


    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithPost_whenMockMVC_thenResponseCREATE() throws Exception {
        final File jsonFile = new ClassPathResource("init/note.json").getFile();
        final String noteToCreate = Files.readString(jsonFile.toPath());

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteToCreate))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.text").value("test"));

        assertThat(noteRepository.findAll().size()).isEqualTo(6);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithPutAndPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        final int id = 1;
        final File jsonFile = new ClassPathResource("init/updateNote.json").getFile();
        final String noteToUpdate = Files.readString(jsonFile.toPath());

        mockMvc.perform(put("/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteToUpdate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("NEW title"))
                .andExpect(jsonPath("$.text").value("NEW text"));

        assertThat(noteRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithGetAndPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        final int id = 1;

        mockMvc.perform(get("/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("First test note"))
                .andExpect(jsonPath("$.text").value("text for first"));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithDeleteAndPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        final int id = 1;

        mockMvc.perform(delete("/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(noteRepository.findAll().size()).isEqualTo(4);
        assertThat(noteRepository.findById(1)).isEmpty();

    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithPost_whenMockMVC_thenResponseBadRequest() throws Exception {
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", Matchers.containsInAnyOrder(
                Matchers.equalTo("Note must have a text"),
                Matchers.equalTo("Note must have a title"))))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());


        assertThat(noteRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithGetAndPathVariable_whenMockMVC_thenResponseNotFound() throws Exception {
        final int id = 7;

        mockMvc.perform(get("/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0]").value("Note with id: 7 doesn't exist"));

        assertThat(noteRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql"),
            @Sql(value = "classpath:data.sql")
    })
    void givenNoteURIWithDeleteAndPathVariable_whenMockMVC_thenResponseBAD_REQUEST() throws Exception {
        final int id = 7;

        mockMvc.perform(delete("/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0]").value("Note with id: " + id + " doesn't exist"));

        assertThat(noteRepository.findAll().size()).isEqualTo(5);
    }
}