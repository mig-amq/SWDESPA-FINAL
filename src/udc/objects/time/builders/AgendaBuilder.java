package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;

import java.time.LocalDateTime;

public interface AgendaBuilder {
    Agenda build(LocalDateTime start, LocalDateTime end);
    Agenda build (int id, LocalDateTime start, LocalDateTime end);
}
