package com.caneel.jmain.repository;

import com.caneel.jmain.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository  extends JpaRepository<Media, String> {

    Optional<Media> findById(String id);

    Media save(Media media);

}
