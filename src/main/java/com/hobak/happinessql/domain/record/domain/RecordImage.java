package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "record_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_image_id")
    private int recordImageId;

    @Column(nullable = true)
    private String url;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public RecordImage(int recordImageId, String url, Record record) {
        this.recordImageId = recordImageId;
        this.url = url;
        this.record = record;
    }
}
