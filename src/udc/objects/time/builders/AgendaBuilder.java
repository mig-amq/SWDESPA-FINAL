package udc.objects.time.builders;

import java.time.LocalDateTime;

public interface AgendaBuilder {
    void build(LocalDateTime start, LocalDateTime end);
    void build (int id, LocalDateTime start, LocalDateTime end);
}
