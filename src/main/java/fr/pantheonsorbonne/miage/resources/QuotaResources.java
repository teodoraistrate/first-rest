package fr.pantheonsorbonne.miage.resources;

import fr.pantheonsorbonne.miage.dao.QuotaDAO;
import fr.pantheonsorbonne.miage.dto.Quota;
import fr.pantheonsorbonne.miage.service.InsufficientQuotaException;
import fr.pantheonsorbonne.miage.service.NoSuchQuotaException;
import fr.pantheonsorbonne.miage.service.QuotaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/vendor/{vendorId}/quota")
public class QuotaResources {

    @Inject
    QuotaService quotaService;

    @Inject
    QuotaDAO dao;

    @GET
    @Path("/{concertId}")
    public Quota getQuotaForVendorAndConcert(
            @PathParam("vendorId") int vendorId,
            @PathParam("concertId") int concertId) {
        return dao.getQuota(vendorId, concertId);
    }

    @GET
    public List<Quota> getQuotaForVendor(
            @PathParam("vendorId") int vendorId) {
        return dao.getQuotas(vendorId);
    }

    @PUT
    @Path("/{concertId}")
    public void bookConcert(
            @PathParam("vendorId") int vendorId,
            @PathParam("concertId") int concertId,
            BookingRequest bookingRequest) throws NoSuchQuotaException, InsufficientQuotaException {
        quotaService.bookTickets(
                vendorId,
                concertId,
                bookingRequest.seated(),
                bookingRequest.standing());
    }

}
