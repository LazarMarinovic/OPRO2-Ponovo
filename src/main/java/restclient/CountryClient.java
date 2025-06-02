package restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import client.CountryResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("")
@RegisterRestClient(configKey = "country-api")
public interface CountryClient {
    @GET
    @Path("/AvailableCountries")
    List<CountryResponse> getAvailableCountries();
}
