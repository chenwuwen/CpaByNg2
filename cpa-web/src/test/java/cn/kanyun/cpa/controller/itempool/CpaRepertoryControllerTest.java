package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)  //必须要写，相当于提供了spring的环境
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = "classpath*:*.xml")
public class CpaRepertoryControllerTest {
    @Autowired
    private org.springframework.web.context.WebApplicationContext context;
    @Autowired
    private CpaRepertoryService cpaRepertoryService;
    //    spring3.2之后出现了org.springframework.test.web.servlet.MockMvc 类,对springMVC单元测试进行支持
    //    可以对所有的controller来进行测试
    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    //仅仅对单个Controller来进行测试
    // mockMvc = MockMvcBuilders.standaloneSetup(new MeunController()).build();
    @Before
    public void setUp() throws Exception {
        System.out.println("---------");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addUnitExam() throws Exception {
        CpaRepertory cpaRepertory = new CpaRepertory();
        cpaRepertory.setTestStem("下列软件中，计算机系统所必备的是( )");
        cpaRepertory.setTestType(ExamClassificationEnum.CPU_ACCOUNT);
        cpaRepertory.setQuestionType(QuestionTypeEnum.SINGLE);
        cpaRepertory.setInsertDate(LocalDateTime.now());
        Set<CpaOption> cpaOptions = Collections.EMPTY_SET;
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
        System.out.println("主键K的值为：" + k);
    }

}