package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatting is a Querydsl query type for Chatting
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 1450349576L;

    public static final QChat chat = new QChat("chat");

    public final NumberPath<Integer> chatNo = createNumber("chatNo", Integer.class);

    public final StringPath chatTime = createString("chatTime");

    public final StringPath chatting = createString("chatting");

    public final NumberPath<Integer> coferenceNo = createNumber("coferenceNo", Integer.class);

    public final StringPath userId = createString("userId");

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}

