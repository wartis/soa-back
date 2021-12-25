package service;

import model.AstartesCategory;
import model.MeleeWeapon;
import model.SpaceMarine;
import model.Weapon;
import service.dto.FiltrationParams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FilterSpaceMarineService {

    public List<SpaceMarine> handleFiltration(List<SpaceMarine> marines, FiltrationParams params) {
        final Stream<SpaceMarine> spaceMarineStream = marines.stream()
            .filter(marine -> containsString(marine.getName(), params.getName()))
            .filter(marine -> period(params.getMinX(), params.getMaxX(), marine.getCoordinates().getX()))
            .filter(marine -> period(params.getMinId(), params.getMaxId(), marine.getId()))
            .filter(marine -> period(params.getMinY(), params.getMaxY(), marine.getCoordinates().getY()))
            .filter(marine -> marine.getHealth() == null ||
                period(params.getMinHealth(), params.getMaxHealth(), marine.getHealth()))
            .filter(marine -> {
                if (params.getCategory().size() == 1) {
                    return inCategoriesList(marine.getCategory(), params.getCategory());
                }

                return marine.getCategory() == null || inCategoriesList(marine.getCategory(), params.getCategory());
            })
            .filter(marine -> {
                if (params.getWeapon().size() == 1) {
                    return inWeaponsList(marine.getWeaponType(), params.getWeapon());
                }
                return marine.getWeaponType() == null || inWeaponsList(marine.getWeaponType(), params.getWeapon());
            })
            .filter(marine -> {
                if (params.getMeleeWeapon().size() == 1) {
                    return inMeleeWeaponsList(marine.getMeleeWeapon(), params.getMeleeWeapon());
                }

                return marine.getMeleeWeapon() == null ||
                    inMeleeWeaponsList(marine.getMeleeWeapon(), params.getMeleeWeapon());
            });

            if (params.isChapterFiltrationSet()) {
                return spaceMarineStream.filter(marine -> marine.getChapter() != null)
                    .filter(marine -> marine.getChapter().getName() == null || containsString(marine.getChapter().getName(), params.getChapterName()))
                    .filter(marine -> marine.getChapter().getWorld() == null || containsString(marine.getChapter().getWorld(), params.getChapterWorld()))
                    .filter(marine -> marine.getChapter().getParentLegion() == null || containsString(marine.getChapter().getParentLegion(), params.getChapterParLeg()))
                    .filter(marine -> period(params.getChapterMinId(), params.getChapterMaxId(), marine.getChapter().getId()))
                    .filter(marine -> period(params.getChapterMinMarCount(), params.getChapterMaxMarCount(), marine.getChapter().getMarinesCount()))
                    .collect(Collectors.toList());
            }

            return spaceMarineStream.collect(Collectors.toList());
    }

    private boolean containsString(String source, String target) {
        return source.contains(target);
    }

    private boolean inCategoriesList(AstartesCategory category, List<AstartesCategory> categories) {
        return categories.contains(category);
    }

    private boolean inWeaponsList(Weapon weapon, List<Weapon> weapons) {
        return weapons.contains(weapon);
    }

    private boolean inMeleeWeaponsList(MeleeWeapon weapon, List<MeleeWeapon> weapons) {
        return weapons.contains(weapon);
    }

    private boolean period(long leftConstraint, long rightConstraint, long value) {
        return leftConstraint <= value && value <= rightConstraint;
    }

    private boolean period(float leftConstraint, float rightConstraint, float value) {
        return leftConstraint <= value && value <= rightConstraint;
    }



}
