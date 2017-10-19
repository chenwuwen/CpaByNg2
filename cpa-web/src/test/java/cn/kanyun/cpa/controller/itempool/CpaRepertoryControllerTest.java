package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.itempool.ICpaRepertoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext.xml")
public class CpaRepertoryControllerTest {
    @Resource
    private ICpaRepertoryService cpaRepertoryService;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addUnitExam() throws Exception {
        CpaRepertory cpaRepertory = new CpaRepertory();
        cpaRepertory.setTestStem("下列软件中，计算机系统所必备的是( )");
        cpaRepertory.setTestType("cpuAccount");
        cpaRepertory.setChoice("exclusive");
        cpaRepertory.setInsertDate(new Timestamp(System.currentTimeMillis()));
        List<CpaOption> cpaOptions = new ArrayList<>();
            CpaOption cpaOption1 = new CpaOption();
            cpaOption1.setSelectData("A");
            cpaOption1.setOptionData("操作系统");
            CpaOption cpaOption2 = new CpaOption();
            cpaOption1.setSelectData("B");
            cpaOption1.setOptionData("支持服务程序");
            CpaOption cpaOption3 = new CpaOption();
            cpaOption1.setSelectData("C");
            cpaOption1.setOptionData("应用程序");
            CpaOption cpaOption4 = new CpaOption();
            cpaOption1.setSelectData("D");
            cpaOption1.setOptionData("数据库管理系统");
        cpaOptions.add(cpaOption1);
        cpaOptions.add(cpaOption2);
        cpaOptions.add(cpaOption3);
        cpaOptions.add(cpaOption4);
        CpaSolution cpaSolution = new CpaSolution();
        cpaSolution.setResult("A");
        Integer k = cpaRepertoryService.saveUnitExam(cpaRepertory, cpaOptions, cpaSolution);
        System.out.println("主键K的值为："+k);
    }

}