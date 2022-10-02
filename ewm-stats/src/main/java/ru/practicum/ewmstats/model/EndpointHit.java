package ru.practicum.ewmstats.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс данных с информацией об обращении к ресурсу для сохранения на сервере статистики
 * @author Evgeny S
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statistics")
public class EndpointHit {
    /**
     * Id обращения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;

    /**
     * Идентификатор сервиса для которого записывается информация.
     */
    @Column(nullable = false, length = 50)
    @NotBlank
    private String app;

    /**
     * URI по которому выполнено обращение.
     */
    @Column(nullable = false, length = 200)
    @NotBlank
    private String uri;

    /**
     * IP-адрес пользователя выполнившего обращение к ресурсу
     */
    @Column(nullable = false, length = 15)
    @NotBlank
    private String ip;

    /**
     * Дата и время обращения к ресурсу
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime timestamp;
}
