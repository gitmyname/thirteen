package org.thirteen.datamation.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.thirteen.datamation.DatamationAppilcation;
import org.thirteen.datamation.model.po.DmColumnPO;

import javax.transaction.Transactional;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatamationAppilcation.class)
public class DmColumnRepositoryTest {

    @Autowired
    private DmTableRepository dmTableRepository;
    @Autowired
    private DmColumnRepository dmColumnRepository;

    @Test
    public void save() {
        DmColumnPO po = new DmColumnPO();
        po.setTableCode("rental_stock");
        po.setCode("test_field");
        po.setName("测试字段");
        po.setJavaType("java.lang.Integer");
        po.setDbType("INT");
        po.setLength(null);
        po.setNotNull((byte) 1);
        po.setStatus((byte) 1);
        po.setRemark(null);
        dmColumnRepository.save(po);
    }

}
