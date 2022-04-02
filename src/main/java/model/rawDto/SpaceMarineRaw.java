package model.rawDto;

import exceptions.WrongRequestException;
import lombok.Getter;
import lombok.Setter;
import model.enums.AstartesCategory;
import model.Chapter;
import model.Coordinates;
import model.enums.MeleeWeapon;
import model.SpaceMarine;
import model.enums.Weapon;
import model.xmlLists.Messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SpaceMarine")

@Getter
@Setter
public class SpaceMarineRaw {

    @XmlElement
    private String id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private CoordinatesRaw coordinates;

    @XmlElement
    private String health; //Поле может быть null, Значение поля должно быть больше 0

    @XmlElement
    private String category;

    @XmlElement
    private String weaponType; //Поле может быть null

    @XmlElement
    private String meleeWeapon; //Поле может быть null

    @XmlElement
    private ChapterRaw chapter; //Поле может быть null


    public SpaceMarine toEntity() throws WrongRequestException {
        //господи, какой же это говнокод
        final Messages messages = new Messages();
        final SpaceMarine spaceMarine = new SpaceMarine();

        spaceMarine.setName(name);
        try {
            if (id != null) {
                spaceMarine.setId(Long.parseLong(id));
            }
        } catch (Exception ex) {
            messages.addNewMessage("Поле id должно быть целым числом.");
        }

        try {
            if (health != null) {
                if (health.equals("undefined")) {
                    health = null;
                } else {
                    spaceMarine.setHealth(Integer.parseInt(health));
                }
            }
        } catch (Exception ex) {
            messages.addNewMessage("Поле health должно быть целым числом.");
        }

        try {
            if (category != null) {
                spaceMarine.setCategory(AstartesCategory.valueOf(category));
            }
        } catch (Exception ex) {
            messages.addNewMessage("Несуществующее значение для category ");
        }

        try {
            if (weaponType != null) {
                spaceMarine.setWeaponType(Weapon.valueOf(weaponType));
            }
        } catch (Exception ex) {
            messages.addNewMessage("Несуществующее значение для weaponType");
        }

        try {
            if (meleeWeapon != null) {
                spaceMarine.setMeleeWeapon(MeleeWeapon.valueOf(meleeWeapon));
            }
        } catch (Exception ex) {
            messages.addNewMessage("Несуществующее значение для meleeWeapon ");
        }

        if (coordinates != null) {
            Coordinates coordinatesEntity = new Coordinates();

            try {
                if (coordinates.getX() != null) {
                    coordinatesEntity.setX(Float.parseFloat(coordinates.getX()));
                }
            } catch (Exception ex) {
                messages.addNewMessage("X должен быть вещественным числом");
            }

            try {
                if (coordinates.getY() != null) {
                    coordinatesEntity.setY(Long.parseLong(coordinates.getY()));
                }
            } catch (Exception ex) {
                messages.addNewMessage("Y должен быть целым числом");
            }

            spaceMarine.setCoordinates(coordinatesEntity);
        }

        if (chapter != null) {
            Chapter chapterEntity = new Chapter();

            try {
                if (chapter.getId() != null) {
                    if (chapter.getId().equals("undefined")) {
                        chapter.setId(null);
                    }

                    chapterEntity.setId(Long.parseLong(chapter.getId()));
                }
            } catch (Exception ex) {
                messages.addNewMessage("Chapter-id должно быть целым числом");
            }

            try {
                if (chapter.getMarinesCount() != null) {
                    chapterEntity.setMarinesCount(Integer.parseInt(chapter.getMarinesCount()));
                }
            } catch (Exception ex) {
                messages.addNewMessage("Chapter-MarinesCount должно быть целым числом");
            }

            chapterEntity.setName(chapter.getName());
            chapterEntity.setWorld(chapter.getWorld());
            chapterEntity.setParentLegion(chapter.getParentLegion());

            spaceMarine.setChapter(chapterEntity);
        }

        if (!messages.getMessages().isEmpty()) {
            throw new WrongRequestException(messages);
        }

        return spaceMarine;
    }

}
