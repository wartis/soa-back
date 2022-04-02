package service;

import dao.ChapterDAO;
import dao.SpaceMarineDAO;
import exceptions.WrongRequestException;
import model.Chapter;
import model.SpaceMarine;
import model.enums.Weapon;
import model.xmlLists.Marines;
import model.xmlLists.Messages;
import service.ancillary.PaginationService;
import service.ancillary.SortSpaceMarineService;
import service.ancillary.SpaceMarineFiltrationService;
import service.ancillary.ValidationService;
import service.dto.ChapterInGroupElementDto;
import service.dto.GetAllMethodParams;
import service.dto.PageDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceMarineService {

    private final SpaceMarineDAO spaceMarineDao = new SpaceMarineDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();
    private final SpaceMarineFiltrationService filterService = new SpaceMarineFiltrationService();
    private final SortSpaceMarineService sortService = new SortSpaceMarineService();
    private final PaginationService paginationService = new PaginationService();
    private final ValidationService validationService = new ValidationService();


    public Optional<SpaceMarine> getById(Long id) {
        return spaceMarineDao.getSpaceMarine(id);
    }

    public Marines getAll(GetAllMethodParams params)
        throws WrongRequestException {

        List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines();

        PageDto page;
        if (spaceMarines == null) {
            page = PageDto.getEmptyPage();
        } else {
            spaceMarines = filterService.handleFiltration(spaceMarines, params.getFiltrationParams());
            spaceMarines = sortService.handleSorting(spaceMarines, params.getSortingParams());
            page = paginationService.handlePagination(spaceMarines, params.getPaginationParams());
        }

        final Marines marines = new Marines();
        marines.setMarines(page.getMarines());
        marines.setPageSize(page.getPageSize());
        marines.setPageNum(page.getCurPage());
        marines.setTotalElements(page.getMarines().size());

        return marines;
    }

    public SpaceMarine create(SpaceMarine spaceMarine) throws WrongRequestException {
        spaceMarine.setId(null); //т.к. сущность новая у неё не может быть id
        spaceMarine.resolveEnums();

        final Optional<Messages> optionalMessage = validationService.checkSpaceMarine(spaceMarine);
        if (optionalMessage.isPresent()) {
            throw new WrongRequestException(optionalMessage.get());
        }

        final Chapter chapter = spaceMarine.getChapter();

        if (chapter != null && chapter.getId() != null) {
            final Optional<Chapter> dbChapter = chapterDAO.getChapter(chapter.getId());
            if (dbChapter.isPresent()) {
                spaceMarine.setChapter(dbChapter.get());
            } else {
                Messages messages = new Messages();
                messages.addNewMessage("Не существует chapter'а с указанным id");
                throw new WrongRequestException(messages);
            }
        }

        final long smId = spaceMarineDao.createSpaceMarine(spaceMarine);

        return spaceMarineDao.getSpaceMarine(smId).get();
    }

    public SpaceMarine update(Long id, SpaceMarine spaceMarine)
        throws WrongRequestException {

        spaceMarine.setId(id);
        spaceMarine.resolveEnums();

        final Optional<Messages> optionalMessage = validationService.checkSpaceMarine(spaceMarine);
        if (optionalMessage.isPresent()) {
            throw new WrongRequestException(optionalMessage.get());
        }

        final Optional<SpaceMarine> spaceMarineFromDb = spaceMarineDao.getSpaceMarine(spaceMarine.getId());

        if (spaceMarineFromDb.isPresent()) {
            final Chapter chapter = spaceMarine.getChapter();

            if (chapter != null && chapter.getId() != null) {
                final Optional<Chapter> dbChapter = chapterDAO.getChapter(chapter.getId());
                if (dbChapter.isPresent()) {
                    spaceMarine.setChapter(chapter);
                } else {
                    Messages messages = new Messages();
                    messages.addNewMessage("Не существует chapter'а с указанным id");
                    throw new WrongRequestException(messages);
                }
            }


            final SpaceMarine marineDb = spaceMarineFromDb.get();

            spaceMarine.setCreationDate(marineDb.getCreationDate());
            spaceMarineDao.updateSpaceMarine(spaceMarine);
            return spaceMarine;
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        if (spaceMarineDao.getSpaceMarine(id).isPresent()) {
            return spaceMarineDao.deleteSpaceMarine(id);
        }

        return false;
    }

    public List<ChapterInGroupElementDto> getChaptersInGroup() {
        final Map<Chapter, List<SpaceMarine>> groupedMarines = spaceMarineDao.getAllSpaceMarines().stream()
            .filter(marine -> marine.getChapter() != null)
            .collect(Collectors.groupingBy(SpaceMarine::getChapter));

        return groupedMarines.entrySet().stream()
            .map(el -> new ChapterInGroupElementDto(el.getKey(), el.getValue().size()))
            .collect(Collectors.toList());
    }

    public Marines getAllByNameSubstring(String substr) {
        final Marines marines = new Marines();
        final List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines()
            .stream()
            .filter(spaceMarine -> spaceMarine.getName().contains(substr))
            .collect(Collectors.toList());

        marines.setMarines(spaceMarines);

        return marines;
    }

    public Marines getAllByWeaponType(Weapon weaponType) {
        final Marines marines = new Marines();
        final List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines()
            .stream()
            .filter(spaceMarine -> spaceMarine.getWeaponType() != null && spaceMarine.getWeaponType() != Weapon.NONE)
            .filter(spaceMarine -> spaceMarine.getWeaponType().ordinal() > weaponType.ordinal())
            .collect(Collectors.toList());

        marines.setMarines(spaceMarines);

        return marines;
    }
}
