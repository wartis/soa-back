package service;

import exceptions.WrongRequestException;
import lombok.RequiredArgsConstructor;
import model.SpaceMarine;
import model.xmlLists.Messages;
import service.dto.PageDto;
import service.dto.PaginationParams;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PaginationService {

    private final ValidationService validationService = new ValidationService();

    private PageDto getPage(final List<SpaceMarine> spaceMarines, Integer pageNum, Integer pageSize) {
        if (pageSize == null) {
            pageSize = spaceMarines.size();
        }

        pageNum -= 1;
        final List<SpaceMarine> pageRecords = spaceMarines.stream()
            .skip((long) pageNum * pageSize)
            .limit(pageSize)
            .collect(Collectors.toList());

        return new PageDto(pageRecords, spaceMarines.size(), pageNum+1, pageSize);
    }

    public PageDto handlePagination(final List<SpaceMarine> spaceMarines, PaginationParams params)
        throws WrongRequestException
    {
        if (params.getPageSize() == null) {
            params.setPageSize(spaceMarines.size() + "");
        }

        PageDto pageDto = new PageDto(spaceMarines, spaceMarines.size(), params.getPageNum(), params.getPageSize());

        final Optional<Messages> messages = validationService.validatePaginationParams(pageDto);
        if (messages.isPresent()) {
            throw new WrongRequestException(messages.get());
        }

        pageDto = getPage(spaceMarines, params.getPageNum(), params.getPageSize());

        return pageDto;
    }

}
