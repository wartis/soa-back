package service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.SpaceMarine;

import java.util.Comparator;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum SortTypeEnum {
    ID(Comparator.comparingLong(SpaceMarine::getId), sm -> true),
    NAME(Comparator.comparing(SpaceMarine::getName), sm -> true),
    X((x1, x2) -> Float.compare(x1.getCoordinates().getX(), x2.getCoordinates().getX()), sm -> true),
    Y((y1, y2) -> Float.compare(y1.getCoordinates().getX(), y2.getCoordinates().getX()), sm -> true),
    HEALTH((sm1, sm2) -> Integer.compare(sm1.getHealth(), sm2.getHealth()), sm -> sm.getHealth() != null),
    CHAPTER_ID((sm1, sm2) -> Long.compare(sm1.getChapter().getId(), sm2.getChapter().getId()), sm -> sm.getChapter() != null),
    CHAPTER_NAME((sm1, sm2) -> sm1.getChapter().getName().compareTo(sm2.getChapter().getName()), sm ->sm.getChapter() != null && sm.getChapter().getName() != null),
    CHAPTER_PARENT_LEGION((sm1, sm2) -> sm1.getChapter().getParentLegion().compareTo(sm2.getChapter().getParentLegion()), sm ->sm.getChapter() != null && sm.getChapter().getParentLegion() != null),
    CHAPTER_MARINES_COUNT((sm1, sm2) -> Long.compare(sm1.getChapter().getMarinesCount(), sm2.getChapter().getMarinesCount()), sm -> sm.getChapter() != null),
    CHAPTER_WORLD((sm1, sm2) -> sm1.getChapter().getWorld().compareTo(sm2.getChapter().getWorld()), sm ->sm.getChapter() != null && sm.getChapter().getWorld() != null),
    CATEGORY((sm1, sm2) -> Integer.compare(sm1.getCategory().ordinal(), sm2.getCategory().ordinal()), sm -> sm.getCategory() != null),
    WEAPON_TYPE((sm1, sm2) -> Integer.compare(sm1.getWeaponType().ordinal(), sm2.getWeaponType().ordinal()), sm -> sm.getWeaponType() != null),
    MELEE_WEAPON((sm1, sm2) -> Integer.compare(sm1.getMeleeWeapon().ordinal(), sm2.getMeleeWeapon().ordinal()), sm -> sm.getMeleeWeapon() != null);

    private final Comparator<SpaceMarine> comparator;

    public final Predicate<SpaceMarine> notNull;

}

