package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chapter")

@Getter
@Setter

@Entity
@Table(name = "chapter")
public class Chapter {
    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @XmlElement
    @Column(name = "name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    @Column(name = "parentLegion")
    private String parentLegion;

    @XmlElement
    @Column(name = "marinesCount")
    private int marinesCount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

    @XmlElement
    @Column(name = "world")
    private String world; //Поле может быть null

}
