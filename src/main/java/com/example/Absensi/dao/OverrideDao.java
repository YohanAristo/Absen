package com.example.Absensi.dao;

import com.example.Absensi.model.Override;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface OverrideDao extends JpaRepository<Override, String> {
    List<Override> findOverridesByUserIdOrderByDatesAsc(String id);
    List<Override> findOverridesByUserIdAndDatesBetween(String id, String before, String after);

    List<Override> findAllByOrderByDatesAsc();


    @Transactional
    void deleteOverridesByUserId(String id);
}
