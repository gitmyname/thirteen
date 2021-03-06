package org.thirteen.authorization.repository;

import org.springframework.stereotype.Repository;
import org.thirteen.authorization.model.po.SysBizTypePO;
import org.thirteen.authorization.repository.base.BaseRepository;

/**
 * @author Aaron.Sun
 * @description 业务类型数据操作层接口
 * @date Created in 21:42 2020/2/21
 * @modified By
 */
@Repository
public interface SysBizTypeRepository extends BaseRepository<SysBizTypePO, String> {
}
