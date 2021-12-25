package model.xmlLists;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.ElementList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errors")

@Getter
@Setter
public class Messages {
    @ElementList(entry = "message")
    private List<String> messages = new ArrayList<>();

    public void addNewMessage(String message) {
        messages.add(message);
    }

}
