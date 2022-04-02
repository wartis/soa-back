package service.ancillary;

import model.SpaceMarine;
import model.xmlLists.Messages;
import service.dto.PageDto;

import java.util.Optional;

public class ValidationService {

    public Optional<Messages> checkSpaceMarine(SpaceMarine spaceMarine) {
        final Messages messages = new Messages();

        if (spaceMarine.getName() == null) {
            messages.addNewMessage("Поле name не может быть null.");
        }
        if (spaceMarine.getName() != null && spaceMarine.getName().trim().isEmpty()) {
            messages.addNewMessage("Поле name не может быть пустым.");
        }

        if (spaceMarine.getCoordinates() == null) {
            messages.addNewMessage("Координаты не могут быть null.");
        }

        if (spaceMarine.getCoordinates() != null) {
            if (spaceMarine.getCoordinates().getX() > 421) {
                messages.addNewMessage("X не может быть больше 421");
            }

            if (spaceMarine.getCoordinates().getY() == null) {
                messages.addNewMessage("Y не может быть null");
            }

            if (spaceMarine.getCoordinates().getY() != null && spaceMarine.getCoordinates().getY() > 873) {
                messages.addNewMessage("Y не может быть больше 873");
            }
        }

        if (spaceMarine.getHealth() != null && spaceMarine.getHealth() <= 0) {
            messages.addNewMessage("Health не может быть меньше 0");
        }

        if (spaceMarine.getChapter() != null) {
            if (spaceMarine.getChapter().getId() == null && spaceMarine.getChapter().getName() == null) {
                messages.addNewMessage("Chapter-name не может быть null");
            }

            if (spaceMarine.getChapter().getId() == null && spaceMarine.getChapter().getName() != null && spaceMarine.getChapter().getName().trim().isEmpty()) {
                messages.addNewMessage("Chapter-name не может быть пустым");
            }

            if (spaceMarine.getChapter().getId() == null && spaceMarine.getChapter().getWorld() == null) {
                messages.addNewMessage("Chapter-world не может быть null");
            }

            if (spaceMarine.getChapter().getMarinesCount() > 1000 || spaceMarine.getChapter().getMarinesCount() < 0) {
                messages.addNewMessage("Chapter-marines count должно быть не меньше 0 и не больше 1000");
            }
        }

        if (messages.getMessages().isEmpty()) return Optional.empty();
        return Optional.of(messages);
    }

    public Optional<Messages> validatePaginationParams(PageDto pageDto) {
        final Messages messages = new Messages();

        if (pageDto.getCurPage() <= 0) {
            messages.addNewMessage("Страница не может быть отрицательным числом или нулём.");
        }
        if (pageDto.getPageSize() < 0) {
            messages.addNewMessage("Размер страницы не может быть отрицательным числом.");
        }

        if (pageDto.getCurPage() > pageDto.getTotalPages()) {
            messages.addNewMessage("Размер страницы не может быть больше, чем доступное число страниц.");
        }

        return messages.getMessages().isEmpty() ? Optional.empty() : Optional.of(messages);
    }
}
