package service.dto;

import lombok.Getter;
import lombok.Setter;
import model.xmlLists.Messages;

import java.util.Arrays;
import java.util.List;

@Getter
public class SortingParams {

    public static List<String> SORT_PARAMS = Arrays.asList("sortType", "sortOrder");

    @Setter
    private Messages messages;

    private SortTypeEnum  sortType = SortTypeEnum.ID;;
    private SortOrderEnum sortOrder = SortOrderEnum.ASC;;

    public void setParam(String paramName, String value) {
        if (paramName.equals("sortType")) {
            setSortType(value);
            return;
        }

        if (paramName.equals("sortOrder")) {
            setSortOrder(value);
        }
    }


    public void setSortType(String sortType) {
        try {
            this.sortType = SortTypeEnum.valueOf(sortType);
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение sortType");
        }
    }


    public void setSortOrder(String sortOrder) {
        try {
            this.sortOrder = SortOrderEnum.valueOf(sortOrder);
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение sortOrder");
        }
    }
}
