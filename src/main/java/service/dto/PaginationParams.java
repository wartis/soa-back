package service.dto;

import lombok.Getter;
import lombok.Setter;
import model.xmlLists.Messages;

import java.util.Arrays;
import java.util.List;

@Getter
public class PaginationParams {

    public static List<String> PAGINATION_PARAMS = Arrays.asList("pageNum", "pageSize");

    @Setter
    private Messages messages;

    private Integer pageNum = 1;
    private Integer pageSize = null;

    public void setParam(String paramName, String value) {
        if (paramName.equals("pageNum")) {
            setPageNum(value);
            return;
        }

        if (paramName.equals("pageSize")) {
            setPageSize(value);
        }
    }


    public void setPageNum(String pageNum) {
        try {
            this.pageNum = Integer.parseInt(pageNum);
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение pageNum");
        }
    }


    public void setPageSize(String pageSize) {
        try {
            this.pageSize = Integer.parseInt(pageSize);
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение pageSize");
        }
    }
}
