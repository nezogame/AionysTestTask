package org.denys.hudymov.aionystesttask.config;

import lombok.extern.slf4j.Slf4j;
import org.denys.hudymov.aionystesttask.entity.Note;
import org.denys.hudymov.aionystesttask.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(NoteRepository noteRepository){
        return args -> {
            var note = Note.builder()
                    .id(null)
                    .title("First note")
                    .text("Text about first note")
                    .build();
            log.info("Preloading " +  noteRepository.save(note));

            note = Note.builder()
                    .id(null)
                    .title("Second note")
                    .text("Text about second note")
                    .build();
            log.info("Preloading " +  noteRepository.save(note));

            note = Note.builder()
                    .id(null)
                    .title("Third note")
                    .text("Text about third note")
                    .build();
            log.info("Preloading " +  noteRepository.save(note));

            note = Note.builder()
                    .id(null)
                    .title("Fourth note")
                    .text("Text about fourth note")
                    .build();
            log.info("Preloading " +  noteRepository.save(note));
        };
    }
}
