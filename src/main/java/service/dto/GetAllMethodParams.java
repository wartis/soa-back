package service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllMethodParams {
    private FiltrationParams filtrationParams = new FiltrationParams();

    private SortingParams sortingParams = new SortingParams();

    private PaginationParams paginationParams = new PaginationParams();
}
