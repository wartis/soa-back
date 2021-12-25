package model.rawDto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "coordinates")

@Getter
@Setter
public class CoordinatesRaw {

    @XmlElement
    private String x; //Максимальное значение поля: 421, Поле не может быть null

    @XmlElement
    private String y; //Максимальное значение поля: 873, Поле не может быть null
}
