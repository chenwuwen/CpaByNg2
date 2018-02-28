package cn.kanyun.cpa.controller;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2018/2/27 0027.
 */

/**
 * 一般类调用Service使用@AutoWrite或者@Resource注入,会出现注入为null的情况,即使此类加了@Component注解
 * 解决方案：1：在类上添加@Component注解，2：静态初始化该类，3：在调用注入的Service方法上添加@PostConstruct注解(一般会建立init方法,加入@PostConstruct注解
 * 因为加了@PostConstruct注解的方法会在servlet启动时自动执行，所以通过init方法得到注入bean的实例,最后在需要调用的方法中直接使用即可)
 * 从Java EE 5规范开始，Servlet中增加了两个影响Servlet生命周期的注解（Annotion）；@PostConstruct和@PreDestroy。这两个注解被用来修饰一个非静态的void()方法
 * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。PreDestroy（）方法在destroy()方法执行执行之后执行
 */
@Component
public class LuceneComponent {

    private static final String path = "D:\\BaiduNetdiskDownload\\JAVA高级项目实战";

    @Resource(name = CpaRepertoryService.SERVICE_NAME)
    private CpaRepertoryService cpaRepertoryService;

    private static LuceneComponent luceneComponent;

    @PostConstruct
    public void init() {
        luceneComponent=this;
        luceneComponent.cpaRepertoryService = this.cpaRepertoryService;
    }

    /**
     * @describe: 创建索引
     * @params:
     * @Author: Kanyun
     * @Date: 2018/2/27 0027 13:12
     */


    public void createIndex() throws IOException {
        //1: 从数据库查询所有试题的信息

        CpaResult result = cpaRepertoryService.getScrollData();

        System.out.println("---------CpaRepertory.size()=" + result.getTotalCount());

        //2: 将原始数据放入lucene的document对象
        List<Document> docList = new ArrayList<Document>();
        List<CpaRepertory> cpaRepertories = (List<CpaRepertory>) result.getData();
        Document doc;
        if (null != cpaRepertories && cpaRepertories.size() > 0) {
            for (CpaRepertory cpaRepertory : cpaRepertories) {
                //将CpaRepertory对象里面需要建立索引的字段放入到document对象的field域里面
                doc = new Document();

                //根据不同字段的需求,创建field对象
                //域的三个属性  分词  索引  存储
                //id 不分词,不索引 存储
                Field cpaRepertoryId = new StoredField("id", cpaRepertory.getId());
                //试题题干
                //分词 索引  存储
                Field testStem = new TextField("testStem", cpaRepertory.getTestStem(), Field.Store.YES);
                //书籍价格
                //分词 索引  存储
//                Field bookPrice= new TextField("price",book.getPrice().toString(), Field.Store.YES);
                //书籍图片
                //不分词 不索引 存储(url)
//                Field bookPic= new StoredField("pic",book.getPic());
                //书籍描述
                //分词  索引  不存储
//                Field bookDesc= new TextField("description",book.getDescription(),Store.NO);

                //把组织好的field信息存储到document对象
                doc.add(cpaRepertoryId);
                doc.add(testStem);


                docList.add(doc);
            }
        }
        System.out.println("---------docList.size()=" + docList.size());

        //将document对象用分词器进行一个分词,得到索引
        Analyzer analyzer = new StandardAnalyzer();
        //创建存储索引的目录
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(path));
        //创建构造索引的配置对象
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);

        //开始构造索引
        IndexWriter writer = new IndexWriter(directory, cfg);
        if (null != docList && docList.size() > 0) {
            for (Document document : docList) {
                writer.addDocument(document);
            }
        }
        //关闭indexWriter
        writer.close();
    }

    /**
     * @describe: 查询索引
     * @params:
     * @Author: Kanyun
     * @Date: 2018/2/27 0027 13:11
     */
    public void queryIndex(String fld, String name) throws IOException {
        //1:创建一个查询对象
        Query query = new TermQuery(new Term(fld, name));

        //2:执行搜索
        //指定索引库的目录
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(path));

        //创建一个indexReader对象
        IndexReader reader = DirectoryReader.open(directory);

        //创建一个indexSearch搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);

        //执行搜索（分页）
        TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = searcher.doc(docId);

            System.out.println("==========================");
            System.out.println("试题id = " + doc.get("id"));
            System.out.println("试题题干 = " + doc.get("cpaRepertoryId"));


        }

    }

    /**
     * @describe: 删除指定的索引
     * @params:
     * @Author: Kanyun
     * @Date: 2018/2/27 0027 13:11
     */
    public void deleteTheIndex(String fld, String name) throws IOException {
        //将document对象用分词器进行一个分词,得到索引
        Analyzer analyzer = new StandardAnalyzer();
        //创建存储索引的目录
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(path));
        //创建构造索引的配置对象
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);

        //开始构造索引
        IndexWriter writer = new IndexWriter(directory, cfg);
        writer.deleteDocuments(new Term(fld, name));
        writer.close();
    }
}
