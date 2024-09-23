package fr.pantheonsorbonne.miage.dao;

import fr.pantheonsorbonne.miage.dto.Quota;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class QuotaDAO {
    Set<Quota> quotas = new HashSet<>();

    private void onStartup(@Observes StartupEvent event) {
        quotas.add(new Quota(1,1,20,20));
        quotas.add(new Quota(1,2,30,30));
        quotas.add(new Quota(2,2,30,30));
    }

    public List<Quota> getQuotas(int vendorId) {
        return quotas.stream().filter(q-> q.vendorId() == vendorId)
                .toList();
    }

    public Quota getQuota(int vendorId, int concertId) {
        return quotas.stream()
                .filter(q-> q.vendorId() == vendorId
                        && q.concertId() == concertId)
                .findFirst().orElse(null);
    }

    public void saveQuota(Quota quota) {
        Optional<Quota> existingQuotas =
                quotas.stream()
                        .filter(q-> q.vendorId() == quota.vendorId())
                        .filter(q-> q.concertId() == quota.concertId())
                        .findAny();
        if (existingQuotas.isPresent()) {
            quotas.remove(existingQuotas.get());
        }
        quotas.add(quota);
    }
}
