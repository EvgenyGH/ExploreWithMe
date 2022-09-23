package ru.practicum.ewmstats.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statistics")
public class EndpointHit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "request_id")
        private Integer id;

        //Идентификатор сервиса для которого записывается информация.
        @Column(nullable = false, length = 50)
        @NotBlank
        private String app;

        //URI для которого был осуществлен запрос.
        @Column(nullable = false, length = 200)
        @NotBlank
        private String uri;

        //IP-адрес пользователя, осуществившего запрос
        @Column(nullable = false, length = 15)
        @NotBlank
        private String ip;

        //Дата и время запроса
        @Column(nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @NotNull
        private LocalDateTime timestamp;
}
