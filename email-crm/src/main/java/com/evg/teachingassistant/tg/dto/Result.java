package com.evg.teachingassistant.tg.dto;

import lombok.Data;

@Data
public class Result {
    private String file_id;
    private String file_unique_id;
    private String file_size;
    private String file_path;
}
