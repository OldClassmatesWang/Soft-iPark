package com.yunlong.softpark.mapper;

import com.yunlong.softpark.entity.ColumnEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnMapper {
    /**
     * 根据id查询栏目实体
     * @param columnId
     * @return
     */
    ColumnEntity selectByColumnId(String columnId);

    /**
     * 根据id更新栏目的下载次数
     * @param columnId
     * @param columnEntityDownloads
     */
    void UpdateDownloads(String columnId, int columnEntityDownloads);
}
