package com.amanefer.crm.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskResponseAsPage {

    private List<TaskResponseDto> tasks;
    private int pageNumber;
    private int pagesCount;
}
