package cn.kanyun.cpa.controller.lucene;

import cn.kanyun.cpa.controller.LuceneComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kanyun
 * @date 2018/2/28 0028.
 */
@Controller
@RequestMapping("/api/lucene")
public class LuceneController {
    @Resource
    private LuceneComponent luceneComponent;

    @RequestMapping("/createIndex")
    public void createIndex() throws IOException {
        luceneComponent.createIndex();
    }

    @RequestMapping("/search")
    public void search(String val,HttpServletResponse response) throws IOException {
        luceneComponent.queryIndex("testStem",val);
    }
}
