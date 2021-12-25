package model.rawDto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chapter")

@Getter
@Setter
public class ChapterRaw {
    @XmlElement
    private String id;

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private String parentLegion;

    @XmlElement
    private String marinesCount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

    @XmlElement
    private String world; //Поле может быть null
}

