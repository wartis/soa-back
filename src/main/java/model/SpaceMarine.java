package model;


import lombok.Getter;
import lombok.Setter;
import model.enums.AstartesCategory;
import model.enums.MeleeWeapon;
import model.enums.Weapon;
import org.hibernate.annotations.CreationTimestamp;
import util.LocalDateTimeAdapter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SpaceMarine")

@Getter
@Setter

@Entity
@Table(name = "spacemarine")
public class SpaceMarine {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    @Column(name = "name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "x", column = @Column(name = "coordinates_x", nullable = false)),
        @AttributeOverride(name = "y", column = @Column(name = "coordinates_y", nullable = false))
    })
    private Coordinates coordinates;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @XmlElement
    @Column(name = "health")
    private Integer health; //Поле может быть null, Значение поля должно быть больше 0

    @XmlElement
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private AstartesCategory category;

    @XmlElement
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type")
    private Weapon weaponType; //Поле может быть null

    @XmlElement
    @Enumerated(EnumType.STRING)
    @Column(name = "melee_weapon")
    private MeleeWeapon meleeWeapon; //Поле может быть null

    @XmlElement
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter; //Поле может быть null

    public void resolveEnums() {
        if (this.getCategory() != null && this.getCategory() == AstartesCategory.NONE) {
            this.setCategory(null);
        }

        if (this.getMeleeWeapon() != null && this.getMeleeWeapon() == MeleeWeapon.NONE) {
            this.setMeleeWeapon(null);
        }

        if (this.getWeaponType() != null && this.getWeaponType() == Weapon.NONE) {
            this.setWeaponType(null);
        }
    }

}
