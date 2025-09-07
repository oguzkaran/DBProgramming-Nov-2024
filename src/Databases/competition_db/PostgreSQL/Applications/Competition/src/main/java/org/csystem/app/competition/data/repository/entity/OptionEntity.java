package org.csystem.app.competition.data.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Setter
public class OptionEntity {
    @Accessors(prefix = "m_")
    private long m_id;

    @Accessors(prefix = "m_")
    private String m_description;

    @Accessors(prefix = "m_")
    private long m_questionId;

    @Accessors(prefix = "m_")
    private boolean m_isAnswer;
}
