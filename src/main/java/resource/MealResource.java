package resource;

import exception.MealException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Meal;
import model.MealOrder;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import repository.MealRepository;
import java.util.List;



@Path("/meal/")
public class MealResource {
    @Inject
    private MealRepository mealRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMeal")
    public Response addMeal(Meal meal) {
        Meal m = mealRepository.createMeal(meal);

        return Response.ok().entity(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMealOrder")
    public Response addMealOrder(MealOrder mealOrder) {
        MealOrder mo = null;

        try {
            mo = mealRepository.createMealOrder(mealOrder);
        } catch (MealException e) {
            return Response.ok().entity(e.getMessage()).build();
        }

        return Response.ok().entity(mo).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllMeals")
    public Response getAllMeals() {
        List<Meal> meals = mealRepository.getMeals();

        return Response.ok().entity(meals).build();
    }

    @POST
    @Path("/{mealId}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadMealImage(dto.MultipartRequest requestData, @PathParam("mealId") Long mealId) {

        Meal m = mealRepository.findMealById(mealId);

        if(m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Obrok sa ID: " + mealId + " nije pronadjen")
                    .build();
        }

        if (requestData == null || requestData.getFile() == null ||
                requestData.getFile().fileName() == null || requestData.getFile().fileName().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Fajl nije poslat ili je neispravan u 'file' delu forme.")
                    .build();
        }

        FileUpload fileUpload = requestData.getFile();
        String originalFileName = fileUpload.fileName();

        String fileExtension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0 && i < originalFileName.length() - 1) {
            fileExtension = originalFileName.substring(i + 1).toLowerCase();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Fajl nema validnu ekstenziju.")
                    .build();
        }

        List<String> allowedExtensions = List.of("pdf", "jpg", "jpeg", "png");
        if (!allowedExtensions.contains(fileExtension)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Nedozvoljen tip fajla. Dozvoljeni su: pdf, jpg, jpeg, png.")
                    .build();
        }

        String uploadDir = "./uploads/meals/";
        try {
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            String uniqueFileName = "meal_" + mealId + "_" + java.util.UUID.randomUUID() + "." + fileExtension;
            java.nio.file.Path destinationPath = uploadPath.resolve(uniqueFileName);

            java.nio.file.Files.move(fileUpload.uploadedFile(), destinationPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            m.setImagePath(destinationPath.toAbsolutePath().toString());

            return Response.ok(m).entity("Slika uspešno sačuvana!").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greška prilikom čuvanja fajla: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{mealId}/image")
    public Response getMealImage(@PathParam("mealId") Long mealId) {
        Meal m = mealRepository.findMealById(mealId);

        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Obrok sa ID: " + mealId + " nije pronađen")
                    .build();
        }

        String imagePath = m.getImagePath();
        if (imagePath == null || imagePath.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Obrok nema sliku.")
                    .build();
        }

        java.io.File file = new java.io.File(imagePath);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Fajl nije pronađen na serveru.")
                    .build();
        }

        String contentType = "application/octet-stream";
        try {
            contentType = java.nio.file.Files.probeContentType(file.toPath());
        } catch (Exception ignored) {}

        return Response.ok(file)
                .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"")
                .type(contentType)
                .build();
    }
}
