package restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import client.HolidayResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("")
@RegisterRestClient(configKey = "country-api")
public interface HolidayClient {
    @GET
    @Path("/NextPublicHolidays/{countryCode}")
    List<HolidayResponse> getNextPublicHolidays(@PathParam("countryCode") String countryCode);
}
