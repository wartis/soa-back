package servlets;

import exceptions.WrongRequestException;
import model.SpaceMarine;
import model.Weapon;
import model.rawDto.SpaceMarineRaw;
import model.xmlLists.ChaptersInGroup;
import model.xmlLists.Marines;
import model.xmlLists.Messages;
import service.ExtractionService;
import service.SpaceMarineService;
import service.dto.ChapterInGroupElementDto;
import service.dto.GetAllMethodParams;
import util.Jaxb;
import util.ResponseBuilder;
import util.ServletUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/spacemarines/*")
public class SpaceMarineServlet extends HttpServlet {

    private final SpaceMarineService spaceMarineService = new SpaceMarineService();

    private final ExtractionService extractionService = new ExtractionService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/xml");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setStatus(200);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            handleFindAll(request, response);
            return;
        }

        if (pathInfo.equals("/chapters/group")) {
            handleChaptersGroups(request, response);
            return;
        }

        if (pathInfo.equals("/name/substr")) {
            handleFindAllNameSubstr(request, response);
            return;
        }

        if (pathInfo.equals("/weaponType/superior-group")) {
            handleFindAllByWeaponType(request, response);
            return;
        }

        if (ServletUtil.checkIfPathContainsId(pathInfo)) {
            handleFindById(request, response);
            return;
        }

        ResponseBuilder.create(response)
            .setStatus(404);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String body = ServletUtil.getBody(request);
            final SpaceMarineRaw spaceMarineRaw = Jaxb.fromStr(body, SpaceMarineRaw.class);
            SpaceMarine spaceMarine = spaceMarineRaw.toEntity();

            spaceMarine = spaceMarineService.create(spaceMarine);

            ResponseBuilder.create(response)
                .setStatus(201)
                .setBody(spaceMarine);

        } catch (WrongRequestException ex) {
            ResponseBuilder.create(response)
                    .setStatus(400)
                    .setBody(ex.getErrors());
        } catch (JAXBException | IOException e) {
            Messages messages = new Messages();
            messages.addNewMessage("Запрос некорректен. Невозможно выполнить парсинга тела запроса.");

            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(messages);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            final Long id = ServletUtil.getIdFromRequest(request);
            final String body = ServletUtil.getBody(request);
            final SpaceMarineRaw spaceMarineRaw = Jaxb.fromStr(body, SpaceMarineRaw.class);
            SpaceMarine spaceMarine = spaceMarineRaw.toEntity();
            spaceMarine = spaceMarineService.update(id, spaceMarine);

            //todo переписать
            if (spaceMarine != null) {
                response.setStatus(204);
            } else {
                response.setStatus(404);
            }
        } catch (WrongRequestException ex) {
            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(ex.getErrors());
        } catch (JAXBException | IOException e) {
            Messages messages = new Messages();
            messages.addNewMessage("Запрос некорректен. Невозможно выполнить парсинга тела запроса.");

            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(messages);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        if (ServletUtil.checkIfPathContainsId(request.getPathInfo())) {
            final Long id = ServletUtil.getIdFromRequest(request);
            final boolean deleteStatus = spaceMarineService.delete(id);

            if (deleteStatus) ResponseBuilder.create(response).setStatus(204);
            else ResponseBuilder.create(response).setStatus(404);

            return;
        }

        ResponseBuilder.create(response).setStatus(404);
    }

    private void handleFindAll(HttpServletRequest request, HttpServletResponse response) {
        final Marines marines;
        try {
            final GetAllMethodParams params = extractionService.extractParams(request);

            marines = spaceMarineService.getAll(params);
            ResponseBuilder.create(response)
                .setStatus(200)
                .setBody(marines);

        } catch (WrongRequestException ex) {
            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(ex.getErrors());
        }
    }

    private void handleChaptersGroups(HttpServletRequest request, HttpServletResponse response) {
        final List<ChapterInGroupElementDto> groupByChapter = spaceMarineService.getChaptersInGroup();
        final ChaptersInGroup chaptersInGroup = new ChaptersInGroup();
        chaptersInGroup.setGroup(groupByChapter);

        ResponseBuilder.create(response)
            .setStatus(200)
            .setBody(chaptersInGroup);
    }

    private void handleFindById(HttpServletRequest request, HttpServletResponse response) {
        final Long marineId = ServletUtil.getIdFromRequest(request);
        final Optional<SpaceMarine> spaceMarine = spaceMarineService.getById(marineId);

        if (spaceMarine.isPresent()) {
            ResponseBuilder.create(response)
                .setStatus(200)
                .setBody(spaceMarine.get());

        } else {
            ResponseBuilder.create(response)
                .setStatus(404);
        }
    }

    private void handleFindAllByWeaponType(HttpServletRequest request, HttpServletResponse response) {
        try {
            final Weapon weaponType = extractionService.extractWeaponTypeFromParam(request);
            final Marines marines = spaceMarineService.getAllByWeaponType(weaponType);

            ResponseBuilder.create(response)
                .setStatus(200)
                .setBody(marines);
        } catch (WrongRequestException ex) {
            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(ex.getErrors());
        }
    }


    private void handleFindAllNameSubstr(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String substr   = extractionService.extractNameSubstring(request);
            final Marines marines = spaceMarineService.getAllByNameSubstring(substr);

            ResponseBuilder.create(response)
                .setStatus(200)
                .setBody(marines);
        } catch (WrongRequestException ex) {
            ResponseBuilder.create(response)
                .setStatus(400)
                .setBody(ex.getErrors());
        }
    }


}
