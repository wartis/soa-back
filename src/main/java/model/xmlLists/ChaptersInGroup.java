package model.xmlLists;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.ElementList;
import service.dto.ChapterInGroupElementDto;

import java.util.List;

@Getter
@Setter
public class ChaptersInGroup {
    @ElementList
    private List<ChapterInGroupElementDto> group;
}
