package exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.xmlLists.Messages;

@Getter
@RequiredArgsConstructor
public class WrongRequestException extends Exception {
    private final Messages errors;
}
