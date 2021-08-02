package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConferenceRoom is a Querydsl query type for ConferenceRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConferenceRoom extends EntityPathBase<ConferenceRoom> {

    private static final long serialVersionUID = 530549849L;

    public static final QConferenceRoom conferenceRoom = new QConferenceRoom("conferenceRoom");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath conferenceCategory = createString("conferenceCategory");

    public final NumberPath<Integer> conferenceNo = createNumber("conferenceNo", Integer.class);

    public final StringPath conferencePassword = createString("conferencePassword");

    public final StringPath description = createString("description");

    public final BooleanPath isActive = createBoolean("isActive");

    public final NumberPath<Integer> limitUsersNum = createNumber("limitUsersNum", Integer.class);

    public final StringPath owner = createString("owner");

    public final DateTimePath<java.sql.Timestamp> startTime = createDateTime("startTime", java.sql.Timestamp.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public QConferenceRoom(String variable) {
        super(ConferenceRoom.class, forVariable(variable));
    }

    public QConferenceRoom(Path<? extends ConferenceRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConferenceRoom(PathMetadata metadata) {
        super(ConferenceRoom.class, metadata);
    }

}

