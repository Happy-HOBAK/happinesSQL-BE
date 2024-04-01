package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int recordId;

    @Column(nullable = false)
    private int happiness;


    @Column(nullable = true)
    private String memo;

    @Column(nullable = true)
    private String imgUrl;

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private Location Location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private Activity activity;
    @Builder
    public Record(int recordId, int happiness, String memo, String imgUrl) {
        this.recordId = recordId;
        this.happiness = happiness;
        this.memo = memo;
        this.imgUrl = imgUrl;
    }
}
