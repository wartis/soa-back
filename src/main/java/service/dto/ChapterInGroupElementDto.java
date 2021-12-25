package service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Chapter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChapterInGroupElementDto {
    private Chapter chapter;
    private int number;
}
