package org.denys.hudymov.aionystesttask.repository;

import org.denys.hudymov.aionystesttask.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
