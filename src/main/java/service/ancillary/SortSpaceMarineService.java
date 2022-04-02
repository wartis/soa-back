package service.ancillary;

import model.SpaceMarine;
import service.dto.SortOrderEnum;
import service.dto.SortingParams;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SortSpaceMarineService {

    public List<SpaceMarine> handleSorting(List<SpaceMarine> spaceMarines, SortingParams params) {
        final List<SpaceMarine> notNullValues = spaceMarines.stream()
            .filter(params.getSortType().notNull)
            .sorted(params.getSortType().getComparator())
            .collect(Collectors.toList());

        final List<SpaceMarine> nullValues = spaceMarines.stream()
            .filter((sm) -> !params.getSortType().notNull.test(sm))
            .collect(Collectors.toList());


        if (params.getSortOrder() == SortOrderEnum.ASC) {
            notNullValues.addAll(nullValues);
            return notNullValues;
        }

        Collections.reverse(notNullValues);
        notNullValues.addAll(nullValues);
        return notNullValues;
    }

}
