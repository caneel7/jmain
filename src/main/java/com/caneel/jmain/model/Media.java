package com.caneel.jmain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "media")
@Getter
@Setter
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String directory;

    @Column(nullable = false)
    private String value;

    @Column(name = "mime_type")
    private String mimeType;

    @Column()
    private int size;

    @Column(name = "is_active")
    private boolean isActive;

    public String getURL()
    {
        return this.directory + "/" + this.value;
    }

}
