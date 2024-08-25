package com.amanefer.crm.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseAsPage {

    private List<TaskResponseDto> tasks;
    private int pageNumber;
    private int pagesCount;
}
