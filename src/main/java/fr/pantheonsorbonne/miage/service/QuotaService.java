package fr.pantheonsorbonne.miage.service;

import fr.pantheonsorbonne.miage.dao.QuotaDAO;
import fr.pantheonsorbonne.miage.dto.Quota;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class QuotaService {

    @Inject
    QuotaDAO quotaDAO;

    public void bookTickets(int vendorId, int concertId, int seated, int standing) throws InsufficientQuotaException, NoSuchQuotaException {

        Quota quota = quotaDAO.getQuota(vendorId, concertId);
        if (Objects.isNull(quota)) {
            throw new NoSuchQuotaException();
        }

        var remainingSeated = quota.seated() - seated;
        var remainingStanding = quota.standing() - standing;

        if (remainingSeated > 0 && remainingStanding > 0) {
            quotaDAO.saveQuota(
                    new Quota(vendorId, concertId, remainingSeated, remainingStanding)
            );
        }
        else {
            throw new InsufficientQuotaException();
        }

    }
}
