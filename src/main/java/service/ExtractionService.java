package service;

import exceptions.WrongRequestException;
import model.Weapon;
import model.xmlLists.Messages;
import service.dto.FiltrationParams;
import service.dto.GetAllMethodParams;
import service.dto.PaginationParams;
import service.dto.SortingParams;

import javax.ws.rs.core.MultivaluedMap;

public class ExtractionService {

    public String extractNameSubstring(MultivaluedMap<String, String> map) throws WrongRequestException {
        final Messages messages = new Messages();


        if (map.size() > 1) {
            messages.addNewMessage("Запрос поддерживает только входной параметр nameSubstr");
            throw new WrongRequestException(messages);
        }

        if (map.size() == 0 || !map.containsKey("nameSubstr")) {
            messages.addNewMessage("Запрос обязательно должен содержать параметр nameSubstr");
            throw new WrongRequestException(messages);
        }

        return map.get("nameSubstr").get(0);
    }


    public GetAllMethodParams extractParams(MultivaluedMap<String, String> paramsMap) throws WrongRequestException {
        final Messages messages = new Messages();

        final GetAllMethodParams params = new GetAllMethodParams();

        params.getFiltrationParams().setMessages(messages);
        params.getPaginationParams().setMessages(messages);
        params.getSortingParams().setMessages(messages);


        paramsMap.forEach((key, value) -> {
            if (FiltrationParams.FILTRATION_PARAMS.contains(key)) {
                params.getFiltrationParams().setParam(key, value.get(0));
            } else if (SortingParams.SORT_PARAMS.contains(key)) {
                params.getSortingParams().setParam(key, value.get(0));
            } else if (PaginationParams.PAGINATION_PARAMS.contains(key)) {
                params.getPaginationParams().setParam(key, value.get(0));
            } else {
                messages.addNewMessage("Указан несуществующий параметр (" + key + ")");
            }
        });

        if (!messages.getMessages().isEmpty()) {
            throw new WrongRequestException(messages);
        }

        return params;
    }



    public Weapon extractWeaponTypeFromParam(MultivaluedMap<String, String> map) throws WrongRequestException {
        final Messages messages = new Messages();

        if (map.size() > 1) {
            messages.addNewMessage("Запрос поддерживает только входной параметр weaponType");
            throw new WrongRequestException(messages);
        }

        if (map.size() == 0 || !map.containsKey("weaponType")) {
            messages.addNewMessage("Запрос обязательно должен содержать параметр weaponType");
            throw new WrongRequestException(messages);
        }

        final String weaponTypeStr = map.get("weaponType").get(0);
        try {
            final Weapon weapon = Weapon.valueOf(weaponTypeStr);
            if (weapon == Weapon.NONE) throw new Exception();
            return weapon;
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение weaponType. Скорее всего данный weaponType не существует.");
            throw new WrongRequestException(messages);
        }
    }
}
