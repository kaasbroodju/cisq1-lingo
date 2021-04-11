package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String id;

    @BeforeEach
    void createNewGameBeforeEach() throws Exception {
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/lingo/game/start");

        id = mockMvc.perform(createRequest).andReturn().getResponse().getContentAsString().split(",")[0].split(":")[1];
        System.out.println("qwerty" + id);
    }

    @Test
    @DisplayName("Create a game.")
    void createNewGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/start");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get a unknown game")
    void getUnknownGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/lingo/game/{id}", "-1");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get a game using the id of create game url")
    void getGameAfterCreatingIt() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/lingo/game/{id}", id);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Get a round using the id of create game url")
    void getRoundAfterCreatingIt() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/lingo/game/{id}/round", id);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Create a new round while one is still ongoing")
    void createNewRoundWhileOneIsStillOnGoing() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/{id}/round", id);

        mockMvc.perform(request)
                .andExpect(status().isNotModified());
    }

    @Test
    @DisplayName("Create a new round even though the game has failed.")
    void createNewRoundWhenGameFailed() throws Exception {
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingo/game/{id}/guess", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"guess\" : \"tests\"}");

        for (int i = 0; i < 6; i++) {
            mockMvc.perform(guessRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isNotEmpty());

        }

        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/{id}/round", id);

        mockMvc.perform(request)
                .andExpect(status().isNotModified());
    }

    @Test
    @DisplayName("Fail to answer after round is over.")
    void makeGuessAfterRoundIsOver() throws Exception {
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingo/game/{id}/guess", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"guess\" : \"tests\"}");

        for (int i = 0; i < 6; i++) {
            System.out.println();
            mockMvc.perform(guessRequest)
                    .andExpect(status().isOk());
        }

        mockMvc.perform(guessRequest)
                .andExpect(status().isNotModified());
    }
}