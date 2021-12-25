package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "coordinates")

@Getter
@Setter

@Embeddable
public class Coordinates {

    @XmlElement
    private Float x; //Максимальное значение поля: 421, Поле не может быть null

    @XmlElement
    private Long y; //Максимальное значение поля: 873, Поле не может быть null
}
