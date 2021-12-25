package model.xmlLists;

import lombok.Getter;
import lombok.Setter;
import model.SpaceMarine;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

@Getter
@Setter
public class Marines {

    @Element
    private int pageSize;

    @Element
    private int pageNum;

    @Element
    private int totalElements;

    @ElementList
    private List<SpaceMarine> marines;
}
