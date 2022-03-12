package resource;

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
import util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Path("/spacemarines")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SpaceMarineResource {

    private final SpaceMarineService spaceMarineService = new SpaceMarineService();

    private final ExtractionService extractionService = new ExtractionService();

    @GET
    @Path("/chapters/group")
    public Response getChaptersGroupedBySpacemarines() {
        final List<ChapterInGroupElementDto> groupByChapter = spaceMarineService.getChaptersInGroup();
        final ChaptersInGroup chaptersInGroup = new ChaptersInGroup();
        chaptersInGroup.setGroup(groupByChapter);

        return Response.status(Response.Status.OK)
                .entity(chaptersInGroup)
                .build();
    }

    @GET
    @Path("/name/substr")
    public Response getSpacemarinesByNameSubstring(@QueryParam("nameSubstr") String nameSubstr) {
        if (nameSubstr == null) {
            final Messages messages = new Messages();
            messages.addNewMessage("nameSubstr - это обязательный параметр");

            return Response.status(Response.Status.BAD_REQUEST)
                .entity(messages)
                .build();
        }

        final Marines marines = spaceMarineService.getAllByNameSubstring(nameSubstr);

        return Response.status(Response.Status.OK)
                .entity(marines)
                .build();
    }

    @GET
    @Path("/weaponType/superior-group")
    public Response getSpacemarinesBySuperiorGroup(@QueryParam("weaponType") String weaponTypeString) {
        try {
            final Weapon weaponType = extractionService.extractWeaponTypeFromParam(weaponTypeString);
            final Marines marines = spaceMarineService.getAllByWeaponType(weaponType);

            return Response.status(Response.Status.OK)
                    .entity(marines)
                    .build();
        } catch (WrongRequestException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getErrors())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getSpacemarineById(@PathParam("id") String id) {
        try {
            final Long marineId = Long.parseLong(id);
            final Optional<SpaceMarine> spaceMarine = spaceMarineService.getById(marineId);

            if (spaceMarine.isPresent()) {
                return Response.status(Response.Status.OK)
                    .entity(spaceMarine.get())
                    .build();
            }

            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAllSpacemarines(@Context UriInfo uriInfo) {
        final Marines marines;
        try {
            final GetAllMethodParams params = extractionService.extractParams(uriInfo.getQueryParameters());

            marines = spaceMarineService.getAll(params);

            return Response.status(Response.Status.OK)
                .entity(marines)
                .build();
        } catch (WrongRequestException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(ex.getErrors())
                .build();
        }
    }

    @POST
    public Response createSpacemarine(@Context HttpServletRequest request) {
        try {
            final String body = ServletUtil.getBody(request);
            final SpaceMarineRaw spaceMarineRaw = Jaxb.fromStr(body, SpaceMarineRaw.class);
            SpaceMarine spaceMarine = spaceMarineRaw.toEntity();

            spaceMarine = spaceMarineService.create(spaceMarine);

            return Response.status(Response.Status.CREATED)
                    .entity(spaceMarine)
                    .build();

        } catch (WrongRequestException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getErrors())
                    .build();
        } catch (JAXBException | IOException e) {
            Messages messages = new Messages();
            messages.addNewMessage("Запрос некорректен. Невозможно выполнить парсинга тела запроса.");

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messages)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateSpacemarine(@Context HttpServletRequest request, @PathParam("id") String id) {
        try {
            final Long marineId = Long.parseLong(id);
            final String body = ServletUtil.getBody(request);
            final SpaceMarineRaw spaceMarineRaw = Jaxb.fromStr(body, SpaceMarineRaw.class);
            SpaceMarine spaceMarine = spaceMarineRaw.toEntity();
            spaceMarine = spaceMarineService.update(marineId, spaceMarine);

            if (spaceMarine != null) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (WrongRequestException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(ex.getErrors())
                .build();
        } catch (JAXBException | IOException e) {
            Messages messages = new Messages();
            messages.addNewMessage("Запрос некорректен. Невозможно выполнить парсинга тела запроса.");

            return Response.status(Response.Status.BAD_REQUEST)
                .entity(messages)
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSpacemarineById(@PathParam("id") String id) {
        try {
            final Long spacemarineId = Long.parseLong(id);
            final boolean deleteStatus = spaceMarineService.delete(spacemarineId);

            if (deleteStatus) return Response.status(Response.Status.NO_CONTENT).build();
            else throw new WrongRequestException(null);

        } catch (NumberFormatException | WrongRequestException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

