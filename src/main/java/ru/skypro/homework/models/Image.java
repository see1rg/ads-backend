package ru.skypro.homework.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    private String id;
    private Integer fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(fileSize, image.fileSize) && Objects.equals(mediaType, image.mediaType) && Arrays.equals(data, image.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileSize, mediaType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
